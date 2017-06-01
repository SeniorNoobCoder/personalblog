package com.sdhsie.base.util.databackup;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class PropertyUtil {
	
	/**
	 * 
	  * @Title: readPts
	  * @Description: 读取properties文件
	  * @param @param file
	  * @param @return    设定文件
	  * @return Properties    返回类型
	  * @throws
	 */
	public static Properties readPts(String file){
		Properties prop = new Properties();  
		 try {
			//读取属性文件a.properties
			prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream(file));
//			InputStream in = new BufferedInputStream (new FileInputStream(file));
//			prop.load(in);     ///加载属性列表
			Iterator<String> it=prop.stringPropertyNames().iterator();
			while(it.hasNext()){
			    String key=it.next();
			    System.out.println(key+":"+prop.getProperty(key));
			}
//			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * 
	  * @Title: savePts
	  * @Description: 保存properties信息
	  * @param @param file
	  * @param @param key
	  * @param @param value    设定文件
	  * @return void    返回类型
	  * @throws
	 */
	public static void savePts(String file,String key,String value){
		try {
			Properties prop = new Properties();     
			 ///保存属性到b.properties文件
//			prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("properties/data.properties"));
			FileOutputStream oFile = new FileOutputStream(file, true);//true表示追加打开
			prop.setProperty(key, value);
			prop.store(oFile, "最新加入key："+key+",value:"+value);
			oFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
    public static void main(String[] args) { 
        Properties prop = new Properties();     
        try{
            //读取属性文件a.properties
        	prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("properties\\data.properties"));
//            InputStream in = new BufferedInputStream (new FileInputStream("properties\\data.properties"));
//            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                System.out.println(key+":"+prop.getProperty(key));
            }
//            in.close();
            
            
            ///保存属性到b.properties文件
            FileOutputStream oFile = new FileOutputStream(System.getProperty("user.dir")+"\\properties\\data.properties", true);//true表示追加打开
            prop.setProperty("phone", "10086");
            prop.store(oFile, "The New properties file");
            oFile.close();
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    } 
    
    
    
    public static String getRootPath() {
    	System.out.println("111");
    	System.out.println(System.getProperty("user.dir"));
    	  String classPath = PropertyUtil.class.getClassLoader().getResource("/").getPath();
    	  System.out.println();
    	  String rootPath  = "getRootPath()";
    	  //windows下
    	  if("\\".equals(File.separator)){   
    	   rootPath  = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
    	   rootPath = rootPath.replace("/", "\\");
    	  }
    	  //linux下
    	  if("/".equals(File.separator)){   
    	   rootPath  = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
    	   rootPath = rootPath.replace("\\", "/");
    	  }
    	  return rootPath;
    	 }
    
}