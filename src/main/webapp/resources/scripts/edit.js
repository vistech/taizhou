

/*获取某个Id的会议数据*/
function postEditData(){
  var titleInfo=$("#themeInfo").val(),timeInfo=$("#timeInfo").val(),subInfo=$("#subInfo").val();
  var addressInfo=$("#addressInfo").val(),timeLenInfo=$("#timeLenInfo").val(),noteInfo=$("#extractInfo").val();
  var id=$("#info").data("id");

  //参会人员
  var jsonPersonList=[];
  $("#personInfo span").each(function(){
	  var id=$(this).find("input").attr("id");
	  var isHave=$(this).find("input").val()=="1" ? isHave=true : isHave=false;
	  var name=$(this).text();
	  var person={id:id,name:name,isHave:isHave};
	  jsonPersonList.push(person);
  });
  
  //时间轴
  var timelineList=[];
  if($(".timePoint")){
    $(".timePoint").each(function(key,val){
     var theme=$(this).find(".headline").val();
	 var startDate="";
	 var text=$(this).find(".subtitle").val();
	 var credit=$(this).find(".credit").val();
	 var caption=$(this).find(".caption").val()
	 var materialType=$(this).find(".mediaType option:selected").attr("name"),material=$(this).find(".Media").val();
	 var dateType=$(this).find(".dateType option:selected").val();
	 //时间类型:日期+时间
	 if(dateType=="date"){
	   startDate=$(this).find(".startDate").val();
	 }else if(dateType=="time"){
	   startDate=$(this).find("input[id^='timepicker']").val();
	 }
	 
	 //媒体类型
	 if(materialType=="picture" && $(this).find(".Media").val()==""){
		 $(".errorTips").text("请上传文件");
		 $(".errorTips").slideDown();
		 $("#addTimeline").attr("disabled",true);
		 return;
	 }
	 //console.log(text);
	 var cid=$(this).data("cid"),tid=$(this).data("tid"),timepoint;

	 if(cid==undefined && tid==undefined || cid=="" && tid==""){
	    timepoint={caption: caption,credit: credit,material: material,materialType: materialType,dateType:dateType,startDate: startDate,text: text,theme: theme};
	 }else{
	    timepoint={caption: caption,cId: cid,credit: credit, material: material,materialType: materialType,dateType:dateType,startDate: startDate,text: text,theme: theme,tId: tid};
	 }
	 timelineList.push(timepoint);
    });
  }
  var dataObj={  
       meetingInfo:{
                  theme:titleInfo,
                  meetingTime:timeInfo,
                  meetingAddr:addressInfo,
                  title:subInfo,
				  id : id,
                  timeLen:timeLenInfo,
                  joinPersonList:jsonPersonList,
                  extract:noteInfo
             },
            timelineList:timelineList
   
    };
	
  // console.log(dataObj);

   $.post("jsp/managerMeeting/save",{meetingJson:JSON.stringify(dataObj),option:"update"},function(json){
	  if(json.success=="true"){
	    $(".successTips").text("修改成功");
		$(".successTips").slideDown();
		var t=setTimeout(function(){
	      $(".successTips").slideUp();
		},4000);
	  }
   });
}

function init(){
  $("#return").bind("click",function(){ $("#rightFrame").load("management.html");});
  $("#edit").bind("click",function(){  postEditData();});
  $("#timeLenInfo").bind({
      focusin : function(){$(".errorTips").slideUp();},
	 focusout : function(){var isNumber=$.isNumeric($(this).val()); if(!isNumber)$(".errorTips").text("请填写正确的数据格式").slideDown();}
  });
}

$(document).ready(function(){
  init();
});