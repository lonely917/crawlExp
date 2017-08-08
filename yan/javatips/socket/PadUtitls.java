package yan.javatips.socket;

/**
 * 完成主要通信交互环节
 * @author yan
 *
 */
public class PadUtitls {

	public static Boolean setConfig(Page1 page1){
		Boolean result = false;
		
		//开始配置-----
		//1. 模板选择
		byte[] dataTemplate = ControlCommand.buildCmdTemplateConfigSet(page1.getTemplate());
		
		
		return result;
	}
}
