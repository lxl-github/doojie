package com.doojie.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.dao.ModuleMapper;
import com.doojie.dao.PermissionMapper;
import com.doojie.dao.RoleMapper;
import com.doojie.domain.po.Module;
import com.doojie.domain.po.Permission;
import com.doojie.domain.po.Role;
import com.doojie.service.service.MainService;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;
	
	
	
	//生成树
	@Override
	public String createFunctionTree(Integer employeeId,String domain){
		StringBuffer headStr = new StringBuffer(50);
		StringBuffer endStr = new StringBuffer();
		headStr.append("\r\nfunction tree_init(){\r\n");
        headStr.append("var treeLeaf=\"\";\r\n");
        headStr.append("\r\n var tree=new Tree();\r\n").
        	append("tree.setExpandDepth(1);\r\n").
        	append("tree.setShowRoot(true);\r\n").
        	append("tree.setplusIcon(\"lplus.gif\");\r\n").
        	append("tree.setminusIcon(\"lminus.gif\");\r\n").
        	append("tree.setImagePath(\""+domain+"/static/treeImage\");\r\n").
        	append("tree.setTargetFrame(\"rhs\");\r\n");
        endStr.append("tree.show();\r\n");
        endStr.append("}\r\n");
        endStr.append("tree_init();\r\n");
        //菜单树中功能部门字符串
        String renderStr = createUserFunctionTreeleaf(employeeId,domain);
        return headStr.append(renderStr).append(endStr).toString();
	}
	
	private String createUserFunctionTreeleaf(Integer employeeId,String domain){
		StringBuffer renderTree = new StringBuffer();
		//获取角色列表
		List<Role> roleList = roleMapper.selectRoleByEmployeeId(employeeId);
		
		List<Module> moduleList = moduleMapper.selectModuleListByRoleId(roleList);
		
		Integer index = 1;
		
		for(Module module : moduleList){
			if(module.getIsRoot().intValue() == 1){
				//放入顶级节点到existTreeNodes中，然后js树中添加根节点
				renderTree.append("var tree1 = tree.addRoot(\"" + module.getModuleName()
						+ "\");\r\n");
			}else{
				renderTree.append("var tree_"+ index +" = tree1.addItem('"+ module.getModuleName() + "','','rhs');\r\n");
				List<Permission> permissionList = permissionMapper.selectPermissionByModuleId(module.getId(),1);
				for(Permission permission : permissionList){
					renderTree.append("treeLeaf=tree_"+index+".addItem('"+ permission.getPermissionName() + "','"+ domain +"/"
						+ ((permission.getUrl().charAt(0) == '/') ? (permission.getUrl().substring(1)) : permission.getUrl()) + "','"
						+ permission.getTarget()
						+ "','Tree_Arrow_03.gif');\r\n");
				}
				index++;
			}
		}
		
        return renderTree.toString();
	}
}
