


/**
 * 设置页面载入完后的功能
 * 
 */
$(document).ready(function(){
	ajaxWait();		//设置ajax等待特效
	//设置表格奇偶行换色，并且光标移动换色
	changeTableColor();
});

/**
 * 页面以红色字体显示在状态栏显示消息提示
 * 
 * 
 * @param msg 消息内容
 * @param type 操作成功或失败 type="success"或type="fail" 默认为success
 * @param locObj jquery Object 附加至哪个页面元素里 如$("#mydiv")，可不传此参数，若不传默认附加至$(".m_ri")元素里
 * @returns {Boolean}
 * 
 * 调用示例：
 * tip（"操作成功!"）;				//默认位置显示 操作成功
 * tip（"操作失败!","fail"）;		//默认位置显示 操作失败
 * tip（"操作失败!","fail",$("#mydiv")）;	//$("#mydiv")里显示 操作失败
 */
function tip(msg,type,locObj){
	if(msg=="") return false;
	var success="<p id=\"_success\" align=\"center\" class=\"mT20 f16 c_orange\">" +
			"<img src=\""+ctx+"/static/img/ok.gif\" align=\"absmiddle\"/>" +
			"<span id=\"_msg\">&nbsp;&nbsp;" +msg+
			"</span></p>";
			
	var fail="<p id=\"_fail\" class=\"mT10 c_orange\">&nbsp;&nbsp;&nbsp;&nbsp;" +
			"<img src=\""+ctx+"/static/img/error.gif\" align=\"absmiddle\"/> " +
					"<span id=\"_msg\">&nbsp;&nbsp;"+msg+"</span></p>";
	//定位状态栏位置
	var obj= null;
	if(typeof(locObj)!="undefined"){
		obj = locObj;
	}else{
		obj = $(".m_ri");
	}
	if(obj.length==0){
		alert("指定状态栏位置不存在！");
		return false;
	}
	var statusBar = null;
	if(type=='fail'){
		
		var statusBar = $("#_fail",obj);
		if(statusBar.length==0){
			obj.append(fail);
			}
		else{
			statusBar.remove();
			obj.append(fail);
		}
		statusBar =  $("#_fail",obj);
		}
	else{
		var statusBar = $("#_success",obj);
		if(statusBar.length==0){
			obj.prepend(success);
			}
		else{
			statusBar.remove();
			obj.prepend(success);
		}
		statusBar =   $("#_success",obj);
		}
	window._statusBar =statusBar; 
	setTimeout(function(){
		_statusBar.slideUp(500);
		_statusBar = null;
	},1500);
	statusBar = null;
}

function getCookie(objName) {//获取指定名称的cookie的值
	var arrStr = document.cookie.split("; ");
	for ( var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if (temp[0] == objName)
			return unescape(temp[1]);
	}
}

//删除cookie 
function delCookie(name){
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

function FloatSub(arg1,arg2){   
	var r1,r2,m,n;   
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;};
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}; 
	m=Math.pow(10,Math.max(r1,r2));   
	 //动态控制精度长度   
	n=(r1>=r2)?r1:r2;   
	return ((arg1*m-arg2*m)/m).toFixed(n);   
}  

function FloatAdd(arg1,arg2){   
	var r1,r2,m;   
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} ;  
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} ;  
	m=Math.pow(10,Math.max(r1,r2));   
	return (arg1*m+arg2*m)/m;   
} 






/**
 * ajaxWait
 * 设置ajax提交时等待的特效
 * 
 * 依赖jquery1.7
 * 依赖jquery.blockUI.js
 * 页面需加上DIV
 * <div id="showLoading" style="display:none;border: 1px solid #999999;padding:15px 10px;color:#006699;font-size:14px; ">
	    <img src="${ctx}/static/loading.gif">&nbsp;&nbsp;<b>正在处理，请稍后..</b>
	  </div>
	使用方法：
		页面载入完成后，调用ajaxWait()，设置特效。
		特效开关为$.ajax([options])中的options多加一个参数wait:true，如
		$.ajax({url:'http://xxx',
		async:false,
		dataType:'json',
		success:function(){
			...
		},
		wait:true			//等待特效开关，不加的话不显示特效
		});
 */
function ajaxWait(){
	var d1,d2;		//一次调用耗时
	var time = 400;		//默认最短展示400毫秒，太短会造成一闪而过，闪屏。
	$.ajaxSetup({
	    beforeSend:function(data){
	    	if(this.wait){
	    		d1 = new Date();
	    		$.blockUI({ message: $('#showLoading') });
	    	}
			
		},
		complete:function(data){
			if(this.wait){
				d2 = new Date();
				if(d2.getTime()-d1.getTime()<time){
					setTimeout("$.unblockUI();",time);
					}else{
						$.unblockUI();
						}
				
				
				}
			}
		});

}

/**
 * 表单提交时，显示等待特效
 * 依赖jquery1.7
 * 依赖jquery.blockUI.js
 * 页面需加上DIV
 * <div id="showLoading" style="display:none;border: 1px solid #999999;padding:15px 10px;color:#006699;font-size:14px; ">
	    <img src="${ctx}/static/loading.gif">&nbsp;&nbsp;<b>正在处理，请稍后..</b>
	  </div>
 * 使用方法：加到form的onsubmit里如 onsubmit="fmsubmit();"
 */
function fmsubmit(form){
	var fm = null;
	var theEvent = window.event || arguments.callee.caller.arguments[0];
//	if($.browser.msie){
		if (typeof(theEvent.target) == "undefined") { 
			fm = theEvent.srcElement; 
		} else {
			fm = theEvent.target;
		}
		if(form !=null){
			fm = form;
		}
		//theEvent.srcElement;
//	}else{
//		fm = theEvent.target;
//	}
	if(fm.tagName!="FORM"){
		fm = $(fm).parents().find("form:first")[0];
		}
	if(typeof($(fm).valid)=="function"){
		if($(fm).valid()){
			$.blockUI({ message: $('#showLoading') });
			}
	}
}

/**
 * 验证仓库是否为登录找好所能管理的仓库
 * @param id
 * @returns {Boolean}
 */
function check(id){
	var str = false;
	if(id){
		$.ajax({
			url:contextPath+"/ajax/base/check?id="+id,
			dataType:"json",
			async : false,
			success:function(date){
				if(date == 1){
					str = true;
				} else {
					str = false;
				}
			},
			error:function(){
				str = false;
			}
		});
	}
	return str;
}

/**
 * 价格相加
 * @param numf
 * @param numt
 * @returns {String}
 */
function addprice(numf, numt){
	var str = 'error';
	if(id){
		$.ajax({
			url:contextPath+"/ajax/base/add",
			data:{"numF":numf,"numT":numt},
			dataType:"json",
			async : false,
			success:function(date){
				str = date;
			},
			error:function(){
				str = 'error';
			}
		});
	}
	return str;
}

/**
 * 价格相减
 * @param numf
 * @param numt
 * @returns {String}
 */
function subprice(numf, numt){
	var str = 'error';
	if(id){
		$.ajax({
			url:contextPath+"/ajax/base/sub",
			data:{"numF":numf,"numT":numt},
			dataType:"json",
			async : false,
			success:function(date){
				str = date;
			},
			error:function(){
				str = 'error';
			}
		});
	}
	return str;
}

/**
 * 价格相除
 * @param numf
 * @param numt
 * @returns {String}
 */
function divprice(numf, numt){
	var str = 'error';
	if(id){
		$.ajax({
			url:contextPath+"/ajax/base/div",
			data:{"numF":numf,"numT":numt},
			dataType:"json",
			async : false,
			success:function(date){
				str = date;
			},
			error:function(){
				str = 'error';
			}
		});
	}
	return str;
}

/**
 * 价格相乘
 * @param numf
 * @param numt
 * @returns {String}
 */
function mulprice(numf, numt){
	var str = 'error';
	if(id){
		$.ajax({
			url:contextPath+"/ajax/base/mul",
			data:{"numF":numf,"numT":numt},
			dataType:"json",
			async : false,
			success:function(date){
				str = date;
			},
			error:function(){
				str = 'error';
			}
		});
	}
	return str;
}



/**
 * 级联下拉列表selectCtrl对象定义
 */
var selectCtrl = new Object();

/**		下拉级联数据对象	**/
selectCtrl.obj = {};

/**		页面下拉列表控件ID数组	**/
selectCtrl.arr = [];

/**		初始化函数	**/
selectCtrl.init = function(obj,arr){
	this.obj = obj;
	this.arr = arr;
	this.manualSelect();
	this.categoryInit();
}
selectCtrl.show = function(){
	alert(this.s);
}

/**		设置第一个下拉列表	**/
selectCtrl.manualSelect = function(){
	//初始化第一个下拉列表
	var sCtrl = $("#"+[this.arr][0])[0];
	var options = sCtrl.options;
	if (options == undefined) return;
	options.length=0;
	
	var first =  this.obj["id"];
	for(var i=0;i<first.length;i++){
		var id = first[i];
		var text = this.obj.name[id];
		options.add(new Option(text,id));
	}
	
	//设定初始值
	var val = $("#"+[this.arr][0]).attr("val");
	if(val!=""&&typeof(val)!="undefined"){
		$("#"+[this.arr][0]).val(val);
	}
	
	
	
	
	
	//设置选择事件
	for(var i=0;i<this.arr.length;i++){
		
		$("#"+this.arr[i]).bind("change",function(){
				var id = this.value;
				var sCtrl = selectCtrl.nextCtrl(this.id);
				if(typeof(sCtrl)=="object"){
					selectCtrl.select(id,sCtrl);
				}
				
		});
		
	}
	
	
};


//若第一个下拉列表有值，级联初始化其它下拉列表

selectCtrl.categoryInit = function(){
	for(var i=0;i<this.arr.length;i++){
		var id = this.arr[i];
		var val = $("#"+id).val();
		if(typeof(val)!="undefined" && val!=""){
			if(i+1>=this.arr.length) return;
			var ctrl = $("#"+this.arr[i+1]);
			if(ctrl.length<1) continue;
			this.select(val,ctrl[0] );
		}
		
	}
	
};


//取下个下拉选框
selectCtrl.nextCtrl = function(id){
	for(var i=0;i<this.arr.length;i++){
		if(this.arr[i]==id){
			if(i+1<this.arr.length){
				return $("#"+this.arr[i+1])[0];
			}
		}
		
	}
};


selectCtrl.select = function(val,sCtrl){
	var options = sCtrl.options;
	if (options == undefined) return;
	options.length=0;
	var selectVal = this.obj[val].id;
	var additional = this.obj[val].aid;
	
	//附加节点信息
	for(var i=0;i<additional.length;i++){
		var id = additional[i];
		var text = this.obj[val].additional[id];
		options.add(new Option(text,id));
	}
	
	//下级节点信息
	for(var i=0;i<selectVal.length;i++){
		var id = selectVal[i];
		var text = this.obj[val].name[id];
		options.add(new Option(text,id));
	}
	
	//设定选中
	var v = $(sCtrl).attr("val");
	if(v!=""&&typeof(v)!="undefined"){
		$(sCtrl).val(v);
	}
	
	
};



/**
 * ui短名称设置
 */
var ui = {};
ui.select = function(selectObj,selects){
	selectCtrl.init(selectObj,selects);
}

/**
 * 级联下拉列表selectCtrl对象定义结束
 */

function changeTableColor(){
	$("#listTable >tbody >tr:even").css({background:"#ffffff"});
	$("#listTable >tbody >tr:odd").css({background:"#fafafa"});
	$("#listTable >tbody >tr").mousemove(function(){
		$(this).css({background:"#fffae1"});
	});
	
	$("#listTable >tbody >tr").mouseout(function(){
		$("#listTable >tbody >tr:even").css({background:"#ffffff"});
		$("#listTable >tbody >tr:odd").css({background:"#fafafa"});
	});
}