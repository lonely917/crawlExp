package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class GrabBeauty {

	static String baseUrl = "http://guomo.me/";
	static int start = 100;
	static int end = 2000;
	
	//初始化参数
	static
	{
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream("configGrabBeauty"));
			baseUrl = (String) pro.get("baseUrl");
			start = Integer.parseInt(pro.getProperty("start"));
			end = Integer.parseInt(pro.getProperty("end"));
			pro.list(System.out);
		} catch(Exception e){
			Tools.log("exception", e.toString());
		}
	}

	//单线程抓取图片
	public void doWork()
	{
		//遍历指定范围的页面
		for(int i=start;i<=end;i++)
		{
			String remoteUrl = baseUrl + i + ".html";
			String html = Tools.getHtml(remoteUrl);
			
			//获取该页面图片地址集合，并下载图片 img a 是两个选项
			List<String> picUrls  = Tools.parseUrlsFromHtml(html, "img");
			for (String picUrl : picUrls) {
				
				//这里对指定特征的链接进行下载，可以指定图片类型，或来源网站
				if(picUrl.contains("imagetwist")){
					Tools.getPic(picUrl,i+"");//保存在i目录下
				}
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GrabBeauty().doWork();
	}

}
