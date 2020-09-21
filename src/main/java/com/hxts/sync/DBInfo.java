package com.hxts.sync;

import java.io.Serializable;

/**
 * @author zhaonan
 * @date 2020/9/10 10:10
 * @description 数据库信息
 * @version 1.0.0
 */

public class DBInfo implements Serializable {
	private static final long serialVersionUID = 3095305945250814263L;
	// 数据库连接
	private String url;
	// 数据库用户名
	private String username;
	// 数据库密码
	private String password;
	// 数据库类型(对应mysql还是sqlserver)
	private String dbtype;
	// 数据库驱动
	private String driver;

	final public String getUrl() {
		return url;
	}

	final public void setUrl(String url) {
		this.url = url;
	}

	final public String getUsername() {
		return username;
	}

	final public void setUsername(String username) {
		this.username = username;
	}

	final public String getPassword() {
		return password;
	}

	final public void setPassword(String password) {
		this.password = password;
	}

	final public String getDbtype() {
		return dbtype;
	}

	final public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	final public String getDriver() {
		return driver;
	}

	final public void setDriver(String driver) {
		this.driver = driver;
	}
}
