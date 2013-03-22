/**
 * 
 */
package weibo4android.examples.favorites;

import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;


/**
 * @author sina
 *
 */
public class CreateFavorite {

	/**
	 * 添加收藏
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
			Status status = weibo.createFavorite(Long.parseLong(args[2]));
			System.out.println(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
