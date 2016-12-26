package cn.edu.bjtu.weibo.dao;

import java.util.List;

import cn.edu.bjtu.weibo.model.Topic;

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
	String insertNewTopic(Topic topic);
	/**
	 * 
	 * @param topicid
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	List<String> getAllWeibo(String topicId,int pageIndex, int pagePerNumber);
	List<String> getAllWeibo(String topicId);
	boolean insertWeibo(String topicid,String weiboId);
	
	/**
	 * 
	 * @param topicid
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	List<String> getAllComment(String topicid,int pageIndex, int pagePerNumber);
	List<String> getAllComment(String topicid);
	boolean insertComment(String topicid,String commentid);
	
	List<String> getAllTopic();
	List<String> getAllTopic(int pageIndex,int pagePerNumber);
	/**
	 * 
	 * @param pageIndex
	 * @param pagePerNumber
	 * @return
	 */
	List<String> getHotTopic(int pageIndex,int pagePerNumber);
	List<String> getHotTopic();
	/**
	 * 
	 * @param topicid
	 * @return
	 */
	String getContent(String topicid);
	
	/**
	 * this will be automatically update by insert weibo or comment.
	 * @param topicid
	 * @return
	 */
	int getContenteNumberContainTopic(String topicid);
	/**
	 * 
	 * @param topicId
	 * @return
	 */
	String getTimeofTopic(String topicId);
	/**
	 * 
	 * @param topicId
	 * @return
	 */
	String getOwnerTopic(String topicId);
}
 