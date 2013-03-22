/**
 * 
 */
package weibo4android.examples.user;

import java.util.List;

import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;

/**
 * @author sina
 *
 */
public class GetFollowers {

	/**
	 * 返回用户关注对象列表，并返回最新微博文章。
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			List<User> list = weibo.getFollowersStatuses();
			for(User user : list) {
				System.out.println(user.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
