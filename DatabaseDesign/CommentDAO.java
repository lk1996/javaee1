package cn.edu.bjtu.weibo.dao;

import java.util.List;

import cn.edu.bjtu.weibo.model.Comment;

public interface CommentDAO {
	
	/**
	 * when this happen, it will automatically increase the total comment number
	 * @param comment
	 * @return new comment Id
	 */
	String insertNewComment(Comment comment);
	
	/**
	 * delete comment, when this happen, it will automatically decrease the total comment number
	 * @param commentId
	 * @return
	 */
	boolean deleteComment(String commentId);
	
	/**
	 * get the comment model
	 * @param commentId
	 * @return
	 */
	Comment getComment(String commentId);
	
	/**
	 * these two service just get the number of the total comment number, and the number will be updated by insertNewComment and deleteComment
	 * @return
	 */
	int getTotalCommentNumber();
	
	/**
	 * find who give this comment
	 * @param commentid
	 * @return
	 */
	String getOwner(String commentid);
	
	/**
	 * find when this comment was created.
	 * @param commentid
	 * @return
	 */
	String getTime(String commentid);
	
	/**
	 * When some one try to add or remove his like for this comment, and the number will be automatically updated by 
	 * insertLikeList and deleteLikeList
	 * @param CommentId
	 * @return
	 */
	boolean insertLikeList(String commentId,String userId);
	boolean deleteLikeList(String commentId,String userId);
	List<String> getLikeList(String commentId, int pageIndex, int numberPerPage);
	int getLikeNumber(String commentId);
	
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
	boolean insertCommentList(String commentId,String commentIdForComment);
	boolean deleteCommentList(String commentId,String commentIdForComment);
	List<String> getCommentList(String commentId);
	int getCommentNumber(String commentId);
	
	String getContent(String commentId);
	List<String> getAtUsersInComment(String commentId);
	List<String> getTopicInComment(String commentId);
	
	/**
	 * this function means there are two comments A(A also can be a weibo) and B,
	 * B is the comment for A
	 *  A
	 *  |-B
	 * so if I want to use B to find A, then this commentId is for B, and it will return commentId for A
	 * @param commentId
	 * @return
	 */
	String getBeCommentedWeiboOrCommentId(String commentId);
}
