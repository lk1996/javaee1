package cn.edu.bjtu.weibo.dao;

import java.util.List;

public interface CommentDAOImpl implements  CommentDAO {
	
	static String host = "127.0.0.1";
	static int port = 6379;
	Jedis client = new Jedis(host, port);
	/**
	 * 
	 * @param commentContent
	 * @param becommentedWeiboOrComment
	 * @param owner
	 * @param time
	 * @return the id 
	 */
	
	public String  insert(String commentContent,String becommentedWeiboOrComment,String owner,String time )
	{
				
		Long count=client.incr("commentNumber");		
		String str = String.format("%05d", count);
		String key = "comment:c" + str + ":";
		jedis.set(key + "commentContent", commentContent);
		jedis.set(key + "becommentedWeiboOrComment", becommentedWeiboOrComment);
		jedis.set(key + "owner", owner);
			jedis.set(key + "time", time);
	
		
		return true;
		
		
	}

	 public String getCommentNumber(String commentid)
	 {
		 return client.get("comment:" + commentid + ":commentNumber");
		 
		 
		 
		 
	 }
	
	public boolean updateLikeNumber(String commentid,String likeNumber)
	{
		
		String result = client.set("comment:" + commentid + ":likeNumber", likeNumber);
		if (result.equals("OK")) {
			return true;
		}
		return false;
		
		
		
	}
	public boolean updateLikeList(String commentid,String userId)
	{
		
	String key = "comment:"+commentid + ":like";
		client.lpush(key, userId);
		return true;
		
		
		
		
	}
	
	public boolean updateCommentNUmber(String commentid,String commentNumber)
	{
		
		String result = client.set("comment:" + commentid + ":commentNumber", commentNumber);
		if (result.equals("OK")) {
			return true;
		}
		return false;
		
		
	}
	public boolean updateCommentList(String commentid,String commemtidComment)
	{
		
		String key = "comment:"+commentid + ":comment";
		client.lpush(key, commemtidComment);
		return true;
		
		
	}
	
	public String getContent(String commentId)
	{
		return client.get("comment:" + commentid + ":content");
		 
		
		
		
	}
	public List<String> getAtUsersInComment(String commentId)
	{
		return client.lrange("comment:"+commentId+"owner",0,-1);
		
		
		
	}
	public List<String> getTpoicInComment(String commentId)
	{
		
		return client.lrange("comment:"+commentId+"owner",0,-1);
		
		
	}
	public String getBeCommentedWeiboOrCommentId(String commentId)
	{
		
		return client.get("comment:" + commentid + ":becommentedWeiboOrComment");
		
		
	}
	public String getLikeNumber(String commentId)
	{
		
		return client.get("comment:" + commentid + ":likeNumber");
		
		
	}
	public String getCommentNumber(String commentId)
	{
		
		return client.get("comment:" + commentid + ":commentNumber");
		
		
	}
	
	public List<String> getLikeList(String commentId)
	{
			return client.lrange("comment:"+commentId+"like",0,-1);
		
		
		
	}
	public List<String> getCommentList(String commentId)
	{
		
			return client.lrange("comment:"+commentId+"comment",0,-1);
		
		
	}
	
	public boolean deleteComment(String commentId)
	{
			Set<String> set = client.keys("comment:"+commentId + ":*");
		Iterator<String> it = set.iterator();
		int count = 0;
		while (it.hasNext()) {
			String keyStr = it.next();
			System.out.println(keyStr);
			client.del(keyStr);
			count++;
		}

		if (count == 0)
			return false;
		return true;
		
		
		
	}
	
	public boolean deleteCommentFromComment(String fromCommentId, String commentId)
	{
		String key = "comment:"+fromcomment+ ":comment";
		jedis.lrem(key, 1, commentId);
		key = fromcomment+ ":commentNumber";
		client.decr(key);
		return true;
		
		
		
		
	}
	
	public String getOwner(String commentid)
	{
		
		return client.get("comment:" + commentid + ":owner");
		
		
	}
	public	String getTime(String commentid)
	{
	return client.get("comment:" + commentid + ":time");
	}
