package org.c2y2.imports.config;


/**
 * 
 * @name ImportFileColumn
 * @description 批量导入列配置
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月24日 下午10:17:36
 * @version：1.0.0
 */
public class ImportFileColumn {
	private String columnName;//字段姓名
	private String field;//值域名（用于pojo）
	private Class<?> fieldType;//值域名（用于pojo）
	private String title;//字段名称
	private String valueType;//字段类型
	private Boolean notNull;//不可为空
	private Integer maxLength;//字段长度
	private String dateFormat;//时间格式
	private String regexExpress;// 正则表达
	private String regexTitle;//格式提示
	
	
	
	public Class<?> getFieldType() {
		return fieldType;
	}


	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public ImportFileColumn() {
		super();
	}
	
	
	public String getRegexTitle() {
		return regexTitle;
	}


	public void setRegexTitle(String regexTitle) {
		this.regexTitle = regexTitle;
	}





	
	public String getRegexExpress() {
		return regexExpress;
	}


	public void setRegexExpress(String regexExpress) {
		this.regexExpress = regexExpress;
	}


	public String getColumnName() {
		return columnName;
	}


	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public Boolean getNotNull() {
		return notNull;
	}
	public void setNotNull(Boolean notNull) {
		this.notNull = notNull;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
}
