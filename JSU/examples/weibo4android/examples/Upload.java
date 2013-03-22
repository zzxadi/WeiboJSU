package weibo4android.examples;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.http.ImageItem;



public class Upload {
	public static void main(String args[]){
	try{
	
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	Weibo weibo = new Weibo();
        
        weibo.setToken(args[0], args[1]);
        
        try{
        	byte[] content= readFileImage(args[2]);
        	System.out.println("content length:" + content.length);
        	ImageItem pic=new ImageItem("pic",content);
        	
        	String s=java.net.URLEncoder.encode("那一年T哭了，哭的无声无息。","utf-8");
        	Status status=weibo.uploadStatus(s, pic);
        	
        	System.out.println("Successfully upload the status to ["
        			+status.getText()+status.getOriginal_pic()+"].");
            }
             catch(Exception e1){
             	e1.printStackTrace();
            }
	    }catch(Exception ioe){
        	System.out.println("Failed to read the system input.");
	    }
	}
public static byte[] readFileImage(String filename)throws IOException{
		BufferedInputStream bufferedInputStream=new BufferedInputStream(
				new FileInputStream(filename));
		int len =bufferedInputStream.available();
		byte[] bytes=new byte[len];
		int r=bufferedInputStream.read(bytes);
		if(len !=r){
			bytes=null;
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}
}
     