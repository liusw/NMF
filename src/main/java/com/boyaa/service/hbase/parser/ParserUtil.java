package com.boyaa.service.hbase.parser;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

import org.apache.log4j.Logger;

public class ParserUtil {
	static Logger logger = Logger.getLogger(ParserUtil.class);
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	/**
	 * 获取查询语句中所查询表的List
	 * 
	 * @param select
	 * @return
	 */
	public static List<String> getTable(Select select) {
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		return tablesNamesFinder.getTableList(select);
	}
	
	/**
	 * 获取PlainSelect对象
	 * @param select
	 * @return
	 */
	public static PlainSelect getPlainSelect(Select select){
		PlainSelect plain = (PlainSelect) select.getSelectBody();
		return plain;
	}

	/**
	 * 获取查询列的SelectItem列表
	 * 
	 * @param select
	 * @return
	 */
	public static List<SelectItem> getSelectItems(Select select) {
		return getPlainSelect(select).getSelectItems();
	}

	/**
	 * 获取查询列以,分开的字符串
	 * 
	 * @param select
	 * @return
	 */
	public static String getColumnString(Select select) {
		StringBuffer columns = new StringBuffer();
		List<SelectItem> selectitems = getSelectItems(select);
		for (int i = 0; i < selectitems.size(); i++) {
			columns.append(i == 0 ? "" : ",").append(selectitems.get(i));
		}
		return columns.toString();
	}

	/**
	 * 获取查询列的字符串列表
	 * 
	 * @param select
	 * @return
	 */
	public static List<String> getColumnList(Select select) {
		List<String> columns = new ArrayList<String>();
		List<SelectItem> selectitems = getSelectItems(select);

		for (int i = 0; i < selectitems.size(); i++) {
			columns.add(selectitems.get(i).toString());
		}
		return columns;
	}
	
	public static String getStringValue(String value) {
		if (value.startsWith("'") && value.endsWith("'")) {
			return value.substring(1, value.length() - 1);
		}
		if (value.startsWith("\"") && value.endsWith("\"")) {
			return value.substring(1, value.length() - 1);
		}
		return value;
	}
}
