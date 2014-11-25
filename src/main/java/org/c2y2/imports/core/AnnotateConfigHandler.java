package org.c2y2.imports.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.c2y2.imports.annotate.ImportColumnConfig;
import org.c2y2.imports.annotate.ImportExcel;
import org.c2y2.imports.config.ImportFileColumn;
import org.c2y2.imports.config.ImportFileConfig;

public class AnnotateConfigHandler {
	public static ImportFileConfig handler(Class<?> clazz){
		Annotation annotationTable = clazz.getAnnotation(ImportExcel.class);
		if(annotationTable!=null){
			/*根据注解生成导入配置*/
			ImportFileConfig importFileConfig = new ImportFileConfig();
			importFileConfig.setClazz(clazz);
			ImportExcel importExcel = (ImportExcel)annotationTable;
			List<ImportFileColumn> importColumnConfigList = new ArrayList<ImportFileColumn>();
			String tableName = importExcel.tableName();
			
			
			String insertSqlConfig = importExcel.insertSql();
			
			String validator = importExcel.validator();
			importFileConfig.setValidator(validator);
			
			int headerCount = importExcel.headerCount();
			importFileConfig.setHeaderCount(headerCount);
			
			StringBuilder insertSql = new StringBuilder("insert into ").append(tableName).append("(");
			StringBuilder valueSql = new StringBuilder(" values(");
			if(annotationTable.annotationType().isAnnotation()){
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					Annotation fileAnnotation = field.getAnnotation(ImportColumnConfig.class);
					if(fileAnnotation.annotationType().isAnnotation()){
						/*根据注解生成列配置*/
						ImportColumnConfig importColumnConfig = (ImportColumnConfig) fileAnnotation;
						ImportFileColumn importFileColumn = new ImportFileColumn();
						Class<?> classType = field.getType();//获取字段类型然后设置默认正则
						String defaultRegex = ".*";
						if(classType == String.class){
							defaultRegex = ".*";
						}else if(classType == Integer.class){
							defaultRegex = "^\\-?[0-9]*\\.?0?$";
						}else if (classType == Double.class) {
							defaultRegex = "^\\-?[0-9]*.[0-9]*$";
						}else if(classType == Boolean.class){
							defaultRegex = "^((true)|(false)|(0)|(1))*";
						}else if (classType == Long.class) {
							defaultRegex = "^\\-?[0-9]*";
						}
						importFileColumn.setFieldType(classType);
						
						String title = importColumnConfig.title();//excel表头名
						importFileColumn.setTitle(title);
						
						String columnName = importColumnConfig.columnName();//excel导入时对应数据库表字段名
						importFileColumn.setColumnName(columnName);
						
						String regexExpress = importColumnConfig.regexExpress();//校验正则
						importFileColumn.setRegexExpress(regexExpress.equals("")?defaultRegex:regexExpress);
						
						String regexTitle = importColumnConfig.regexTitle();//错误后正则提示
						importFileColumn.setRegexTitle(regexTitle);
						
						boolean isNotNull = importColumnConfig.isNotNull();//不可为空
						importFileColumn.setNotNull(isNotNull);
						
						String fieldName = field.getName();
						String first = fieldName.substring(0, 1);
						importFileColumn.setField(fieldName.replaceFirst(first,first.toUpperCase()));
						System.out.println(importFileColumn.getField());
						
						int maxLength = importColumnConfig.maxLength();
						importFileColumn.setMaxLength(maxLength);
						importColumnConfigList.add(importFileColumn);
						
						insertSql.append(columnName).append(",");//生成sql
						valueSql.append("?").append(",");
					}
				}
			}
			insertSql.append(")").append(valueSql).append(")");
			String insert = insertSql.toString().replace(",)", ")");
			importFileConfig.setInsertSql(insertSqlConfig.equals("")?insert:insertSqlConfig);
			importFileConfig.setColumnList(importColumnConfigList);
			return importFileConfig;
		}
		return null;
	}
}
