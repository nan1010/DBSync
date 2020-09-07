package com.hxts.sync.build;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hxts.sync.DBInfo;
import com.hxts.sync.JobInfo;

/**
 * @author zhaonan
 * @date 2020/9/7
 * @description 数据库同步任务类
 * @version 1.0.0
 */
public class Task {

	private DBInfo srcDb;
	private DBInfo destDb;
	private List<JobInfo> jobList;
	private String code;
	private static Logger logger = Logger.getLogger(Task.class);

	private Task() {
	}

	/**
	 * 创建DBSyncBuilder对象
	 * 
	 * @return DBSyncBuilder对象
	 */
	public static Task builder() {
		return new Task();
	}

	/**
	 * 初始化数据库信息并解析jobs.xml填充数据
	 * 
	 * @return DBSyncBuilder对象
	 */
	public Task init() {
		srcDb = new DBInfo();
		destDb = new DBInfo();
		jobList = new ArrayList<JobInfo>();
		SAXReader reader = new SAXReader();
		try {
			// 读取xml的配置文件名，并获取其里面的节点
			Element root = reader.read("jobs.xml").getRootElement();
			Element src = root.element("source");
			Element dest = root.element("dest");
			Element jobs = root.element("jobs");
			// 遍历job即同步的表
			for (@SuppressWarnings("rawtypes")
			Iterator it = jobs.elementIterator("job"); it.hasNext();) {
				jobList.add((JobInfo) elementInObject((Element) it.next(), new JobInfo()));
			}
			//
			elementInObject(src, srcDb);
			elementInObject(dest, destDb);
			code = root.element("code").getTextTrim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 解析e中的元素，将数据填充到o中
	 * 
	 * @param e 解析的XML Element对象
	 * @param o 存放解析后的XML Element对象
	 * @return 存放有解析后数据的Object
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object elementInObject(Element e, Object o) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = o.getClass().getDeclaredFields();
		for (int index = 0; index < fields.length; index++) {
			Field item = fields[index];
			// 当前字段不是serialVersionUID，同时当前字段不包含serialVersionUID
			if (!"serialVersionUID".equals(item.getName()) && !item.getName().contains("serialVersionUID")) {
				item.setAccessible(true);
				item.set(o, e.element(item.getName()).getTextTrim());
			}
		}
		return o;
	}

	/**
	 * 启动定时任务，同步数据库的数据，jobInfo作为参数传递
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		for (int i = 0; i < jobList.size(); i++) {
			JobInfo jobInfo = jobList.get(i);
			new Thread(new JobThread(jobInfo)).start();
		}
	}
	
	//线程任务类
	public class JobThread implements Runnable {
		private JobInfo jobInfo;

		JobThread(JobInfo jobInfo) {
			this.jobInfo = jobInfo;
		}

		@Override
		public void run() {
			while (true) {
				logger.info("正在执行第" + jobInfo.getName() + "个任务");
				String logTitle = "[" + code + "]" + jobInfo.getName() + " ";
				logger.info("开始任务执行: ");
				Connection inConn = null;
				Connection outConn = null;
				try {
					inConn = createConnection(srcDb);
					outConn = createConnection(destDb);
					if (inConn == null) {
						logger.error("请检查源数据连接!");
						return;
					} else if (outConn == null) {
						logger.error("请检查目标数据连接!");
						return;
					}
					long start = System.currentTimeMillis();
					assembleAndExcuteSQLAndDelete(inConn, outConn, jobInfo);
					logger.info("执行耗时: " + (System.currentTimeMillis() - start) + "ms");
				} catch (SQLException e) {
					logger.error(logTitle + e.getMessage());
					logger.error(logTitle + " SQL执行出错，请检查是否存在语法错误");
					throw new RuntimeException(logTitle + e.getMessage());
				} finally {
					logger.info("关闭源数据库连接");
					destoryConnection(inConn);
					logger.info("关闭目标数据库连接");
					destoryConnection(outConn);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

		}

	}

	/**
	 * 创建数据库连接
	 * 
	 * @param db
	 * @return
	 */
	private Connection createConnection(DBInfo db) {
		try {
			Class.forName(db.getDriver());
			Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
			conn.setAutoCommit(false);
			return conn;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 关闭并销毁数据库连接
	 * 
	 * @param conn
	 */
	private void destoryConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 拼凑SQL语句，执行同步后删除源数据
	public String assembleAndExcuteSQLAndDelete(Connection inConn, Connection outConn, JobInfo jobInfo)
			throws SQLException {
		String uniqueName = generateString(6) + "_" + jobInfo.getName();
		String[] destFields = jobInfo.getDestTableFields().split(",");
		destFields = this.trimArrayItem(destFields);
		String[] srcFields = destFields;
		String srcField = jobInfo.getSrcTableFields();
		if (!isEmpty(srcField)) {
			srcFields = this.trimArrayItem(srcField.split(","));
		}
		String[] updateFields = jobInfo.getDestTableUpdate().split(",");
		updateFields = this.trimArrayItem(updateFields);
		String destTable = jobInfo.getDestTable();
		String destTableKey = jobInfo.getDestTableKey();

		PreparedStatement pst = inConn.prepareStatement(jobInfo.getSrcSql());
		ResultSet rs = pst.executeQuery();
		StringBuilder sql = new StringBuilder();
		StringBuffer sqlDel = new StringBuffer();
		StringBuilder delIds = new StringBuilder();

		if (!rs.next())
			return null;
		long count = 0;
		if ("insert".equals(rs.getString("action")) || "update".equals(rs.getString("action"))) {
			sql.append("insert into ").append(destTable).append(" (").append(jobInfo.getDestTableFields())
					.append(") values ");

			sql.append(rs.getString("action_sql")).append(",");
			delIds.append(rs.getObject("ids")).append(",");
			count++;
			if (count > 0) {
				sql = sql.deleteCharAt(sql.length() - 1);
				if ((!isEmpty(jobInfo.getDestTableUpdate())) && (!isEmpty(jobInfo.getDestTableKey()))) {
					sql.append(" on duplicate key update ");
					for (int index = 0; index < updateFields.length; index++) {
						sql.append(updateFields[index]).append("= values(").append(updateFields[index])
								.append(index == (updateFields.length - 1) ? ")" : "),");
					}
					String newSql = new StringBuffer("alter table ").append(destTable).append(" add constraint ")
							.append(uniqueName).append(" unique (").append(destTableKey).append(");")
							.append(sql.toString()).append(";alter table ").append(destTable).append(" drop index ")
							.append(uniqueName).toString();

					// 执行SQL语句，删除源数据
					PreparedStatement pst1 = outConn.prepareStatement("");
					Statement statement = inConn.createStatement();
					String[] sqlList = newSql.split(";");
					for (int index = 0; index < sqlList.length; index++) {
						pst1.addBatch(sqlList[index]);
					}
					pst1.executeBatch();
					logger.info("目标数据表插入：" + sqlList[1]);
					delIds.deleteCharAt(delIds.length() - 1);
					statement.executeUpdate("delete from " + jobInfo.getSrcTable() + " where " + srcFields[0] + " in "
							+ "(" + delIds.toString() + ")");
					logger.info("删除源数据表数据： delete from " + jobInfo.getSrcTable() + " where " + srcFields[0]
							+ " in " + " (" + delIds.toString() + ")");

					statement.close();
					outConn.commit();
					inConn.commit();
					pst1.close();

					if (rs.next()) {
						if (!"insert".equals(rs.getString("action"))) {
							pst.close();
							rs.close();
							return newSql;
						}

					} else {
						pst.close();
						rs.close();
						return newSql;
					}
				}
			}
			logger.debug(sql.toString());
		} else if ("delete".equals(rs.getString("action"))) {
			sqlDel.append("delete from ").append(jobInfo.getDestTable()).append(" where ")
					.append(jobInfo.getDestTableKey()).append(" in (").append(rs.getString("action_sql")).append(")");
			StringBuffer sqlSrcDel = new StringBuffer();
			sqlSrcDel.append("delete from ").append(jobInfo.getSrcTable()).append(" where ").append(srcFields[0])
					.append(" in (").append(rs.getString("ids")).append(")");
			Statement statementIn = inConn.createStatement();
			Statement statementOut = outConn.createStatement();
			// 删除之前要判断是否为空
			statementOut.executeUpdate(sqlDel.toString());
			logger.info("删除目标数据库数据： " + sqlDel.toString());
			statementIn.executeUpdate(sqlSrcDel.toString());
			logger.info("删除源数据库数据： " + sqlSrcDel.toString());
			statementOut.close();
			if (rs.next()) {
				if (!"delete".equals(rs.getNString("action"))) {
					pst.close();
					rs.close();
					outConn.commit();
					inConn.commit();
					return sqlDel.toString();
				}
			} else {
				pst.close();
				rs.close();
				outConn.commit();
				inConn.commit();
				return sqlDel.toString();
			}
		}
		return destTableKey;
	}

	/**
	 * 去除String数组每个元素中的空格
	 * 
	 * @param src 需要去除空格的数组
	 * @return 去除空格后的数组
	 */
	private String[] trimArrayItem(String[] src) {
		if (src == null || src.length == 0)
			return src;
		String[] dest = new String[src.length];
		for (int i = 0; i < src.length; i++) {
			dest[i] = src[i].trim();
		}
		return dest;
	}

	/**
	 * 产生随机字符串
	 * 
	 * @param length 字符串的长度
	 * @return 随机的字符串
	 */
	public static String generateString(int length) {
		if (length < 1)
			length = 6;
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String genStr = "";
		for (int index = 0; index < length; index++) {
			genStr = genStr + str.charAt((int) ((Math.random() * 100) % 26));
		}
		return genStr;
	}

	/**
	 * @description 字符串工具类
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
}
