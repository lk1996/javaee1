package cn.edu.bjtu.weibo.dao;
import java.util.List;
import cn.edu.bjtu.weibo.model.Comment;
import cn.edu.bjtu.weibo.model.User;
import redis.clients.jedis.Jedis;
public interface CommentDAO {
	static String host = "127.0.0.1";
	static int port = 6379;
	Jedis client = new Jedis(host, port);
	
	public String getComment(String commentid){
		return client.get("User:" + commentid + ":comment");
		
	}
	public String getTopicUserNumber(String commentid){
		return client.get("User:" + commentid + ":TopicUserNumber");
		
	}
	public String getOwner(String commentid)
	{
		
			return client.get("User:" + commentid + ":OWner");
		
		
	}
	public String getTime(String commentid);
	{
		
		return client.get("User:" + commentid + ":Time");
		
	}
}
