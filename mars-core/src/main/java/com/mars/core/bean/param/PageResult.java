package com.mars.core.bean.param;
import java.util.List;
import java.util.Map;

public class PageResult<T> extends DataResult {

	private Long total;

	private List<T> list;

	public PageResult(Long total, List<T> list) {
		this.total = total;
		this.list = list;
	}

    public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
