package test;

public class TestThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	
	/**
	 * 自定义线程的两种方式
	 */
	public static void testThread()
	{
		//传入一个runnable实例
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("create thread by passing a runnable target !");
			}
		}).start();
		
		//继承thread类，重写thread的run方法
		new Thread(){
			@Override
			public void run() {
				System.out.println("create thread by Override run method !");
			};
		}.start();
	}
}
