package yan.javatips.http;

import java.io.IOException;
import java.lang.reflect.Method;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TestBase64 {

	public static String content = "YmiBIprB5/uJ4tKsqpRpzrdoMzaz8D281XSoBvzywAk=";
	public static String src = "{\"TotalCount\":\"12402\"}";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			 String content_result = new String(decode(content), "gb2312");
			 System.out.println(content_result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
		

		try {
			System.out.println(encode(src.getBytes()));
			System.out.println(encodeBase64(src.getBytes()));
			System.out.println(encodeBase64(src.getBytes("utf-8")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/***
	 * encode by Base64
	 */
	public static String encodeBase64(byte[] input) throws Exception {
		Class clazz = Class
				.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod = clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		Object retObj = mainMethod.invoke(null, new Object[] { input });
		return (String) retObj;
	}

	/***
	 * decode by Base64
	 */
	public static byte[] decodeBase64(String input) throws Exception {
		Class clazz = Class
				.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod = clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		Object retObj = mainMethod.invoke(null, input);
		return (byte[]) retObj;
	}

	/**
	 * ½âÂë
	 * @param bstr
	 * @return
	 */
	public static String encode(byte[] bstr) {
		return new BASE64Encoder().encode(bstr);
	}

	/**
	 * ½âÂë
	 * 
	 * @param str
	 * @return string
	 */
	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}
}
