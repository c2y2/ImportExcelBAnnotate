package org.c2y2.imports.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @name ImportColumnConfig
 * @description 用于批量导入列配置的注解
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月24日 下午10:16:30
 * @version：1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ImportColumnConfig {
	/**
	 * 用于标记实体导入时excel标题头
	 * @return
	 */
	public String title();
	/**
	 * 用于标记实体导入时在数据库中的字段名
	 * @return
	 */
	public String columnName();
	/**
	 * 用于标记实体导入时校验正则
	 * @return
	 */
	public String regexExpress() default "";
	/**
	 * 用于标记实体导入时校验正则校验失败后提示信息
	 * @return
	 */
	public String regexTitle();
	/**
	 * 用于标记实体域是否为空 默认不为空
	 * @return
	 */
	public boolean isNotNull() default true; 
	/**
	 * 用于标记实体域长度 默认-1时表示未设置
	 * @return
	 */
	public int maxLength() default -1;
	/**
	 * 用于标记时间类型的格式
	 * @return
	 */
	public String dataFormat() default "yyyy-MM-dd HH:mm:ss";
}
