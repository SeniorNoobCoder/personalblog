package com.sdhsie.base.util.upload;

import com.sdhsie.base.util.FileUtil;
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.PageData;
import com.sdhsie.base.util.Verify;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


public class FileUpload {

	
	 /**
     * 本地保存文件
     */
    public static PageData saveLocalFile(HttpServletRequest request, String filePath, PageData pd) throws IOException {
    	CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    	if(multipartResolver.isMultipart(request)){
             MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest)request;
             Iterator<String> iterator = multipartRequest.getFileNames();
             while(iterator.hasNext()){
                MultipartFile  file  = multipartRequest.getFile(iterator.next());
                if(!file.isEmpty()){
	            	//上传到阿里云OSS服务器
	            	String fileName = file.getOriginalFilename();
	            	String hz = fileName.substring(fileName.lastIndexOf("."));
//	            	String key = filePath+ GuidUtil.getGuid()+hz;
					String key = GuidUtil.getGuid()+hz;
//	            	String path= ParaUtil.localName+key;
					String realPath= request.getSession().getServletContext().getRealPath(ParaUtil.localName+filePath);
					File pacFile = new File(realPath);
	            	if(!pacFile.exists()){
						pacFile.mkdirs();
	            	}
					String picPath = realPath+key;
					File f1 = new File(picPath);
	            	file.transferTo(f1);
					//					====图片上传至服务器 start =====
//					File imgFile = new File(picPath);
					InputStream input = new FileInputStream(f1);
					FtpUtil.uploadFile(filePath,key,input);//上传图片到图片服务器
//					图片上传至服务器 end
					f1.delete();//删除临时图片
//			        pd.put(file.getName(),filePath+key);
					pd.put(file.getName(), ParaUtil.localName+filePath+"/"+key);
	            }
             }
          }
		return pd;
    }
    
    
    /**
     * 本地更新
     * @param request
     * @param filePath
     * @param pd
     * @return
     * @throws IOException
     */
    public static PageData uploadLocalFile(HttpServletRequest request, String filePath, PageData pd) throws IOException {
    	CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
    	if(multipartResolver.isMultipart(request)){
             MultipartHttpServletRequest  multipartRequest = (MultipartHttpServletRequest)request;
             Iterator<String> iterator = multipartRequest.getFileNames();
             while(iterator.hasNext()){
                MultipartFile  file  = multipartRequest.getFile(iterator.next());
                if(!file.isEmpty()){
	            	//上传到本地服务器
                	String delPath=pd.getString(file.getName()+"_old");
                	deleteLocalFile(delPath);
                	
	            	String fileName = file.getOriginalFilename();
	            	String hz = fileName.substring(fileName.lastIndexOf("."));
	            	String key = GuidUtil.getGuid()+hz;
	            	
//	            	String path=ParaUtil.localName+key;
					String realPath= request.getSession().getServletContext().getRealPath(ParaUtil.localName+filePath);
					File pacFile = new File(realPath);
	            	if(!pacFile.exists()){
						pacFile.mkdirs();
	            	}
					String picPath = realPath+key;
					File f1 = new File(picPath);
	            	file.transferTo(f1);
//					====图片上传至服务器 start =====
//					File imgFile = new File(picPath);
					InputStream input = new FileInputStream(f1);
					FtpUtil.uploadFile(filePath,key,input);//上传图片到图片服务器
//					图片上传至服务器 end
					f1.delete();//删除临时图片
			        pd.put(file.getName(), ParaUtil.localName+filePath+key);
	            }
             }
          }
		return pd;
    }
    
    
    /**
     * 本地删除单一文件
     * @throws IOException
     */
    public static void deleteLocalFile(String key) throws IOException {
    	if(Verify.verifyIsNotNull(key)){
    		if(!key.contains(ParaUtil.zdyPath)){
	    		String path= ParaUtil.localName+key;
		    	File f1 = new File(path);
		    	if(f1.exists()){
		    		f1.delete();
		    		f1.deleteOnExit();
		    	}
	    	}
    	}
    }
    
    
    /**
     * 本地删除集合文件
     * @throws IOException
     */
    public static void deleteLocalFile(String key[]) throws IOException {
    	for (String string : key) {
    		if(Verify.verifyIsNotNull(string)){
    			if(!string.contains(ParaUtil.zdyPath)){
	    			String path= ParaUtil.localName+string;
		        	File f1 = new File(path);
		        	if(f1.exists()){
		        		f1.delete();
		        		f1.deleteOnExit();
		        	}
	    		}
    		}
		}
    }
    
    
    
    /**
     * 获取本地文件路径
     * @return
     * @throws IOException
     */
    public static String findLocalFileUrl(String key) throws IOException {
    	String path="";
		String nn = ParaUtil.cloudfile;
    	if (Verify.verifyIsNotNull(key)) {
			path= ParaUtil.cloudfile+key.replace("/IMAGES/","/ecarePic/");
		}
		return path;
    }
    
    
    /**
     * 本地文件下载
     * @throws Exception
     */
    public static void downloadLocalFile(HttpServletResponse response,String key,String filename) throws Exception {
    	String path="";
    	if(Verify.verifyIsNotNull(key)){
    		path= ParaUtil.localName+key;
    		FileUtil.downFile(response, key, filename);
    	}
    }

}
