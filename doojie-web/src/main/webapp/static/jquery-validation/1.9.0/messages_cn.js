/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: CN
 */
jQuery.extend(jQuery.validator.messages, {
	required : "必选字段",
	remote : "请修正该字段",
	email : "请输入正确格式的电子邮件",
	url : "请输入合法的网址",
	date : "请输入合法的日期",
	dateISO : "请输入合法的日期 (ISO).",
	number : "请输入合法的数字",
	digits : "只能输入整数",
	creditcard : "请输入合法的信用卡号",
	equalTo : "请再次输入相同的值",
	accept : "请输入拥有合法后缀名的字符串",
	maxlength : jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
	minlength : jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
	rangelength : jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
	range : jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max : jQuery.validator.format("请输入一个最大为 {0} 的值"),
	min : jQuery.validator.format("请输入一个最小为 {0} 的值")
});


// 特殊字符验证
jQuery.validator.addMethod("specialCharValidate", function(value, element) {
	var pattern = new RegExp(
			"[\\\\`~!@%#$^&*()=|{}':;',　\\[\\]<>/?_ \\.；：%……+￥（）【】\"\“。，、？]");
	return this.optional(element) || !pattern.test(value);
}, "不能包含特殊字符");

// 折扣验证
jQuery.validator.addMethod("isDiscount", function(value, element) {
	if (/^0\.[1-9]{1}\d?$|^0\.0[1-9]{1}$/.test(value)) {
		return this.optional(element) || true;
	}
	return this.optional(element) || false;

}, "请输入正确的折扣");

//新密码不能和旧密码相同
jQuery.validator.addMethod("same", function(value, element,param) {
		return this.optional(element) || $(param).val() != value;
}, "新密码不能与旧密码重复");


// 判断符合手机号码
jQuery.validator.addMethod("isMobel", function(value, element, params) {
	if (/^13\d{9}$/g.test(value) || (/^15[0-35-9]\d{8}$/g.test(value))
			|| (/^18[0-9]\d{8}$/g.test(value))) {
		return true || this.optional(element);
	} else {
		return false || this.optional(element);
	}
}, "输入正确的手机号码");

// 校验电话号码和传真号码
jQuery.validator.addMethod("isPhone", function(value, element, params) {
	if ((/^(0\d{2,3}-)(\d{7,8})(-(\d{3,}))?$/.test(value))) {
		return true || this.optional(element);
	} else {
		return false || this.optional(element);
	}
}, "号码格式不正确");

/**    
 * 自定义验证规则——对电话号码进行验证    
 */    
$.validator.addMethod("isTel",function(value, element)        
        {            
                // “/\(?0\d{2,3}[) -]?\d{7,8}/”匹配电话号码的格式多种：010-82839278、(010)82839278、01082839278等，但是，这样有一个问题    
                // 如：(01082839278这样的也会匹配。当然可以用分支条件"|"解决，比较麻烦。而且以什么开始或结束也没有匹配。    
                // 为了简单起见，去掉有"()"的形式。匹配区号3位，则本地号8位，区号4位，则本地号7位的号码。    4001231234
                var tel = /^0\d{2}[-]?\d{8}$|^0\d{3}[-]?\d{7}$|(((400)(\d{3})(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$|^13\d{9}$|^15[0-35-9]\d{8}$|^17[7]\d{8}$|^18[0-9]\d{8}$/;    
                return this.optional(element) || (tel.test(value));    
        }    
        , "联系电话格式不对"    
);

$.validator.addMethod("isTels",function(value,element){
	var telReg = /^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
	return this.optional(element) || (tel.test(value));
},"联系电话格式不对");

//下拉框验证 
jQuery.validator.addMethod("selectNone", function(value, element) { 
return value != "0" || this.optional(element); 
}, "请选择一项"); 

$.validator.addMethod("isNotOne",function(value,element){
	var reg= /^(1|0\.\d{1,2})$/;
	return this.optional(element) || (reg.test(value));
},"不能输入大于1或等于0的值");

