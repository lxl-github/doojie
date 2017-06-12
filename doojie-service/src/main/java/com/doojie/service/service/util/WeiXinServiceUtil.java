package com.doojie.service.service.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.util.DateUtil;
import com.doojie.common.util.HttpClientUtil;
import com.doojie.common.util.IpAdressUtil;
import com.doojie.common.util.JsonUtil;
import com.doojie.common.util.MD5Util;
import com.doojie.common.util.RandomUtil;
import com.doojie.common.util.XmlUtil;
import com.doojie.domain.dto.weixin.RequestPayParamDTO;

@Service
public class WeiXinServiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinServiceUtil.class);

	// 微信JSSDK的AccessToken请求URL地址
	public final static String weixin_jssdk_acceToken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	// 微信JSSDK的ticket请求URL地址
	public final static String weixin_jssdk_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	//获取用户唯一标识
	public final static String weixin_jssdk_openId_url = "https://api.weixin.qq.com/cgi-bin/user/info";
	//微信公众号appId
	public final static String WEIXIN_APPID = "wxd06f27e4c9b04024";
	//微信公众号密钥
	public final static String WEIXIN_SECRET = "f0abd6821f5a9764201d347683226f19";
	//商户appId
	public final static String MCH_APPID = "wxd06f27e4c9b04024";
	//商户支付密钥
	public final static String API_KEY = "1234567890qwertyuiopasdfghjklzxc";
	//商户号
	public final static String MCH_ID = "1274705201";
	//支付成功后通知地址
//	public final static String NOTIFY_TEST_URL = "https://wei.tunnel.mobi/doojie-web/webApp/wxPay/notify?productId=";

    public final static String NOTIFY_URL = "http://gzwx.doojie.com/webApp/wxPay/notify?productId=";

	//统一下单接口，获取预支付Id
	public final static String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//签名类型
	public final static String SIGN_TYPE = "MD5";
	//授权地址
	public final static String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";
	//获取openId地址
	public final static String AUTH_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	//授权回调方法地址
//	public final static String AUTH_CALLBACK_TEST_URL = "https://wei.tunnel.mobi/doojie-web/webApp/weiXinAuthCallBack";

    //授权回调方法地址
    public final static String AUTH_CALLBACK_URL = "http://gzwx.doojie.com/webApp/weiXinAuthCallBack";
	
	@Autowired
	RedisCacheServiceUtil redisCacheServiceUtil;

	/**
	 * 获取微信授权的后的OpenId
	 * 
	 * @author lxl
	 */
	@SuppressWarnings("unchecked")
	public String getAuthOpenId(String code) {
		String returnString = "";
		try {
			SortedMap<String, String> map = new TreeMap<String, String>();
			map.put("appid", WEIXIN_APPID);
			map.put("secret", WEIXIN_SECRET);
			map.put("code",code);
			map.put("grant_type","authorization_code");
			String message = HttpClientUtil.get(map,
					AUTH_ACCESSTOKEN_URL);

			// 如果请求成功
			if (null != message) {
				try {
					Map<String, Object> map2 = JsonUtil.fromJson(message,
							Map.class);
					returnString = (String) map2.get("openid");
				} catch (Exception e) {
					returnString = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 获取微信JSSDK的access_token
	 * 
	 * @author lxl
	 */
	@SuppressWarnings("unchecked")
	public String getJSSDKAccessToken() {
		String returnString = "";
		try {
			String access_token = (String) redisCacheServiceUtil
					.get("weixin_token");
			if (access_token == null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("appid", WEIXIN_APPID);
				map.put("secret", WEIXIN_SECRET);
				String message = HttpClientUtil.get(map,
						weixin_jssdk_acceToken_url);

				// 如果请求成功
				if (null != message) {
					try {
						Map<String, Object> map2 = JsonUtil.fromJson(message,
								Map.class);
						returnString = (String) map2.get("access_token");
						Integer expired = (Integer) map2.get("expires_in");
						redisCacheServiceUtil.set("weixin_token", returnString,
								Long.valueOf(expired.toString()));
					} catch (Exception e) {
						returnString = null;
					}
				}
			} else {
				returnString = access_token;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 * 获取微信JSSDK的ticket
	 * 
	 * @author lxl
	 */
	public String getJSSDKTicket(String access_token) {
		String returnString = "";
		try {
			String ticket = (String) redisCacheServiceUtil
					.get("weixin_ticket");
			if(ticket == null){
				Map<String, String> map = new HashMap<String, String>();
				map.put("access_token", access_token);
				map.put("type", "jsapi");
				String message = HttpClientUtil.get(map, weixin_jssdk_ticket_url);
	
				// 如果请求成功
				if (null != message) {
					try {
						Map<String, Object> map2 = JsonUtil.fromJson(message,
								Map.class);
						returnString = (String) map2.get("ticket");
						Integer expired = (Integer) map2.get("expires_in");
						redisCacheServiceUtil.set("weixin_ticket", returnString,
								Long.valueOf(expired.toString()));
					} catch (Exception e) {
						returnString = null;
					}
				}
			}else{
				returnString = ticket;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}
	
	/**
	 * 通过openId获取微信用户系信息
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private String getWeiXinUser(String openId){
		String access_token = getJSSDKAccessToken();
		String returnString = "";
		try{
			Map<String,String> map = new LinkedHashMap<String, String>();
			map.put("access_token",access_token);
			map.put("openid",openId);
			map.put("lang","zh_CN");
			String message = HttpClientUtil.get(map,weixin_jssdk_openId_url);
			// 如果请求成功
			if (null != message) {
				try {
					Map<String, Object> map2 = JsonUtil.fromJson(message,
							Map.class);
					returnString = (String) map2.get("openid");
				} catch (Exception e) {
					e.printStackTrace();
					returnString = null;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnString;
	}
	
    /**
     * js扫描生成签名,采用SHA-1方式
     * <br>
     * 创建时间：2015年10月19日上午9:46:30
     * <br>
     * @author lixiaoliang
     * <br>
     * @param jsapi_ticket
     * @param url
     * @return
     *
     */
    public Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String str;
        String signature = "";
 
        //注意这里参数名必须全部小写，且必须有序
        str = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
 
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
 
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
 
        return ret;
    }
 
    /**
     * 获取请求统一下单后返回参数,通过jsAPI调用支付
     * <br>
     * 创建时间：2015年10月2日下午5:27:50
     * <br>
     * @author lixiaoliang
     * <br>
     * @param produtName 商品名称
     * @param totalFee	总价
     * @param ip	当前Ip
     * @param tradeNo 交易单号
     * @return
     * @throws JDOMException
     * @throws IOException
     *
     */
    @SuppressWarnings({"unchecked" })
	public RequestPayParamDTO getWeiXinPayRequestParam(Integer productId,String produtName,String totalFee,String ip,String tradeNo,String openId) throws JDOMException, IOException{
    	SortedMap<Object,Object> resultMap = new TreeMap<Object, Object>();
    	String requestParamString = getWeiXinPayRequestXmlString(productId,produtName, totalFee, ip, tradeNo, openId);
        logger.info("调用统一支付请求参数：{}",requestParamString);
    	String resultXMLString = HttpClientUtil.httpsRequest(PAY_URL,"POST",requestParamString);
        logger.info("调用统一支付请求结果：{}",resultXMLString);
    	Map<String, String> map = XmlUtil.doXMLParse(resultXMLString);
    	SortedMap<Object,Object> params = new TreeMap<Object,Object>();
    	String timeStamp = create_timestamp();
    	String nonceStr = map.get("nonce_str");
        params.put("appId",MCH_APPID);
        params.put("timeStamp",timeStamp);
        params.put("nonceStr", nonceStr);
        params.put("package", "prepay_id=" + map.get("prepay_id"));
        params.put("signType",SIGN_TYPE);
        String paySign = createSign(params);
        resultMap.put("packageValue", "prepay_id=" + map.get("prepay_id"));    //这里用packageValue是预防package是关键字在js获取值出错
        resultMap.put("paySign", paySign);
        resultMap.put("nonceStr", nonceStr);
        resultMap.put("timeStamp",timeStamp);
        return getWeiXinPayParams(resultMap);
    }
    
    /**
     * 字节转hex 用于js扫描签名编码
     * <br>
     * 创建时间：2015年10月19日上午9:47:05
     * <br>
     * @author lixiaoliang
     * <br>
     * @param hash
     * @return
     *
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
 
    /**
     * 生成uuid，用于js扫描
     * <br>
     * 创建时间：2015年10月19日上午9:48:58
     * <br>
     * @author lixiaoliang
     * <br>
     * @return
     *
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
    
    /**
	 * 获取随机字符串，用于微信支付
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = DateUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = RandomUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
    
    /**
     * 生成时间戳
     * <br>
     * 创建时间：2015年10月19日上午9:49:57
     * <br>
     * @author lixiaoliang
     * <br>
     * @return
     *
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    /**
     * 获取支付请求参数
     * <br>
     * 创建时间：2015年10月2日下午4:04:46
     * <br>
     * @author lixiaoliang
     * <br>
     * @return
     *
     */
    private RequestPayParamDTO getWeiXinPayParams(SortedMap<Object,Object> paramMap){
    	RequestPayParamDTO requestPayParamDTO = new RequestPayParamDTO();
    	requestPayParamDTO.setAppId(MCH_APPID);
    	requestPayParamDTO.setNonceStr((String)paramMap.get("nonceStr"));
    	requestPayParamDTO.setPackageValue((String)paramMap.get("packageValue"));
    	requestPayParamDTO.setPaySign((String)paramMap.get("paySign"));
    	requestPayParamDTO.setTimeStamp((String)paramMap.get("timeStamp"));
    	requestPayParamDTO.setSignType(SIGN_TYPE);
        logger.info("获取支付请求参数：{}",requestPayParamDTO.toString());
    	return requestPayParamDTO;
    }
    
    /**
     * 获取支付签名，采用MD5方式
     * <br>
     * 创建时间：2015年10月2日下午3:59:03
     * <br>
     * @author lixiaoliang
     * <br>
     * @return
     *
     */
    @SuppressWarnings("rawtypes")
	private String createSign(SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + API_KEY);
        String sign = MD5Util.getMD5(sb.toString()).toUpperCase();
        logger.info("获取调用统一下单接口的签名：{}",sign);
        return sign;
    }
    
    /**
     * 获取请求微信统一下单参数
     * <br>
     * 创建时间：2015年10月2日下午4:40:38
     * <br>
     * @author lixiaoliang
     * <br>
     * @param produtName 商品名称
     * @param totalFee	总价
     * @param ip	当前Ip
     * @param tradeNo 交易单号
     * @return xmlString
     *
     */
    private String getWeiXinPayRequestXmlString(Integer productId,String produtName,String totalFee,String ip,String tradeNo,String openId){
    	SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
    	parameters.put("appid", MCH_APPID);
    	parameters.put("mch_id", MCH_ID);
    	parameters.put("nonce_str", create_nonce_str());
    	parameters.put("body", produtName);
    	parameters.put("out_trade_no",tradeNo);
    	parameters.put("total_fee", totalFee);
    	parameters.put("spbill_create_ip",ip);
    	parameters.put("notify_url", NOTIFY_URL + productId.toString());
    	parameters.put("trade_type", "JSAPI");
    	parameters.put("openid",openId);
    	String sign = createSign(parameters);
    	parameters.put("sign", sign);
    	
    	return XmlUtil.getRequestXml(parameters);
    }
    
}
