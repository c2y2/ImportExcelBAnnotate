package org.c2y2.imports.util;

import org.apache.log4j.Logger;

/**
 * 
 * @name LogFactory
 * @description 日志生成工具
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月25日 下午4:10:03
 * @version：1.0.0
 */
public class LogFactory {
	/**
	 * 
	 * @name getLoger
	 * @todo (获取日志生成器) 
	 * @conditions (这里描述这个方法适用条件 – 可选) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param clazz
	 * @return  Logger 
	 * @auth c2y2 2014年11月25日-下午4:10:07 
	 * @exception  
	 * @since  1.0.0
	 */
	public static Logger getLoger(Class<?> clazz){
		return Logger.getLogger(clazz);
	}
}
