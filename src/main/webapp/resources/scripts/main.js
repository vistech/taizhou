/*匹配url参数*/
function url_params(name){
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg); 
  if (r!=null) return unescape(r[2]); return null;
}
/*注销登陆*/
function exit(){
   if($.cookie("username") && $.cookie("password")){
      $.removeCookie("username");
	  $.removeCookie("password");
   }
   window.location.href="login.html";
}
/**/
function init(){
	//登陆用户
	// var username=url_params("username");
	// if(username==null || username==""){
	//    window.location.href="login.html";
	// }
	// $("#username").text(username);
	
	//加载右边框架加载默认页面---创建流程页面management
	//$("#rightFrame").load("create.html");
	 $("#rightFrame").load("management.html");
	
	/*注销登陆
	$("#exit").bind("click",function(){
	   if($.cookie("username") && $.cookie("password")){
        $.removeCookie("username");
	    $.removeCookie("password");
      }
      window.location.href="login.html";
	});*/
	
	//加载右侧相应页面
	$("#create").add("#management").bind("click",function(){
		var page=$(this).attr("id");
		$("#rightFrame").load(page+".html");
	});
	
	//创建新页面
	$("#createButton").bind("click",function(){
	         $.ajax({
			      url : "jsp/showTimeLine",
			  success : function(json){
					   var fileName=json.json_name;
					   console.log(json.json_name);
					   if(fileName==null || fileName=="")return;
					   window.location.href="timeline.html?fileName="+fileName;
					 }
				});
	});
	//
	
}
/*入口函数*/
$(document).ready(function(){
   init();
});