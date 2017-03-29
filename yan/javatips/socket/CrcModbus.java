package yan.javatips.socket;

/**
 * crc16 modbus校验
 * 初值0xff
 * 多项式(x^16+x^15+x^2+1) 8005(反序A001)
 * 根据开源libcrc的c版本进行java平台移植，https://github.com/lammertb/libcrc
 * @author yan
 */
public class CrcModbus {
		
	final static int CRC_START_MODBUS = 0xFFFF;
	final static int CRC_POLY_16 = 0xA001;
	static int[] crc_tab16 = new int[256];
	static boolean crc_tab16_init = false;
	
	public static int crc_modbus( byte[]input_str, int num_bytes ) {

		int crc;
		int i;

		if ( ! crc_tab16_init ) init_crc16_tab();

		crc = CRC_START_MODBUS;

		if ( input_str != null ) 
			for (i = 0; i < num_bytes; i++) {
				crc = (char) ((crc >> 8) ^ crc_tab16[(crc ^ input_str[i]) & 0x00FF]);
			}

		return crc;

	}
	
	static void init_crc16_tab( ) {

		int i;
		int j;
		int crc;
		int c;

		for (i=0; i<256; i++) {

			crc = 0;
			c   = i;

			for (j=0; j<8; j++) {

				if ( ((crc ^ c) & 0x0001)!=0 ) 
					crc = ( crc >> 1 ) ^ CRC_POLY_16;
				else                     
					crc =   crc >> 1;

				c = c >> 1;
			}

			crc_tab16[i] = crc;
		}

		crc_tab16_init = true;

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		byte[] data = new byte[]{0x01,0x02,0x03};
		byte[] data = "123456789".getBytes();
		int a = crc_modbus(data, data.length);
		System.out.println(Integer.toHexString(a));
		assert(a == 0x4b37);
		
		data = new byte[]{0x01,0x02,0x03};
		a = crc_modbus(data, data.length);
		System.out.println(Integer.toHexString(a));
		assert(a == 0x6161);
	}

}
