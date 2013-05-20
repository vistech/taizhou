function url_params(name){
      var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
      var r = window.location.search.substr(1).match(reg); 
      if (r!=null) return unescape(r[2]); return null;
}
/*初始函数*/
function init(){
    var fileName=url_params("fileName");
    if(fileName!=null || fileName!="" || fileName!=undefined){
    var timeline_config = {
                width : "100%",
               height : "100%", 
               source : "resources/timeline_json/"+fileName+".json",
                 lang : "zh-ch",
    start_zoom_adjust : "5",
                  css : "timeline/css/timeline.css",
                   js : "timeline/js/timeline.js"
         }
  }
}
/*入口函数*/
$(document).ready(function(){
    init();
});