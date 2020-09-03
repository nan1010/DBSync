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
package io.mykit.db.sync.entity;

import java.io.Serializable;

/**
 * @author liuyazhuang
 * @date 2018/9/11 10:13
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
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the srcTable
	 */
	public String getSrcTable() {
		return srcTable;
	}
	/**
	 * @param srcTable the srcTable to set
	 */
	public void setSrcTable(String srcTable) {
		this.srcTable = srcTable;
	}
	/**
	 * @return the srcSql
	 */
	public String getSrcSql() {
		return srcSql;
	}
	/**
	 * @param srcSql the srcSql to set
	 */
	public void setSrcSql(String srcSql) {
		this.srcSql = srcSql;
	}
	/**
	 * @return the srcTableFields
	 */
	public String getSrcTableFields() {
		return srcTableFields;
	}
	/**
	 * @param srcTableFields the srcTableFields to set
	 */
	public void setSrcTableFields(String srcTableFields) {
		this.srcTableFields = srcTableFields;
	}
	/**
	 * @return the destTable
	 */
	public String getDestTable() {
		return destTable;
	}
	/**
	 * @param destTable the destTable to set
	 */
	public void setDestTable(String destTable) {
		this.destTable = destTable;
	}
	/**
	 * @return the destTableFields
	 */
	public String getDestTableFields() {
		return destTableFields;
	}
	/**
	 * @param destTableFields the destTableFields to set
	 */
	public void setDestTableFields(String destTableFields) {
		this.destTableFields = destTableFields;
	}
	/**
	 * @return the destTableKey
	 */
	public String getDestTableKey() {
		return destTableKey;
	}
	/**
	 * @param destTableKey the destTableKey to set
	 */
	public void setDestTableKey(String destTableKey) {
		this.destTableKey = destTableKey;
	}
	/**
	 * @return the destTableUpdate
	 */
	public String getDestTableUpdate() {
		return destTableUpdate;
	}
	/**
	 * @param destTableUpdate the destTableUpdate to set
	 */
	public void setDestTableUpdate(String destTableUpdate) {
		this.destTableUpdate = destTableUpdate;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
