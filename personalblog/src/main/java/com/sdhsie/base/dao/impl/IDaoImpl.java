/**    
 * 文件名：IDao.java    
 *    
 * 版本信息：    
 * 日期：2014-2-10    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.sdhsie.base.dao.impl;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sdhsie.base.dao.IDao;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.PagerList;
import com.sdhsie.base.util.SystemContext;

/**    
 *     
 * 项目名称：web-core    
 * 类名称：IDao    
 * 类描述：baseDao的实现    
 * 创建人：anxingtao    
 * 创建时间：2014-2-10 上午09:27:15    
 * 修改人：anxingtao    
 * 修改时间：2014-2-10 上午09:27:15    
 * 修改备注：    
 * @version     
 *     
 */
@SuppressWarnings({"unchecked"})
@Repository
public class IDaoImpl implements IDao {

	
	/**
	 * 注入相应的sqlSessionTemplate
	 */
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	  
	  /**
	   * 保存单一对象
	   */
	  public Object save(String str, Object obj)throws Exception{
	    return this.sqlSessionTemplate.insert(str, obj);
	  }

	  /**
	   * saveBatch :  保存list列表
	   * @param  name    
	   * @param  @return    
	   * @Exception 异常对象  
	   * @date 2014-2-10
	   * @@version
	   */
	public void saveBatch(String str, List objs)throws Exception{
	    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();

	    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	    try {
	      if (objs != null) {
	        int i = 0; for (int size = objs.size(); i < size; i++) {
	          sqlSession.insert(str, objs.get(i));
	        }
	        sqlSession.flushStatements();
	        sqlSession.commit();
	        sqlSession.clearCache();
	      }
	    } finally {
	      sqlSession.close();
	    }
	  }

	  /**
	   * saveBatch :  保存对象数组集合
	   * @param  name    
	   * @param  @return    
	   * @Exception 异常对象  
	   * @date 2014-2-10
	   * @@version
	   */
	  public void saveBatch(String str, Object[] objs)throws Exception{
	    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();

	    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	    try {
	      if ((objs != null) && (objs.length > 0)) {
	        for (Object obj : objs) {
	          sqlSession.insert(str, obj);
	        }
	        sqlSession.flushStatements();
	        sqlSession.commit();
	        sqlSession.clearCache();
	      }
	    } finally {
	      sqlSession.close();
	    }
	  }

	  /**
	   * 更新单一对象
	   */
	  public Object update(String str, Object obj)throws Exception{
	    return Integer.valueOf(this.sqlSessionTemplate.update(str, obj));
	  }

	  /**
	   * updateBatch :  更新对象的list列表
	   * @param  name    
	   * @param  @return    
	   * @Exception 异常对象  
	   * @date 2014-2-10
	   * @@version
	   */
	  public void updateBatch(String str, List objs)throws Exception{
	    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();

	    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	    try {
	      if (objs != null) {
	        int i = 0; for (int size = objs.size(); i < size; i++) {
	          sqlSession.update(str, objs.get(i));
	        }
	        sqlSession.flushStatements();
	        sqlSession.commit();
	        sqlSession.clearCache();
	      }
	    } finally {
	      sqlSession.close();
	    }
	  }

	  /**
	   * updateBatch :  更新对象数组的集合
	   * @param  name    
	   * @param  @return    
	   * @Exception 异常对象  
	   * @date 2014-2-10
	   * @@version
	   */
	  public void updateBatch(String str, Object[] objs)throws Exception{
	    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();

	    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	    try {
	      if ((objs != null) && (objs.length > 0)) {
	        for (Object obj : objs) {
	          sqlSession.update(str, obj);
	        }
	        sqlSession.flushStatements();
	        sqlSession.commit();
	        sqlSession.clearCache();
	      }
	    } finally {
	      sqlSession.close();
	    }
	  }

	  /**
	   * deleteBatch :  根据list集合删除对象
	   * @param  name    
	   * @param  @return    
	   * @Exception 异常对象  
	   * @date 2014-2-10
	   * @@version
	   */
	  public void deleteBatch(String str, List objs)throws Exception{
	    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();

	    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	    try {
	      if (objs != null) {
	        int i = 0; for (int size = objs.size(); i < size; i++) {
	          sqlSession.delete(str, objs.get(i));
	        }
	        sqlSession.flushStatements();
	        sqlSession.commit();
	        sqlSession.clearCache();
	      }
	    } finally {
	      sqlSession.close();
	    }
	  }

	  /**
	   * deleteBatch :  根据数组删除对象
	   * @param  name    
	   * @param  @return    
	   * @Exception 异常对象  
	   * @date 2014-2-10
	   * @@version
	   */
	  public void deleteBatch(String str, Object[] objs)throws Exception{
	    SqlSessionFactory sqlSessionFactory = this.sqlSessionTemplate.getSqlSessionFactory();

	    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	    try {
	      if ((objs != null) && (objs.length > 0)) {
	        for (Object obj : objs) {
	          sqlSession.delete(str, obj);
	        }
	        sqlSession.flushStatements();
	        sqlSession.commit();
	        sqlSession.clearCache();
	      }
	    } finally {
	      sqlSession.close();
	    }
	  }

	  /**
	   * 删除单一对象
	   */
	  public Object delete(String str, Object obj)throws Exception{
	    return Integer.valueOf(this.sqlSessionTemplate.delete(str, obj));
	  }

	  /**
	   * 查询单一对象
	   */
	  public Object findForObject(String str, Object obj)throws Exception{
	    return this.sqlSessionTemplate.selectOne(str, obj);
	  }

	  /**
	   * 查询对象数组
	   */
	  public Object findForList(String str, Object obj)throws Exception{
	    return this.sqlSessionTemplate.selectList(str, obj);
	  }

	  /**
	   * 查询map对象
	   */
	  public Object findForMap(String str, Object obj, String key, String value)throws Exception{
	    return this.sqlSessionTemplate.selectMap(str, obj, key);
	  }

	  /**
	   * 分页查询对象集合
	   */
	  public PagerList findForPageList(PagerList pager, PageData pd) throws Exception
	  {
		
		  if(pd.getString("index")==null||"".equals(pd.getString("index"))){
			  pager.setIndex(0);
		  }else{
			  pager.setIndex(Integer.parseInt(pd.getString("index")));
		  }
		  
		  if(pd.getString("mysize")==null||"".equals(pd.getString("mysize"))){
			  pager.setSize(0);
		  }else{
			  pager.setSize(Integer.parseInt(pd.getString("mysize")));
		  }
		  
		Integer size ;
		Integer index;
		if(pager.getSize()==0){
			size = SystemContext.getPageSize();
		}else{
			size = pager.getSize();
		}
		
		if(pager.getIndex()==0){
			index = SystemContext.getPageIndex();
		}else{
			index = pager.getIndex();
		}
	    pd.put("offset", (index-1)*size);
	    pd.put("size", size);
	    pd.put("Index", index);
	    pd.put("offend", index*size);
	    pager.setOffset(index*size);
	    pager.setSize(size);
	    pager.setIndex(index);
	    Object count = this.sqlSessionTemplate.selectOne(pager.getSqlCount(), pd);
	    int totol = count == null ? 0 : ((Integer)count).intValue();
	    
	    int totalIndex = totol/size+1;
	    pager.setTotalIndex(totalIndex);
	    pager.setTotal(totol);
	    if (totol != 0) {
	      List lists = this.sqlSessionTemplate.selectList(pager.getSqlPage(), pd);
	      pager.setDatas(lists);
	    }
	    return pager;
	  }


	}