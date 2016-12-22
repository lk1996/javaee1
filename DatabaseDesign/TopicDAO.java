package cn.edu.bjtu.weibo.dao;

import java.util.List;

import cn.edu.bjtu.weibo.model.Topic;
import redis.clients.jedis.Jedis;
public interface TopicDAO {
	static String host = "127.0.0.1";
	static int port = 6379;
	Jedis client = new Jedis(host, port);
	public boolean insertNewTopic(Topic t)
	{
		String result;
		String key_name = "t:" + String.valueOf(t.getTopicid());
		result = client.set(key_name + ":Topicid", String.valueOf(t.getTopicid()));
		result = client.set(key_name + ":Topicontent", t.getContent());
		result = client.set(key_name + ":TopicUserNumber", t.getTopicUserNumber());
		result = client.set(key_name + ":Owner", t.getOwner());
		result = client.set(key_name + ":Tine",t.getTine());
		
		
		
	}
	
	public List<String> getUserList(String topicid){
		Jedis jedis = new Jedis("localhost");
		String key = "t:"+topicid+":UserList";
		List<String> list = jedis.lrange(key, 0, -1);  
		return list;
		
		
		
	}
	
	public String getContent(String topicid);
	{
		return client.get("topic:" + topicid + ":content");
		
		
	}
	public String getTopicUserNumber(String topicid);
	{
			return client.get("topic:" + topicid + ":TopicUserNumber");
		
		
	}
	public String getOwner(String topicid);
	{
		return client.get("topic:" + topicid + ":TopicUserNumber");
	}
	public String getTine(String topicid)
	{
		
		return client.get("topic:" + topicid + ":Tine");
		
	}
}
 