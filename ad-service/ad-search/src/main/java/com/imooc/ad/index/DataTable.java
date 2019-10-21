package com.imooc.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Seven
 * @date 2019/10/20 18:28
 * @description 用于加载所有的索引操作组件(标识@Component的index类) 可以算一个工具类
 *              aware->想要什么，用来初始化加载指定的组件 另一个接口用于指定初始化的顺序优先级
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    /**
     * 因为是static的 所以要用类名来 . 点
     */
    private static ApplicationContext applicationContext;

    private static final Map<Class, Object> DATA_TABLE_MAP = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    /**
     * 指定为最高优先级加载本组件类
     * @return
     */
    @Override
    public int getOrder() {
        // int类型的最小值
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz) {
        T instance = (T) DATA_TABLE_MAP.get(clazz);
        // 获取到了 直接返回
        if (instance != null) {
            return instance;
        }
        // 没获取到 先保存再返回
        DATA_TABLE_MAP.put(clazz, bean(clazz));

        return (T) DATA_TABLE_MAP.get(clazz);
    }

    /*
     * 不要通过类名称获取bean
    @SuppressWarnings("all")
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }*/

    @SuppressWarnings("all")
    private static <T> T bean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

}
