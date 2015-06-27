<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>randomGraph</title>
<link rel="shortcut icon" href="../favicon.ico"> 
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/slicebox.css" />
<link rel="stylesheet" type="text/css" href="css/custom.css" />
<link href="css/kuang.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="d3.js"></script>
</head>
<style type="text/css">
.node {
	stroke: #fff;
	stroke-width: 1.5px;
	cursor: pointer;
}

.nodetext {
	fill: #000;
	font-size: 12px;
	cursor: pointer;
	pointer-events: none;
}
</style>
<body>
<h1>Network Models <span>Random Graph Model</span></h1>
	<div id="main" class="pos">
		<div id="put" class="pos1">
			<form id="from1" action="randomGraphShow" method="post">
				<table id="table1" border="0">
					<tr>
						<td>number of node:</td>
						<td><input type="text" name="nodeNumber"></td>
					</tr>
					
					<tr>
						<td>probability:</td>
						<td><input type="text" name="probability"></td>
					</tr>
					<tr>
						<td>json file name:</td>
						<td><input type="text" name="fileName"></td>
					</tr>
				</table>
				<p align="center">
					<input type="submit" value="submit">
				</p>
				<hr>
				<table id="table2" border="0">
					<tr>
						<td>average degree:</td>
						<td></td>
					</tr>
					<tr>
						<td>clustering coefficient:</td>
						<td></td>
					</tr>
					<tr>
						<td>average path length:</td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>

		<div id="svg" class="pos3">
			
		</div>
	</div>

</body>
</html>