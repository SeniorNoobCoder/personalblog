package com.sdhsie.base.util;

import java.util.List;

/**
 * 分页对象
 * @author Administrator
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class PagerList<T> {
	/**
	 * 分页的大小
	 */
	private int size;
	/**
	 * 分页的起始大小
	 */
	private int offset;
	/**
	 * 分页的起始页
	 */
	private int index;
	/**
	 * 总记录数
	 */
	private long total;
	/**
	 * 总页数
	 */
	private long totalIndex;
	/**
	 * 分页的数据
	 */
	private List<T> datas;
	
	private List resource;
	
	/**
	 * 分页的sqlcount，查询数量
	 */
	private String sqlCount;
	
	/**
	 * 分页的sqlpage，查询数据
	 */
	private String sqlPage;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	public String getSqlCount() {
		return sqlCount;
	}
	public void setSqlCount(String sqlCount) {
		this.sqlCount = sqlCount;
	}
	public String getSqlPage() {
		return sqlPage;
	}
	public void setSqlPage(String sqlPage) {
		this.sqlPage = sqlPage;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public long getTotalIndex() {
		return totalIndex;
	}
	public void setTotalIndex(long totalIndex) {
		this.totalIndex = totalIndex;
	}

	public List getResource() {
		return resource;
	}
	public void setResource(List resource) {
		this.resource = resource;
	}
}
