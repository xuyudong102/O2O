package com.xyd.split;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;


/**
 * @author XuYuDong
 * @version 1.0
 * @description com.xyd.split
 * @date 2020/3/25
 * @description 拦截mybaits 传递过来的sql信息 根据sql信息去判断 到底是用主库(DDL)还是从库(DML)
 */
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
@Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
    //正则匹配
    private static final String REGEX=".*insert\\u0020.|.*delete\\u0020.|.*update\\u0020.|";
    //记录日志
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //判断当前是不是事务
        boolean synchronizationActive= TransactionSynchronizationManager.isActualTransactionActive();
        //获取sql语句变量的一些参数
        Object[] objects = invocation.getArgs();
        //决定增删改查操作的
        MappedStatement ms = (MappedStatement) objects[0];
        //决定DataSource的
        String lookupkey= DynamicDataSourceHolder.DB_MASTER;
        if(synchronizationActive!=true){
            //如果没有使用事务
            //读方法
            if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)){
                //如果是读方法的话
                if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    //如果selectKey 为自增id查询主键(SELECT LAST_INE_INSERT_ID())方法 使用主库
                    lookupkey = DynamicDataSourceHolder.DB_MASTER;
                }else{
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    //因为sql语句有很多换行制表符 所以需要替换成空格隔开
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]"," ");
                    if(sql.matches(REGEX)){
                        //如果正则匹配成功 代表sql语句中含有insert delete update
                        //使用主库
                        lookupkey =  DynamicDataSourceHolder.DB_MASTER;
                    }else{
                        //使用从库读取
                        lookupkey = DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        }else {
            //使用事务管理的直接进入主库
            lookupkey =  DynamicDataSourceHolder.DB_MASTER;
        }
        //日志输出 方法名 主库还是从库 sql类型
        logger.debug("设置方法[{}] user[{}] Stretegy,SqlCommanType[{}]..",
                ms.getId(),lookupkey,ms.getSqlCommandType().name());
        DynamicDataSourceHolder.setDbType(lookupkey);
        //完成下面的操作
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        //Executor 里面装的是所有增删改查的操作 通过interceptor 来决定使用哪一个数据源
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }else{
            return target;
        }
    }

    /**
     * 初始化设置
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
