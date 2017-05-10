package com.sdhsie.base.util;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties props = new Properties();
    private static String fileName = "config.properties";
    public Config() {
        System.out.println();
    }
    public static String getValue(String key) {
        return props.getProperty(key);
    }
    public static void updateProperties(String key, String value) {
        try {
            props.setProperty(key, value);
            FileOutputStream fos = new FileOutputStream(Config.class.getResource("/").getPath()+"/"+fileName);
            props.store(fos, null);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
//            InputStream is = new FileInputStream(config.class.getResource("/").getPath()+"/taskConfig.properties");
//            props.load(is);
        } catch (FileNotFoundException var1) {
            var1.printStackTrace();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//          String uu =  Config.getValue("imagesUrl");
//        System.out.println(uu);
//    }
}
