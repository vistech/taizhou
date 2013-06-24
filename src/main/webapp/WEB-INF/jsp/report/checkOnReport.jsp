<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html />
<html>
<head>
	<title>Hello Report</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"></head>
	<style type="text/css">
		body{
			font-family:Tahoma, Verdana, 宋体;
		}
		.table-bordered{
			border:1px solid #A5ACB5;
			border-collapse:separate;
			border-left:0;
			width:100%;
			background-color:transparent;
			border-spacing:0;
			font-size:12px;
		}
		.table-bordered th, .table-bordered td{
			border-left:1px solid #A5ACB5;
			line-height:20px;
			padding:5px;
			font-size:12px;
		}
		.table-bordered td{
			border-top:1px solid #A5ACB5;
		}
		.table-bordered th{
			font-weight:bold;
		}
	</style>
<body>
			<h2 style="font-size: 14px;">一、出勤报告</h2>
		<table class="table-bordered">
					<tr>
						<th width="33.3%">
							\
						</th>
						<th width="33.3%">
							满勤
						</th>
						<th width="33.3%">
							满勤率
						</th>
					</tr>
					<c:forEach items="${crs}" var="f">
						<tr>
							<td align="center">${f.category}</td>
							<td align="center">${f.fullAttendance}</td>
							<td align="center">${f.fullAttendanceRate}</td>
						</tr>
					</c:forEach>	
			</table>
    <h2 style="font-size: 14px;">二、月度巡查满勤率排行榜</h2>
    		<table class="table-bordered" >
					<tr>
						<th width="33.3%">
							\
						</th>
						<th width="33.3%">
							满勤
						</th>
						<th width="33.3%">
							满勤率
						</th>
					</tr>
					<c:forEach items="${cqs}" var="f">
						<tr>
							<td align="center">${f.city}</td>
							<td align="center">${f.manqin}</td>
							<td align="center">${f.manqinlv}</td>
						</tr>
					</c:forEach>	
			</table>
</body>
</html>