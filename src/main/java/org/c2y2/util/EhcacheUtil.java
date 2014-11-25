package org.c2y2.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;



/**
 * 
 * @name EhcacheUtil
 * @description 
 * @author c2y2 lexiangtaotao1988@gmail.com http://www.c2y2.org
 * @date 2014年11月24日 下午10:09:48
 * @version：1.0.0
 */
public class EhcacheUtil {
	private static final CacheManager CACHE_MANAGER = CacheManager.create();
	/**
	 * 
	 * @name getCache
	 * @todo (用于获取cache) 
	 * @conditions (通过缓存key获取相应的cache) 
	 * @param cacheName
	 * @return  Cache 
	 * @auth c2y2 2014年11月24日-下午9:59:51 
	 * @exception  
	 * @since  1.0.0
	 */
	public static Cache getCache(String cacheName){
		return CACHE_MANAGER.getCache(cacheName);
	}
	
	/**
	 * 
	 * @name setCache
	 * @todo (设置cache名，并添加新缓存) 
	 * @conditions (没有缓存时新添加) 
	 * @step (这里描述这个方法业务步骤 – 可选) 
	 * @param cacheName  void 
	 * @auth c2y2 2014年11月24日-下午10:07:04 
	 * @exception  
	 * @since  1.0.0
	 */
	public static void setCache(String cacheName){
		CACHE_MANAGER.addCache(cacheName);
	}
}
