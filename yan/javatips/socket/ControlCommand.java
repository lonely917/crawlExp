package yan.javatips.socket;

import java.util.Iterator;
import java.util.List;

public class ControlCommand {

	//模板
	public static byte TEMPLATE_CONFIG_SET = 0x20;
	public static byte TEMPLATE_CONFIG_GET = 0x21;
	
	//页面
	public static byte Page1_CONFIG_SET = 0x22;
	public static byte Page1_CONFIG_GET = 0x23;
	public static byte Page2_CONFIG_SET = 0x24;
	public static byte Page2_CONFIG_GET = 0x25;
	
	//按键配置
	public static byte KEY_CFG_SET = 0x26;
	public static byte KEY_CFG_REQ = 0x27;
	
	//继电器配置
	public static byte RLY_CFG_SET = 0x28;
	public static byte RLY_CFG_REQ = 0x29;
	
	//红外配置
	public static byte RMC_CFG_SET = 0x2a;
	public static byte RMC_CFG_REQ = 0x2b;
	
	//继电器状态批量设置&获取
	public static byte RLY_STAT_SET = 0x40;
	public static byte RLY_STAT_REQ = 0x41;
	
	//动作
	public static byte KEY_ACT_CMD = 0x60;//按键动作
	public static byte RLY_ACT_CMD = 0x61;//继电器动作
	
	//红外透传
	public static byte IFR_TRANS_CMD = 0x62;
	
	
	//构建指令
	public static byte[] buildCommand(){
		return null;
	}
	
	/**
	 * 模板设置指令
	 * @param id
	 * @return
	 */
	public static byte[] buildCmdTemplateConfigSet(int id)
	{
		byte[] data = new byte[]{TEMPLATE_CONFIG_SET, 0, 0};
		data[1] = (byte) (id & 0xff);//低位
		data[2] = (byte) (id >>> 8);//高位
		return data;
	}
	
	/**
	 * 模板获取指令
	 * @return
	 */
	public static byte[] buildCmdTemplateConfigGet()
	{
		byte[] data = new byte[]{TEMPLATE_CONFIG_GET};
		return data;
	}
	
	/**
	 * 设置一级页面配置信息
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
			data[j++] = (byte) map[i];//取低位
		}
		return data;
	}

	/**
	 * 获取一级页面配置信息
	 * @return
	 */
	public static byte[] buildCmdPage1ConfigGet()
	{
		byte[] data = new byte[]{Page1_CONFIG_GET};
		return data;
	}
	
	/**
	 * 设置二级页面
	 * @param page_id
	 * @param page_num
	 * @param map
	 * @param isRemote 是否遥控器 1遥控器  0继电器
	 * @return
	 */
	public static byte[] buildCmdPage2ConfigSet(char page_id, char page_num, char[]map, Boolean isRemote)
	{
		byte[] data = new byte[6+page_num];
		data[0] = Page2_CONFIG_SET;
		data[1] = (byte) (page_id & 0xff);
		data[2] = (byte) (page_id >>> 8);
		data[3] = (byte) (isRemote==true?1:0);//true 遥控器1  false 继电器 0
		data[4] = (byte) page_num;
		data[5] = (byte) (page_num >>> 8 );
		for (int i = 0,j=6; i < map.length; i++) {
			data[j++] = (byte) map[i];//取低位
		}
		return data;
	}
	
	/**
	 * 获取二级页面配置信息的指令生成
	 * @param page_id
	 * @return
	 */
	public static byte[] buildCmdPage2ConfigGet(char page_id)
	{
		byte[] data = new byte[]{Page2_CONFIG_GET, (byte) (page_id&0xff), (byte) (page_id>>>8)};
		return data;
	}
	
	/**
	 * 按键配置
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
	 * 按键配置获取
	 * @param key_id
	 * @return
	 */
	public static byte[] buildCmdKeyConfigGet(char key_id)
	{
		byte[] data = new byte[]{KEY_CFG_REQ, (byte) (key_id&0xff), (byte) (key_id>>>8)};
		return data;
	}
	
	/**
	 * 遥控器配置
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
	 * 遥控器配置查询
	 * @param id
	 * @return
	 */
	public static byte[] buildCmdRemoteConfigReq(char id)
	{
		byte[] data = new byte[]{RMC_CFG_REQ, (byte) (id&0xff), (byte) (id>>>8)};
		return data;
	}
	
	/**
	 * 继电器状态设置，注意继电器其实编号，这里从0开始
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
	 * 继电器状态批量获取
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
	 * 按键动作
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
	 * 继电器动作
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
	 * 红外透传
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
