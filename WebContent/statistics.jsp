<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拟合</title>
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/slicebox.css" />
<link rel="stylesheet" type="text/css" href="css/custom.css" />
<link href="css/kuang.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="d3.js"></script>
</head>
<body>
	<h1>
		Network Models <span>图的导入</span>
	</h1>
	<div id="main" class="pos">
		<div id="put" class="pos1">
			<form action="statisticsShow" method="post" enctype="multipart/form-data">
				please select the graph<input type="file" name="upfile" value="select file">
				<input type="submit" name="submit" value="submit">

			</form>
		</div>
	</div>
</body>
</html>