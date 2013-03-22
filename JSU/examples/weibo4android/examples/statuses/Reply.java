/**
 * 
 */
package weibo4android.examples.statuses;

import java.util.List;

import weibo4android.Comment;
import weibo4android.Status;
import weibo4android.Weibo;


/**
 * @author sina
 *
 */
public class Reply {

	/**
	 * 对一条微博评论信息进行回复
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
        	List<Status> list = weibo.getUserTimeline(args[2]);
        	if(list.size() > 0) {
        		//最新一条微博信息id
        		String sid = list.get(0).getId()+"";
        		List<Comment> comments = weibo.getComments(sid);
        		if(comments.size() > 0) {
        			String cid = comments.get(0).getId()+"";//评论的id
        			Comment status = weibo.reply(sid, cid, "回复内容");//args[3]：回复内容
        			System.out.println(status.toString());
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
