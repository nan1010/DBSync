/**
 * Copyright 2018-2118 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mykit.db.sync.sync.impl;

import io.mykit.db.sync.entity.JobInfo;
import io.mykit.db.sync.sync.DBSync;
import io.mykit.db.sync.utils.Tool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyazhuang
 * @date 2018/9/11 10:21
 * @description MySQL数据库同步实现
 * @version 1.0.0
 */
public class MySQLSync extends AbstractDBSync implements DBSync {
    private Logger logger = Logger.getLogger(MySQLSync.class);

    @Override
    public String assembleSQL(String srcSql, Connection conn, JobInfo jobInfo) throws SQLException {
        String uniqueName = Tool.generateString(6) + "_" + jobInfo.getName();
        String[] fields = jobInfo.getDestTableFields().split(",");
        fields = this.trimArrayItem(fields);
        String[] updateFields = jobInfo.getDestTableUpdate().split(",");
        updateFields = this.trimArrayItem(updateFields);
        String destTable = jobInfo.getDestTable();
        String destTableKey = jobInfo.getDestTableKey();
        PreparedStatement pst = conn.prepareStatement(srcSql);
        ResultSet rs = pst.executeQuery();
        StringBuffer sql = new StringBuffer();
        
        StringBuffer sqlDel = new StringBuffer();
        
        int count = 0;
        //List<String> idsList = new ArrayList<String>();
        while(rs.next()) {
        	if ("insert".equals(rs.getString("action"))) {
        		sql.append("insert into ").append(jobInfo.getDestTable()).append(" (").append(jobInfo.getDestTableFields()).append(") values ");
        		sql.append(rs.getString("action_sql")).append(";");   
        	} else if ("delete".equals(rs.getString("action"))) {
        		//sqlDel.append("delete from ").append(jobInfo.getDestTable()).append(" where ").append(rs.getString("action_sql")).append(";");
        		sqlDel.append("delete from ").append(jobInfo.getDestTable()).append(" where ").append(jobInfo.getDestTableKey()).append(" in (").append(rs.getString("action_sql")).append(");");
        	}
        	
           // sql.append("(");
            //for (int index = 0; index < fields.length; index++) {
            //    sql.append("'").append(rs.getString(fields[index])).append(index == (fields.length - 1) ? "'" : "',");
            //}
            //sql.append("),");
        	//idsList.add(rs.getString("ids"));   
			/*
			 * if (count >=2) { break; }
			 */
        }

        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (sql.length() > 0) {
        	//sql存在读取数据读不完的情况
            sql = sql.deleteCharAt(sql.length() - 1);     	
            if ((!jobInfo.getDestTableUpdate().equals("")) && (!jobInfo.getDestTableKey().equals(""))) {           
				  sql.append(" on duplicate key update ");
				  for (int index = 0; index < updateFields.length; index++) {
				  sql.append(updateFields[index]).append("= values(").append(updateFields[index
				  ]).append(index == (updateFields.length - 1) ? ")" : "),"); }
				  sql.append(';');
				   
                //return sql.toString(); //new StringBuffer("alter table ").append(destTable).append(" add constraint ").append(uniqueName).append(" unique (").append(destTableKey).append(");").append(sql.toString())
                        //.apend(";alter table ").append(destTable).append(" drop index ").append(uniqueName).toString();
            }
            logger.debug(sql.toString());            
            //return sql.toString();
        }
        if (sqlDel.length() > 0) {
        	sql.append(sqlDel.toString());
        }
        if (sql.length() == 0) {
        	return null;
        }
        
        /* String ids = String.join(",", idsList);
         Statement  st=conn.createStatement();
        //pst = conn.prepareStatement("delete from sync_police_config where p_id in (" + ids + ")");
        int i=st.executeUpdate("delete from sync_police_config where p_id in (" + ids + ")");
        st.close();
        //pst.executeUpdate();
        //pst.close();
        System.out.println(sql.toString());
        System.out.println(ids+"--------");*/
        return sql.toString();
        
    }

    @Override
    public void executeSQL(String sql, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("");
        String[] sqlList = sql.split(";");
        for (int index = 0; index < sqlList.length; index++) {
            pst.addBatch(sqlList[index]);
        }
        pst.executeBatch();
        conn.commit();
        pst.close();
    }


}
