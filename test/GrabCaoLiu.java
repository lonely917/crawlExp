package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GrabCaoLiu {

	static String baseUrl = "http://dz.3ql.info/";//网址
	static String targetCategory = "http://dz.3ql.info/thread0806.php?fid=16&page=";//目标版块
	static int start = 1;
	static int end = 2;
	static Boolean timeout = true; 
	
	//初始化参数
	static
	{
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream("configGrabCaoLiu"));//Properties加载配置文件
			baseUrl = (String) pro.get("baseUrl");//网址
			targetCategory = (String) pro.getProperty("targetCategory");//目标版块
			start = Integer.parseInt(pro.getProperty("start"));//目标版块起始页面
			end = Integer.parseInt(pro.getProperty("end"));//目标版块结束页面
			Tools.timeout = Boolean.parseBoolean(pro.getProperty("timeout"));//是否设置超时
			pro.list(System.out);
		} catch(Exception e){
			Tools.log("exception", e.toString());
		}
	}

	public static List<String> parseUrlsFromHtml(String content, String tag, Boolean isAll)
	{
		List<String> urls = new ArrayList<String>();
		switch (tag) {
		case "img":
			tag = "<img src=\'";
			break;
		case "input":
			tag = "<input src=\'";
			break;
		default:
			break;
		}
		int start = 0;
		while((start = content.indexOf(tag, start))!=-1)
		{
			int urlStart = start+tag.length();
			String url = content.substring(urlStart, content.indexOf('\'', urlStart));
			if(isAll || url.startsWith("http"))
			{
				urls.add(url);
			}
			start+=url.length();
		}
		return urls;
	}
	
	//单线程抓取图片
	public void doWork()
	{
			for(int i=start;i<=end;i++)
			{
				String remoteUrl = targetCategory+i;
				String html = Tools.getHtml(remoteUrl);
				List<String> urls  = Tools.parseUrlsFromHtml(html, "a", true);
				for (String url : urls) {
					
					//这里对指定特征的链接进行下载，可以指定图片类型，或来源网站
					if(url.contains("htm_data")){
						if(!url.startsWith("http")){
							url  = baseUrl + url;	
						}
						Tools.log("htm_data:"+url);
						getHtmlInputs(url);
					}
				}
			}
			
	}

	public static void getHtmlImgs(String remoteUrl) {
		String html = Tools.getHtml(remoteUrl);
//		System.out.println(html);
		List<String> urls =  parseUrlsFromHtml(html, "img" , false);//img src='这种形式
		for (String url : urls) {
			Tools.getPic(url, remoteUrl.substring(remoteUrl.lastIndexOf('/')+1, remoteUrl.lastIndexOf('.')));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		new GrabCaoLiu().getHtmlInputs("http://dz.x8h.biz/htm_data/16/1701/2215820.html");
//		new GrabCaoLiu().getHtmlImgs("http://dz.x8h.biz/htm_data/21/1701/2232359.html");
		new GrabCaoLiu().doWork();
	}

	public static void getHtmlInputs(String remoteUrl) {
		String html = Tools.getHtml(remoteUrl);
//		System.out.println(html);
		List<String> urls = parseUrlsFromHtml(html, "input" , false);//input src='这种形式
		for (String url : urls) {
			Tools.getPic(url, remoteUrl.substring(remoteUrl.lastIndexOf('/')+1, remoteUrl.lastIndexOf('.')));
		}
	}

}

/**
 * 针对1024社区图片抓取
 * 注意标签input和img两种，以及src的引号是单引号
 * 两个子类型
 * http://dz.x8h.biz/thread0806.php?fid=16&page=2
 * http://dz.x8h.biz/thread0806.php?fid=21
 */