package com.boyaa.mf.vo;

@SuppressWarnings("all")
public class ResourcesTree {
	private int id;
	private String name;
	private String url;
	private int parentId;
	private boolean checked;
	private boolean drag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDrag() {
		//return drag;
		return true;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

}
