package yan.javatips.socket;

/**
 * �����Ҫͨ�Ž�������
 * @author yan
 *
 */
public class PadUtitls {

	public static Boolean setConfig(Page1 page1){
		Boolean result = false;
		
		//��ʼ����-----
		//1. ģ��ѡ��
		byte[] dataTemplate = ControlCommand.buildCmdTemplateConfigSet(page1.getTemplate());
		
		
		return result;
	}
}
