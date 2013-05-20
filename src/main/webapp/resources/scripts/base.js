/*切换上传文件控件*/
function uploadFileToggle(_this){
   var checked=_this.children(":selected").val();
   var currentFile=_this.parent().next(); 
   
   currentFile.find("#file_upload").uploadify({
	'buttonText' : '上传文件',
		   'swf' : 'resources/scripts/uploadify.swf',
      'uploader' : 'jsp/managerMeeting/upload',
	  'onUploadError' : function(file, errorCode, errorMsg, errorString) {
            console.log('The file ' + file.name + ' could not be uploaded: ' + errorString);
        },
	  'onUploadSuccess' : function(file, data, response) {
             var fileUrl=data;
			 $("#mediaDate").val(fileUrl);
			 $("#addTimeline").attr("disabled",false);
        }
    });
	
   if(checked=="图片"){
	  $("#file_upload").show();
	  $("#mediaDate").val("");
   }else{
	  $("#file_upload").hide();
   } 
}

/*切换Tabs*/
function TabsToggle(){
   $("#example-one").organicTabs();
   $("#example-two").organicTabs({"speed": 200});
}

/*添加时间轴*/
function addTimeline(){
   var headline=$("#titleDate").val();
   var startDate="";
   var text=$("#textDate").val();
   var credit=$("#linkDate").val();
   var caption=$("#noteDate").val();
   var timeline=$("#date").data("timeline");
   var materialType=$(".mediaType").find("option:selected").attr("name"),material=$("#mediaDate").val();
   var dateType=$("#dateType").find("option:selected").val();
   
   //媒体类型
   if(material==""){ 
	  materialType=="picture" ? $(".errorTips").text("请上传文件") : $(".errorTips").text("请填写媒体内容");
	  $(".errorTips").slideDown();
	  $("#addTimeline").attr("disabled",true);
	  return; 
   }
   //时间类型
   if(dateType=="date"){
      startDate=$("#timeDate").val()
   }else if(dateType=="time"){
      startDate=$("#timepicker").val();
   }
   
   
  /* if(headline=="" || startDate==""){
	  $(".errorTips").text("请填写必填项！");
	  $(".errorTips").slideDown();
	  var t=setTimeout(function(){
	     $(".errorTips").slideUp();
		  $("#addTimeline").attr("disabled",false);
	  },4000);
	  $("#addTimeline").attr("disabled",true);
	  return;
   }   */
   
   var keyframe={caption: caption,credit: credit,material: material,materialType: materialType,dateType:dateType,startDate: startDate,text: text,theme: headline}
   
   timeline.push(keyframe);
   $("#date").data("timeline",timeline);

   $(".successTips").text("添加关键帧成功");
   $(".successTips").slideDown();
   var t=setTimeout(function(){
	    $(".successTips").slideUp();
   },4000);
   
   //清空关键帧数据
   /*$("#titleDate").val(""),$("#timeDate").val(""),$("#mediaDate").val("");
   $("#linkDate").val(""),$("#noteDate").val(""),$("#textDate").val("");
   $(".mediaType").find("option:first").attr("selected",true),$("#mediaDate").val("");*/
}
/*必填项*/
function requiredItem(_this,required){
   var val=_this.val();
   var currentTips=_this.parent().prevAll(".errorTips");
   if(required=="isNull"){
      if(val==null || val=="" || val==undefined){
	    currentTips.text("请填写必填项(*)").slideDown(); 
      }
   }else if(required=="isNumber"){
      var isNum=$.isNumeric(val);
	  if(!isNum){
	    currentTips.text("请填写正确的数据格式").slideDown();
	  }
   }
}

/*创建会议流程*/
function postCreateData(){
   //基本信息
   var titleInfo=$("#titleInfo").val(),timeInfo=$("#timeInfo").val(),subInfo=$("#subInfo").val();
   var addressInfo=$("#addressInfo").val(),timeLenInfo=$("#timeLenInfo").val(),noteInfo=$("#noteInfo").val();
   //参会人员 flag是否存在参会人员
   var jsonPersonList=[],flag=false;
   $("#personInfo span").each(function(){
	  var id=$(this).find("input").attr("id");
	  var isHave=$(this).find("input").val();
	  if(isHave=="1"){
	    isHave=true;flag=true;
	  }else{
	    isHave=false;
	  }
	  var name=$(this).text();
	  var person={id:id,name:name,isHave:isHave};
	  jsonPersonList.push(person);
    });
	
   if(titleInfo=="" || timeInfo=="" || !flag){
      $(".errorTips").text("请填写基本资料必填项！");
	  $(".errorTips").slideDown();
	  var t=setTimeout(function(){
	     $(".errorTips").slideUp();
		 $("#createButton").attr("disabled",false);
	  },4000);
	  $("#createButton").attr("disabled",true);
	  return;
    }
	
   /*if(timeLenInfo=="" || timeLenInfo==null || timeLenInfo==undefined){
       
   }*/
   //时间轴
   var timeline=$("#date").data("timeline");
   //if(timeline.length==0)return;
   var dataObj={
              meetingInfo:{
                  theme:titleInfo,
                  meetingTime:timeInfo,
                  meetingAddr:addressInfo,
                  title:subInfo,
                  timeLen:timeLenInfo || 0,
                  joinPersonList:jsonPersonList,
                  extract:noteInfo
             },
            timelineList:timeline
      };

   $.post("jsp/managerMeeting/save",{meetingJson:JSON.stringify(dataObj),option:"save"},function(json){
			//console.log(json)
			var mtid=json.mtid;
			$.get("jsp/showTimeLine?mtid="+mtid,function(data){
			    var fileName=data.json_name;
		        if(fileName==null || fileName=="") return;
		        //window.location.href="timeline.html?fileName="+fileName;
				window.open("timeline.html?fileName="+fileName,"newwindow");
			},"json");
			
   });
   
}
/*参会人员列表*/
function loadPersonList(){
	
   $.get("jsp/managerMeeting/personInfo",function(data){
	  var _data=data;
	  var children=$("#personInfo").children();
      //默认为空
	  if(children.length!=0){
	       $("#personInfo").children().remove();
	  }
	  
      $.each(_data,function(key,val){
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
   },"json");
}
//匹配时间格式
function FormateCheck(str) { 
  var re = /^(?:[01]\d|2[0-3])(?::[0-5]\d)$/; 
  var result = re.test(str); 
  return result; 
}
/**/
function init(){
	//初始化时间轴
	var timeline=[];
	$("#date").data("timeline",timeline);
	//参会人员
	loadPersonList();
	
	//日历控件
	$(".datepicker").datepicker({ dateFormat: "yy,mm,dd" });
	$(".datepicker").datepicker( "option",$.datepicker.regional["zh-CN"] );
	//时间控件
    $('#timepicker').timepicker({'timeFormat': 'H:i','step': 05 });
	/*切换Tabs*/
    TabsToggle();
	//
	$("#dateType").change(function(){
	   $("#timeDate").toggle();
	   $("#timepicker").toggle();
	});

    /*切换上传文件控件*/
    $(".mediaType").change(function(){uploadFileToggle($(this));});
	
	//添加时间点
	$("#addTimeline").bind("click",function(){ addTimeline();});
	
	//必选项
	$(".headline").add("#timeLenInfo").add(".mediaType").bind({
		 focusin : function(){ var currentTips=$(this).parent().prevAll(".errorTips");currentTips.slideUp();},
		focusout : function(){ $(this).attr("id")!="timeLenInfo" ? requiredItem($(this),"isNull") : requiredItem($(this),"isNumber");}
	});
	//创建会议流程
	$("#createButton").bind("click",function(){ postCreateData();});
}
/*入口函数*/
$(document).ready(function(){
   init();
});