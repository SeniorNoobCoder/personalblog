package com.sdhsie.webService.toro.utils;

import org.springframework.util.StringUtils;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
/**
 * HTTP工具类
 * 提供HTTP访问方法
 * @author georeg.li
 * @see
 * @since 1.0
 */
public class WebUtils {


    public static final String SUCC = "200";
    public static final String FAIL = "404";
    public static final int CONN_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 5000;
    public static final int RETRY = 3;

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    /**
     * HTTP访问管理(POST File)
     * @param urlStr
     * @return
     */
    public static String postURL(String urlStr, Map<String, Object> params) {
        String flag = FAIL;
        int c = 1;
        for (int i = 0; i < c; i++) {
            flag = httpPostReq(urlStr, params, null);
            if (flag.startsWith(SUCC)) {
                flag = flag.substring(3);
                break;
            }
        }
        return flag;
    }

    /**
     * HTTP POST File请求
     *
     * @param urlStr
     * @return
     */
    private static String httpPostReq(String urlStr, Map<String, Object> params, String encode) {
        String result = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = null;
            conn = getHttpURLConnection(url);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-agent", "Mozilla/4.0");
            conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            if (StringUtils.isEmpty(encode)) {
                encode = "utf-8";
            }
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (String key : params.keySet()) {
                    param.append("&");
                    param.append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), encode));
                }
                conn.getOutputStream().write(param.toString().getBytes());
                conn.getOutputStream().flush();
                conn.getOutputStream().close();
            }
            System.out.println("params->" + params);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            result = String.valueOf(conn.getResponseCode()); // suc=200
            if (conn.getResponseCode() == 200) {
                String line = "";
                for (line = br.readLine(); line != null; line = br.readLine()) {
                    result += line;
                }
            }
            System.out.println("result->" + result);
        } catch (MalformedURLException e) {
            System.out.println("!!!Http请求URL报错!" + urlStr);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("!!!Http请求IO报错!" + urlStr);
        } catch (Exception e) {
            System.out.println("!!!Http请求报错!" + urlStr);
            e.printStackTrace();
        }

        return result;
    }

    private static HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection conn;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            } catch (Exception e) {
                //throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;// 默认都认证通过
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        return conn;
    }
}
