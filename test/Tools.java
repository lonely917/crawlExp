package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 工具类，用于爬取数据
 * 
 * @author yan
 * 
 */
public class Tools {

	/**
	 * @param name
	 * @param remoteUrl
	 * @param localDir
	 * @return
	 */
	public static boolean getPic(String remoteUrl, String localDir, String name) {
		boolean result = false;

		// get the file name, combine the local dir and name, deal with the dir
		// or parent dir
		String fileName = name;
		if (localDir != null && localDir.length() > 0) {
			File dir = new File(localDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			fileName = localDir + "/" + fileName;
		}

		// create new file, deal with data transfer
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(remoteUrl);
				//add timeout para
				RequestConfig config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(50000).build(); 
				httpGet.setConfig(config);
				httpGet.setHeader(
						"User-Agent",
						"Mozilla/4.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");

				log("http get:" + httpGet.getURI().toString());
				CloseableHttpResponse response = httpclient.execute(httpGet);
				log(httpGet.getURI().toString() + "-->"
						+ response.getStatusLine());

				// ready to get file
				if (response.getStatusLine().toString().contains("200 OK")) {
					FileOutputStream fos = new FileOutputStream(file);
					InputStream is = response.getEntity().getContent();
					byte content[] = new byte[1024];
					int len = 0;
					int tmp = 0;

					// read and write
					try 
					{
						while ((tmp = is.read(content, 0, content.length)) != -1) {
							fos.write(content, 0, tmp);
							len += tmp;
							if(tmp==0){
								//for some cases this return 0, also can set read timeout to solve this problem
								log("socket read 0 occured");
								break;
							}
						}

					} catch (Exception e) {
						log("exception read and write:", remoteUrl + "-" + e.toString());
					}
					
					try 
					{
						fos.close();	
					} catch (Exception e) {
						log("exception fos close:", remoteUrl + "-" + e.toString());
					}
					try {
						is.close();	
					} catch (Exception e) {
						log("exception is close:", remoteUrl + "-" + e.toString());
					}
					

					log(fileName + "-size:" + len);
					result = true;
				}
				try {
					response.close();
				} catch (Exception e) {
					log("exception response close:", remoteUrl + "-" + e.toString());
				}
				try {
					httpclient.close();
				} catch (Exception e) {
					log("exception httpclient close:", remoteUrl + "-" + e.toString());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				log("exception:", remoteUrl + "-" + e.toString());
			}

		}
		else
		{
			log("file exists:"+remoteUrl);
		}

		return result;
	}

	public static boolean getPic(String remoteUrl) {
		String localDir = "";
		int start = remoteUrl.indexOf(".com") + 4;
		int end = remoteUrl.lastIndexOf("/");
		if (start < end) {
			localDir = remoteUrl.substring(start + 1, end);
		} else {
			// localDir "" for this form : "http:....aajdk.com/test.jpg"

		}
		return getPic(remoteUrl, localDir);
	}

	public static boolean getPic(String remoteUrl, String localDir) {
		String name = "test";
		name = remoteUrl.substring(remoteUrl.lastIndexOf("/") + 1);
		return getPic(remoteUrl, localDir, name);
	}

	public static String getHtml(String remoteUrl) {
		String result = "error";
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(remoteUrl);
			httpGet.setHeader(
					"User-Agent",
					"Mozilla/4.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
			log("get:" + httpGet.getURI().toString());
			CloseableHttpResponse response = httpclient.execute(httpGet);
			log(httpGet.getURI().toString() + "-->" + response.getStatusLine());

			if (response.getStatusLine().toString().contains("200 OK")) {
				result = EntityUtils.toString(response.getEntity());
			}

			response.close();
			httpclient.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	// 手动实现检索，不借助开源工具
	public static List<String> parseUrlsFromHtml(String content, String tag,
			Boolean isAll) {
		List<String> urls = new ArrayList<String>();
		switch (tag) {
		case "img":
			tag = "<img src=\"";
			break;
		case "a":
			tag = "<a href=\"";// 除了http开头地址，这里可能是相对url；上下文设置的标签；js代码；图片地址等
			break;
		case "input":
			tag = "<input src=\"";
			break;
		default:
			break;
		}
		int start = 0;
		while ((start = content.indexOf(tag, start)) != -1) {
			int urlStart = start + tag.length();
			String url = content.substring(urlStart,
					content.indexOf('"', urlStart));
			if (isAll || url.startsWith("http")) {
				urls.add(url);
			}
			start += url.length();
		}
		return urls;
	}

	public static List<String> parseUrlsFromHtml(String content, String tag) {
		return parseUrlsFromHtml(content, tag, false);
	}

	public static boolean mkDir(String dir) {
		File file = new File(dir);
		return file.mkdir();// 目录已经存在，返回false，不会重新创建，源头是WinNTFileSystem的createDirectory，native方法
	}

	public static boolean mkDirs(String dir) {
		File file = new File(dir);
		return file.mkdirs();// 目录已经存在，返回false，不同于mkdir的是父目录不存在时支持创建多级目录，内部利用mkdir以及自身递归调用实现
	}

	public static void log(String tag, String s) {
		log(tag + ":" + s);
	}

	public static void log(String s) {
		System.out.println(s);
	}

	// main for test
	public static void main(String args[]) {
		// getPic("http://img158.imagetwist.com/th/12151/xgs4pmom5u60.jpg",
		// "12", "11.jpg");
		// getPic("http://img158.imagetwist.com/th/12151/xgs4pmom5u60.jpg","");
		// getPic("http://img158.imagetwist.com/th/12151/xgs4pmom5u60.jpg");
		
//		 getPic("http://imagetwist.com/rsd035pat05r/032.jpg");
//		getPic("http://imagddetwist.com/rsd035paddt05r/032.jpg");
		
		getPic("http://img8.uploadhouse.com/fileuploads/23611/236117185594562ef161bd4e0d9df3e15e113bac.jpg","");
		
		getPic("https://s29.postimg.org/gmautgsgn/0c93c8cfda19184c7e8b03d979e1dfce.jpg","");
		
		// System.out.println(mkDirs("t/t/t"));
		// File file = new File("z");
		// System.out.println(file.exists());

		// String s = "http://img158.imagetwist.com/th/12151/xgs4pmom5u60.jpg";
		// s = s.replaceAll("http://[^/]*/", "").replaceAll("/[^/]*\\.[^/]*",
		// "");
		// System.out.println(s);

		// System.out.println(getHtml("http://guomo.me/category/luoli"));

		// String s = getHtml("http://guomo.me/1000.html");
		// System.out.println(s);
		// List<String> imgs = parseUrlsFromHtml(s, "img");
		// List<String> urls = parseUrlsFromHtml(s, "a");
		// System.out.println(imgs.size()+":"+imgs);
		// System.out.println(urls.size()+":"+urls);

//		String s = getHtml("http://dz.x8h.biz/thread0806.php?fid=16");
//		System.out.println(s);
//		List<String> imgs = parseUrlsFromHtml(s, "img", true);
//		List<String> urls = parseUrlsFromHtml(s, "a", true);
//		System.out.println(imgs.size() + ":" + imgs);
//		System.out.println(urls.size() + ":" + urls);

		// Tools.getPic("http://ipoock.com/img/g4/20161124233012dxa9i.jpeg");
	}

}
/**
 * 1.6中对mkdirs方法针对1.5进行了改进，使得mkdirs可以在多线程中使用？ 文件操作分析以及常用套路，目录，文件，递归思想
 * 多线程爬取和文件写入问题 正则表达式使用 html标签解析,regex过滤以及jSoup解析 单元测试思想 多线程和线程池
 */

//main函数中的测试例子，如果不配置超时可能导致一直阻塞，网络问题or网盘服务器问题
//设置超时可能会出现图片部分下载的情况