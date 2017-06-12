package com.doojie.domain.dto.weixin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayNotify", propOrder = {
    "OpenId",
    "AppId",
    "IsSubscribe",
    "TimeStamp",
    "NonceStr",
    "AppSignature",
    "SignMethod"
})
@XmlRootElement(name="xml")
public class PayNotify {

	String OpenId;
	String AppId;
	Integer IsSubscribe; //用户是否关注了公众号。1 为关注，0 为未关注。
	Long TimeStamp;
	String NonceStr;
	String AppSignature;
	String SignMethod;
	public String getOpenId() {
		return OpenId;
	}
	public void setOpenId(String openId) {
		OpenId = openId;
	}
	public String getAppId() {
		return AppId;
	}
	public void setAppId(String appId) {
		AppId = appId;
	}
	public Integer getIsSubscribe() {
		return IsSubscribe;
	}
	public void setIsSubscribe(Integer isSubscribe) {
		IsSubscribe = isSubscribe;
	}
	public Long getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		TimeStamp = timeStamp;
	}
	public String getNonceStr() {
		return NonceStr;
	}
	public void setNonceStr(String nonceStr) {
		NonceStr = nonceStr;
	}
	public String getAppSignature() {
		return AppSignature;
	}
	public void setAppSignature(String appSignature) {
		AppSignature = appSignature;
	}
	public String getSignMethod() {
		return SignMethod;
	}
	public void setSignMethod(String signMethod) {
		SignMethod = signMethod;
	}

}
