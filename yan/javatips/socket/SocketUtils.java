package yan.javatips.socket;

import java.io.IOException;
import java.net.Socket;

public class SocketUtils {
	public static SocketUtils socketInstance= null;
	public static SocketUtils getSocketInstance(){
		if(socketInstance == null){
			socketInstance = new SocketUtils();
		}
		return socketInstance;
	}
	public Socket socket = null;
	
	private SocketUtils(){
		
	}
	
	//��ʼ��
	public void initConnection(){
		if(socket == null){
			socket = new Socket();
		}
	}
	
	//����ָ��
	public void send(byte[] data){
		if(socket == null){
			return ;
		}
		try {
			socket.getOutputStream().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
