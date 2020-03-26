package com.xyd.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.split
 * @date 2020/3/25
 * @description 用来获得到底什么类型的数据库操作
 * 是读取还是 修改
 */
public class DynamicDataSourceHolder {
    //记录日志
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    //线程安全
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static  final String DB_MASTER = "master";
    public  static final String DB_SLAVE = "slave";

    /**
     * 获取线程的dbtype
     * @return
     */
    public static String getDbType() {
        String db = contextHolder.get();
        if(db==null){
            db=DB_MASTER;
        }
        return db ;
    }

    /**
     * 设置线程的dbtype
     * @param str
     */
    public static void  setDbType(String str){
        logger.debug("所使用的数据源为:"+str);
        contextHolder.set(str);
    }

    /**
     * 清理连接类型
     */
    public  static void clearDbType(){
        contextHolder.remove();
    }
}
