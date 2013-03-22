/**
 *
 */
package weibo4android.examples.statuses;

import java.util.List;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;


/**
 * @author sina
 *
 */
public class GetStatus {

	/**
	 * 获取单条ID的微博信息，作者信息将同时返回
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
        try {
        	Weibo weibo = new Weibo();
			weibo.setToken(args[0],args[1]);
        	List<Status> list = weibo.getUserTimeline(args[2], new Paging(1).count(4));
        	if(list.size() > 0) {
        		Status status = weibo.showStatus(list.get(0).getId());
            	System.out.println( status.getId() + "  : "+status.getText());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
