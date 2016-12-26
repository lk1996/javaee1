package cn.edu.bjtu.weibo.dao;

import java.util.List;
import java.util.Iterator;

import java.util.Set;
import cn.edu.bjtu.weibo.model.Comment;
import redis.clients.jedis.Jedis;
public interface CommentDAOImpl implements  CommentDAO {
	
	/**
	 * when this happen, it will automatically increase the total comment number
	 * @param comment
	 * @return new comment Id
	 
	 */
	 Jedis jedis;

	public CommentDAOImpl() {
		jedis = new Jedis("localhost", 6379);
	}

	public String insertNewComment(Comment comment)
	{
		Long count=jedis.incr("commentNumber");		
		String str = String.format("%05d", count);
		String key = "comment:c" + str + ":";
		jedis.set(key + "owner", comment. getOwner());
		jedis.set(key + "commentContent", comment.getContent());
		jedis.set(key + "time", comment.getTime());
		jedis.set(key + "becommentedWeiboOrComment", comment.getBeCommentedWeiboOrCommentId());
		for(int i=0;i<comment.getAtUsersInComment().size();i++){
			jedis.lpush(key+"comment", comment.getAtUsersInComment().get(i));
		}
		for(int i=0;i<comment.getTopicInComment().size();i++){
			jedis.lpush(key+"like", comment.getTopicInComment().get(i));
		}
		jedis.set(key + "commentNumber", comment.getCommentNumber());
	
		jedis.set(key + "likeNumber",comment. getLikeNumber());
		jedis.lpush("commentList", str);
		return "OK";
		
		
		
		
	}
	
	/**
	 * delete comment, when this happen, it will automatically decrease the total comment number
	 * @param commentId
	 * @return
	 */
	public boolean deleteComment(String commentId)
	{
		Set<String> set = jedis.keys("comment:"+commentId + ":*");
		Iterator<String> it = set.iterator();
		int count = 0;
		while (it.hasNext()) {
			String keyStr = it.next();
			System.out.println(keyStr);
			jedis.del(keyStr);
			count++;
		}
        
		if (count == 0)
			return false;
		jedis.decr("commentNumber");
		jedis.lrem("commentList", 1,commentId);
		return true;
		
		
		
		
	}
	
	/**
	 * get the comment model
	 * @param commentId
	 * @return
	 */
	public Comment getComment(String commentId)
	{
		
		Comment comment=new Comment();
		String key = "comment:" + commentId;
		comment.setAtUserIdList(jedis.lrange(key+":atUsersInComment", 0, -1));
		comment.setCommentNumber(Integer.valueOf(jedis.get(key+"commentNumber")).intValue());
		comment.setContent(jedis.get(key+"commentContent"));
		comment.setDate(jedis.get(key+"time"));
		
		comment.setLike(Integer.valueOf(jedis.get(key+"likeNumber")).intValue());
		comment.setTopicIdList(jedis.lrange(key+":TopicInComment", 0, -1));
		comment.setUserId(jedis.get(key+"owner"));
		return comment;
		
		
	}
	
	/**
	 * these two service just get the number of the total comment number, and the number will be updated by insertNewComment and deleteComment
	 * @return
	 */
	public int getTotalCommentNumber()
	{
		return Integer.valueOf(jedis.get("commentNumber")).intValue();
		
		
	}
	
	/**
	 * find who give this comment
	 * @param commentid
	 * @return
	 */
	public String getOwner(String commentid)
	{
		String key = "comment:"+commentid + ":owner";
		return jedis.get(key);
		
		
		
		
	}
	
	/**
	 * find when this comment was created.
	 * @param commentid
	 * @return
	 */
	public String getTime(String commentid)
	{
		String key = "comment:"+commentid + ":time";
		return jedis.get(key);
		
		
		
	}
	
	/**
	 * When some one try to add or remove his like for this comment, and the number will be automatically updated by 
	 * insertLikeList and deleteLikeList
	 * @param CommentId
	 * @return
	 */
	public boolean insertLikeList(String commentId,String userId)
	{
			String key = "comment:"+commentId + ":like";
		jedis.lpush(key, userId);
		key = "comment:"+commentId + ":likeNumber";
		jedis.incr(key);
		return true;
		
		
		
	}
	public boolean deleteLikeList(String commentId,String userId)
	{
		String key="comment:"+commentId+"like";
		Long a=jedis.lrem(key, 1, userId);
		if(a==1)
		{jedis.decr("comment:"+commentId+"likeNumber");
		return true;	
		}
		return false;
		
		
		
		
	}
	
	public List<String> getLikeList(String commentId, int pageIndex, int numberPerPage)
	{
		String key = "comment:"+commentId + ":like";
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
	public int getLikeNumber(String commentId)
	{
		String key = "comment:"+commentId + ":likeNumber";
		return Integer.valueOf(jedis.get(key)).intValue();
		
		
		
	}
	
	/**
	 * when some one try to add or remove his comment for this comment, and the number will be automatically updated by
	 * insertCommentList and deleteCommentList
	 * there are two comments, A and B
	 * B is the comment for A
	 *  A
	 * 	|-B
	 * so commentId stands for A
	 * commentIdForComment stands for B
	 * @param commentId
	 * @param commemtIdComment
	 * @return
	 */
	public boolean insertCommentList(String commentId,String commentIdForComment)
	{
		String key = "comment:"+commentId + ":comment";
		jedis.lpush(key, commentIdForComment);
		 key = "comment:"+commentId + ":commentNumber";
			jedis.incr(key);
		return true;
		
		
		
	}
	public boolean deleteCommentList(String commentId,String commentIdForComment)
	{
		String key="comment:"+commentId+"comment";
		Long a=jedis.lrem(key, 1, commentIdForComment);
		if(a==1)
		{jedis.decr("comment:"+commentId+"commentNumber");
		return true;	
		}
		return false;
		
		
		
	}
	public List<String> getCommentList(String commentId)
	{
		String key="comment:"+commentId+":commentList";		
		return jedis.lrange(key, 0, -1);
		
		
		
	}
	public int getCommentNumber(String commentId)
	{
		String key = "comment:"+commentId + ":commentNumber";
		return Integer.valueOf(jedis.get(key)).intValue();
		
		
		
	}
	
	public String getContent(String commentId)
	{
		String key ="comment:"+ commentId + ":commentContent";
		return jedis.get(key);
		
		
	}
	public List<String> getAtUsersInComment(String commentId)
	{
		String key="comment:"+commentId+":atUserInComment";		
		return jedis.lrange(key, 0, -1);
		
		
		
	}
	public List<String> getTopicInComment(String commentId)
	{
		
		String key="comment:"+commentId+":topicInComment";		
		return jedis.lrange(key, 0, -1);
		
	}
	
	/**
	 * this function means there are two comments A(A also can be a weibo) and B,
	 * B is the comment for A
	 *  A
	 *  |-B
	 * so if I want to use B to find A, then this commentId is for B, and it will return commentId for A
	 * @param commentId
	 * @return
	 */
	public String getBeCommentedWeiboOrCommentId(String commentId)
	{
		String key = "comment:"+commentid + ":becommentedWeiboOrComment";
		return jedis.get(key);
		
		
	}
}
