package com.doojie.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;


public final class PropertiesUtil {
	
	private PropertiesUtil(){}
	
	public static Properties getProperties(String... fileNames) {
		Properties prop = new Properties();
		for (String fileName : fileNames) {
			InputStream is = null;
			try {
				is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
				prop.load(is);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		return prop;
	}
	
//	public static String getSystemProperty(String propertyName){
//		return getProperties("system.properties").getProperty(propertyName);
//	}
}
