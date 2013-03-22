/**
 * 
 */
package weibo4android.examples.account;

import weibo4android.User;
import weibo4android.Weibo;



/**
 * @author sina
 *
 */
public class EndSession {

	/**
	 * 清除已验证用户的session
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
        	weibo.setToken(args[0],args[1]);
        	User user = weibo.endSession();
        	System.out.println(user.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
