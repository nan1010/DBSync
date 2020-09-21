package com.hxts.sync;

import java.io.Serializable;

/**
 * @author zhaonan
 * @date 2020/9/10 10:10
 * @description 任务信息
 * @version 1.0.0
 */
public class JobInfo implements Serializable {
	private static final long serialVersionUID = -1907092113028096170L;

	// 任务名称
	private String name;
	// 任务表达式
	private String srcTable;
	// 源数据源sql
	private String srcSql;
	// 源表数据字段
	private String srcTableFields;
	// 目标数据表
	private String destTable;
	// 目标表数据字段
	private String destTableFields;
	// 目标表主键
	private String destTableKey;
	// 目标表可更新的字段
	private String destTableUpdate;

	/**
	 * @return the name
	 */
	final public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	final public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the srcTable
	 */
	final public String getSrcTable() {
		return srcTable;
	}

	/**
	 * @param srcTable the srcTable to set
	 */
	final public void setSrcTable(String srcTable) {
		this.srcTable = srcTable;
	}

	/**
	 * @return the srcSql
	 */
	final public String getSrcSql() {
		return srcSql;
	}

	/**
	 * @param srcSql the srcSql to set
	 */
	final public void setSrcSql(String srcSql) {
		this.srcSql = srcSql;
	}

	/**
	 * @return the srcTableFields
	 */
	final public String getSrcTableFields() {
		return srcTableFields;
	}

	/**
	 * @param srcTableFields the srcTableFields to set
	 */
	final public void setSrcTableFields(String srcTableFields) {
		this.srcTableFields = srcTableFields;
	}

	/**
	 * @return the destTable
	 */
	final public String getDestTable() {
		return destTable;
	}

	/**
	 * @param destTable the destTable to set
	 */
	final public void setDestTable(String destTable) {
		this.destTable = destTable;
	}

	/**
	 * @return the destTableFields
	 */
	final public String getDestTableFields() {
		return destTableFields;
	}

	/**
	 * @param destTableFields the destTableFields to set
	 */
	final public void setDestTableFields(String destTableFields) {
		this.destTableFields = destTableFields;
	}

	/**
	 * @return the destTableKey
	 */
	final public String getDestTableKey() {
		return destTableKey;
	}

	/**
	 * @param destTableKey the destTableKey to set
	 */
	final public void setDestTableKey(String destTableKey) {
		this.destTableKey = destTableKey;
	}

	/**
	 * @return the destTableUpdate
	 */
	final public String getDestTableUpdate() {
		return destTableUpdate;
	}

	/**
	 * @param destTableUpdate the destTableUpdate to set
	 */
	final public void setDestTableUpdate(String destTableUpdate) {
		this.destTableUpdate = destTableUpdate;
	}

	/**
	 * @return the serialversionuid
	 */
	final public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
