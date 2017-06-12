function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
//返回
function goBack(){
	
	window.history.go(-1);
}
//获取滚动条当前的位置 
function getScrollTop() { 
var scrollTop = 0; 
if (document.documentElement && document.documentElement.scrollTop) { 
scrollTop = document.documentElement.scrollTop; 
} 
else if (document.body) { 
scrollTop = document.body.scrollTop; 
} 
return scrollTop; 
} 

//获取当前可是范围的高度 
function getClientHeight() { 
var clientHeight = 0; 
if (document.body.clientHeight && document.documentElement.clientHeight) { 
clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight); 
} 
else { 
clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight); 
} 
return clientHeight; 
} 

//获取文档完整的高度 
function getScrollHeight() { 
return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight); 
} 
//验证邮箱
function
checkEmail(str){
   var
re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
   if(re.test(str)){
       return true;
   }else{
      return false;
   }
}

//正则表达式替换隐藏手机号的某四位
function mobileReplace(mobile){
	var m = mobile.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
	return m;
}

//获取本地存储
function getStorage(key) {
	if(window.sessionStorage){
		var v = window.sessionStorage.getItem(key);
		return v;
	}else{
		return getCookie(key);
	}
}

//移除本地存储
function cleanStorage(key){
	if(window.sessionStorage){
		window.sessionStorage.removeItem(key);
	}else{
		removeCookie(key);
	}
}

//本地存储
function setStorage(key, value){
	if(window.sessionStorage){
		window.sessionStorage.setItem(key,value);
	}else{
		setCookie(key,value);
	}
}


//设置cookie
function setCookie(name, value){   
    /* iDay 表示过期时间   
    cookie中 = 号表示添加，不是赋值 */        
//    document.cookie=name+'='+value;
	$.cookie(name,value,{ path: '${ctx}'});
}

//移除cookie
function removeCookie(name){   
    /* -1 天后过期即删除 */   
    $.cookie(name, '', { expires: -1 });
}

//获取cookie
function getCookie(name){
    /* 获取浏览器所有cookie将其拆分成数组 */   
//    var arr=document.cookie.split('; ');  
//    for(var i=0;i<arr.length;i++)    {
//        /* 将cookie名称和值拆分进行判断 */       
//        var arr2=arr[i].split('=');               
//        if(arr2[0]==name){           
//            return unescape(arr2[1]);       
//        }   
//    }       
//    return null;
	return $.cookie(name);
}

function cleanStorageAll(){
	cleanStorage("location");
	cleanStorage("area");
	cleanStorage("address");
}