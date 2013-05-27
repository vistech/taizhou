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
	<div id="employee_grid" class="mini-datagrid" style="width:100%;height:117px;" showPager="false" url="getCheckOnReportJson">
        <div property="columns">
        	<div field="category" width="40" headerAlign="center" align="center">\</div>        
            <div field="fullAttendance" width="50" headerAlign="center" align="center">满勤</div>
            <div field="fullAttendanceRate" width="50" headerAlign="center" align="center">满勤率</div>  
            <div field="bDate" width="50" headerAlign="center" align="center">起日期</div>  
            <div field="eDate" width="50" headerAlign="center" align="center">止日期</div>                               
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

	</script>
</body>
</html>