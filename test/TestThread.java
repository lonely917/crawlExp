package test;

public class TestThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	
	/**
	 * �Զ����̵߳����ַ�ʽ
	 */
	public static void testThread()
	{
		//����һ��runnableʵ��
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("create thread by passing a runnable target !");
			}
		}).start();
		
		//�̳�thread�࣬��дthread��run����
		new Thread(){
			@Override
			public void run() {
				System.out.println("create thread by Override run method !");
			};
		}.start();
	}
}
