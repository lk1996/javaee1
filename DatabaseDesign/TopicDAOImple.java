package cn.edu.bjtu.weibo.dao;


import java.util.Iterator;
import java.util.List;
import java.util.Set;
import cn.edu.bjtu.weibo.model.Topic;
import redis.clients.jedis.Jedis;
/**
 * this is the data about topic
 * @author ji
 *
 */
public interface TopicDAO {
	/**
	 * insert a new topic
	 * @param topic
	 * @return
	 
	 
	 */
	 
	 
	 	Jedis jedis;

	public WeiboDAOImpl() {
		jedis = new Jedis("localhost", 6379);
	}

	public String insertNewTopic(Topic topic)
	{
		Long count=jedis.incr("topicNumber");		
		String str = String.format("%05d", count);
		String key = "topic:t" + str + ":";
		jedis.set(key + "owner", topic. getOwnerTopic());
		jedis.set(key + "content", topic.getContent());
		jedis.set(key + "time", topic.getTimeofTopic);
		for(int i=0;i<topic.getAllWeibo().size();i++){
			jedis.lpush(key+"weibo", topic.getAllWeibo().get(i));
		}
		for(int i=0;i<topic.getAllComment().size();i++){
			jedis.lpush(key+"comment", topic.getAllComment().get(i));
		}
		jedis.set(key + "contentNumberContainTopic",topic.getContenteNumberContainTopic());
		
		jedis.lpush("topicList", str);
		return "OK";
		
		
		
	}
	/**
	 * 
	 * @param topicid
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	public List<String> getAllWeibo(String topicId,int pageIndex, int pagePerNumber)
	{
		String key = "topic:"+topicId + ":weibo";
		List<String> list = jedis.lrange(key, 0, -1);
		int start=pageIndex*numberPerPage;
	    int end=start+numberPerPage;
	    if(end>=list.size())
	    	end=list.size();
	    if(pageIndex<0||numberPerPage<=0||end>=list.size()){
	    	return null;
	    }
		return list.subList(start, end);
		
		
		
	}
	public List<String> getAllWeibo(String topicId)
	{
		String key="topic:"+topicId+":weibo";		
		return jedis.lrange(key, 0, -1);
		
		
	}
	public boolean insertWeibo(String topicid,String weiboId)
	{
		
		String key = "topic:"+topicid + ":weibo";
		jedis.lpush(key, weiboId);
		key = "comment:"+commentId + ":weiboNumber";
		jedis.incr(key);
		return true;
		
		
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
		String key = "topic:"+topicId + ":comment";
		List<String> list = jedis.lrange(key, 0, -1);
		int start=pageIndex*numberPerPage;
	    int end=start+numberPerPage;
	    if(end>=list.size())
	    	end=list.size();
	    if(pageIndex<0||numberPerPage<=0||end>=list.size()){
	    	return null;
	    }
		return list.subList(start, end);
		
		
		
		
	}
	public List<String> getAllComment(String topicid)
	{
		String key="topic:"+topicId+":comment";		
		return jedis.lrange(key, 0, -1);
		
		
		
	}
	public boolean insertComment(String topicid,String commentid)
	{
		String key = "topic:"+topicid + ":comment";
		jedis.lpush(key, commentid);
		key = "comment:"+commentId + ":commentNumber";
		jedis.incr(key);
		return true;
		
		
		
	}
	
	public List<String> getAllTopic()
	{
		return jedis.lrange("allTopic", 0, -1);
		
		
		
	}
	public List<String> getAllTopic(int pageIndex,int pagePerNumber)
	{
				List<String> list = new ArrayList<String>();
		Set keys = jedis.keys("topic:allTopic:*:topicList");
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			String obj1 = (String) t1.next();
			list.add(obj1);
		}
		List<String> subList = list.subList((pageIndex - 1) * pagePerNumber, pageIndex * pagePerNumber);
		return subList;
	}
		
		
		
	}
	/**
	 * 
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	public List<String> getHotTopic(int pageIndex,int pagePerNumber)
	{
				List<String> list = new ArrayList<String>();
		Set keys = client.keys("topic:hotTopic:*:hotTopicList");
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			String obj1 = (String) t1.next();
			list.add(obj1);
		}
		List<String> subList = list.subList((pageIndex - 1) * pagePerNumber, pageIndex * pagePerNumber);
		return subList;
	}
		
		
	}
	public List<String> getHotTopic()
	{
		
		
		return jedis.lrange("allTopic", 0, -1);
		
	}
	/**
	 * 
	 * @param topicid
	 * @return
	 */
	public String getContent(String topicid)
	{
		
		String key ="topic:"+ topicid + ":content";
		return jedis.get(key);
		
		
	}
	
	/**
	 * this will be automatically update by insert weibo or comment.
	 * @param topicid
	 * @return
	 */
	public int getContenteNumberContainTopic(String topicid)
	{
		String key ="topic:"+ topicid + ":contentNumberContainTopic";
		return jedis.get(key);
		
		
	}
	/**
	 * 
	 * @param topicId
	 * @return
	 */
	public String getTimeofTopic(String topicId)
	{
		
		String key ="topic:"+ topicid + ":time";
		return jedis.get(key);
		
	}
	/**
	 * 
	 * @param topicId
	 * @return
	 */
	public String getOwnerTopic(String topicId)
	{
		
		
		String key ="topic:"+ topicid + ":owner";
		return jedis.get(key);
	}
}
 