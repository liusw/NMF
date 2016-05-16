package com.boyaa.service.hbase.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.WithinGroupExpression;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.boyaa.base.utils.Constants;
import com.boyaa.base.utils.JSONUtil;
import com.boyaa.mf.util.ColumnUtil;

public class SelectSqlVisitor implements SelectVisitor,FromItemVisitor,ExpressionVisitor{
	static Logger taskLogger = Logger.getLogger("taskLogger");
	static Logger errorLogger = Logger.getLogger("errorLogger");
	static Logger fatalLogger = Logger.getLogger("fatalLogger");
	
	private Scan scan = new Scan();
	private String table;
	//目前只支持and操作
	private FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
	//返回结果集要过滤的项
	private JSONObject resultFilterJson=JSONUtil.getJSONObject();
	//组成rowkey的参数及值,由于现在的rowkey最多是由_plat,_uid,_tm这三个组成,所以直接获取这三个的值
	private JSONObject rowkeyJson= JSONUtil.getJSONObject();
	private String rowKey;
	
	//所选择的列
	private List<String> returnColumns = new ArrayList<String>();
	private List<String> tables = new ArrayList<String>();
	private Map<String, String[]> columnTypeMapping;
	
	//limit
	private Long rowCount = null;
	private Long offset = null;
	private List<String> rowkeys = null;
	
	/**
	 * 构造函数
	 * @param select
	 */
	public SelectSqlVisitor(Select select) {
		select.getSelectBody().accept(this);
	}
	
	public JSONObject getResultFilterJson() {
		return resultFilterJson;
	}

	public JSONObject getRowkeyJson() {
		return rowkeyJson;
	}

	public FilterList getFilterList() {
		return filterList;
	}

	public Scan getScan() {
		return scan;
	}

	public String getTable() {
		if(tables!=null && tables.size()>0){
			table = tables.get(0);
			columnTypeMapping = ColumnUtil.getColumnTypeMapping(table);
		}
		return table;
	}
	
	public List<String> getReturnColumns() {
		return returnColumns;
	}
	
	public Long getRowCount() {
		return rowCount;
	}

	public Long getOffset() {
		return offset;
	}
	
	public String getRowKey() {
		return rowKey;
	}

	/**
	 * 第一个执行,初奴化一些参数
	 */
	@Override
	public void visit(PlainSelect plainSelect) {
		List<SelectItem> selectItems = plainSelect.getSelectItems();
		for (SelectItem item : selectItems) {
			if (item.toString().equals("*")) {
				break;
			}
			returnColumns.add(item.toString());
			//设置要查询的列, 没有处理字段别名等等情况
			this.scan.addColumn(Constants.COLUMN_FAMILY_BYTE,Bytes.toBytes(item.toString()));
		}

		plainSelect.getFromItem().accept(this);
		//获取rowkey
		rowkeys = ColumnUtil.getRowKeyRule(table);
		
		if (plainSelect.getWhere() != null) {
			plainSelect.getWhere().accept(this);
		}
		
		initLimit(plainSelect);
	}
	
	/**
	 * 初始化limit
	 * @param plainSelect
	 */
	private void initLimit(PlainSelect plainSelect) {
		Limit limit = plainSelect.getLimit();
		if (limit == null) {
			return;
		}
		this.rowCount = limit.getRowCount();
		this.offset = limit.getOffset();
	}
	
	/**
	 * 设置所查询的表名
	 */
	@Override
	public void visit(Table table) {
		if(table!=null){
			this.tables.add(table.getName());
			getTable();
		}
	}
	
	/**
	 * 接受where语句中等于这种情况
	 */
	@Override
	public void visit(AndExpression and) {
		and.getLeftExpression().accept(this);
      and.getRightExpression().accept(this);
	}
	
	/**
	 * 处理where语句中等于这种条件,并加入过滤器中
	 * 注意类型的转换
	 */
	@Override
	public void visit(EqualsTo equalsTo) {
		String column = equalsTo.getLeftExpression().toString();
		String value = ParserUtil.getStringValue(equalsTo.getRightExpression().toString());
		
		// 获取rowkey相关参数
		try {
			if(column.equals(Constants.COLUMN_NAME_ROWKEY)){//如果查询中有rowkey
				rowKey=value;
			}
			
			if(!column.equals(Constants.COLUMN_NAME_TM) && rowkeys.contains(column)){
				rowkeyJson.put(column,value);
			}
			if(column.equals(Constants.COLUMN_NAME_TM)){
				long startTime = ColumnUtil.getTmValue(value,0);
				long endTime = ColumnUtil.getTmValue(value,1);
				rowkeyJson.put(Constants.COLUMN_NAME_STARTTIME,startTime);
				rowkeyJson.put(Constants.COLUMN_NAME_ENDTIME,endTime);
				rowkeyJson.put(Constants.COLUMN_NAME_TM,value);
				
				SingleColumnValueFilter sfilter = new SingleColumnValueFilter(
						Constants.COLUMN_FAMILY_BYTE, Bytes.toBytes(column),
						CompareOp.GREATER_OR_EQUAL, ColumnUtil.convertType(column, Long.toString(startTime), columnTypeMapping));
				SingleColumnValueFilter efilter = new SingleColumnValueFilter(
						Constants.COLUMN_FAMILY_BYTE, Bytes.toBytes(column),
						CompareOp.LESS_OR_EQUAL, ColumnUtil.convertType(column, Long.toString(endTime), columnTypeMapping));
				
				this.filterList.addFilter(sfilter);
				this.filterList.addFilter(efilter);
			}else{
				SingleColumnValueFilter filter = new SingleColumnValueFilter(
						Constants.COLUMN_FAMILY_BYTE, Bytes.toBytes(column),
						CompareOp.EQUAL, ColumnUtil.convertType(column,value, columnTypeMapping));
				
				this.filterList.addFilter(filter);
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		String column = notEqualsTo.getLeftExpression().toString();
		String value = notEqualsTo.getRightExpression().toString();
		
		SingleColumnValueFilter filter = new SingleColumnValueFilter(
				Constants.COLUMN_FAMILY_BYTE, Bytes.toBytes(column),
				CompareOp.NOT_EQUAL, ColumnUtil.convertType(column, value, columnTypeMapping));
		
		this.filterList.addFilter(filter);
	}
	
	/**
	 * 大于要判断是否为数字,如果是数字,但数据库是字符串,则不能使用过滤器
	 */
	@Override
	public void visit(GreaterThan greaterThan) {
		String column = greaterThan.getLeftExpression().toString();
		String value = greaterThan.getRightExpression().toString();
		
		try {
			if(column.equals(Constants.COLUMN_NAME_TM)){
				rowkeyJson.put(Constants.COLUMN_NAME_STARTTIME, ColumnUtil.getTmValue(value,1));
			}else{
				//判断比较的字段是不是在数据库中的设置是不是整型,如果是就加入到过滤器,如果不是就设置为结果集过滤
				if(ColumnUtil.isNumberType(columnTypeMapping, column)){
					greatherAndEqualsFilter(column,value,CompareOp.GREATER);
				}else{
					if(returnColumns!=null && returnColumns.size()>0 && !returnColumns.contains(column)){
						returnColumns.add(column);
					}
					
					resultFilterJson.put(column, value);
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		String column = greaterThanEquals.getLeftExpression().toString();
		String value = greaterThanEquals.getRightExpression().toString();
		
		try {
			if(column.equals(Constants.COLUMN_NAME_TM)){
				rowkeyJson.put(Constants.COLUMN_NAME_STARTTIME, ColumnUtil.getTmValue(value,0));
			}else{
				if(ColumnUtil.isNumberType(columnTypeMapping, column)){
					greatherAndEqualsFilter(column,value,CompareOp.GREATER_OR_EQUAL);
				}else{
					if(returnColumns!=null && returnColumns.size()>0 && !returnColumns.contains(column)){
						returnColumns.add(column);
					}
					resultFilterJson.put(column, value);
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	@Override
	public void visit(MinorThan minorThan) {
		String column = minorThan.getLeftExpression().toString();
		String value = minorThan.getRightExpression().toString();
		
		try {
			if(column.equals(Constants.COLUMN_NAME_TM)){
				rowkeyJson.put(Constants.COLUMN_NAME_ENDTIME, ColumnUtil.getTmValue(value,0));
			}else{
				if(ColumnUtil.isNumberType(columnTypeMapping, column)){
					minorAndEqualsFilter(column,value,CompareOp.LESS);
				}else{
					if(returnColumns!=null && returnColumns.size()>0 && !returnColumns.contains(column)){
						returnColumns.add(column);
					}
					resultFilterJson.put(column, value);
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		String column = minorThanEquals.getLeftExpression().toString();
		String value = minorThanEquals.getRightExpression().toString();
		
		try {
			if(column.equals(Constants.COLUMN_NAME_TM)){
				rowkeyJson.put(Constants.COLUMN_NAME_ENDTIME, ColumnUtil.getTmValue(value,1));
			}else{
				if(ColumnUtil.isNumberType(columnTypeMapping, column)){
					minorAndEqualsFilter(column,value,CompareOp.LESS_OR_EQUAL);
				}else{
					if(returnColumns!=null && returnColumns.size()>0 && !returnColumns.contains(column)){
						returnColumns.add(column);
					}
					resultFilterJson.put(column, value);
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
	}
	
	/**
	 * 设置大于或大于等于某个正数的filter
	 * @param column
	 * @param value
	 * @param compareOp
	 */
	private void greatherAndEqualsFilter(String column,String value,CompareOp compareOp){
		FilterList	filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		
		// 大于某个正数
		SingleColumnValueFilter and_greater_equal_positive = new SingleColumnValueFilter(
				Constants.COLUMN_FAMILY_BYTE,Bytes.toBytes(column),compareOp, ColumnUtil.convertType(column, value, columnTypeMapping));
		and_greater_equal_positive.setFilterIfMissing(true);
		filters.addFilter(and_greater_equal_positive);
		
		// 获取某种类型的最大值
		byte[] maxValue = ColumnUtil.getMaxValue(column, columnTypeMapping);
		// 小于最大正数
		SingleColumnValueFilter or_less_positive = new SingleColumnValueFilter(
				Constants.COLUMN_FAMILY_BYTE,Bytes.toBytes(column),CompareOp.LESS, maxValue);
		or_less_positive.setFilterIfMissing(true);
		filters.addFilter(or_less_positive);

		this.filterList.addFilter(filters);
	}
	
	/**
	 * 小于或小于等于某个整数的处理情况
	 * @param column
	 * @param value
	 * @param compareOp
	 */
	private void minorAndEqualsFilter(String column,String value,CompareOp compareOp){
		// 小于某个正数/所有小数中的任意一个都可获取
		FilterList	filters = new FilterList(FilterList.Operator.MUST_PASS_ONE);
		
		// 当小于0时，不需要添加小于条件，只要把所有负数都查出来即可,这里不做处理
		if(Integer.parseInt(value)!=0 || !compareOp.equals(CompareOp.LESS)){
			SingleColumnValueFilter or_less_equal_positive = new SingleColumnValueFilter(
					Constants.COLUMN_FAMILY_BYTE,Bytes.toBytes(column),compareOp,ColumnUtil.convertType(column, value, columnTypeMapping));
			or_less_equal_positive.setFilterIfMissing(true);
			filters.addFilter(or_less_equal_positive);
		}
		
		// 获取某种类型(整数)的最小值
		byte[] minValue = ColumnUtil.getMinValue(column, columnTypeMapping);
		// 大于或等于最小负数
		SingleColumnValueFilter or_greater_equal_negative = new SingleColumnValueFilter(
				Constants.COLUMN_FAMILY_BYTE,Bytes.toBytes(column),CompareOp.GREATER_OR_EQUAL, minValue);
		or_greater_equal_negative.setFilterIfMissing(true);
		filters.addFilter(or_greater_equal_negative);
		
		this.filterList.addFilter(filters);
	}
	
	//暂时未处理的方法
	@Override
	public void visit(OrExpression arg0) {}
	@Override
	public void visit(SetOperationList arg0) {}
	@Override
	public void visit(WithItem arg0) {}
	@Override
	public void visit(SubSelect arg0) {}
	@Override
	public void visit(SubJoin arg0) {}
	@Override
	public void visit(LateralSubSelect arg0) {}
	@Override
	public void visit(ValuesList arg0) {}
	@Override
	public void visit(NullValue arg0) {}
	@Override
	public void visit(Function arg0) {}
	@Override
	public void visit(SignedExpression arg0) {}
	@Override
	public void visit(JdbcParameter arg0) {}
	@Override
	public void visit(JdbcNamedParameter arg0) {}
	@Override
	public void visit(DoubleValue arg0) {}
	@Override
	public void visit(LongValue arg0) {}
	@Override
	public void visit(DateValue arg0) {}
	@Override
	public void visit(TimeValue arg0) {}
	@Override
	public void visit(TimestampValue arg0) {}
	@Override
	public void visit(Parenthesis arg0) {}
	@Override
	public void visit(StringValue arg0) {}
	@Override
	public void visit(Addition arg0) {}
	@Override
	public void visit(Division arg0) {}
	@Override
	public void visit(Multiplication arg0) {}
	@Override
	public void visit(Subtraction arg0) {}
	@Override
	public void visit(Between between) {
		String column = between.getLeftExpression().toString();
		String value1 = between.getBetweenExpressionStart().toString();
		String value2 = between.getBetweenExpressionEnd().toString();
		try {
			
			if (column.equals(Constants.COLUMN_NAME_TM)) {
				rowkeyJson.put(Constants.COLUMN_NAME_STARTTIME,ColumnUtil.getTmValue(value1, 0));
				rowkeyJson.put(Constants.COLUMN_NAME_ENDTIME,ColumnUtil.getTmValue(value2, 1));
				
				greatherAndEqualsFilter(column,String.valueOf(ColumnUtil.getTmValue(value1, 0)),CompareOp.GREATER_OR_EQUAL);
				minorAndEqualsFilter(column,String.valueOf(ColumnUtil.getTmValue(value2, 1)),CompareOp.LESS_OR_EQUAL);
			} else {
				if (ColumnUtil.isNumberType(columnTypeMapping, column)) {
					greatherAndEqualsFilter(column, value1,CompareOp.GREATER_OR_EQUAL);
					minorAndEqualsFilter(column, value2,CompareOp.LESS_OR_EQUAL);
				} else {
					if (returnColumns != null && returnColumns.size() > 0 && !returnColumns.contains(column)) {
						returnColumns.add(column);
					}
				}
			}
		} catch (JSONException e) {
			errorLogger.error(e.getMessage());
		} catch (ParseException e) {
			errorLogger.error(e.getMessage());
		}
	}
	@Override
	public void visit(InExpression arg0) {}
	@Override
	public void visit(IsNullExpression arg0) {}
	@Override
	public void visit(LikeExpression arg0) {}
	@Override
	public void visit(Column arg0) {}
	@Override
	public void visit(CaseExpression arg0) {}
	@Override
	public void visit(WhenClause arg0) {}
	@Override
	public void visit(ExistsExpression arg0) {}
	@Override
	public void visit(AllComparisonExpression arg0) {}
	@Override
	public void visit(AnyComparisonExpression arg0) {}
	@Override
	public void visit(Concat arg0) {}
	@Override
	public void visit(Matches arg0) {}
	@Override
	public void visit(BitwiseAnd arg0) {}
	@Override
	public void visit(BitwiseOr arg0) {}
	@Override
	public void visit(BitwiseXor arg0) {}
	@Override
	public void visit(CastExpression arg0) {}
	@Override
	public void visit(Modulo arg0) {}
	@Override
	public void visit(AnalyticExpression arg0) {}
	@Override
	public void visit(WithinGroupExpression arg0) {}
	@Override
	public void visit(ExtractExpression arg0) {}
	@Override
	public void visit(IntervalExpression arg0) {}
	@Override
	public void visit(OracleHierarchicalExpression arg0) {}
	@Override
	public void visit(RegExpMatchOperator arg0) {}
	@Override
	public void visit(JsonExpression arg0) {}
	@Override
	public void visit(RegExpMySQLOperator arg0) {}
	@Override
	public void visit(UserVariable arg0) {}
}
