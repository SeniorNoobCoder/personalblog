package com.sdhsie.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sdhsie.base.util.Config;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class PushUtilTest {
    protected static final Logger LOG = LoggerFactory.getLogger(PushUtilTest.class);
    //在极光注册上传应用的 appKey 和 masterSecret
    protected static final String APP_KEY = Config.getValue("APP_KEY");
    protected static final String MASTER_SECRET = Config.getValue("MASTER_SECRET");
    /**
     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
     * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
     */
    private static long timeToLive =  60 * 60 * 24;

    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
        sendPushAllUser();//发送给所有人
//        testSendIosAlert();
//		testSendPush();//具体某个人
//        testSendPush_fromJSON();
//        testSendPushes();
//        testSendPushesWithMultiCallback();
//        sendPushUser();//推送给指定的人
//        sendPushTag();//推送给指定组
//        testSendWithSMS();
        sendPushAllUserMeg();
	}

	// 使用 NettyHttpClient 异步接口发送请求
    public static void testSendPushes() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, clientConfig);
        for (int i = 0; i < 4; i++) {
            NettyHttpClient.BaseCallback callback = new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    System.out.println("callback i");
                }
            };
            MyThread thread = new MyThread(client, callback);
            thread.start();
        }
    }

    public static void testSendPushesWithMultiCallback() {
        NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET),
                null, ClientConfig.getInstance());
        String host = (String) ClientConfig.getInstance().get(ClientConfig.PUSH_HOST_NAME);
        URI uri = null;
        try {
            uri = new URI(host + (String) ClientConfig.getInstance().get(ClientConfig.PUSH_PATH));
            PushPayload payload = PushPayload.alertAll("test");
            System.out.println(payload.toString());
            NettyHttpClient.BaseCallback callback1 = new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    System.out.println("callback1 Got result: " + responseWrapper.responseContent);
                }
            };
            NettyHttpClient.BaseCallback callback2 = new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    System.out.println("callback2 Got result: " + responseWrapper.responseContent);
                }
            };
            MyThread thread1 = new MyThread(client, callback1);
            MyThread thread2 = new MyThread(client, callback2);
            thread1.start();
            thread2.start();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static class MyThread extends Thread {

        private NettyHttpClient client;
        private NettyHttpClient.BaseCallback callback;

        public MyThread(NettyHttpClient client, NettyHttpClient.BaseCallback callback) {
            this.client = client;
            this.callback = callback;
        }

        @Override
        public void run() {
            // super.run();
            System.out.println("running send push");
            try {
                String host = (String) ClientConfig.getInstance().get(ClientConfig.PUSH_HOST_NAME);
                URI uri = new URI(host + (String) ClientConfig.getInstance().get(ClientConfig.PUSH_PATH));
                PushPayload payload = PushPayload.alertAll("test");
                System.out.println(payload.toString());
                client.sendRequest(HttpMethod.POST, payload.toString(), uri, callback);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	public static void testSendPush() {
	    // HttpProxy proxy = new HttpProxy("localhost", 3128);
	    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
		ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_newly_support();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
	}

	//use String to build PushPayload instance
    public static void testSendPush_fromJSON() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlatformNotification.class, new InterfaceAdapter<PlatformNotification>())
                .create();
        // Since the type of DeviceType is enum, thus the value should be uppercase, same with the AudienceType.
        String payloadString = "{\"platform\":{\"all\":false,\"deviceTypes\":[\"IOS\"]},\"audience\":{\"all\":false,\"targets\":[{\"audienceType\":\"TAG_AND\",\"values\":[\"tag1\",\"tag_all\"]}]},\"notification\":{\"notifications\":[{\"soundDisabled\":false,\"badgeDisabled\":false,\"sound\":\"happy\",\"badge\":\"5\",\"contentAvailable\":false,\"alert\":\"Test from API Example - alert\",\"extras\":{\"from\":\"JPush\"},\"type\":\"cn.jpush.api.push.model.notification.IosNotification\"}]},\"message\":{\"msgContent\":\"Test from API Example - msgContent\"},\"options\":{\"sendno\":1429488213,\"overrideMsgId\":0,\"timeToLive\":-1,\"apnsProduction\":true,\"bigPushDuration\":0}}";
        PushPayload payload = gson.fromJson(payloadString, PushPayload.class);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }
	
	public static PushPayload buildPushObject_all_all_alert() {
	    return PushPayload.alertAll(ALERT);
	}

    /**
     * 某个用户
     */
    public static void sendPushUser() {
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
//        config.setPushHostName("https://api.jpush.cn");
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_alias_alert();

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }
    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("1111"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }
    /**
     * 某个用户
     */
    public static void sendPushTag() {
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
//        config.setPushHostName("https://api.jpush.cn");
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_tag_alertWithTitle();

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }
    
    public static PushPayload buildPushObject_android_and_ios() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                		.setAlert("alert content")
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle("Android Title").build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.addExtra("extra_key", "extra_value").build())
                		.build())
                .build();
    }

    public static void buildPushObject_with_extra() {

        JsonObject jsonExtra = new JsonObject();
        jsonExtra.addProperty("extra1", 1);
        jsonExtra.addProperty("extra2", false);

        Map<String, String> extras = new HashMap<String, String>();
        extras.put("extra_1", "val1");
        extras.put("extra_2", "val2");

        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("tag1"))
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title")
                                .addExtras(extras)
                                .addExtra("booleanExtra", false)
                                .addExtra("numberExtra", 1)
                                .addExtra("jsonExtra", jsonExtra)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();

        System.out.println(payload.toJSON());
    }
    
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("business"))
//                .setNotification(Notification.newBuilder()
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                .setAlert(ALERT)
//                                .setBadge(5)
//                                .setSound("happy")
//                                .addExtra("from", "JPush")
//                                .build())
//                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }

    public static PushPayload buildPushObject_android_newly_support() {
        JsonObject inbox = new JsonObject();
        inbox.add("line1", new JsonPrimitive("line1 string"));
        inbox.add("line2", new JsonPrimitive("line2 string"));
        inbox.add("contentTitle", new JsonPrimitive("title string"));
        inbox.add("summaryText", new JsonPrimitive("+3 more"));
        Notification notification = Notification.newBuilder()
                .addPlatformNotification(AndroidNotification.newBuilder()
                        .setAlert(ALERT)
                        .setBigPicPath("path to big picture")
                        .setBigText("long text")
                        .setBuilderId(1)
                        .setCategory("CATEGORY_SOCIAL")
                        .setInbox(inbox)
                        .setStyle(1)
                        .setTitle("Alert test")
                        .setPriority(1)
                        .build())
                .build();
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId("1111"))
                .setNotification(notification)
                .setOptions(Options.sendno())
                .build();
    }
    
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//        return PushPayload.newBuilder()//
//                .setPlatform(Platform.android())//
//                .setAudience(Audience.tag("business"))//.newBuilder()//
////                        .addAudienceTarget(AudienceTarget.tag("business"))//
//////                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))//
////                        .build())
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(MSG_CONTENT)
//                        .addExtra("from", "JPush")
//                        .build())
//                .build();


        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("business"))
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                .setTitle(TITLE)
                        .setMsgContent(MSG_CONTENT)
                        .build()
                )
                .build();
    }

    /**
     * 发送message
     */
    public static void sendPushAllUserMeg() {
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
//        config.setPushHostName("https://api.jpush.cn");
        JPushClient jPushClient = new JPushClient(MASTER_SECRET,APP_KEY);
//        JPushClient jpushClient = jPushClient.sendMessageAll(MSG_CONTENT);//new JPushClient(MASTER_SECRET, APP_KEY, null, config);

        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras();
//        PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage();

        try {
//            PushResult result = jPushClient.sendAndroidMessageWithAlias(TITLE,MSG_CONTENT,"1111");//.sendNotificationAll(MSG_CONTENT);//.sendMessageAll(MSG_CONTENT);
            PushResult result = jPushClient.sendMessageAll(MSG_CONTENT);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    public static void sendPushAllUser() {
        ClientConfig config = ClientConfig.getInstance();
        // Setup the custom hostname
//        config.setPushHostName("https://api.jpush.cn");
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_all_alert();

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        }
    }

    public static void testSendIosAlert() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);

        IosAlert alert = IosAlert.newBuilder()
                .setTitleAndBody("test alert", "subtitle", "test ios alert json")
                .setActionLocKey("PLAY")
                .build();
        try {
            PushResult result = jpushClient.sendIosNotificationWithAlias(alert, new HashMap<String, String>(), "alias1");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void testSendWithSMS() {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            SMS sms = SMS.content("Test SMS", 10);
            PushResult result = jpushClient.sendAndroidMessageWithAlias("Test SMS", "test sms", sms, "1111");
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

}

