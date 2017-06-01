package com.sdhsie.port.partner.service;

import java.util.List;

import com.sdhsie.base.util.PageData;

public interface PartnerUserService {
	/**
	 * 
	 * @Title: findList
	 * @Description: 查询信息列表
	 * @param @param pd
	 * @param @return 设定文件
	 * @return List<PageData> 返回类型
	 * @throws
	 */
	public List<PageData> findList(PageData pd);


	public PageData findLoginInfo(PageData pd);


	public void updateUser(PageData pd);

}
