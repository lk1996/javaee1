package cn.edu.bjtu.weibo.dao;

import java.util.List;

import cn.edu.bjtu.weibo.model.Topic;
/**
 * this is the data about topic
 * @author ji
 *
 */
public interface TopicDAOImpl implements TopicDaoImpl {
	 	static String host = "127.0.0.1";
	static int port = 6379;
	Jedis client = new Jedis(host, port);
	
	
	public boolean updateNumberofTopic(String topicid,String contentNumberContainTopic)
	{
			String result = client.set("comment:" + commentid + ":contentNumberContainTopic", contentNumberContainTopic);
		if (result.equals("OK")) {
			return true;
		}
		return false;
		
		
		
	}
	public String getTopicNumber()
	{
		
		 Set keys = client.keys("Topic:*:id");  
        Iterator t=keys.iterator();  
        int i=0;
        while(t.hasNext()){   
          i++;
        }  
        return String.valueOf(i);
	}
	/**
	 * insert a new topic 
	 * @param content (the topic content)
	 * @param UserId (the id of user who create topic)
	 * @param date 
	 * @param WeiboId(the id of weibo which contains the topic )
	 * @return
	 */
	public boolean insertNewTopic(String content,String UserId,String date,String WeiboId)
	 {
		 			Long count=client.incr("TopicNumber");		
		String str = String.format("%05d", count);
		String key = "Topic:t" + str + ":";
		client.set(key + "content", content);
		client.set(key + "owner", UserId);
		client.set(key + "date", date );
		
		client.set(key + "weiboId",weiboId);
	
		return true;
		 
		 
		 
	 }
	/**
	 * 
	 * @param topicid
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	public List<String> getAllWeibo(String topicid,int pageIndex, int pagePerNumber)
	{
		String key = "Topic:"+topicid+":weibo";
		List<String> list = client.lrange(key, 0, -1);
		List<String> subList = list.subList((pageIndex-1)*pagePerNumber, pageIndex*pagePerNumber);
		return subList;
		
		
		
		
	}
	/**
	 * 
	 * @param topicid
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	public List<String> getAllComment(String topicid,int pageIndex, int pagePerNumber)
	{
		String key = "Comment:"+topicid+":comment";
		List<String> list = client.lrange(key, 0, -1);
		List<String> subList = list.subList((pageIndex-1)*pagePerNumber, pageIndex*pagePerNumber);
		return subList;
		
		
		
		
	}
	/**
	 * 
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	public List<String> getAllTopic(int pageIndex,int pagePerNumber)
	{
		String key = "Topic:"+topicid+":allTopic";
		List<String> list = client.lrange(key, 0, -1);
		List<String> subList = list.subList((pageIndex-1)*pagePerNumber, pageIndex*pagePerNumber);
		return subList;
		
		
		
	}
	/**
	 * 
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	public List<String> gethotTopic(int pageIndex,int pagePerNumber)
	{
		String key = "Topic:"+topicid+":hotTopic";
		List<String> list = client.lrange(key, 0, -1);
		List<String> subList = list.subList((pageIndex-1)*pagePerNumber, pageIndex*pagePerNumber);
		return subList;
		
		
		
		
	}
	/**
	 * 
	 * @param topicid
	 * @return
	 */
	public String getContent(String topicid)
	{
		
			return client.get("Topic:" + topicid + ":comment");
		
		
	}
	/**
	 * 
	 * @param topicid
	 * @param weiboId
	 * @return
	 */
	public boolean insertWeibo(String topicid,String weiboId)
	{
		
			String key = "Topic:"+topicid+":Weibo";
		if(client.lpush(key, weiboId)>0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param topicid
	 * @param commentid
	 * @return
	 */
	public boolean insertComment(String topicid,String commentid)
	{
			String key = "Topic:"+topicid+":comment";
		if(client.lpush(key, commentid)>0){
			return true;
		}
		return false;
		
		
		
	}
	/**
	 * 
	 * @param topicid
	 * @return
	 */
	public String getContenteNumberContainTopic(String topicid)
	{
		return client.get("Topic:" + topicid + ":commmentNumberContainTopic");
		
		
		
		
	}
	/**
	 * 
	 * @param topicId
	 * @return
	 */
	public String getTimeofTopic(String topicid)
	
	{
		
		return client.get("Topic:" + topicid + ":time");
		
		
		
	}

	/**
	 * 
	 * @param topicId
	 * @return
	 */
	String getOwnerTopic(String topicId)
	{
		
		return client.get("Topic:" + topicid + ":owner");
		
	}
}
 