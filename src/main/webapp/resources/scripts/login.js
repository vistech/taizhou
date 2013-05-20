// zhailulu
function login(){
   var username=$("#username").val();
   var password=$("#password").val();
   //用户名和密码不能为空
   if(username=="" || password==""){
      $("#errorTips").slideDown().text("用户名或者密码不能为空");
	  $("#login").attr("disabled",true);
	  return;
   }
   
   ajax(username,password);

}
/*回车登录*/
function enterLogin(){
	 $(document).keydown(function(e){
		 var isFocusUser=$("#username").is(":focus");
		 var isFocusPwd=$("#password").is(":focus");
		 
		 if(isFocusUser || isFocusPwd) {
			 if(e.keyCode == 13){
				$('#login').trigger('click');
			 }
		 }
	 });
}
function ajax(username,password){
   $.ajax({
     type: 'POST',
	 async: "false",
	 dataType:"json",
     url: "jsp/login",
     data: {name:username,pass:password},
	 success : function(data,textstatus,jqXHR){
		 var isSuccess=data.success;
		 if(isSuccess){
			 //自动登陆
			 var keep=$("#remember").val();
			 if(keep=="1"){
                 $.cookie("username", username, { expires: 1 });
                 $.cookie("password", password, { expires: 1 });
             }
		     window.location.href="main.html?username="+username;
		 }else{
		     var bug=data.msg;
			 //显示后台报错信息
			 $("#errorTips").slideDown().text(bug);
			 $("#login").attr("disabled",true);
		 }
		 
	 },
	 error : function(jqXHR, textStatus, errorThrown){
		 console.log(textStatus);
	 }
   });	

}
function init(){
  //自动聚焦
  if($("#signin_wrapper")){
     $("#username").focus();
  }
  
  /*自动登陆cookie*/
  if($.cookie("username") && $.cookie("password")){
	  var username=$.cookie("username");
	  var password=$.cookie("password");
	  $("#username").val(username);
      $("#password").val(password);
	  ajax(username,password);
  }
	
  //选择是否自动登陆
  $("#remember").bind("click",function(){ 
     if($(this).val()=="1"){
	    $(this).attr("checked",false);
		$(this).val("0");
	 }else if($(this).val()=="0"){
	    $(this).attr("checked",true);
		$(this).val("1");
	 }
  });
  //登陆
  $("#login").bind("click",function(){login();});
  
  //隐藏错误提示框
  $("#username").add("#password").focus(function(){
     if($("#errorTips"))$("#errorTips").slideUp();
	 $("#login").attr("disabled",false);
  });
  //回车登陆
  enterLogin();
}

$(document).ready(function(){
     init();
});