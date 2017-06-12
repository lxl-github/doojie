package com.doojie.quartz.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtils {

	private static ApplicationContext applicationContext;

    private static ApplicationContext getContext() {

        if (applicationContext == null) {
            applicationContext =  new ClassPathXmlApplicationContext("SimpleBeans.xml");

        }
        return applicationContext;

    }

    public static Object getBean(String beanName) {

        return getContext().getBean(beanName);

    }
}
