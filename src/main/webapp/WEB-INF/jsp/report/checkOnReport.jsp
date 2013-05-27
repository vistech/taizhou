<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html />
<html>
<head>
	<title>Hello Report</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"></head>
	<script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
	<link href="../../resources/miniui/scripts/miniui/themes/gray/skin.css" rel="stylesheet" type="text/css" />
	<script src="../../resources/miniui/scripts/boot.js" type="text/javascript"></script>
<body>
	<div id="tabs1" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;">
		<div name="tab1" title="今日报告">
			<h2>一、出勤报告</h2>
	<div id="employee_grid" class="mini-datagrid" style="width:100%;height:117px;" showPager="false" url="getCheckOnReportJson?reportName=${reportName}">
        <div property="columns">
        	<div field="category" width="60" headerAlign="center" align="center">\</div>        
            <div field="fullAttendance" width="80" headerAlign="center" align="center">满勤</div>
            <div field="fullAttendanceRate" width="80" headerAlign="center" align="center">满勤率</div>                                
        </div>
    </div> 
    <h2>二、月度巡查满勤率排行榜</h2>
	<div id="chuQingSort_grid" class="mini-datagrid" style="width:100%;height:255px;" showPager="false" url="getChuQingSort_Reportjson">
        <div property="columns">
        	<div field="city" width="60" headerAlign="center" align="center">县市区</div>        
            <div field="manqin" width="80" headerAlign="center" align="center">满勤</div>
            <div field="manqinlv" width="80" headerAlign="center" align="center">满勤率</div>                               
        </div>
    </div> 

		</div>
		<div name="tab2" title="考勤规则">
			<ul>
				<li>汛期每日一次</li>
				<li>非汛期三日一次</li>
				<li>遇特殊天气，临时加报</li>
			</ul>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("employee_grid");
        grid.load();

        var chuQingSort_grid=mini.get("chuQingSort_grid");
        chuQingSort_grid.load();

	</script>
</body>
</html>