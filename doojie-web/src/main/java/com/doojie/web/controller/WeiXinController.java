package com.doojie.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doojie.common.util.HttpClientUtil;
import com.doojie.service.service.util.RedisCacheServiceUtil;
import com.doojie.service.service.util.WeiXinServiceUtil;
@Controller
@RequestMapping("webApp")
public class WeiXinController {

	@Autowired
	WeiXinServiceUtil weiXinServiceUtil;
	
	@Autowired
	RedisCacheServiceUtil redisCacheServiceUtil;
	
	@SuppressWarnings({ "unchecked", "static-access" })
	protected void getWeiXinToken(Model model,HttpServletRequest request){
		String js_accessToken = weiXinServiceUtil.getJSSDKAccessToken();  //获取微信jssdk---access_token
        String jsapi_ticket = weiXinServiceUtil.getJSSDKTicket(js_accessToken); //获取微信jssdk---ticket
//        Map<String,String> map = (Map<String, String>)redisCacheServiceUtil.get("weixin_sign");
//        if(map != null){
//        	model.addAttribute("timestamp", map.get("timestamp"));
//	        model.addAttribute("nonceStr", map.get("nonceStr"));
//	        model.addAttribute("signature", map.get("signature"));
//        }else{
	        //获取完整的URL地址
	        String fullPath = HttpClientUtil.getUrl(request);
	        Map<String,String> data = weiXinServiceUtil.sign(jsapi_ticket, fullPath);
//	        redisCacheServiceUtil.set("weixin_sign",data,7200l);
	        model.addAttribute("timestamp", data.get("timestamp"));
	        model.addAttribute("nonceStr", data.get("nonceStr"));
	        model.addAttribute("signature", data.get("signature"));
//        }
        model.addAttribute("appId",weiXinServiceUtil.WEIXIN_APPID);
	}
	
	public String getTokenCode(String code){
		return (String)redisCacheServiceUtil.get(code);
	}
	
	public void delTokenCode(String code){
		redisCacheServiceUtil.del(code);
	}
	
}
