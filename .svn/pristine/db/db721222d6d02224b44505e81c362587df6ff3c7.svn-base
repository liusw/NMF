package com.boyaa.service.hbase.parser;

import net.sf.jsqlparser.parser.CCJSqlParserManager;

public class SqlParserManager {
	private static CCJSqlParserManager parserManager = null;

	private SqlParserManager() {
	}

	/**
	 * ： 描 述:获取实例对象.
	 * 
	 * @return
	 */
	public static CCJSqlParserManager getInstance() {
		if (parserManager == null) {
			parserManager = new CCJSqlParserManager();
		}
		return parserManager;
	}
}
