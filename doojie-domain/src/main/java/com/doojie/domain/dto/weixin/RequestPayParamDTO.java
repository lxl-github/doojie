package com.doojie.domain.dto.weixin;

/**
 * 功能概述：请求微信统一支付接口的参数对象
 * <br/>
 * 创建时间：2015年10月2日下午3:43:29
 * <br/>
 * 修改记录：
 * @author lixiaoliang
 */
public class RequestPayParamDTO {
	
	//公众号名称，由商户传入
	private String appId;
	//时间戳，自 1970 年以来的秒数
	private String timeStamp;
	//随机串
	private String nonceStr;
	//订单详情
	private String packageValue;
	//微信签名方式
	private String signType;
	//微信签名
	private String paySign;
	
	public RequestPayParamDTO(){
		
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}	
}
