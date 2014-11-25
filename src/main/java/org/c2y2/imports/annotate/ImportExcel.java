package org.c2y2.imports.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.c2y2.imports.util.ImportFileConstants;

/**
 * 
 * @name ImportExcel
 * @description 用于导入配置注解
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月24日 下午10:17:08
 * @version：1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ImportExcel {
	/**
	 * 批量导入的类 标记表名
	 */
	public String tableName();
	/**
	 * 批量导入的类 标记文件类型
	 */
	public String fileType() default ImportFileConstants.FILE_TYPE_EXCEL2003;
	/**
	 * 批量导入的类 批量导入插入sql
	 */
	public String insertSql() default "";
	/**
	 * 批量导入的类 业务校验正则
	 */
	public String validator() default "";
	/**
	 * 批量导入的类 头数
	 */
	public int headerCount() default 1;
}
