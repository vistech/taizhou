/*切换上传文件控件*/
function uploadFileToggle(_this,num){
   var checked=_this.children(":selected").val();
   var currentFile=_this.parent().next(); 
   //
   currentFile.find("#file_upload"+num).uploadify({
	'buttonText' : '上传文件',
		   'swf' : 'resources/scripts/uploadify.swf',
      'uploader' : 'jsp/managerMeeting/upload',
	  'onUploadError' : function(file, errorCode, errorMsg, errorString) {
            console.log('The file ' + file.name + ' could not be uploaded: ' + errorString);
        },
	  'onUploadSuccess' : function(file, data, response) {
            var fileUrl=data;
			currentFile.find("input.Media").val(fileUrl);
			$("#addTimeline").attr("disabled",false);
			$(".errorTips").slideUp();
        }
    });
	
   $("#file_upload"+num).add("#file_upload"+num+"-queue").css("margin" ,"5px 0px 0px 100px");
   
   if(checked=="图片"){
	  currentFile.find("#file_upload"+num).show();
	  currentFile.find("input.Media").val("");
   }else{
	  currentFile.find("#file_upload"+num).hide();
   } 
}

/*删除会议*/
function del(_this,id){
	var currentId=id;
	var dialog=art.dialog({
       title : '删除会议',
       content : '请确实是否删除此会议记录？',
	   lock : true,
	   okValue: '确定',
	   ok: function () {
          $.ajax({
               type : 'get',
	          async : "false",
	       dataType : "json",
                url : "jsp/managerMeeting/delete?mtid="+currentId,
	        success : function(data,textstatus,jqXHR){
		        var result=data.success;
				if(result=="true"){
				   _this.parents("tr").remove();
				   $(".errorTips").text("删除成功！");
				   $(".errorTips").slideUp();  
				}else{
				   $(".errorTips").text("删除失败！");
				   $(".errorTips").slideDown();
				}
	       }
          });	 
		  dialog.close(); 
          return false;
       },
	   cancelValue: '取消',
	   cancel:function(){
		  dialog.close();
          return false;
		}
    });
}

/*编辑会议*/
function edit(_this,id){
	//editDialog
	var currentId=id;
	if(id=="" || id==null || id==undefined) return;
	
	$("#rightFrame").load("edit.html",function(){
	    $.get('jsp/managerMeeting/modify?mtid='+id, editMeetingCallback);
	});
	

}
/*成功回调函数*/
function editMeetingCallback(data){
	//console.log(data);
	
	//会议基本信息
	var info=data.msb,meetingInfo=info.meetingInfo,timelineList=info.timelineList;
	$("#themeInfo").val(meetingInfo.theme);$("#timeInfo").val(meetingInfo.meetingTime);$("#subInfo").val(meetingInfo.title);
	$("#addressInfo").val(meetingInfo.meetingAddr);$("#timeLenInfo").val(meetingInfo.timeLen);$("#extractInfo").val(meetingInfo.extract);
    $("#info").data("id",meetingInfo.id);
	var person=meetingInfo.joinPersonList;
	$.each(person,function(key,val){
	   var checkbox=$("<span><input type='checkbox' name='' value='' class='checkbox' id='"+val.id+"'/>"+val.name+"</span>");
	   if(val.isHave==1){
		  checkbox.find("input").attr("checked",true);
		  checkbox.find("input").val("1");
	   }else{
	      checkbox.find("input").attr("checked",false);
		  checkbox.find("input").val("0");
	   }
	   checkbox.appendTo($("#personInfo"));
	   checkbox.find("input[type='checkbox']").click(function(){
	       if($(this).val()=="0"){
		       $(this).val("1");
		   }else{
		       $(this).val("0");
		   }
	   });
	});
		
	if(timelineList.length==0){
	  $( "#accordion" ).accordion();
	  return;
	}
	//时间轴 timelineList
	$.each(timelineList,function(key,val){
	   var num=key+1;
	   var type=val.materialType,len=timelineList.length,dateType=val.dateType;
        //keyFrame模板
	   var timeline=$("<h3><a href='#'>关键帧"+num+"</a></h3><div><ul class='timePoint'><li><label><b class='required'>*</b>标题：</label><input type='text' name='' value='"+val.theme+"' class='text headline'/><span class='note'>时间点标题</span></li><li><label><b class='required'>*</b>日期类型：</label><select class='dateType'><option value='date'>日期</option><option value='time'>时间</option></select></li><li><label><b class='required'>*</b>日期/时间：</label><input type='text' name='' class='text startDate datepicker'/><input type='text' class='text timepicker' id='timepicker"+num+"' style='display:none;'/><span class='note'>时间点开始时间，格式为“年,月,日” 如：2012,1,23</span></li><li><label><b class='required'>*</b>媒体类型：</label><select class='mediaType' name='"+type+"'><option name='text'>大段文本</option><option name='url'>网址</option><option name='picture'>图片</option><option name='map'>google map</option></select><span class='note'>主题放置内容，格式为文本，网页链接等</span></li><li><label></label><input type='text' name='' value='' class='text Media'/><input id='file_upload"+num+"' name='file_upload"+num+"' type='file' multiple='true' style='display:none;'></li><li><label>链接：</label><input type='text' name='' value='"+val.credit+"' class='text credit'/><span class='note'>媒体引用链接地址</span></li><li><label>说明：</label><input type='text' name='' value='"+val.caption+"' class='text caption'/><span class='note'>媒体链接文本说明</span></li><li><label>文本：</label><textarea class='subtitle'></textarea><span class='note'>时间点文本</span></li></ul><input type='button' name='' value='删除' class='button right' id='delKey"+num+"'></div>");
       timeline.appendTo($("#accordion"));
	   timeline.find(".subtitle").val(val.text);
	   timeline.find(".timePoint").data({"cid":val.cid,"tid":val.tid});
	   //时间类型
	   if(dateType=="date"){
	     timeline.find(".startDate").val(val.startDate).show();
		 timeline.find(".timepicker").hide();
	   }else if(dateType=="time"){
	     timeline.find(".timepicker").val(val.startDate).show();
		 timeline.find(".startDate").hide();
	   }

	   timeline.find(".dateType option[value='"+dateType+"']").attr("selected",true);
	   timeline.find(".mediaType option[name='"+type+"']").attr("selected",true);
	   
	   //媒体类型
	   if(type!="picture"){
		  //折叠菜单和日历
		  timeline.find("input.Media").val(val.material);
		  $(".datepicker").datepicker({ dateFormat: "yy,mm,dd" });
	   }else if(type=="picture"){
		  //上传文件控件
		  var fileUpload=timeline.find("#file_upload"+num);
		  fileUpload.uploadify({
		     'buttonText' : '上传文件',
		            'swf' : 'resources/scripts/uploadify.swf',
               'uploader' : 'jsp/managerMeeting/upload',
		  'onUploadError' : function(file, errorCode, errorMsg, errorString) {
               console.log('The file ' + file.name + ' could not be uploaded: ' + errorString);
            },
	    'onUploadSuccess' : function(file, data, response) {
                  var fileUrl=data;
			      timeline.find("input.Media").val(fileUrl);
			      $("#addTimeline").attr("disabled",false);
            }
          });
		  
		  timeline.find("#file_upload"+num).add("#file_upload"+num+"-queue").css("margin" ,"5px 0px 0px 100px");
		  if(val.material==null || val.material==""){
			  return; 
		  }
		  timeline.find("input.Media").val(val.material);
	      fileUpload.show();
	   }
	   //绑定切换上传控件
	   timeline.find(".mediaType").change(function(){uploadFileToggle($(this),num);});
       //
	   timeline.find(".dateType").change(function(){
		   timeline.find(".startDate").toggle();
		   timeline.find("#timepicker"+num).toggle();
	   });
	   timeline.find('#timepicker'+num).timepicker({'timeFormat': 'H:i','step': 05 });
	   //绑定删除关键帧
	   timeline.find("#delKey"+num).bind("click",function(){
		  var tid=val.tid;
		  $.post("jsp/managerMeeting/delTimeline",{tid : tid},function(json){
		     if(json.success=="true"){
	            $(".successTips").text("删除关键帧成功");
		        $(".successTips").slideDown();
		        var t=setTimeout(function(){
	              $(".successTips").slideUp();
		        },4000);
				timeline.remove();
	       }
		  });
	   });
      // timeline.appendTo($("#accordion"));
	});
	//折叠菜单和日历
	$( "#accordion" ).accordion();
	$(".datepicker").datepicker({ dateFormat: "yy,mm,dd" });
	//新增时间点
	$("#addTimeline").live("click",function(){  addTimeline();});
}
//添加时间点
function addTimeline(){
   var num=$("#accordion").find("a").length;
   var temp=$("<h3><a href='#'>关键帧"+num+"</a></h3><div><ul class='timePoint'><li><label><b class='required'>*</b>标题：</label><input type='text' name='' value='' class='text headline'/><span class='note'>时间点标题</span></li><li><label><b class='required'>*</b>日期类型：</label><select class='dateType'><option value='date'>日期</option><option value='time'>时间</option></select></li><li><label><b class='required'>*</b>日期/时间：</label><input type='text' name='' value='' class='text startDate datepicker'/><input type='text' class='text timepicker' id='timepicker"+num+"' style='display:none;'/><span class='note'>时间点开始时间，格式为“年,月,日” 如：2012,1,23</span></li><li><label><b class='required'>*</b>媒体类型：</label><select class='mediaType' name=''><option name='text'>大段文本</option><option name='url'>网址</option><option name='picture'>图片</option><option name='map'>google map</option></select><span class='note'>主题放置内容，格式为文本，网页链接等</span></li><li><label></label><input type='text' name='' value='' class='text Media'/><input id='file_upload"+num+"' name='file_upload"+num+"' type='file' multiple='true' style='display:none;'></li><li><label>链接：</label><input type='text' name='' value='' class='text credit'/><span class='note'>媒体引用链接地址</span></li><li><label>说明：</label><input type='text' name='' value='' class='text caption'/><span class='note'>媒体链接文本说明</span></li><li><label>文本：</label><textarea class='subtitle'></textarea><span class='note'>时间点文本</span></li></ul><input type='button' name='' value='删除' class='button right' id='delKey"+num+"'></div>");
   temp.appendTo($("#accordion"));
   //时间类型;
   temp.find(".dateType").change(function(){temp.find(".startDate").toggle();temp.find("#timepicker"+num).toggle();});
   temp.find('#timepicker'+num).timepicker({'timeFormat': 'H:i','step': 05 });
   //删除关键帧
   temp.find("#delKey"+num).bind("click",function(){ temp.remove();$("#accordion").accordion("option", "active", num-1);});
   //绑定上传控件
   temp.find(".mediaType").change(function(){uploadFileToggle($(this),num);});
   
   //销毁accordion
   $("#accordion").accordion("destroy");
   //create
   $("#accordion").accordion(); 
   //active
   $("#accordion").accordion("option", "active", num);
   $(".datepicker").datepicker({ dateFormat: "yy,mm,dd" });
   //上传修改数据
   //postEditData();
}

/*查看会议*/
function view(_this,id){
  var currentId=id;
   $.ajax({
     type: 'get',
	 async : "false",
	 dataType :"json",
     url : "jsp/showTimeLine?mtid="+id,
	 success : function(data,textstatus,jqXHR){
		  var fileName=data.json_name;
		  if(fileName==null || fileName=="") return;
		  //window.location.href="timeline.html?fileName="+fileName;
		  //新窗口中打开
	      window.open("timeline.html?fileName="+fileName,"newwindow","alwaysRaised=yes,z-look=yes");
	   }
   });	
}

/*第一次请求默认页面*/
function getListDefaultCallback(data,textstatus,jqXHR){
	$("#loading").remove();
	var totalPages=data.countPage;
	var currentPage=data.pageNo;
	var pagerText="第 1 页 共"+totalPages+"页";
	
	$(".currentPage").val(pagerText);
	$(".currentPage").data({"currentPage" : currentPage,"totalPages": totalPages});
	
	bindPagerEvent(totalPages,currentPage);
	
	createListModule(data);
}

/*创建会议列表*/
function createListModule(data){
	var list=data.meetingManager;
	var tbody=$("#list tbody").children();
	
	/*翻页清除表格*/
	if(tbody.length!=0){
	   tbody.remove();
	}
	//创建表格列表
	$.each(list,function(key,val){
		 var tr=$("<tr><td>"+val.id+"</td><td>"+val.theme+"</td><td>"+val.meetingTime+"</td><td>"+val.meetingAddr+"</td><td>"+val.meetingPerson+"</td><td><span class='view'>查看</span><span class='edit'>编辑</span><span class='del'>删除</span></td></tr>");
		 tr.appendTo($("#list tbody"));
		 tr.find("td:nth-child(6)").data("id",val.id);
		 /*删除会议*/
         tr.find(".del").bind("click",function(){  del($(this),val.id);});
	     /*编辑会议*/
	     tr.find(".edit").bind("click",function(){ edit($(this),val.id);});
	     /*查看会议*/
	     tr.find(".view").bind("click",function(){ view($(this),val.id);});
	});
}
/*加载列表*/
function loadList(pageNo){
    $.ajax({
      type: 'get',
	  async : "false",
	  dataType :"json",
      url : "jsp/managerMeeting?pid=1&pageNo="+pageNo,
	  success : getListDefaultCallback,
	  error : function(jqXHR, textStatus, errorThrown){
		 //console.log(textStatus);
	  }
   });	
}
/*请求页面操作*/
function getPagesInfo(current,totalPages){
	var pagerText="第 "+current+" 页 共"+totalPages+"页";
	$(".currentPage").val(pagerText);
	$(".currentPage").data({"currentPage" : current,"totalPages": totalPages});
	ajaxPager(current);
}
/*请求当前页相关数据*/
function ajaxPager(current){
	  var isNumber=$.isNumeric(current);
	  
	  if(isNumber){
	    loadList(current);
	  }
	  
}
/*绑定分页事件*/
function bindPagerEvent(totalPages,currentPage){
	/*首页*/
	$(".first").bind("click",function(){
		var first=1;
		getPagesInfo(first,totalPages);
	});
	/*上一页*/
	$(".prev").bind("click",function(){
		var current=$(".currentPage").data("currentPage");
		var prev=current-1;
		if(current==1){
		   return;	
		}
		getPagesInfo(prev,totalPages);
	});
	/*下一页*/
	$(".next").bind("click",function(){
		var current=$(".currentPage").data("currentPage");
		var next=current+1;
		if(current==totalPages){
			return;
		}
		getPagesInfo(next,totalPages);
	});
	/*最后一页*/
	$(".last").bind("click",function(){
		getPagesInfo(totalPages,totalPages);
	});
	
}

function init(){
	/*加载会议列表列表*/
	loadList(1);
	$("#ui-datepicker-div").hide();
}
$(document).ready(function(){
    init();
});