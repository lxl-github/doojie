//自动关闭提示框  
function Alert(str) { 
   // alert(document.getElementById("alertmsgDiv"));
    //document.body.removeChild(document.getElementById("alertmsgDiv"));
    if(document.getElementById("alertmsgDiv")!=null){
        return;
    }
    var msgw,msgh,bordercolor;     
    var sWidth,sHeight;  
    //获取当前窗口尺寸  
    sWidth = document.body.offsetWidth;  
    sHeight = document.body.offsetHeight;  
    //创建提示窗口的div  
    var msgObj = document.createElement("div")  
    msgObj.setAttribute("id","alertmsgDiv");  
    msgObj.setAttribute("align","center");  
    msgObj.style.background="#000"; 
    msgObj.style.opacity ="0.6";
    msgObj.style.filter="alpha(opacity = 60)";
    msgObj.style.border="1px solid #000";
    msgObj.style.color="#fff";
    msgObj.style.bgColor="rgba(0, 0, 0, .6)";
	msgObj.style.borderRadius="5px";
    msgObj.style.position = "absolute";  
    msgObj.style.left = "50%";  
    msgObj.style.font="1em Verdana, Geneva, Arial, Helvetica, sans-serif";  
    //窗口距离左侧和顶端的距离   
     msgObj.style.marginLeft = "-40%";  
    //窗口被卷去的高+（屏幕可用工作区高/2）-150  
   // alert(document.body.scrollTop+(window.screen.availHeight/2)-150);  window.pageYOffset   
  // alert(document.documentElement.clientHeight+","+document.clientHeight);
  // var clietHeight = document.documentElement.clientHeight || document.clientHeight;
    msgObj.style.top =(window.pageYOffset+document.documentElement.clientHeight/2)-50 +"px";  
    msgObj.style.width="80%";
    msgObj.style.textAlign = "center";  
    msgObj.style.zIndex = "90001";  
    document.body.appendChild(msgObj);  
    //提示信息  
    var txt = document.createElement("p");  
    txt.setAttribute("id","msgTxt");  
    txt.style.margin="10px 0";  
    txt.innerHTML = str;  
    document.getElementById("alertmsgDiv").appendChild(txt);  
    //设置关闭时间  
    window.setTimeout("closewin()",2000);   
}  
function closewin() {  
    document.body.removeChild(document.getElementById("alertmsgDiv"));  
}
function getHeight(){
    var yScroll;
    if(window.innerHeight&&window.scrollMaxY){
        yScroll=window.innerHeight+window.scrollMaxY;
    }else if(document.body.scrollHeight>document.body.offsetHeight){//allbutExplorerMac
        yScroll=document.body.scrollHeight;
    }else{
        //ExplorerMac...wouldalsoworkinExplorer6Strict,MozillaandSafari
        yScroll=document.body.offsetHeight;
    }
    var windowHeight;
    if(self.innerHeight){ //allexceptExplorer
       windowHeight=self.innerHeight;
    }else if(document.documentElement&&document.documentElement.clientHeight){//Explorer6StrictMode
        windowHeight=document.documentElement.clientHeight;
    }else if(document.body){//otherExplorers
        windowHeight=document.body.clientHeight;
    }
    //forsmallpageswithtotalheightlessthenheightoftheviewport
    if(yScroll<windowHeight){
        pageHeight=windowHeight;
    }else{
        pageHeight=yScroll;
    }
    return pageHeight;
}

//取窗口滚动条高度
function getScrollTop(){   
    var scrollTop=0;   
    if(document.documentElement&&document.documentElement.scrollTop){   
        scrollTop=document.documentElement.scrollTop;   
    }else if(document.body){   
        scrollTop=document.body.scrollTop;   
    }   
    return scrollTop;   
}