package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.EntityUtils;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class HttpHack {

	//默认的候选url列表
	public static String[] candidates = {
								"http://blog.csdn.net/java_student09/article/details/52139185",
								"http://blog.csdn.net/java_student09/article/details/52139344"
								} ;
	
	//候选url列表，从文件初始化获得，没有文件则从候选集合生成
	public static List<String> candidateList = new ArrayList<String>();
	
	//刷新频率默认值
	public static int interval = 1000; 
	
	//网站前缀 选用
	public static String baseUrl = ""; 
	
	//静态代码块，从配置文件获取url集合，程序加载的时候调用
	static
	{
		//load candidates to candidateList
		System.out.println("static code");

		File f = new File("config");
		if(f.exists())
		{
			try 
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String url;
				while((url = br.readLine())!= null)
				{
					//过滤 #过滤本行
					if(url.startsWith("#")) continue; //
					
					//刷新频率 @表示刷新间隔
					else if(url.startsWith("@"))
					{
						try {
							interval = Integer.parseInt(url.substring(1));
						} catch (Exception e) {

						}
					}
					else
					{
						candidateList.add(url);
					}
				}
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.out.println("read config error");
			}
		}
		else
		{
			//文件不存在则从候选集合中生成
			candidateList = Arrays.asList(candidates);
			System.out.println("no config file using default!");
		}
		
		//输出目标url集合
		for (String url : candidateList) {
			System.out.println(url);
		}
		
		//输出刷新频率
		System.out.println("interval :"+interval);
		
	}
	
    public static void main(String[] args) {

    		HttpThreadMultiUrl hackWeb = new HttpThreadMultiUrl("MultiUrlThread1");
    		hackWeb.start();
    		try {
				hackWeb.join();
			} catch (Exception e) {
				// TODO: handle exception
			}
//    	  new HttpThread("T1",candidates[0]).start();
//    	  new HttpThread("T2",candidates[1]).start();
//    	  new HttpThread("T3",candidates[2]).start();
  
//    	  System.out.println(HttpThread.ss);
    	  
    	  
    }

    /**
     * 对某一固定url进行访问，一个thread绑定一个url
     * @author yan
     *
     */
    static class HttpThread extends Thread
    {
    	private String url;
    	private String name;
    	public static String ss= "sss";
    	static 
    	{
    		System.out.println("static code in HttpThread");
    	}
    	
    	public HttpThread() {
			// TODO Auto-generated constructor stub
		}
    	
    	public HttpThread(String name, String url)
    	{
    		this.name = name;
    		this.url = url;
    	}
    	
    	public void run() {
    		
    		try 
    		{
                while(true)
                {
                	CloseableHttpClient httpclient = HttpClients.createDefault();
                    try {
                    	HttpGet httpGet = new HttpGet(url);
                        httpGet.setHeader("User-Agent", "Mozilla/4.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
                        CloseableHttpResponse response1 = httpclient.execute(httpGet);
                        try {
//                            System.out.println(name+ response1.getStatusLine());                           
                            if(!response1.getStatusLine().toString().contains("200 OK"))
                            {
                            	response1.close();
                            	httpclient.close();
                            	break;
                            }
                        } finally {
                            response1.close();
                        }

                    } finally {
                        httpclient.close();
                    }
                    
                    try {
        				Thread.sleep(1000);
        			} catch (Exception e) {
        				// TODO: handle exception
        			}
                }
			} catch (Exception e) {
				// TODO: handle exception
			}

    	}
    }
    
    /**
     * 对candidates进行遍历访问，一个thread遍历访问集合中所有的url
     * @author yan
     *
     */
    static class HttpThreadMultiUrl extends Thread
    {
    	private String name;
    	
    	static 
    	{
    		System.out.println("static code in HttpThreadMultiUrl");
    	}
    	
    	public HttpThreadMultiUrl() {
			// TODO Auto-generated constructor stub
		}
    	
    	public HttpThreadMultiUrl(String name)
    	{
    		this.name = name;
    	}
    	
    	public void run() {
    		if(candidateList.size()==0)
    		{
    			System.out.println("candidateList.size()==0 thread exit");
    		}
    		int url_index = -1;
    		try 
    		{
                while(true)
                {
                	url_index = (url_index+1)%candidateList.size();
                	CloseableHttpClient httpclient = HttpClients.createDefault();
                    try {
                    	HttpGet httpGet = new HttpGet(candidateList.get(url_index));
                        httpGet.setHeader("User-Agent", "Mozilla/4.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
                        CloseableHttpResponse response = httpclient.execute(httpGet);
                        System.out.println(httpGet.getURI().toString()+"-->"+response.getStatusLine());

                        if(response.getStatusLine().toString().contains("200 OK"))
                        {
                        	System.out.println(getVisitValue(EntityUtils.toString(response.getEntity())));
                        }
                        response.close();

                    }catch (Exception e)
                    {
                    	e.printStackTrace();
                    }
                    finally {
                        httpclient.close();
                    }
                    
                    try {
        				Thread.sleep(interval);
        			} catch (Exception e) {
        				// TODO: handle exception
        			}
                }
			} catch (Exception e) {
				// TODO: handle exception
			}

    	}
    	
    	//<span class="link_view" title="阅读次数">26649人阅读</span>
    	private String getVisitValue(String s)
    	{
    		String preString = "span class=\"link_view\" title=\"阅读次数\">";
    		String sufString = "</span>";
    		if(s.contains(preString))
    		{
    			String cutting = s.substring(s.indexOf(preString)+preString.length());
    			String value = cutting.substring(0, cutting.indexOf(sufString));
    			return value;
    		}
			return "0";
    		
    	}
    }
}