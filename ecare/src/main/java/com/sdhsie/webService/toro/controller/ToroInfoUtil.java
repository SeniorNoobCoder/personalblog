package com.sdhsie.webService.toro.controller;
import com.sdhsie.base.util.Config;
import com.sdhsie.base.util.PageData;
import com.sdhsie.webService.toro.utils.WebUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zcx on 2017/2/8.
 */
public class ToroInfoUtil {
    /**
     * 获取token
     */
    public static final String APPID = Config.getValue("TORO_APPID");
    public static final String SECRET = Config.getValue("TORO_SECRET");
    public static final String TORO_IP =  Config.getValue("TORO_IP");
    public static final String TORO_PORT =  Config.getValue("TORO_PORT");
    public static final String TORO_CONTEXT =  Config.getValue("TORO_CONTEXT");
    public static final String TORO_URL = TORO_IP+"/"+TORO_CONTEXT+"/";

    /**
     * 获取token
     * @return
     */
    public static String getToroToken(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appid", APPID);
        params.put("secret", SECRET);
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"token", params);
        JSONObject jsonObject =  JSONObject.fromObject(result);
        String token = jsonObject.getString("access_token");
        return token;
    }

    /**
     * 获取设备用户定位
     * @param sn
     * @return
     */
    public static PageData getToroLoc(String sn){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn",sn);
        params.put("access_token",getToroToken());
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"loc", params);
        PageData pd = new PageData();
        if(result!=null && result!="") {
            JSONObject jsonObject = JSONObject.fromObject(result);
            Date d = new Date();
            SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            pd.put("lon", jsonObject.get("lon"));
            pd.put("lat", jsonObject.get("lat"));
            pd.put("end_time", sdfd.format(d));
            pd.put("poi", jsonObject.get("poi"));
            pd.put("errcode",jsonObject.get("errcode"));
        }
        return pd;
    }

    /**
     * 获取设备基本信息
     * @param sn
     * @return
     */
    public static PageData getToroInfo(String sn){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn",sn);
        params.put("access_token",getToroToken());
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"info/get", params);
        JSONObject jsonObject =  JSONObject.fromObject(result);
        PageData pd = new PageData();
        pd.put("errcode",jsonObject.get("errcode"));//错误码
        pd.put("status",jsonObject.get("status"));//-1：未激活，1：在线，0，离线
        pd.put("tl_version",jsonObject.get("tl_version"));//固件版本号
        pd.put("power",jsonObject.get("power"));//电量
        pd.put("role",jsonObject.get("role"));//角色，1：监护人，0：家庭成员
        pd.put("model",jsonObject.get("model"));//机型
        pd.put("switch",jsonObject.get("switch"));//开关状态, 顺序:GPS,蓝牙,来电防火墙,远程拾音,录音监听,陌生区域,定位服务,震动开关(弃用),低电提醒开关
        pd.put("contacts",jsonObject.get("contacts"));//联系人配置
        pd.put("phone",jsonObject.get("phone"));//手机号码
        return pd;
    }

    /**
     * 获取历史足迹
     * @return
     */
    public static List<PageData> getToroTrace(String sn,String dateString){
//        String dateString = "2016-02-07";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
        long time = date.getTime();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn", sn);
        params.put("access_token",getToroToken());
        params.put("tm",time);
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"trace", params);
        //=========================================
        List<PageData> pdList = new ArrayList<PageData>();
        if(result!=null && result!="") {
            JSONObject jsonObject = JSONObject.fromObject(result);
            if(jsonObject.getString("errcode").equals("0")){
                String data = jsonObject.getString("data");
                JSONArray json = JSONArray.fromObject(data);
                for (int i = 0; i < json.size(); i++) {
                    PageData pd = new PageData();
                    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    pd.put("lat", job.get("lat"));
                    pd.put("lon", job.get("lon"));
                    String times[] = job.get("times").toString().split(",");
                    SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                    //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
                    Date start_date = new Date(Long.valueOf(times[0]));
                    Date end_date = new Date(Long.valueOf(times[1]));
                    pd.put("start_time", sdfd.format(start_date));
                    pd.put("end_time", sdfd.format(end_date));
    //                pd.put("poi", jsonObject.get("poi"));
                    pdList.add(pd);
            }
        }
        }
        return pdList;
    }

    /**
     * 获取设备基本信息
     * @param sn
     * @return
     */
    public static PageData getToroEvaluate(String sn,String orderNo){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn",sn);
        params.put("orderNo",orderNo);
        params.put("access_token",getToroToken());
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"order/evaluate", params);
        JSONObject jsonObject =  JSONObject.fromObject(result);
        PageData pd = new PageData();
        pd.put("errcode",jsonObject.get("errcode"));//错误码
        return pd;
    }

    /**
     * 获取联系人信息
     * @param sn
     * https://IP:PORT/CONTEXT/contacts/get
     * 快捷键（T83云融定制设备，快捷对应如下：1、2、3、4对应A、B、C、D，语音键对应：E）
     */
    public static List<PageData> getContacts(String sn){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn", sn);
        params.put("access_token",getToroToken());
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"contacts/get", params);
        //=========================================
        List<PageData> pdList = new ArrayList<PageData>();
        if(result!=null && result!="") {
            JSONObject jsonObject = JSONObject.fromObject(result);
            if(jsonObject.getString("errcode").equals("1")){
                String data = jsonObject.getString("data");
                JSONArray json = JSONArray.fromObject(data);
                for (int i = 0; i < json.size(); i++) {
                    PageData pd = new PageData();
                    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    pd.put("mobile", job.get("mobile"));
                    pd.put("name", job.get("name"));
                    pd.put("cid", job.get("cid"));
                    pd.put("key", job.get("key"));
                    pdList.add(pd);
                }
            }
        }
        return pdList;
    }

    /**
     * 设置联系人
     * @param infoPd
     * @return
     */
    public static boolean setContacts(PageData infoPd){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn",infoPd.get("sn"));
        params.put("cid",infoPd.get("cid"));
            params.put("sign", infoPd.get("sign"));
//        if(!infoPd.get("sign").equals(3)) {
        if(!infoPd.get("sign").toString().equals("3")) {
            params.put("mobile", infoPd.get("mobile"));
            params.put("name", infoPd.get("name"));
            params.put("key", infoPd.get("key"));
        }
        params.put("access_token",getToroToken());
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"contacts/set", params);
        JSONObject jsonObject =  JSONObject.fromObject(result);
        int errcode = Integer.valueOf(jsonObject.get("errcode").toString());
        if(1==errcode){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 获取电子围栏信息
     * @param sn
     * @return
     */
    public static List<PageData> getFence(String sn){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn", sn);
        params.put("access_token",getToroToken());
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"fence/get", params);
        //=========================================
        List<PageData> pdList = new ArrayList<PageData>();
        if(result!=null && result!="") {
            JSONObject jsonObject = JSONObject.fromObject(result);
            if(jsonObject.getString("errcode").equals("1")){
                String data = jsonObject.getString("data");
                JSONArray json = JSONArray.fromObject(data);
                for (int i = 0; i < json.size(); i++) {
                    PageData pd = new PageData();
                    JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    pd.put("fence_id", job.get("fence_id"));
                    pd.put("name", job.get("name"));
                    pd.put("radius", job.get("radius"));
                    pd.put("lon", job.get("lon"));
                    pd.put("lat", job.get("lat"));
                    pd.put("status", job.get("status"));
                    pd.put("alarm_type", job.get("alarm_type"));
                    pdList.add(pd);
                }
            }
        }
        return pdList;
    }

    /**
     * 设置电子围栏
     * @param infoPd
     * @return
     */
    public static boolean setFence(PageData infoPd){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn",infoPd.get("sn"));
        params.put("fence_id",infoPd.get("fence_id"));
        params.put("sign",infoPd.get("sign"));
        if(!infoPd.get("sign").toString().equals("3")) {
            params.put("name", infoPd.get("name"));
            params.put("radius", infoPd.get("radius"));
            params.put("lon", infoPd.get("lon"));
            params.put("lat", infoPd.get("lat"));
            params.put("status", 1);
            params.put("alarm_type", 3);
        }
        params.put("access_token",getToroToken());
        // 请求token 获取
        String result = WebUtils.postURL(TORO_URL+"fence/set", params);
        JSONObject jsonObject =  JSONObject.fromObject(result);
        int errcode = Integer.valueOf(jsonObject.get("errcode").toString());
        if(1==errcode){
            return true;
        }else {
            return false;
        }
    }

    /**
     * {"data":[{"alarm_type":3,"fence_id":"58b6854928f2baff79e2fe0a","lat":36.67212,"lon":117.134178,"name":"丁豪广场","radius":322,"status":1},
     * {"alarm_type":3,"fence_id":"58b6855e28f2baff79e3084f","lat":36.672189,"lon":117.129929,"name":"添加","radius":1000,"status":1},
     * {"alarm_type":0,"fence_id":"58b7ea2928f276b824d81f06","lat":0,"lon":0,"radius":0,"status":0},
     * {"alarm_type":0,"fence_id":"58b7ea8928f276b824d85bba","lat":0,"lon":0,"radius":0,"status":0}],"errcode":1}
     * @param args
     */
    public static void main(String[] args) {
        //{"cid":"58b6625f28f2b60310a322d9","mobile":"13506408036","name":"添加"}
        PageData pd = new PageData();
        pd.put("sn","5062000159");
        pd.put("fence_id","58b6855e28f2baff79e3084f");
        pd.put("sign",3);
//        pd.put("mobile","10086");
//        pd.put("name","添加");
//        pd.put("key","A");
//        pd.put("lat","36.672189");
        ToroInfoUtil.setFence(pd);//.setContacts(pd);
//        ToroInfoUtil.getFence("5062000159");
//        String nn = "{\"data\":[{\"accuracy\":0,\"lat\":22.583737,\"loc_way\":3,\"lon\":113.961259,\"times\":\"1486456297819,1486456297819\"},{\"accuracy\":0,\"lat\":22.583748,\"loc_way\":3,\"lon\":113.96167,\"times\":\"1486445695497,1486445695497\"}],\"errcode\":0}";
//        List<PageData> pdList = new ArrayList<>();
//        PageData pd = new PageData();
//        JSONObject jsonObject =  JSONObject.fromObject(nn);
//        String data =jsonObject.getString("data");
//        JSONArray json = JSONArray.fromObject(data);
//        for(int i=0;i<json.size();i++){
//            JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//            pd.put("lat",job.get("lat"));
//            pd.put("lon",job.get("lon"));
//
//            String times[] = job.get("times").toString().split(",");
//            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
//            //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
//            Date start_date = new Date(Long.valueOf(times[0] ));
//            Date end_date = new Date(Long.valueOf(times[1] ));
////                String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
//            pd.put("start_time",sdf.format(start_date));
//            pd.put("end_time",sdf.format(end_date));
//            pdList.add(pd);
//        }
    }
}
