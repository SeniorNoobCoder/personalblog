package com.sdhsie.app.cache;

/**
 * Created by zcx on 2017/1/5.
 */
//测试类，
class Test {
    public static void setCache(){
        CacheManager.putCache("abc", new Cache("1212", "1212", 1, true));
    }
    public static void getCache(){
        System.out.println(CacheManager.getCacheInfo("abc"));
    }
    public static void main(String[] args) {
        setCache();
        getCache();
//        Cache c = new Cache();
//       c.setValue("3333");
//        CacheManager.putCache("123322",c);
//        System.out.println(CacheManager.getCacheInfo("abc"));
//        CacheManager.putCache("abc", new Cache());
//        CacheManager.putCache("def", new Cache());
//        CacheManager.putCache("ccc", new Cache());
//        CacheManager.clearOnly("");
//        Cache c = new Cache();
//        for (int i = 0; i < 10; i++) {
//            CacheManager.putCache("" + i, c);
//        }
//        CacheManager.putCache("aaaaaaaa", c);
//        CacheManager.putCache("abchcy;alskd", c);
//        CacheManager.putCache("cccccccc", c);
//        CacheManager.putCache("abcoqiwhcy", c);
//        System.out.println("删除前的大小："+CacheManager.getCacheSize());
//        CacheManager.getCacheAllkey();
//        CacheManager.clearAll("aaaa");
//        System.out.println("删除后的大小："+CacheManager.getCacheSize());
//        CacheManager.getCacheAllkey();
    }
}
