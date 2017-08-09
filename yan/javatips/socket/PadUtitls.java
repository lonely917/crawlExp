package yan.javatips.socket;

/**
 * 完成主要通信交互环节
 * @author yan
 *
 */
public class PadUtitls {
	public static SocketUtils socketUtils = SocketUtils.getSocketInstance();

	public static void setConfig(Page1 page1){
		Boolean result = false;
		
		//开始配置-----
		//1. 模板选择
		byte[] dataTemplate = ControlCommand.buildCmdTemplateConfigSet(page1.getTemplate());
		socketUtils.send(dataTemplate);
		
		//2.一级页面设置 页面图标
		
	}
	
	//模板选择
	public static void setTemplate(Page1 page1){
		byte[] dataTemplate = ControlCommand.buildCmdTemplateConfigSet(page1.getTemplate());
		socketUtils.send(dataTemplate);
	}
	
	//一级页面设置
	public static void setPage1(Page1 page1){
		int num = page1.getPage2s().size()<=6?page1.getPage2s().size():6;
		char[] map = new char[num]; //随便生成一个 页面id是两字节，注意需要修改指令构造函数
		for(int i=0;i<num && i<page1.getPage2s().size();i++){
			map[i] = (char) page1.getPage2s().get(i).getPage_id();
		}
		byte[] dataTemplate = ControlCommand.buildCmdPage1ConfigSet((char)page1.getTemplate(),(char) map.length, map);
		socketUtils.send(dataTemplate);
	}
	
	//一级页面图标设置
	public static void setPage1Icon(Page1 page1){
//		char[] map = new char[6];//
//		byte[] dataTemplate = ControlCommand.buildCmdPage2ConfigSet((char)page1.getTemplate(),(char) map.length, map);
//		socketUtils.send(dataTemplate);
	}
	
	//二级页面设置
	public static void setPage2(Page1 page1){
		for (Page2 page : page1.getPage2s()) {
			switch (page.getPage_type()) {
			case 0:
				setPage2Rly(page);
				break;
			case 1:
				setPage2Remote(page);
				break;
			default:
				break;
			}
			
		}
	}
	
	//二级页面设置 继电器
	public static void setPage2Rly(Page2 page){
		int num = page.getKeyList().size();
		char[] map = new char[num];
		for(int i=0;i<num;i++){
			map[i] = (char)page.getKeyList().get(i).getKey_id(); //按键id就当元素id
		}
		byte[] dataTemplate = ControlCommand.buildCmdPage2ConfigSet((char)page.getPage_id(),(char)num,map,false);
		socketUtils.send(dataTemplate);
	}
	
	//二级页面设置 遥控器
	public static void setPage2Remote(Page2 page){
		int num = page.getRmcList().size();
		char[] map = new char[num];
		for(int i=0;i<num;i++){
			map[i] = (char)page.getRmcList().get(i).getRmc_id(); //按键id就当元素id
		}
		byte[] dataTemplate = ControlCommand.buildCmdPage2ConfigSet((char)page.getPage_id(),(char)num,map,false);
		socketUtils.send(dataTemplate);
	}
	
}
