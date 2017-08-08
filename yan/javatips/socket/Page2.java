package yan.javatips.socket;

import java.util.List;

public class Page2 {
	private int page_id;
	private int page_type;
	private List<ElementKey> keyList;
	private List<ElementRmc> rmcList;
	
	public int getPage_id() {
		return page_id;
	}
	public void setPage_id(int page_id) {
		this.page_id = page_id;
	}
	public int getPage_type() {
		return page_type;
	}
	public void setPage_type(int page_type) {
		this.page_type = page_type;
	}
	public List<ElementKey> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<ElementKey> keyList) {
		this.keyList = keyList;
	}
	public List<ElementRmc> getRmcList() {
		return rmcList;
	}
	public void setRmcList(List<ElementRmc> rmcList) {
		this.rmcList = rmcList;
	}
}
