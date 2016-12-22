package cn.edu.bjtu.weibo.dao;

import java.util.List;
import redis.clients.jedis.Jedis;
/**
 * this interface  is  about report the user and weibo
 */
public interface ReportDAO{

	static String host = "127.0.0.1";
	static int port = 6379;
	Jedis client = new Jedis(host, port);
	/**
	 * report user 
	 * @param UserId by report
	 * @param UserId who report others
	 */
	public boolean ReportUser(String UserId,String reportedfromuserId)
	{
		Jedis jedis = new Jedis("localhost");
		String key = "User:"+UserId+":report";
		if(jedis.lpush(key, reportedfromuserId)>0){
			return true;
		};
		return false;
		
		
		
		
	}
	/**
	 * report weibo
	 * @param WeiboId by report
	 * @param UserId who report weibo
	 */
	public boolean ReportWeibo(String WeiboId,String reportedfromuserId)
	{
		Jedis jedis = new Jedis("localhost");
		String key = "Weibo:"+WeiboId+":report";
		if(jedis.lpush(key, reportedfromuserId)>0){
			return true;
		};
		return false;
		
		
	}
	/**
	 * the number of report User
	 */
	public int ReportUserNumber(String ReportUserId)
	{
			return client.get("Report:" + ReportUserId + ":ReportUseNumber");
		
		
	}
	/**
	 * the number of report weibo
	 */
	public int ReportWeiboNumber(String ReportWeiboId)
	{
		
		return client.get("Report:" + ReportUserId + ":ReportWeiboNumber");
		
		
	}
	/**
	 * the listUser of report a user
	 */
	public List<String> ReportedUserfromuserList(String UserId,int pageIndex, int pagePerNumber)
	{
			Jedis jedis = new Jedis("localhost");
		String key = "User:"+userId+":Reported";
		List<String> list = jedis.lrange(key, pageIndex,  pagePerNumber);  
		return list;
		
		
		
	}
	/**
	 * the listUser of report a weibo
	 */
	public List<String> ReportedWeibofromuserList(String WeiboId,int pageIndex, int pagePerNumber)
	{
			Jedis jedis = new Jedis("localhost");
		String key = "User:"+WeiboId+":Reported";
		List<String> list = jedis.lrange(key, pageIndex,  pagePerNumber);  
		return list;
		
		
	}
	/**
	 * the list of  user reported
	 */
	public List<String> ReportUserList(int pageIndex, int pagePerNumber)
	{
		Jedis jedis = new Jedis("localhost");
	
		List<String> list = jedis.lpush(pageIndex, pageIndex);  
		return list;
		
		
		
	}
	/**
	 * the list of weibo reported
	 */
	public List<String> ReportWeiboList(int pageIndex, int pagePerNumber)
	{
		Jedis jedis = new Jedis("localhost");
	
		List<String> list = jedis.lpush(pageIndex, pageIndex);  
		return list;
		
		
		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	}
	
}