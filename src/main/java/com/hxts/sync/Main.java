package com.hxts.sync;

import org.apache.log4j.Logger;

import com.hxts.sync.build.Task;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author zhaonan
 * @date 2020/9/10 10:10
 * @description 程序入口
 * @version 1.0.0
 */
public class Main {
	private static Logger logger = Logger.getLogger(Task.class);
	public static void main(String[] args) {
		logger.info("同步数据开始===>>>" + DateFormat.getDateInstance(0).format(new Date()).toString());
		try {
			Task.builder().init().start();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		logger.info("同步数据结束===>>>" + DateFormat.getDateInstance(0).format(new Date()).toString());
	}
}
