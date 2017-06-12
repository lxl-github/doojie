package com.doojie.common.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * <br>
 * 功能概述：金额基础操作
 * <br>
 * 创建时间：2012-11-16上午11:16:48
 * <br>
 * 修改记录：
 * <br>
 */
public class NumUtil {
	
	/**
	 * 方法功能说明:四舍五入
	 * @param num：四舍五入的对象
	 * @param mart:四舍五入保留的小数位数
	 * @return
	 */
	public static Double round(Object num, int mart){
		return changeObject(num).setScale(mart, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 判断String类型是否可以转化成数值类型
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		return NumberUtils.isNumber(str);
	}
	
	/**
	 * 相加
	 * @param numF
	 * @param numT
	 * @return
	 */
	private static Double add(BigDecimal numF, BigDecimal numT){
		return numF.add(numT).doubleValue();
	}
	
	/**
	 * 相减
	 * @param numF
	 * @param numT
	 * @return
	 */
	private static Double sub(BigDecimal numF, BigDecimal numT){
		return numF.subtract(numT).doubleValue();
	}
	
	/**
	 * 相乘
	 * @param numF
	 * @param numT
	 * @return
	 */
	private static Double mul(BigDecimal numF, BigDecimal numT){
		return numF.multiply(numT).doubleValue();
	}
	
	/**
	 * 相除
	 * @param numF
	 * @param numT
	 * @return
	 */
	private static Double div(BigDecimal numF, BigDecimal numT){
		return numF.divide(numT, 2, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	/**
	 * 相加
	 * @param numF
	 * @param numT
	 * @return
	 */
	public static Double add(Object numF, Object numT){
		return add(changeObject(numF), changeObject(numT));
	}
	
	/**
	 * 相减
	 * @param numF
	 * @param numT
	 * @return
	 */
	public static Double sub(Object numF, Object numT){
		return sub(changeObject(numF), changeObject(numT));
	}
	
	/**
	 * 相乘
	 * @param numF
	 * @param numT
	 * @return
	 */
	public static Double mul(Object numF, Object numT){
		return mul(changeObject(numF), changeObject(numT));
	}
	
	/**
	 * 相除
	 * @param numF
	 * @param numT
	 * @return
	 */
	public static Double div(Object numF, Object numT){
		if("0".equals(changeObject(numT).toString())){
			return null;
		} else {
			return div(changeObject(numF), changeObject(numT));
		}
	}
	
	/**
	 * 转换数据类型
	 * @param obj
	 * @return
	 */
	private static BigDecimal changeObject(Object obj){
		if(obj == null || "".equals(obj) || !isNumber(obj.toString())){
			return new BigDecimal(0);
		} else {
			return new BigDecimal(obj.toString());
		}
	}
	
	/**
	 * 以销售政策的折扣率和原价算出，折扣销售价
	 * @return
	 */
	public static Double getDiscountResult(Double srcDiscount,Double goodsPrice){
		//Double discount = NumUtil.div(srcDiscount, 10);//除10再乘商品价得出小数点折扣
		goodsPrice = NumUtil.mul(srcDiscount, goodsPrice);//算出折扣销售价
		return goodsPrice;
	}
}
