/**
 * 
 */
package weibo4android.examples.ids;

import weibo4android.Weibo;
import weibo4android.WeiboException;


/**
 * @author sina
 *
 */
public class GetFollowersIDs {

	/**
	 * 获取用户粉丝对象uid列表 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			//args[2]:关注用户的id
			long[] ids = weibo.getFollowersIDs(args[2]).getIDs();
			for(long id : ids) {
				System.out.println(id);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
