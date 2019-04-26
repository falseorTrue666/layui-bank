package com.mr.utils;

public class Page {
	// 当前页(页数)
	private Integer page = 1;

	// 每页条数
	private Integer rows = 10;

	// 列名
	private String sort;

	// 升序降序
	private String order = "desc";

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

}
