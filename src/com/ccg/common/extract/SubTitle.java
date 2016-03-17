package com.ccg.common.extract;

public class SubTitle {
	
	String name;
	
	String preFix;
	
	int beginIndex;
	int endIndex;
	int level;
	int page;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPreFix() {
		return preFix;
	}
	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("; ").append(this.getBeginIndex() + ", " + this.endIndex);
		return sb.toString();
	}
}
