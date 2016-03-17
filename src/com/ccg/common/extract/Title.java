package com.ccg.common.extract;

import java.util.ArrayList;
import java.util.List;

public class Title {
	
	String name;
	List<Title> subTile= new ArrayList<Title>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Title> getSubTile() {
		return subTile;
	}
	public void setSubTile(List<Title> subTile) {
		this.subTile = subTile;
	}
}
