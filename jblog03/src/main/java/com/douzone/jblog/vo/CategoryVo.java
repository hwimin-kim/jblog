package com.douzone.jblog.vo;

import org.springframework.stereotype.Component;

@Component
public class CategoryVo {
	private int no;
	private String name;
	private String description;
	private String blogId;
	private int categoryCount;
	
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	
	public int getCategoryCount() {
		return categoryCount;
	}

	public void setCategoryCount(int categoryCount) {
		this.categoryCount = categoryCount;
	}

	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", description=" + description + ", blogId=" + blogId
				+ ", categoryCount=" + categoryCount + "]";
	}
}
