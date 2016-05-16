package com.boyaa.mf.util;
/**
 * 用ThreadLocal存放分页数据
 * @author huangyineng
 *
 */
public class SystemContext {
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	private static ThreadLocal<Integer> pageIndex = new ThreadLocal<Integer>();
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	
	public static void setPageSize(int _pageSize) {
		pageSize.set(_pageSize);
	}
	
	public static int getPageSize() {
		return pageSize.get();
	}
	
	public static void removePageSize() {
		pageSize.remove();
	}
	
	public static void setPageIndex(int _pageIndex) {
		pageIndex.set(_pageIndex);
	}
	
	public static int getPageIndex() {
		return pageIndex.get();
	}
	
	public static void removePageIndex() {
		pageIndex.remove();
	}
	
	public static String getSort() {
		return sort.get();
	}
	
	public static void removeSort() {
		sort.remove();
	}
	
	public static void setSort(String _sort) {
		sort.set(_sort);
	}
	
	public static String getOrder() {
		return order.get();
	}
	
	public static void removeOrder() {
		order.remove();
	}
	
	public static void setOrder(String _order) {
		order.set(_order);
	}
}
