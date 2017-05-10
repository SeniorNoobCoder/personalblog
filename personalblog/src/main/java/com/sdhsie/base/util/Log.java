/**    
 * 文件名：Log.java    
 *    
 * 版本信息：    
 * 日期：2014-1-3    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.sdhsie.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**    
 *     
 * 项目名称：sdsj    
 * 类名称：Log    
 * 类描述：    日志
 * 创建人：anxingtao    
 * 创建时间：2014-1-3 上午09:56:50    
 * 修改人：anxingtao    
 * 修改时间：2014-1-3 上午09:56:50    
 * 修改备注：    
 * @version     
 *     
 */
public class Log
{


public static Logger getInstance(Class classObject)
  {
    Logger log = LoggerFactory.getLogger(classObject);
    return log;
  }

  public static Logger getLogger(Class classObject)
  {
    Logger log = getInstance(classObject);
    return log;
  }
}
