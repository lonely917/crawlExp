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
//			socket = new DatagramSocket();//���������˿�
			InetSocketAddress remote_sa = new InetSocketAddress(server_ip, server_port);
			InetSocketAddress local_sa = new InetSocketAddress(8888);
//			socket.bind(local_sa);//��ͬ��tcp�����Ժ����󶨣�udp��api�ڳ�ʼ����ʱ���Ѿ���(��ָ�������)�����ﲻ���ظ���
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
 * java��socket��֧�֣�Դ������
 * socketͨ����������timeout���������ӳ�ʱ�Լ�����ʱ��
 * ע��server���޼���tcp���ӻ��յ�refuse���쳣�����ӳ�ʱ��������������������������ӳ�ʱ��Ҳ�Ǻܿ���־ܾ������쳣��
 * ����ʱ�����ڴ�ͨ����ȡ���ݵ�ʱ�������ȴ����ʱ�䡣
 * ������ݷ���û�м�ʱ��ȡ���´ζ�ȡ���ܶ����������ɴη������ݵļ��ϣ��Լ��ɶ�ȡ���ݳ�����ȡ����������Χ���ݻ���ϣ���ζ�ȡ���С�
 * ���Ҫ��һ�����������ݵ�������֣�����ȣ���Ȼ��������Լ��һЩ������������������
 * java io�Լ�NIO��ѧϰ
 */