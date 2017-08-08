package yan.javatips.socket;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

public class Transform {

	//转义
	public static byte[] transform(byte[] data){
		List<Byte> bytesList = new ArrayList<Byte>();
		for (int i = 0; i < data.length; i++) {
			switch (data[i]) {
			case 0x24://'$'
				bytesList.add((byte) 0xdb);
				bytesList.add((byte) 0xdc);
				break;
			case 0x2a://'*'
				bytesList.add((byte) 0xdc);
				bytesList.add((byte) 0xdb);
				break;
			case (byte) 0xdb:
				bytesList.add((byte) 0xdb);
				bytesList.add((byte) 0xdb);
				break;
			case (byte) 0xdc:
				bytesList.add((byte) 0xdc);
				bytesList.add((byte) 0xdc);
				break;
			default:
				bytesList.add(data[i]);
				break;
			}
		}
		byte[]tdata = new byte[bytesList.size()];
		for(int i=0;i<tdata.length;i++){
			tdata[i] = bytesList.get(i);
		}
		return tdata;
	}
	
	//反转义
	public static byte[] transform_back(byte[] data){
		List<Byte> bytesList = new ArrayList<Byte>();
		for (int i = 0; i < data.length; i++) {
			switch (data[i]) {
			case (byte) 0xdb:
				{
					if(i+1>=data.length) //数据有误
					{
						return new byte[0];
					}
					else
					{
						if(data[i+1] == 0xdb)
						{
							bytesList.add((byte) 0xdb);//dbdb
							i++;
						}
						else if(data[i+1] == 0xdc)
						{
							bytesList.add((byte) 0x24);//dbdc-0x24
							i++;
						}
						else
						{
							//数据有误
							return new byte[0];
						}
					}
				}
				break;
			case (byte) 0xdc:
				{
					if(i+1>=data.length) //数据有误
					{
						return new byte[0];
					}
					else
					{
						if(data[i+1] == 0xdc)
						{
							bytesList.add((byte) 0xdc);//dcdc
							i++;
						}
						else if(data[i+1] == 0xdb)
						{
							bytesList.add((byte) 0x2a);//dcdb-0x2a
							i++;
						}
						else
						{
							//数据有误
							return new byte[0];
						}
					}
				}
				break;
			default:
				bytesList.add(data[i]);
				break;
			}
		}
		
		byte[]tdata = new byte[bytesList.size()];
		for(int i=0;i<tdata.length;i++){
			tdata[i] = bytesList.get(i);
		}
		return tdata;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] input = new byte[]{0x24, 0x2a, (byte) 0xdb, (byte) 0xdc, 0x00, 0x01};
		byte[] output = transform(input);
		for (int i = 0; i < output.length; i++) {
			System.out.print(Integer.toHexString(output[i]&0x000000ff)+"--");
		}
		
		byte reverse[] = transform_back(output);
		System.out.println();
		for (int i = 0; i < output.length; i++) {
			System.out.print(Integer.toHexString(output[i]&0x000000ff)+"--");
		}
	}

}
