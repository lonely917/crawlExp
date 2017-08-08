package yan.javatips.socket;

import java.sql.Timestamp;
import java.util.Date;

public class UtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Date date = new Date();
		Timestamp tsp = new Timestamp(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
		System.out.println(date.toString());
		System.out.println(tsp.toString());
	}

}
