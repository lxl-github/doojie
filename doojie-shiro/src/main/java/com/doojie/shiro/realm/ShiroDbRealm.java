package com.doojie.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.doojie.dao.PermissionMapper;
import com.doojie.domain.po.Employee;
import com.doojie.domain.po.Permission;
import com.doojie.service.service.EmployeeService;
import com.doojie.service.service.util.RedisCacheServiceUtil;
import com.doojie.shiro.dto.ShiroUser;

public class ShiroDbRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	@Autowired
	private RedisCacheServiceUtil redisCacheServiceUtil;
	
	public ShiroDbRealm() {
		super();
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		DoojieUsernamePasswordToken token = (DoojieUsernamePasswordToken) authToken;
		//用户登录的时候，清空当前权限的缓存，保证取到的权限为最新权限
		redisCacheServiceUtil.del(token.getUsername() + "_auth");
		String username = token.getUsername();
		if(username != null && !"".equals(username)){
			Employee employee =employeeService.getEmployeeByNumber(token.getUsername());
			if (employee != null) {
				return new SimpleAuthenticationInfo(new ShiroUser(employee.getNumber()),
						employee.getPassword(), getName());
			}else{
				throw new AuthenticationException();
			}
		}
		return null;
	}

	/**
	 * 授权查询回调函数, 进行权限效验，判断是否包含此url的响应权限.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权认证：" + principals.getRealmNames());    
	     ShiroUser shiroUser = (ShiroUser)principals.fromRealm(getName()).iterator().next();
	     
	     Employee employee = employeeService.getEmployeeByNumber(shiroUser.getLoginName());
	     
	     /* 这里编写授权代码 */
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> permissionList = (Set<String>)redisCacheServiceUtil.get(employee.getNumber() + "_auth");//memcachedClient.get(employee.getNumber());可以改成读取缓存 
		
		if(permissionList == null){
			permissionList = new HashSet<String>();
			List<Permission> permissionsUrlList = permissionMapper.selectPermissionListByEmployeeId(employee.getId(),null);
			String url;
			int index ;
			for(Permission permission : permissionsUrlList){
				url = permission.getUrl();
				if(url != null&& !"".equals(url)){
					index = url.indexOf("?");
					if(index != -1){
						permissionList.add(url.substring(0,index));
					}else{
						permissionList.add(url);
					}
				}
			}
			info.setStringPermissions(permissionList);
			
			redisCacheServiceUtil.set(shiroUser.getLoginName() + "_auth",info.getStringPermissions());
			
		}else{
			if(logger.isDebugEnabled()){
				logger.info("缓存权限:{}",permissionList.toString());
			}
			info.setStringPermissions(permissionList);
		}
		
		return info;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

}
