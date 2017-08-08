package yan.javatips.socket;

import java.util.List;

public class Page1 {
	private int template;
	private List<Page2> page2s;
	
	public int getTemplate() {
		return template;
	}
	public void setTemplate(int template) {
		this.template = template;
	}
	public List<Page2> getPage2s() {
		return page2s;
	}
	public void setPage2s(List<Page2> page2s) {
		this.page2s = page2s;
	}
}
