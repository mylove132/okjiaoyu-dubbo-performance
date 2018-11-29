package com.okjiaoyu.performance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-11-29:10:47
 * Modify date: 2018-11-29:10:47
 */
public class DubboInit {

    private static DubboInit dubboInit;
    private DubboInit(){}
    private static ApplicationContext context;

    public static synchronized DubboInit getInstance(){
        if (dubboInit == null){
            dubboInit = new DubboInit();
        }
        return dubboInit;
    }

    public ApplicationContext initContext(){
        if (context == null){
            context = new ClassPathXmlApplicationContext("classpath:dubbo.xml");
        }
        return context;
    }

    public Object getBean(String s){
      return  context.getBean(s);
    }

}
