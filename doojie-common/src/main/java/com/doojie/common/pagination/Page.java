package com.doojie.common.pagination;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1450839884161327273L;
	/**
	 * 当前页，默认第一页
	 */
	private int currentPage = 1;
	
	
	/**
	 * 每页记录数，默认20条
	 */
	private int pageSize = 10;
	/**
	 * 总记录数
	 */
	private int totalRecord;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 每页记录结果
	 */
	private List<T> results;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		int totalPage = 0;
		if (totalRecord % pageSize == 0) {
			totalPage = totalRecord / pageSize;
		}
		else {
			totalPage = totalRecord / pageSize + 1;
		}
		this.totalPage = totalPage;
		this.totalRecord = totalRecord;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	

}
