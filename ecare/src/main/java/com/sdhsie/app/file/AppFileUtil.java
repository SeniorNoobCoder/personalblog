package com.sdhsie.app.file;
import com.sdhsie.base.util.GuidUtil;
import com.sdhsie.base.util.upload.FtpUtil;
import com.sdhsie.base.util.upload.ParaUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by zcx on 2017/1/11.
 */
public class AppFileUtil {
    /**
     * 将文件转成base64 字符串
     * @param path 文件路径
     * @return  *
     * @throws Exception
     */

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);;
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);

    }

    /**
     * 将base64字符解码保存文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();

    }

    /**
     * 将base64字符保存文本文件
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void toFile(String base64Code, String targetPath)
            throws Exception {

        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    public static String generateImage(String imgStr, String path, HttpServletRequest request) {
        if (imgStr == null) // 图像数据为空
            return "";
        String  imagePath = ParaUtil.localName+path;//图片上传文件夹
        String realPath= request.getSession().getServletContext().getRealPath(imagePath);//真实的文件保存文件夹路径
        File f1 = new File(realPath);
        if(!f1.exists()){
            f1.mkdirs();
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);// Base64解码
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String key = GuidUtil.getGuid()+".jpg";//文件名称
            OutputStream out = new FileOutputStream(realPath+"/"+key);
            out.write(b);
            out.flush();
            out.close();
            //					====图片上传至服务器 start =====
					File imgFile = new File(realPath+"/"+key);
            InputStream input = new FileInputStream(imgFile);
            FtpUtil.uploadFile(path,key,input);//上传图片到图片服务器
//					图片上传至服务器 end
            imgFile.delete();//删除临时图片
            return imagePath+"/"+key;
        } catch (Exception e) {
            return "";
        }
    }
    public static void main(String[] args) {
//        try {
//            String base64Code = encodeBase64File("f:\\banner001.png");
//            System.out.println(base64Code);
////            decoderBase64File(base64Code, "f:\\111.png");
////            toFile(base64Code, "f:\\11.png");
//            generateImage(base64Code,"f:/11/1.png");
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
        String imagPath = ParaUtil.localName+ParaUtil.business+ParaUtil.user+ParaUtil.authent;
        System.out.println(imagPath);
        File f1 = new File(imagPath);
        if(!f1.exists()){
            f1.mkdirs();
        }
    }
}
