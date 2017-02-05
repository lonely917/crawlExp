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
	
	//��ʼ������
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

	//���߳�ץȡͼƬ
	public void doWork()
	{
		//����ָ����Χ��ҳ��
		for(int i=start;i<=end;i++)
		{
			String remoteUrl = baseUrl + i + ".html";
			String html = Tools.getHtml(remoteUrl);
			
			//��ȡ��ҳ��ͼƬ��ַ���ϣ�������ͼƬ img a ������ѡ��
			List<String> picUrls  = Tools.parseUrlsFromHtml(html, "img");
			for (String picUrl : picUrls) {
				
				//�����ָ�����������ӽ������أ�����ָ��ͼƬ���ͣ�����Դ��վ
				if(picUrl.contains("imagetwist")){
					Tools.getPic(picUrl,i+"");//������iĿ¼��
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
