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

	//Ĭ�ϵĺ�ѡurl�б�
	public static String[] candidates = {
								"http://blog.csdn.net/java_student09/article/details/52139185",
								"http://blog.csdn.net/java_student09/article/details/52139344"
								} ;
	
	//��ѡurl�б����ļ���ʼ����ã�û���ļ���Ӻ�ѡ��������
	public static List<String> candidateList = new ArrayList<String>();
	
	//ˢ��Ƶ��Ĭ��ֵ
	public static int interval = 1000; 
	
	//��վǰ׺ ѡ��
	public static String baseUrl = ""; 
	
	//��̬����飬�������ļ���ȡurl���ϣ�������ص�ʱ�����
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
					//���� #���˱���
					if(url.startsWith("#")) continue; //
					
					//ˢ��Ƶ�� @��ʾˢ�¼��
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
			//�ļ���������Ӻ�ѡ����������
			candidateList = Arrays.asList(candidates);
			System.out.println("no config file using default!");
		}
		
		//���Ŀ��url����
		for (String url : candidateList) {
			System.out.println(url);
		}
		
		//���ˢ��Ƶ��
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
     * ��ĳһ�̶�url���з��ʣ�һ��thread��һ��url
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
     * ��candidates���б������ʣ�һ��thread�������ʼ��������е�url
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
    	
    	//<span class="link_view" title="�Ķ�����">26649���Ķ�</span>
    	private String getVisitValue(String s)
    	{
    		String preString = "span class=\"link_view\" title=\"�Ķ�����\">";
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