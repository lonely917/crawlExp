package yan.javatips.socket;

import java.util.Iterator;
import java.util.List;

public class ControlCommand {

	//ģ��
	public static byte TEMPLATE_CONFIG_SET = 0x20;
	public static byte TEMPLATE_CONFIG_GET = 0x21;
	
	//ҳ��
	public static byte Page1_CONFIG_SET = 0x22;
	public static byte Page1_CONFIG_GET = 0x23;
	public static byte Page2_CONFIG_SET = 0x24;
	public static byte Page2_CONFIG_GET = 0x25;
	
	//��������
	public static byte KEY_CFG_SET = 0x26;
	public static byte KEY_CFG_REQ = 0x27;
	
	//�̵�������
	public static byte RLY_CFG_SET = 0x28;
	public static byte RLY_CFG_REQ = 0x29;
	
	//��������
	public static byte RMC_CFG_SET = 0x2a;
	public static byte RMC_CFG_REQ = 0x2b;
	
	//�̵���״̬��������&��ȡ
	public static byte RLY_STAT_SET = 0x40;
	public static byte RLY_STAT_REQ = 0x41;
	
	//����
	public static byte KEY_ACT_CMD = 0x60;//��������
	public static byte RLY_ACT_CMD = 0x61;//�̵�������
	
	//����͸��
	public static byte IFR_TRANS_CMD = 0x62;
	
	
	//����ָ��
	public static byte[] buildCommand(){
		return null;
	}
	
	/**
	 * ģ������ָ��
	 * @param id
	 * @return
	 */
	public static byte[] buildCmdTemplateConfigSet(int id)
	{
		byte[] data = new byte[]{TEMPLATE_CONFIG_SET, 0, 0};
		data[1] = (byte) (id & 0xff);//��λ
		data[2] = (byte) (id >>> 8);//��λ
		return data;
	}
	
	/**
	 * ģ���ȡָ��
	 * @return
	 */
	public static byte[] buildCmdTemplateConfigGet()
	{
		byte[] data = new byte[]{TEMPLATE_CONFIG_GET};
		return data;
	}
	
	/**
	 * ����һ��ҳ��������Ϣ
	 * @param page_id
	 * @param page_num
	 * @param map
	 * @return
	 */
	public static byte[] buildCmdPage1ConfigSet(char page_id, char page_num, char[]map){
		byte[] data = new byte[5+page_num];
		data[0] = Page1_CONFIG_SET;
		data[1] = (byte) (page_id & 0xff);
		data[2] = (byte) (page_id >>> 8);
		data[3] = (byte) page_num;
		data[4] = (byte) (page_num >>> 8);
		for (int i = 0,j=5; i < map.length; i++) {
			data[j++] = (byte) map[i];//ȡ��λ
		}
		return data;
	}

	/**
	 * ��ȡһ��ҳ��������Ϣ
	 * @return
	 */
	public static byte[] buildCmdPage1ConfigGet()
	{
		byte[] data = new byte[]{Page1_CONFIG_GET};
		return data;
	}
	
	/**
	 * ���ö���ҳ��
	 * @param page_id
	 * @param page_num
	 * @param map
	 * @param isRemote �Ƿ�ң���� 1ң����  0�̵���
	 * @return
	 */
	public static byte[] buildCmdPage2ConfigSet(char page_id, char page_num, char[]map, Boolean isRemote)
	{
		byte[] data = new byte[6+page_num];
		data[0] = Page2_CONFIG_SET;
		data[1] = (byte) (page_id & 0xff);
		data[2] = (byte) (page_id >>> 8);
		data[3] = (byte) (isRemote==true?1:0);//true ң����1  false �̵��� 0
		data[4] = (byte) page_num;
		data[5] = (byte) (page_num >>> 8 );
		for (int i = 0,j=6; i < map.length; i++) {
			data[j++] = (byte) map[i];//ȡ��λ
		}
		return data;
	}
	
	/**
	 * ��ȡ����ҳ��������Ϣ��ָ������
	 * @param page_id
	 * @return
	 */
	public static byte[] buildCmdPage2ConfigGet(char page_id)
	{
		byte[] data = new byte[]{Page2_CONFIG_GET, (byte) (page_id&0xff), (byte) (page_id>>>8)};
		return data;
	}
	
	/**
	 * ��������
	 * @param key_id
	 * @param icon_id
	 * @param rly_id
	 * @return
	 */
	public static byte[] buildCmdKeyConfigSet(char key_id, char icon_id, char rly_id)
	{
		byte[] data = new byte[7];
		data[0] = KEY_CFG_SET;
		data[1] = (byte) key_id;
		data[2] = (byte) (key_id>>>8);
		data[3] = (byte) icon_id;
		data[4] = (byte) (icon_id>>>8);
		data[5] = (byte) rly_id;
		data[6] = (byte) (rly_id>>>8);
		return data;
	}
	
	/**
	 * �������û�ȡ
	 * @param key_id
	 * @return
	 */
	public static byte[] buildCmdKeyConfigGet(char key_id)
	{
		byte[] data = new byte[]{KEY_CFG_REQ, (byte) (key_id&0xff), (byte) (key_id>>>8)};
		return data;
	}
	
	/**
	 * ң��������
	 * @param remote_id
	 * @param remote_type
	 * @return
	 */
	public static byte[] buildCmdRemoteConfigSet(char remote_id, char remote_type){
		byte[] data = new byte[4];
		data[0] = RMC_CFG_SET;
		data[1] = (byte) remote_id;
		data[2] = (byte) (remote_id>>>8);
		data[3] = (byte) remote_type;    
		return data;		
	}
	
	/**
	 * ң�������ò�ѯ
	 * @param id
	 * @return
	 */
	public static byte[] buildCmdRemoteConfigReq(char id)
	{
		byte[] data = new byte[]{RMC_CFG_REQ, (byte) (id&0xff), (byte) (id>>>8)};
		return data;
	}
	
	/**
	 * �̵���״̬���ã�ע��̵�����ʵ��ţ������0��ʼ
	 * @param states
	 * @return
	 */
	public static byte[] buildCmdRlyStatesSet(List<Byte> states)
	{
		byte[] data = new byte[1+3*states.size()];
		data[0] = RLY_STAT_SET;
		for(int i=0,j=1;i<states.size();i++)
		{
			data[j++] = (byte) i;
			data[j++] = (byte) (i>>>8);
			data[j++] = states.get(i);
		}
		return data;
	}
	
	/**
	 * �̵���״̬������ȡ
	 * @param ids
	 * @return
	 */
	public static byte[] buildCmdRlyStatesReq(List<Integer> ids)
	{
		byte[] data = new byte[1+2*ids.size()];
		data[0] = RLY_STAT_REQ;
		for(int i=0,j=1;i<ids.size();i++)
		{
			data[j++] = (byte) ids.get(i).intValue();
			data[j++] = (byte) (ids.get(i).intValue()>>>8);
		}
		return data;
	}
	
	/**
	 * ��������
	 * @param id
	 * @param type
	 * @return
	 */
	public static byte[] buildCmdKeyAct(char id, byte type)
	{
		byte[] data = new byte[4];
		data[0] = KEY_ACT_CMD;
		data[1] = (byte) id;
		data[2] = (byte) (id>>>8);
		data[3] = type;
		return data;
	}
	
	/**
	 * �̵�������
	 * @param id
	 * @param type
	 * @return
	 */
	public static byte[] buildCmdRlyAct(char id, byte type)
	{
		byte[] data = new byte[4];
		data[0] = RLY_ACT_CMD;
		data[1] = (byte) id;
		data[2] = (byte) (id>>>8);
		data[3] = type;
		return data;
	}
	
	/**
	 * ����͸��
	 * @param sub_data
	 * @return
	 */
	public static byte[] buildCmdIfrTrans(byte[] sub_data)
	{
		byte[] data = new byte[1+sub_data.length];
		data[0] = IFR_TRANS_CMD;
		System.arraycopy(sub_data, 0, data, 1, sub_data.length);
		return data;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
