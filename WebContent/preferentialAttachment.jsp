<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>preferentialAttachment</title>
<link rel="shortcut icon" href="../favicon.ico"> 
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/slicebox.css" />
<link rel="stylesheet" type="text/css" href="css/custom.css" />
<link href="css/kuang.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="d3.js"></script>
</head>
<body>
 <h1>Network Models <span>Preferential Attachment Model</span></h1>
	<div id="main" class="pos">
		<div id="put" class="pos1">
			<form action="preferentialAttachmentShow" method="post" enctype="multipart/form-data">
				please select upload file<input type="file" name="upfile"> 
			
				<table id="table1" border="0">
					<tr>
						<td></td>
					</tr>

					<tr>
						<td>add external number of node:</td>
						<td><input type="text" name="nodeNumber"></td>
					</tr>
					<tr>
						<td> new node degree:</td>
						<td><input type="text" name="newNodeDegree"></td>
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

		<div id="svg" class="pos3"></div>
	</div>

</body>
</html>