package com.doojie.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import com.doojie.common.constant.BaseConstant;
import com.doojie.common.constant.OrderConstant;
import com.doojie.common.constant.UserConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.DistanceUtil;
import com.doojie.common.util.IpAdressUtil;
import com.doojie.common.util.JsonUtil;
import com.doojie.common.util.MD5Util;
import com.doojie.common.util.RandomUtil;
import com.doojie.common.util.XmlUtil;
import com.doojie.domain.bo.TimeSpan;
import com.doojie.domain.dto.IpResult;
import com.doojie.domain.dto.weixin.RequestPayParamDTO;
import com.doojie.domain.dto.weixin.WxPayResult;
import com.doojie.domain.po.Bussiness;
import com.doojie.domain.po.Order;
import com.doojie.domain.po.Product;
import com.doojie.domain.po.Recommend;
import com.doojie.domain.po.Region;
import com.doojie.domain.po.TradeRecord;
import com.doojie.domain.po.User;
import com.doojie.domain.vo.AppointmentVo;
import com.doojie.domain.vo.BussinessVo;
import com.doojie.domain.vo.OrderDetailVo;
import com.doojie.domain.vo.RecommendVo;
import com.doojie.domain.vo.TradeRecordVo;
import com.doojie.domain.vo.UserCardVo;
import com.doojie.service.service.BussinessService;
import com.doojie.service.service.OrderService;
import com.doojie.service.service.ProductService;
import com.doojie.service.service.RecommendService;
import com.doojie.service.service.RegionService;
import com.doojie.service.service.ServerService;
import com.doojie.service.service.UserService;
import com.doojie.service.service.util.RedisCacheServiceUtil;
import com.doojie.service.service.util.WeiXinServiceUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Controller
public class WebAppController extends WeiXinController{

	
	private static final Logger logger = LoggerFactory
			.getLogger(WebAppController.class);
	
	@Autowired
	private BussinessService bussinessService;
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private RedisCacheServiceUtil redisCacheServiceUtil;

    /**
     * 跳转用户协议
     * @return
     */
    @RequestMapping("userProctocol")
    public String userProctocol(){
        return "webApp/user/userProtocol";
    }
	
	/**
	 * 跳转首页
	 * @return
	 */
	@RequestMapping("index")
	public String index(){
		return "webApp/user/index/index";
	}
	
	/**
	 * 跳转到店预约
	 * @return
	 */
	@RequestMapping("toStore")
	public String toStore(){
		return "webApp/user/bussiness/list";
	}
	
	/**
	 * 跳转上门预约
	 * @return
	 */
	@RequestMapping("toDoor")
	public String toDoor(){
		return "webApp/user/bussiness/list";
	}
	
	/**
	 * 是否跳转订单
	 * @return
	 */
	@RequestMapping("user/isOrder")
	public @ResponseBody Boolean isOrder(){
		return true;
	}
	
	/**
	 * 是否跳转购卡
	 * @return
	 */
	@RequestMapping("user/isShopCard")
	public @ResponseBody Boolean isShopCard(){
		return true;
	}
	
	/**
	 * 是否跳转我的
	 * @return
	 */
	@RequestMapping("user/isMy")
	public @ResponseBody Boolean isMy(){
		return true;
	}
	
	/**
	 * 跳转订单
	 * @return
	 */
	@RequestMapping("user/order")
	public String order(){
		return "webApp/user/order/index";
	}
	
	/**
	 * 沙箱测试跳转购卡
	 * @return
	 */
	@RequestMapping("user/test/shopCard")
	public String shopCardTest(Model model,HttpServletRequest request){
		this.getWeiXinToken(model, request);
		return "webApp/user/product/index";
	}

    /**
     * 正式环境跳转购卡
     * @return
     */
    @RequestMapping("user/pub/shopCard")
    public String shopCard(Model model,HttpServletRequest request){
        this.getWeiXinToken(model, request);
        return "webApp/user/product/index";
    }
	
	/**
	 * 跳转我
	 * @return
	 */
	@RequestMapping("user/my")
	public String my(){
		return "webApp/user/my/index";
	}
	
	
	@RequestMapping("index2")
	public String index2(){
		return "webApp/user/bussiness/list2";
	}
	
	/**
	 * 车行列表
	 * @param model
	 * @param lng
	 * @param lat
	 * @param currentPage
	 * @param area
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public @ResponseBody String list(Model model,@RequestParam(value="lng") Double lng,@RequestParam(value="lat") Double lat,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="area") String area,HttpServletResponse response,HttpServletRequest request) throws IOException, IllegalAccessException, InvocationTargetException{
		
		if(lng == null || lat == null) {
			//获取ip
			String ip = IpAdressUtil.getRemortIP(request);
			
			//http://api.map.baidu.com/location/ip
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//			queryParams.add("ip", "114.250.5.76");
			queryParams.add("ip", ip);
			queryParams.add("ak", BaseConstant.AK);
			queryParams.add("coor", "bd09ll");
			Client client = Client.create();
			WebResource webResource = client.resource("http://api.map.baidu.com/location/ip");
			String result = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(String.class);
			IpResult ipResult = JsonUtil.fromJson(result, IpResult.class);
			lng = Double.parseDouble(ipResult.getContent().getPoint().getX());
			lat = Double.parseDouble(ipResult.getContent().getPoint().getY());
		}
		
		
		Page<Bussiness> page = new Page<Bussiness>();
		page.setCurrentPage(currentPage);
		
		if(!StringUtils.isNotBlank(area)){
			area = null;
		}
		
		//查询数据对象
		Page<BussinessVo> bussinessVoPageToDB = (Page<BussinessVo>)redisCacheServiceUtil.get("bussiness_list_"+lng+"_"+lat);
		
		if(bussinessVoPageToDB == null){
		
			bussinessVoPageToDB = bussinessService.getBussinessPageList(page,lng,lat,null, null, null, 0,area);
			
			redisCacheServiceUtil.set("bussiness_list_"+lng+"_"+lat,bussinessVoPageToDB);
			
		}
		
		bussinessVoPageToDB.setCurrentPage(page.getCurrentPage());
		
		return getBussinessVoPageToJsonCommon(currentPage, bussinessVoPageToDB);
	}

	/**
	 * 将商家数据转化json的公共方法
	 * @param currentPage
	 * @param bussinessVoPageToDB
	 * @return
	 * @throws IOException
	 */
	private String getBussinessVoPageToJsonCommon(Integer currentPage, Page<BussinessVo> bussinessVoPageToDB)
			throws IOException {
		//返回结果
		Page<BussinessVo> bussinessVoPage = new Page<BussinessVo>();
		//存入十条记录
		List<BussinessVo> bussinessVoList = new ArrayList<BussinessVo>();
		
		if(bussinessVoPageToDB.getTotalRecord() != 0){
			int count = 0;
			int blance = bussinessVoPageToDB.getTotalRecord() - (bussinessVoPageToDB.getPageSize()*(currentPage-1));
			//当剩余记录数 大于 每页数量 则 正常走页数*每页数量
			if(blance > bussinessVoPageToDB.getPageSize()){
				count = bussinessVoPageToDB.getPageSize()*currentPage;
			}else{
				//剩余记录数 + 每页数量*(当前显示页数-1)
				count = blance + (bussinessVoPageToDB.getPageSize()*(currentPage-1));
			}
			
			for(int i = bussinessVoPageToDB.getPageSize()*(currentPage-1);i < count;i++){
				bussinessVoList.add(bussinessVoPageToDB.getResults().get(i));
			}
		}
		bussinessVoPage.setTotalPage(bussinessVoPageToDB.getTotalPage());
		bussinessVoPage.setResults(bussinessVoList);
		return JsonUtil.toJson(bussinessVoPage);
	}
	
	/**
	 * 车行详情
	 * @param id
	 * @param lng
	 * @param lat
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("detail/{lng}/{lat}/{id}")
	public String detail(@PathVariable Integer id,@PathVariable Double lng,@PathVariable Double lat,Model model,HttpServletRequest request) throws IOException{
		
		if(lng == null || lat == null) {
			//获取ip
			String ip = IpAdressUtil.getRemortIP(request);
			
			//http://api.map.baidu.com/location/ip
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//			queryParams.add("ip", "114.250.5.76");
			queryParams.add("ip", ip);
			queryParams.add("ak", BaseConstant.AK);
			queryParams.add("coor", "bd09ll");
			Client client = Client.create();
			WebResource webResource = client.resource("http://api.map.baidu.com/location/ip");
			String result = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(String.class);
			IpResult ipResult = JsonUtil.fromJson(result, IpResult.class);
			lng = Double.parseDouble(ipResult.getContent().getPoint().getX());
			lat = Double.parseDouble(ipResult.getContent().getPoint().getY());
		}
		
		Bussiness bussiness = bussinessService.getBussinessById(id);
		BussinessVo bussinessVo = new BussinessVo();
		double distance = DistanceUtil.getDistance(lng,lat,new Double(bussiness.getLng()),new Double(bussiness.getLat()));
		DecimalFormat df = new DecimalFormat("#.00");
		bussinessVo.setDistance(new Double(df.format(distance)));
		bussinessVo.setBussiness(bussiness);
//		List<BaseServer> baseServerList = serverService.getBaseServerByBussinessId(id);
//		if(baseServerList != null && baseServerList.size() > 0){
//			bussinessVo.setBaseServerList(baseServerList);
//		}
		
//		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		
//		if(userId != null){
//			model.addAttribute("isHasRecommend",recommendService.isHasRecommended(userId,id));
//		}else{
//			model.addAttribute("isHasRecommend",false);
//		}
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		
		//查询数据对象
		Page<RecommendVo> recommendVoPage = recommendService.getRecommendByBussinessIdPageList(page, id,1);
		
		model.addAttribute("page", recommendVoPage);
		
		model.addAttribute("bussinessVo", bussinessVo);
		
		return "webApp/user/bussiness/detail";
	}
	
	/**
	 * 导航路线
	 * @param lng
	 * @param lat
	 * @param name
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("navigation/{lng}/{lat}/{name}")
	public String navigationRoad(@PathVariable Double lng,@PathVariable Double lat,@PathVariable String name,Model model,HttpServletRequest request) throws IOException{
		if(lng == null || lat == null) {
			//获取ip
			String ip = IpAdressUtil.getRemortIP(request);
			
			//http://api.map.baidu.com/location/ip
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//			queryParams.add("ip", "114.250.5.76");
			queryParams.add("ip", ip);
			queryParams.add("ak", BaseConstant.AK);
			queryParams.add("coor", "bd09ll");
			Client client = Client.create();
			WebResource webResource = client.resource("http://api.map.baidu.com/location/ip");
			String result = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(String.class);
			IpResult ipResult = JsonUtil.fromJson(result, IpResult.class);
			lng = Double.parseDouble(ipResult.getContent().getPoint().getX());
			lat = Double.parseDouble(ipResult.getContent().getPoint().getY());
		}
		model.addAttribute("lng",lng);
		model.addAttribute("lat",lat);
		model.addAttribute("name",name);
		return "webApp/user/bussiness/navigationRoad";
	}
	
	@RequestMapping("user/isEnableRecommend")
	public @ResponseBody Boolean isEnableRecommend(){
		return true;
	}
	
	/**
	 * 我要推荐
	 * @param bussinessId
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("user/myRecommend")
	public @ResponseBody Boolean myRecommend(HttpServletRequest request,String json) {
		try{
			Recommend recommend = JsonUtil.fromJson(json, Recommend.class);
			if(recommend.getId() != null){
				Recommend recommendOrgi = recommendService.getRecommendById(recommend.getId());
				recommendOrgi.setModifyTime(DateUtil.getCurrentTimespan());
				recommendOrgi.setReasons(recommend.getReasons());
				recommendOrgi.setRecommendIndex(recommend.getRecommendIndex());
				BeanUtils.copyProperties(recommend, recommendOrgi);
			}else{
				recommend.setCreateTime(DateUtil.getCurrentTimespan());
				recommend.setModifyTime(DateUtil.getCurrentTimespan());
				recommend.setIsTop(0);
				recommend.setStatus(0);
				Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
				recommend.setUserId(userId);//这里需要改成从session中取值
			}
			int count = recommendService.saveRecommend(recommend);
			return count > 0;
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug(e.toString(),e.fillInStackTrace());
			return false;
		}
	}
	
	/**
	 * 跳转推荐圈(暂时废弃)
	 * @return
	 */
	@RequestMapping("recommend")
	public String recommend(){
		return "webApp/user/recommend/list";
	}
	
	/**
	 * 加载推荐圈列表 根据推荐数多少排序（暂时废弃）
	 * @param currentPage
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("user/recommendBussinessList")
	public @ResponseBody String recommendBussinessList(Model model,@RequestParam(value="lng") Double lng,@RequestParam(value="lat") Double lat,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="area") String area,HttpServletResponse response,HttpServletRequest request) throws IOException{
		if(lng == null || lat == null) {
			//获取ip
			String ip = IpAdressUtil.getRemortIP(request);
			
			//http://api.map.baidu.com/location/ip
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			queryParams.add("ip", ip);
			queryParams.add("ak", BaseConstant.AK);
			queryParams.add("coor", "bd09ll");
			Client client = Client.create();
			WebResource webResource = client.resource("http://api.map.baidu.com/location/ip");
			String result = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(String.class);
			IpResult ipResult = JsonUtil.fromJson(result, IpResult.class);
			lng = Double.parseDouble(ipResult.getContent().getPoint().getX());
			lat = Double.parseDouble(ipResult.getContent().getPoint().getY());
		}
		
		if(!StringUtils.isNotBlank(area)){
			area = null;
		}
		
		//查询数据对象
		Page<BussinessVo> bussinessVoPageToDB = bussinessService.getRecommendBussinessAllList(currentPage,lng,lat,area);
		
		return getBussinessVoPageToJsonCommon(currentPage, bussinessVoPageToDB);
	}
	
	/**
	 * 跳转评价列表
	 * <br>
	 * 创建时间：2015年3月23日下午5:52:13
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param bussinessId
	 * @return
	 *
	 */
	@RequestMapping("toRecommendList/{bussinessId}")
	public String toRecommendList(Model model,@PathVariable Integer bussinessId){
		model.addAttribute("bussinessId",bussinessId);
		return "webApp/user/recommend/recommendList";
	}
	
	/**
	 * 评价列表
	 * <br>
	 * 创建时间：2015年3月23日下午5:40:36
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param lng
	 * @param lat
	 * @param currentPage
	 * @param area
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 *
	 */
	@RequestMapping("recommendList")
	public @ResponseBody String recommendList(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="bussinessId") Integer bussinessId,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		
		page.setCurrentPage(currentPage);
		
		//查询数据对象
		Page<RecommendVo> recommendVoPage = recommendService.getRecommendByBussinessIdPageList(page, bussinessId,null);
		
		return JsonUtil.toJson(recommendVoPage);
	}
	
	/**
	 * 跳转我的评价
	 * <br>
	 * 创建时间：2015年3月23日下午6:30:24
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	@RequestMapping("toMyRecommendList")
	public String toMyRecommendList(){
		return "webApp/user/my/myRecommendList";
	}
	
	/**
	 * 我的评价列表
	 * <br>
	 * 创建时间：2015年3月23日下午6:27:13
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param currentPage
	 * @param userId
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 *
	 */
	@RequestMapping("user/myRecommendList")
	public @ResponseBody String myRecommendList(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		
		page.setCurrentPage(currentPage);
		
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		
		//查询数据对象
		Page<RecommendVo> recommendVoPage = recommendService.getRecommendByUserIdPageList(page, userId);
		
		return JsonUtil.toJson(recommendVoPage);
	}
	
	/**
	 * 去评价
	 * @param model
	 * @param orderId
	 * @return
	 */
	@RequestMapping("user/toRecommend/{orderId}")
	public String toRecommend(Model model,@PathVariable Long orderId){
		OrderDetailVo orderDetailVo = orderService.getOrderDetailVoByOrderId(orderId);
		Recommend recommend = null;
		if(orderDetailVo.getRecommend() != null){
			recommend = orderDetailVo.getRecommend();
		}else{
			recommend = new Recommend();
			recommend.setBussinessId(orderDetailVo.getBussinessId());
			recommend.setOrderId(orderDetailVo.getId());
		}
		model.addAttribute("recommend", recommend);
		return "webApp/user/recommend/publish";
	}
	
	/**
	 * 跳转意见反馈
	 * <br>
	 * 创建时间：2015年3月23日下午7:23:09
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	@RequestMapping("uesr/toSuggest")
	public String suggest(){
		return "webApp/user/my/suggest";
	}
	
	/**
	 * 意见反馈
	 * <br>
	 * 创建时间：2015年3月24日上午10:38:49
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param content
	 * @return
	 *
	 */
	@RequestMapping("user/suggest")
	public @ResponseBody Boolean doSuggest(HttpServletRequest request,String content){
		
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		
		int count = userService.saveSuggest(content, userId);

		return count > 0;
	}
	
	/**
	 * 跳转我的交易
	 * <br>
	 * 创建时间：2015年7月18日下午3:23:09
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	@RequestMapping("toMyTrade")
	public String toMyTrade(){
		return "webApp/user/my/myTrade";
	}
	
	@RequestMapping("user/myTrade")
	public @ResponseBody String myTrade(HttpServletRequest request) throws IOException{
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		List<TradeRecord> tradeRecordList = userService.getTradeRecordListByUserId(userId);
		return JsonUtil.toJson(tradeRecordList);
	}
	
	/**
	 * 跳转交易详情
	 * <br>
	 * 创建时间：2015年7月10日上午11:44:18
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param tradeId
	 * @return
	 *
	 */
	@RequestMapping("toMyTradeDetail/{tradeId}")
	public String toMyTradeDetail(Model model,@PathVariable Long tradeId){
		model.addAttribute("tradeId",tradeId);
		return "webApp/user/my/myTradeDetail";
	}
	
	/**
	 * 交易详情
	 * <br>
	 * 创建时间：2015年7月10日上午11:45:20
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param tradeId
	 * @return
	 * @throws IOException
	 *
	 */
	@RequestMapping("user/myTradeDetail")
	public @ResponseBody String myTradeDetail(Model model,Integer tradeId,HttpServletRequest request) throws IOException{
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		TradeRecordVo tradeRecordVo = userService.getTradeRecordVoByUserIdAndTradeRecordId(userId, tradeId);
		return JsonUtil.toJson(tradeRecordVo);
	}
	
	/**
	 * 跳转我的卡券
	 * <br>
	 * 创建时间：2015年7月18日下午3:23:09
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	@RequestMapping("toMyCard")
	public String toMyCard(){
		return "webApp/user/my/myCard";
	}
	
	@RequestMapping("user/myCard")
	public @ResponseBody String myCard(HttpServletRequest request) throws IOException{
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		List<UserCardVo> userCardList = userService.getUserCardVoList(userId,null,null);
		return JsonUtil.toJson(userCardList);
	}
	
	
	/**
	 * 实现自动登录，用于过滤器检测cookie值时，存在openId则调用
	 * <br>
	 * 创建时间：2015年10月19日下午2:50:38
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 *
	 */
	@RequestMapping("reLogin")
	public void reLogin(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String openId = request.getParameter("openId");
		String reqUrl = request.getParameter("reqUrl");
		User user = userService.getUserByOpenId(openId);
        if(user == null){
            request.getRequestDispatcher("/webApp/login").forward(request,response);
        }else{
            request.getSession().setAttribute(BaseConstant.USER,user);
            request.getSession().setAttribute(BaseConstant.USER_ID,user.getId());
            request.getSession().setAttribute(BaseConstant.USER_WEIXIN_OPENID,openId);
            request.getRequestDispatcher(reqUrl).forward(request,response);
        }
	}
	
	@RequestMapping("login")
	public String login(){
		return "webApp/user/login";
	}
	
	/**
	 * 微信授权回调方法
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("weiXinAuthCallBack")
	public String weiXinAuthCallBack(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
		
		String openId = weiXinServiceUtil.getAuthOpenId(code);
		
		request.getSession().setAttribute(BaseConstant.USER_WEIXIN_OPENID,openId);
		
		User user = (User)request.getSession().getAttribute(BaseConstant.USER);
		
		if(user == null){
			Integer userId = (Integer) request.getSession().getAttribute(BaseConstant.USER_ID);
			user = userService.getUserById(userId);
		}
		
		user.setWeixinNumber(openId);
		
		userService.updateUser(user);
		
		Cookie cookie = new Cookie(BaseConstant.COOKIE_USER_WEIXIN_OPENID,openId);
		//设置过期时间为1个月
		cookie.setMaxAge(30*24*3600);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
		
		return my();
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest request,HttpServletResponse response,Model model,@RequestParam("mobile") String mobile,@RequestParam("password") String password) throws IOException{
		User user = userService.getUserByMobile(mobile);
		if(user != null){
			if(user.getPassword().equals(MD5Util.getMD5(password))){
				request.getSession().setAttribute(BaseConstant.USER,user);
				request.getSession().setAttribute(BaseConstant.USER_ID,user.getId());
				//开始执行授权
				if(null == user.getWeixinNumber()){
					return getAuthUrl();
				}else{
					request.getSession().setAttribute(BaseConstant.USER_WEIXIN_OPENID,user.getWeixinNumber());
					Cookie cookie = new Cookie(BaseConstant.COOKIE_USER_WEIXIN_OPENID,user.getWeixinNumber());
					//设置过期时间为1个月
					cookie.setMaxAge(30*24*3600);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
				}
				return "0";
			}else{
				return "2";//账户密码错误
			}
		}else{
			return "1";//账户不存在
		}
	}

	private String getAuthUrl() throws UnsupportedEncodingException {
		String auth_url = WeiXinServiceUtil.AUTH_URL;
		auth_url = auth_url.concat("appid=").concat(WeiXinServiceUtil.WEIXIN_APPID);
		auth_url = auth_url.concat("&redirect_uri=").concat(UriUtils.encodeHost(WeiXinServiceUtil.AUTH_CALLBACK_URL,"utf-8"));
		auth_url = auth_url.concat("&response_type=").concat("code");
		auth_url = auth_url.concat("&scope=").concat("snsapi_base");
		auth_url = auth_url.concat("#wechat_redirect");
		return auth_url;
	}
	
	/**
	 * 去注册
	 * @return
	 */
	@RequestMapping("register")
	public String register(){
		return "webApp/user/register";
	}
	
	/**
	 * 判断注册账户是否已存在
	 * <br>
	 * 创建时间：2015年3月24日下午4:51:54
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param mobile
	 * @return
	 *
	 */
	@RequestMapping("isExistsUser")
	public @ResponseBody Boolean isExistsUser(String mobile){
		User user = userService.getUserByMobile(mobile);
		return user != null;
	}
	
	/**
	 * 开始注册
	 * @param model
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("doRegister")
	@ResponseBody
	public String doRegister(HttpServletRequest request,Model model,@RequestParam("mobile") String mobile,@RequestParam("password") String password) throws UnsupportedEncodingException{
		User user = new User();
		user.setMobile(mobile);
		user.setPassword(MD5Util.getMD5(password));
		user.setSource(UserConstant.SOURCE_REGISTER);
		user.setCreateTime(DateUtil.getCurrentTimespan());
		int count = userService.saveUser(user);
		if(count > 0){
			request.getSession().setAttribute(BaseConstant.USER,user);
			request.getSession().setAttribute(BaseConstant.USER_ID,user.getId());
			return getAuthUrl();
		}else{
			return "false";
		}
	}
	
	@RequestMapping("user/loginOut")
	public @ResponseBody Boolean loginOut(HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
        Cookie cookie = new Cookie(BaseConstant.COOKIE_USER_WEIXIN_OPENID,null);
        cookie.setMaxAge(-1);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
		return true;
	}
	
	/**
	 * 跳转天气指南
	 * @return
	 */
	@RequestMapping("weather")
	public String weather(){
		return "webApp/user/index/weather";
	}
	
	@RequestMapping("user/isEnableYuYue")
	public @ResponseBody Boolean isEnableYuYue(){
		return true;
	}
	
	/**
	 * 根据分类获取用户卡券
	 * @param request
	 * @param category,produces = "text/html;charset=UTF-8"
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getMyCard/{category}/{isDoor}")
	public @ResponseBody String getMyCard(HttpServletRequest request,@PathVariable Integer category,@PathVariable Integer isDoor) throws IOException{
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		List<UserCardVo> userCardVos = userService.getUserCardVoList(userId, category,isDoor);
		return JsonUtil.toJson(userCardVos);
	}
	
	//到店预约 --- 开始下单
	@RequestMapping("toStoreAppointment/{id}/{name}/{type}")
	public String toStoreAppointment(Model model,HttpServletRequest request,@PathVariable Integer id,@PathVariable String name,@PathVariable Integer type) throws IOException{
		model.addAttribute("bussinessId",id);
		model.addAttribute("bussinessName",name);
		User user = (User)request.getSession().getAttribute(BaseConstant.USER);
		if(user == null){
			return login();
		}
		model.addAttribute("userMobile",user.getMobile());
		
		String myCard = getMyCard(request,type,0);
		
		model.addAttribute("myCard",myCard);
		
		model.addAttribute("type",type);
		
		model.addAttribute("plateMap", BaseConstant.PLATE_MAP);
		
		model.addAttribute("letterMap", BaseConstant.LETTER_MAP);
		
		model.addAttribute("appiontmentDate",DateUtil.dateToWeek());
		
		return "webApp/user/appointment/bussinessAppointment";
	}
	
	/**
	 * 根据预约日期获取时段
	 * @param request
	 * @param appointmentDate
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getTimeSpan/{appointmentDate}")
	public @ResponseBody String getTimeSpan(HttpServletRequest request,@PathVariable String appointmentDate) throws IOException{
		Map<String,List<TimeSpan>> map = DateUtil.getTimeSpan();
		List<TimeSpan> timeSpanList = map.get(appointmentDate);
		return JsonUtil.toJson(timeSpanList);
	}
	
	
	@RequestMapping("doStoreAppointment")
	public @ResponseBody Long doStoreAppointment(Model model,HttpServletRequest request,String json) throws IOException{
		try{
			AppointmentVo appointmentVo = JsonUtil.fromJson(json,AppointmentVo.class);
			Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
			return orderService.saveOrder(appointmentVo, userId,OrderConstant.ORDER_APPOI);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.debug("doStoreAppointment save order exception {}",ex.getMessage());
			return 3l;//异常
		}
	}
	
	//上门预约 --- 开始下单
	@RequestMapping("toDoorAppointment")
	public String toDoorAppointment(Model model,HttpServletRequest request) throws IOException{
		User user = (User)request.getSession().getAttribute(BaseConstant.USER);
		if(user == null){
			return login();
		}
		model.addAttribute("userMobile",user.getMobile());
		//默认加载洗车类型的卡
		String myCard = getMyCard(request,1,0);
		
		model.addAttribute("myCard",myCard);
		
		//初始化开通区域，根据定位后得出区域不匹配则无法下单
//		Region region = regionService.getRegionByLikeName("",0);
		//如果区域为null 则当前城市未开通
		
		return "webApp/user/appointment/appointment";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("doDoorAppointment")
	public @ResponseBody Boolean doDoorAppointment(Model model,HttpServletRequest request,String json) throws IOException{
		AppointmentVo appointmentVo = JsonUtil.fromJson(json,AppointmentVo.class);
		
		//预约时检测当天是否有预约未处理的，存在则不可预约当天服务
		return true;
	}
	
	/**
	 * //我的订单
	 * <br>
	 * 创建时间：2015年7月10日上午11:44:22
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param request
	 * @param currentPage
	 * @return
	 * @throws IOException
	 *
	 */
	@RequestMapping("user/myOrder")
	public @ResponseBody String myOrder(Model model,HttpServletRequest request,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage) throws IOException{
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		Page<Order> page = new Page<Order>();
		orderService.getOrderAllPageList(page,null, userId,null);
		return JsonUtil.toJson(page);
	}
	
	/**
	 * 跳转订单详情
	 * <br>
	 * 创建时间：2015年7月10日上午11:44:18
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param orderId
	 * @return
	 *
	 */
	@RequestMapping("user/toMyOrderDetail/{orderId}")
	public String toMyOrderDetail(Model model,@PathVariable Long orderId){
		model.addAttribute("orderId",orderId);
		return "webApp/user/order/detail";
	}
	
	/**
	 * 订单详情
	 * <br>
	 * 创建时间：2015年7月10日上午11:45:20
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param orderId
	 * @return
	 * @throws IOException
	 *
	 */
	@RequestMapping("user/myOrderDetail")
	public @ResponseBody String myOrderDetail(Model model,Long orderId) throws IOException{
		OrderDetailVo orderDetailVo = orderService.getOrderDetailVoByOrderId(orderId);
		return JsonUtil.toJson(orderDetailVo);
	}
	
	/**
	 * 确认订单
	 * @param orderId
	 * @return
	 */
	@RequestMapping("user/orderConfim")//确认- -- 洗车完成后，必须确认，否则45分钟后自动确认
	public @ResponseBody Boolean doConfirm(Long orderId,Integer oldStatus){
		//修改订单状态为已确认，同时扣减卡的消费次数，叠加已消费次数
		try{
			Order order = orderService.getOrderByOrderIdAndOldStatus(oldStatus, orderId);
			boolean f = orderService.updateByOrderIdAndStatus(order, OrderConstant.CONSUM_COMPLETE_ORDER_STATUS, oldStatus);
			return f;
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug("confirm exception {}",e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据区域名称查询区域
	 * @param name
	 * @return
	 */
	@RequestMapping("getRegionIdByName")
	public @ResponseBody Integer getRegionIdByName(String name){
		Region region = regionService.getRegionByLikeName(name,0);
		return region == null ? 0 : region.getId();
	}
	
	//购买都捷卡 -- 分区域显示商品  ，暂时可跳过支付，直接保存都捷卡
	@RequestMapping("user/productList")
	public @ResponseBody String productList(Integer regionId) throws IOException{
		List<Product> productList = productService.getProductList(1, 1,regionId);
		return JsonUtil.toJson(productList);
	}
	
	
	@RequestMapping("user/wxPay")
	public @ResponseBody RequestPayParamDTO pay(Integer productId,HttpServletRequest request){
		Product product = productService.getProductById(productId);
		String outTradeNo = orderService.getTradeNo();
		String totalFee = String.valueOf(product.getDiscountPrice());
		String openId = (String) request.getSession().getAttribute(BaseConstant.USER_WEIXIN_OPENID);
		try {
			RequestPayParamDTO requestPayParamDTO = weiXinServiceUtil.getWeiXinPayRequestParam(productId,StringEscapeUtils.unescapeHtml(product.getName()),totalFee,IpAdressUtil.getIpAddr(request),outTradeNo,openId);
			return requestPayParamDTO;
		} catch (JDOMException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 微信支付成功后回调函数
	 * <br>
	 * 创建时间：2015年10月2日下午10:47:59
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JDOMException
	 *
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("wxPay/notify")
	public @ResponseBody void weixinPaySuccess(HttpServletRequest request,HttpServletResponse response) throws IOException, JDOMException{
		
		String inputLine = null;
		String notityXml = "";
		String resXml = "";

		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("接收到的报文：" + notityXml);
		
		Map m = XmlUtil.doXMLParse(notityXml);
		if(m != null){
			WxPayResult wpr = new WxPayResult();
			wpr.setAppid(m.get("appid").toString());
			wpr.setBankType(m.get("bank_type").toString());
			wpr.setCashFee(m.get("cash_fee").toString());
			wpr.setFeeType(m.get("fee_type").toString());
			wpr.setIsSubscribe(m.get("is_subscribe").toString());
			wpr.setMchId(m.get("mch_id").toString());
			wpr.setNonceStr(m.get("nonce_str").toString());
			wpr.setOpenid(m.get("openid").toString());
			wpr.setOutTradeNo(m.get("out_trade_no").toString());
			wpr.setResultCode(m.get("result_code").toString());
			wpr.setReturnCode(m.get("return_code").toString());
			wpr.setSign(m.get("sign").toString());
			wpr.setTimeEnd(m.get("time_end").toString());
			wpr.setTotalFee(m.get("total_fee").toString());
			wpr.setTradeType(m.get("trade_type").toString());
			wpr.setTransactionId(m.get("transaction_id").toString());
			wpr.setProductId(Integer.valueOf(m.get("productId").toString()));
			User user = userService.getUserByOpenId(wpr.getOpenid());
			
			if("SUCCESS".equals(wpr.getResultCode())){
				Boolean f = userService.getIsContains(wpr.getOutTradeNo());
				String flag = "success";
				if(f){
					flag = userService.saveUserProduct(wpr.getProductId(), user.getId(),wpr.getOutTradeNo(),wpr.getTransactionId());
				}
				
				if(flag.equals("success")){
					resXml = XmlUtil.setXML("SUCCESS", "OK");//通知微信已支付成功，请勿再次调用
					logger.info("微信支付回调完成并支付成功");
				}
			}else{
				resXml = XmlUtil.setXML("FAIL", "报文为空");
			}
		}else{
			resXml = XmlUtil.setXML("FAIL", "报文为空");
		}
		
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
		
		logger.info("微信支付回调完成");
	}
	
	/**
	 * 验码消费生成
	 * @param userId
	 * @param cardId
	 * @param model
	 * @return
	 */
	@RequestMapping("user/greateCheckCode/{cardId}")
	public String consumCheckCode(@PathVariable("cardId")Integer cardId,Model model,HttpServletRequest request){
		Integer userId = (Integer)request.getSession().getAttribute(BaseConstant.USER_ID);
		String key = userId.toString().concat("&").concat(cardId.toString());
		String checkCode = (String)redisCacheServiceUtil.get(key);
		if(checkCode == null){
			String code = RandomUtil.randomCheckCode();
			redisCacheServiceUtil.set(key,code,60l*5);
			redisCacheServiceUtil.set(code,key,60l*5);
			checkCode = code;
		}
		model.addAttribute("code", checkCode);
		model.addAttribute("cardId", cardId);
		return "webApp/user/my/consumeCheckCode";
	}
}