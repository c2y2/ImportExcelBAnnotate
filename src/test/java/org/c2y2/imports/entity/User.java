package org.c2y2.imports.entity;

import org.c2y2.imports.annotate.ImportColumnConfig;
import org.c2y2.imports.annotate.ImportExcel;
import org.c2y2.imports.util.ImportFileConstants;

/**
 * 
 * @name User
 * @description 
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月25日 下午11:19:05
 * @version：1.0.0
 */
@ImportExcel(tableName ="user",sheetName = "用户",fileType=ImportFileConstants.FILE_TYPE_EXCEL2003)
public class User {
	/**
	 * 姓名
	 */
	@ImportColumnConfig(title="姓名",columnName="user_name",regexTitle="",isNotNull=true)
	private String username;
	/**
	 * 性别
	 */
	@ImportColumnConfig(title="性别",columnName="sex",regexTitle="",isNotNull=true)
	private Integer sex;
	/**
	 * 年龄
	 */
	@ImportColumnConfig(title="年龄",columnName="age",regexTitle="",isNotNull=true)
	private Integer age;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
