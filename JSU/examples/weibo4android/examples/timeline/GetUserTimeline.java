/**
 * 
 */
package weibo4android.examples.timeline;

import java.util.List;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;


/**
 * @author sina
 *
 */
public class GetUserTimeline {

	/**
	 * 获取用户发布的微博信息列表 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			//获取24小时内前20条用户的微博信息;args[2]:用户ID
			List<Status> statuses = weibo.getUserTimeline(args[2],new Paging(1,20));
			for (Status status : statuses) {
	            System.out.println(status.getUser().getName() + ":" +status.getId()+":"+
	                               status.getText() + status.getOriginal_pic());
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
