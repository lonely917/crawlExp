package yan.javatips.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class TcpSocket {

	public static void main(String args[]){
		testTcpSocket2();
	}
	
	public static void testTcpSocket()
	{
		String server_ip = "127.0.0.1";
		int server_port = 8899; 
		try {
			Socket socket = new Socket(server_ip, server_port, null, 8888);			
//			DataInputStream dis = new DataInputStream(socket.getInputStream());//DataInputStream封装一层，提供更过操作
			
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			socket.setSoTimeout(50000);//单位ms，设置读超时时间限制
			
			byte data[] = new byte[1024];
			int len = 0;
			while(true){ 
				len = is.read(data);//data没有清空，每次只是读入的数据进行覆盖
				if(len == -1)//数据读完，远端连接断开
					break;
				System.out.println(new String(data, 0, len));
				os.write(data,0,len);
			}
			
			os.close();
			is.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testTcpSocket2()
	{
		String server_ip = "127.0.0.1";
		int server_port = 8899; 
		try {
			Socket socket = new Socket();
			SocketAddress sa_remote = new InetSocketAddress(server_ip, server_port); 
			SocketAddress sa_local = new InetSocketAddress( 8888);
			socket.bind(sa_local);
			socket.connect(sa_remote,0);
			
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			byte data[] = new byte[1024];
			int len = 0;
			while(true){ 
				len = is.read(data);//data没有清空，每次只是读入的数据进行覆盖
				if(len == -1)//数据读完，远端连接断开
					break;
				System.out.println(new String(data, 0, len));
				os.write(data,0,len);
			}
			
			os.close();
			is.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
