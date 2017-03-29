package yan.javatips.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UdpSocket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testUdpSocket();
	}

	public static void testUdpSocket()
	{
		String server_ip = "127.0.0.1";
		int server_port = 8899;
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(8888);
//			socket = new DatagramSocket();//这里会随机端口
			InetSocketAddress remote_sa = new InetSocketAddress(server_ip, server_port);
			InetSocketAddress local_sa = new InetSocketAddress(8888);
//			socket.bind(local_sa);//不同于tcp，可以后续绑定，udp的api在初始化的时候已经绑定(不指定会随机)，这里不能重复绑定
			String s = "hello";
			DatagramPacket dp = new DatagramPacket(s.getBytes(), s.getBytes().length, remote_sa);
			byte data[] = new byte[1024];
			while(true){
				socket.send(dp);
				socket.receive(dp);
				System.out.println(new String(dp.getData(),0,dp.getLength()));
				dp.setData(dp.getData(), 0, dp.getLength());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(socket!=null){
				socket.close();
			}
		}
	}
}
/**
 * udpsocket
 * tcpsocket
 * tcpserversocket
 * java对socket的支持，源码剖析
 * socket通道，阻塞，timeout，创建连接超时以及读超时，
 * 注意server端无监听tcp连接会收到refuse的异常，连接超时并不是这个场景，即便设置连接超时，也是很快出现拒绝连接异常。
 * 读超时则是在从通道读取数据的时候，阻塞等待最大时间。
 * 多次数据发送没有及时读取，下次读取可能读到的是若干次发送数据的集合，以及可读取数据超出读取参数容量范围数据会阻断，多次读取才行。
 * 这就要求一定场景下数据的组合与拆分，打包等，当然可以事先约定一些规则来规避这种情况。
 * java io以及NIO的学习
 */