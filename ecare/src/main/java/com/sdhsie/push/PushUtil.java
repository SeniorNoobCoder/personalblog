package com.sdhsie.push;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import com.sdhsie.base.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PushUtil {
    protected static final Logger LOG = LoggerFactory.getLogger(PushUtil.class);
    //在极光注册上传应用的 appKey 和 masterSecret
    protected static final String APP_KEY_SERVER = Config.getValue("APP_KEY_SERVER");
    protected static final String MASTER_SECRET_SERVER = Config.getValue("MASTER_SECRET_SERVER");
    protected static final String APP_KEY_FAMILY = Config.getValue("APP_KEY_FAMILY");
    protected static final String MASTER_SECRET_FAMILY = Config.getValue("MASTER_SECRET_FAMILY");
    /**
     * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
     * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
     * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
     */
    private static long timeToLive =  60 * 60 * 24;

	public static void main(String[] args) throws APIConnectionException, APIRequestException {
        MessageBean ms = new MessageBean("id","title","content", Config.getValue("NOTICE"));
//        ms.setContentType("contentType");
//        ms.setContent("content");
//        ms.setId("id");
//        ms.setTitle("title");
//        sendPushBusinessAlias(ms);
//        sendPushFamilyAlias(ms);//往家人类型用户群发消息
//        sendPushFamilyAlias(ms, sessionId);//往指定家人用户发送信息
//        //===============================================================
//        sendPushBusinessNAlias(ms);//往非认证用户群发消息
//        sendPushBusinessYIsWorkAlias(ms);//往认证用户并且已开工的用户群发消息
//        sendPushBusinessYNotWorkAlias(ms);//往认证用户并且未开工的用户群发消息
//        sendPushBussinessAlias(ms, "2017021815203138672339");//往指定的商户发送消息
//        sendPushFamilyAlias(ms,"2017021815432428765280");
//        sendPushAllAlias(ms);
	}

    /**
     * 给所有手机用户发送消息
     * @return
     */
    public static PushPayload buildPushObject_all_alias_message(MessageBean meg) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                        .setTitle(meg.getTitle())
                        .setMsgContent(meg.getContent())
                        .setContentType(meg.getContentType())
                        .addExtra("id",meg.getId())
                        .build()
                )
                .build();
    }

    /**
     * 给所有认证的开工商户发送消息
     * @return
     */
    public static PushPayload buildPushObject_businessY_isWork_alias_message(MessageBean meg) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(Config.getValue("BUSINESS_Y_IS_WORK")))
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                        .setTitle(meg.getTitle())
                        .setMsgContent(meg.getContent())
                        .setContentType(meg.getContentType())
                        .addExtra("id",meg.getId())
                        .build()
                )
                .build();
    }
    /**
     * 给所有认证的未开工商户发送消息
     * @return
     */
    public static PushPayload buildPushObject_businessY_notWork_alias_message(MessageBean meg) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(Config.getValue("BUSINESS_Y_NOT_WORK")))
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                        .setTitle(meg.getTitle())
                        .setMsgContent(meg.getContent())
                        .setContentType(meg.getContentType())
                        .addExtra("id",meg.getId())
                        .build()
                )
                .build();
    }
    /**
     * 给所有非认证用户发送消息
     * @return
     */
    public static PushPayload buildPushObject_businessN_alias_message(MessageBean meg) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(Config.getValue("BUSINESS_N")))
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                        .setTitle(meg.getTitle())
                        .setMsgContent(meg.getContent())
                        .setContentType(meg.getContentType())
                        .setContentType(meg.getContentType())
                        .build()
                )
                .build();
    }
    /**
     * 给所有认证商户发送消息
     * @return
     */
//    public static PushPayload buildPushObject_businessY_alias_message(MessageBean meg) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.tag_and(Config.getValue("BUSINESS_Y_IS_WORK"), Config.getValue("BUSINESS_Y_NOT_WORK")))
////                .setNotification(Notification.android(ALERT, TITLE, null))
//                .setMessage(Message.newBuilder()
//                        .setTitle(meg.getTitle())
//                        .setMsgContent(meg.getContent())
//                        .setContentType(meg.getContentType())
//                        .addExtra("id",meg.getId())
//                        .build()
//                )
//                .build();
//    }
    /**
     * 给所有商户发送消息
     * @return
     */
//    public static PushPayload buildPushObject_business_alias_message(MessageBean meg) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.tag_and(Config.getValue("BUSINESS_Y_IS_WORK"), Config.getValue("BUSINESS_Y_NOT_WORK"),Config.getValue("BUSINESS_N")))
////                .setNotification(Notification.android(ALERT, TITLE, null))
//                .setMessage(Message.newBuilder()
//                        .setTitle(meg.getTitle())
//                        .setMsgContent(meg.getContent())
//                        .setContentType(meg.getContentType())
//                        .addExtra("id",meg.getId())
//                        .build()
//                )
//                .build();
//    }
    /**
     * 给所有认证用户发送消息
     * @return
     */
    public static PushPayload buildPushObject_family_alias_message(MessageBean meg) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(Config.getValue("FAMILY")))
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                        .setTitle(meg.getTitle())
                        .setMsgContent(meg.getContent())
                        .setContentType(meg.getContentType())
                        .addExtra("id",meg.getId())
                        .build()
                )
                .build();
    }
    /**
     * 给指定户发送消息
     * @return
     */
    public static PushPayload buildPushObject_business_alias_message(MessageBean meg,String sessionId) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
//                .setAudience(Audience.tag(Config.getValue("FAMILY")))
                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias(sessionId))
                        .build())
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                        .setTitle(meg.getTitle())
                        .setMsgContent(meg.getContent())
                        .setContentType(meg.getContentType())
                        .addExtra("id",meg.getId())
                        .build()
                )
                .build();
    }

    /**
     * 发送给家人消息
     * @param meg
     * @param sessionId
     * @return
     */
    public static PushPayload buildPushObject_family_alias_message(MessageBean meg,String sessionId) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
//                .setAudience(Audience.tag(Config.getValue("FAMILY")))
                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias(sessionId))
                        .build())
//                .setNotification(Notification.android(ALERT, TITLE, null))
                .setMessage(Message.newBuilder()
                        .setTitle(meg.getTitle())
                        .setMsgContent(meg.getContent())
                        .setContentType(meg.getContentType())
                        .addExtra("id",meg.getId())
                        .build()
                )
                .build();
    }
    /**
     * 推送消息给所有用户
     * @param meg
     */
    public static void sendPushAllAlias(MessageBean meg) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET_SERVER, APP_KEY_SERVER, null, config);
        PushPayload payload = buildPushObject_all_alias_message(meg);
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
    /**
     * 推送消息给认证的开工商户
     * @param meg
     */
    public static void sendPushBusinessYIsWorkAlias(MessageBean meg) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET_SERVER, APP_KEY_SERVER, null, config);
        PushPayload payload = buildPushObject_businessY_isWork_alias_message(meg);
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
    /**
     * 推送消息给认证的未开工商户
     * @param meg
     */
    public static void sendPushBusinessYNotWorkAlias(MessageBean meg) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET_SERVER, APP_KEY_SERVER, null, config);
        PushPayload payload = buildPushObject_businessY_notWork_alias_message(meg);
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
    /**
     * 推送消息给认证商户
     * @param meg
     */
//    public static void sendPushBusinessYAlias(MessageBean meg) {
//        ClientConfig config = ClientConfig.getInstance();
//        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);
//        PushPayload payload = buildPushObject_businessY_alias_message(meg);
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            LOG.info("Got result - " + result);
//
//        } catch (APIConnectionException e) {
//            LOG.error("Connection error. Should retry later. ", e);
//
//        } catch (APIRequestException e) {
//            LOG.error("Error response from JPush server. Should review and fix it. ", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Code: " + e.getErrorCode());
//            LOG.info("Error Message: " + e.getErrorMessage());
//            LOG.info("Msg ID: " + e.getMsgId());
//        }
//    }
    /**
     * 推送消息给非认证商户
     * @param meg
     */
    public static void sendPushBusinessNAlias(MessageBean meg) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET_SERVER, APP_KEY_SERVER, null, config);
        PushPayload payload = buildPushObject_businessN_alias_message(meg);
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
    /**
     * 推送消息给所有商户
     * @param meg
     */
//    public static void sendPushBusinessAlias(MessageBean meg) {
//        ClientConfig config = ClientConfig.getInstance();
//        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, config);
//        PushPayload payload = buildPushObject_business_alias_message(meg);
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            LOG.info("Got result - " + result);
//
//        } catch (APIConnectionException e) {
//            LOG.error("Connection error. Should retry later. ", e);
//
//        } catch (APIRequestException e) {
//            LOG.error("Error response from JPush server. Should review and fix it. ", e);
//            LOG.info("HTTP Status: " + e.getStatus());
//            LOG.info("Error Code: " + e.getErrorCode());
//            LOG.info("Error Message: " + e.getErrorMessage());
//            LOG.info("Msg ID: " + e.getMsgId());
//        }
//    }
    /**
     * 推送消息给所有家人
     * @param meg
     */
    public static void sendPushFamilyAlias(MessageBean meg) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET_FAMILY, APP_KEY_FAMILY, null, config);
        PushPayload payload = buildPushObject_family_alias_message(meg);
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
    /**
     * 推送消息给指定的app用户
     * @param meg
     * @param sessionId
     */
    public static void sendPushBussinessAlias(MessageBean meg,String sessionId) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET_SERVER, APP_KEY_SERVER, null, config);
        PushPayload payload = buildPushObject_business_alias_message(meg,sessionId);
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
    /**
     * 推送消息给指定的app用户
     * @param meg
     * @param sessionId
     */
    public static void sendPushFamilyAlias(MessageBean meg,String sessionId) {
        ClientConfig config = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(MASTER_SECRET_FAMILY, APP_KEY_FAMILY, null, config);
        PushPayload payload = buildPushObject_family_alias_message(meg,sessionId);
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
}

