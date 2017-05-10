/**    
 * 文件名：ee.java    
 *    
 * 版本信息：    
 * 日期：2014-3-18    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.sdhsie.base.util;

import java.util.Comparator;

/**    
 *     
 * 项目名称：web-oa    
 * 类名称：ee    
 * 类描述：    
 * 创建人：anxingtao    
 * 创建时间：2014-3-18 下午03:19:50    
 * 修改人：anxingtao    
 * 修改时间：2014-3-18 下午03:19:50    
 * 修改备注：    
 * @version     
 *     
 */
@SuppressWarnings("rawtypes")
public class sortClass implements Comparator{  
    public int compare(Object arg0,Object arg1){  
        PageData p1 = (PageData)arg0;  
        PageData p2 = (PageData)arg1;  
        int flag = p2.getString("sendDate").compareTo(p1.getString("sendDate"));  
        return flag;  
    }  
}  
