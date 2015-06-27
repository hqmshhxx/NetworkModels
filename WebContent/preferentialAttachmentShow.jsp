<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.GenerateNetwork"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<title>preferentialAttachment</title>
<link rel="shortcut icon" href="../favicon.ico"> 
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/slicebox.css" />
<link rel="stylesheet" type="text/css" href="css/custom.css" />
<link href="css/kuang.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="d3.js"></script>
<jsp:useBean id="attachement" type="bean.GenerateNetwork" scope="request"></jsp:useBean>
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

			
			.axis path,
			.axis line {
				fill: none;
				stroke: black;
				shape-rendering: crispEdges;
			}
			
			.axis text {
				font-family: sans-serif;
				font-size: 11px;
			}
</style>
<body>
 <h1>Network Models <span>Preferential Attachment Model</span></h1>
	<div id="main" class="pos">
		<div id="put" class="pos1">
			<form action="preferentialAttachmentShow" method="post" enctype="multipart/form-data">
				please select upload file<input type="file" name="upfile"
				> 
				<table id="table1" border="0">
					<tr>
						<td>add external number of node:</td>
						<td><input type="text" name="nodeNumber"
							value='<jsp:getProperty property="nodeNumber" name="attachement"></jsp:getProperty>'></td>
					</tr>
					<tr>
						<td> new node degree:</td>	
						<td><input type="text" name="newNodeDegree"
						value='<jsp:getProperty property="newNodeDegree" name="attachement"></jsp:getProperty>'></td>
					</tr>
					<tr>
						<td>json file name:</td>
						<td><input id="fileId" type="text" name="fileName"
							value='<jsp:getProperty property="fileName" name="attachement"></jsp:getProperty>'></td>
					</tr>
				</table><p>
				<p align="center">
					<input type="submit" value="submit">
				</p>
				<hr>
				<table id="table2" border="0">
					<tr>
						<td>average degree:</td>
						<td><jsp:getProperty property="averageDegree"
								name="attachement"></jsp:getProperty></td>
					</tr>
					<tr>
						<td>clustering coefficient:</td>
						<td><jsp:getProperty property="clusteringCoefficient"
								name="attachement"></jsp:getProperty></td>
					</tr>
					<tr>
						<td>average path length:</td>
						<td><jsp:getProperty property="averagePath" name="attachement"></jsp:getProperty></td>
					</tr>
				</table>
			</form>
			<hr>
			<p align="center"><strong>degree distribution</strong></p>
			
			<script type="text/javascript">

			//Width and height
			var w = 380;
			var h = 260;
			var padding = 40;
			
			var dataset=[];

			d3.json("1050.json", function(error, ph){								
				dataset=ph.dataset;

				//Create scale functions
				var xScale = d3.scale.linear()
									 .domain([0, d3.max(dataset, function(d) { return d[0]; })])
									 .range([padding, w - padding * 2]);
				
				var yScale = d3.scale.linear()
									 .domain([0, d3.max(dataset, function(d) { return d[1]; })])
									 .range([h - padding, padding]);

				var rScale = d3.scale.linear()
									 .domain([0, d3.max(dataset, function(d) { return d[1]; })])
									 .range([2, 5]);

				//Define X axis
				var xAxis = d3.svg.axis()
								  .scale(xScale)
								  .orient("bottom")
								  .ticks(5);

				//Define Y axis
				var yAxis = d3.svg.axis()
								  .scale(yScale)
								  .orient("left")
								  .ticks(10);

				//Create SVG element
				var svg = d3.select("#put")
							.append("svg")
							.attr("width", w)
							.attr("height", h);

				//Create circles
				svg.selectAll("circle")
				   .data(dataset)
				   .enter()
				   .append("circle")
				   .attr("cx", function(d) {
				   		return xScale(d[0]);
				   })
				   .attr("cy", function(d) {
				   		return yScale(d[1]);
				   })
				   .attr("r", function(d) {
				   		return rScale(d[1]);
				   })
				    .append("svg:title")
				   .text(function(d){
					   return d[0] + " , " + d[1];
					   })
					.attr("font-family", "sans-serif")
				    .attr("font-size", "11px")
				    .attr("fill", "red");
/*
				//Create labels
				svg.selectAll("text")
				   .data(dataset)
				   .enter()
				   .append("text")
				   .text(function(d) {
				   		return d[0] + "," + d[1];
				   })
				   .attr("x", function(d) {
				   		return xScale(d[0]);
				   })
				   .attr("y", function(d) {
				   		return yScale(d[1]);
				   })
				   .attr("font-family", "sans-serif")
				   .attr("font-size", "11px")
				   .attr("fill", "red");
*/				
				//Create X axis
				svg.append("g")
					.attr("class", "axis")
					.attr("transform", "translate(0," + (h - padding) + ")")
					.call(xAxis)
					.append("text")
					.text("degree")
					.attr("transform", "translate(" + (w - padding*2)+"," + "0)");
				
				//Create Y axis
				svg.append("g")
					.attr("class", "axis")
					.attr("transform", "translate(" + padding + ",0)")
					.call(yAxis)
					.append("text")
					.text("probability")
					.attr("transform", "translate(" + -(padding) + ","+(padding-10)+")");							
				});
			
		</script>
		</div>

		<div id="svg" class="pos3">
			<script type="text/javascript">
				var width = 600;
				var height = 500;
				//取得20个颜色的序列
				var color = d3.scale.category20();
				//定义画布
				var svg = d3.select("#svg").append("svg").attr("width", width)
						.attr("height", height);

				
				
				//定义力学结构
				var force = d3.layout.force().charge(-50).linkDistance(200)
						.size([ width, height ]);

				//读取数据

				//	var file=d3.select("#fileId").value+".json";
				var file = document.getElementById("fileId").value + ".json";

				d3.json(file, function(error, graph) {

					force

					.nodes(graph.nodes)

					.links(graph.links)

					.start();

					//定义连线

					var link = svg.selectAll(".link")

					.data(graph.links)

					.enter()

					.append("line")

					.attr("class", "link")

					.attr("stroke", "#09F")

					.attr("stroke-opacity", "0.4")

					.style("stroke-width", 1);

					//定义节点标记

					var node = svg.selectAll(".node")

					.data(graph.nodes)

					.enter()

					.append("g")

					.call(force.drag);

					//节点圆形标记

					node.append("circle")

					.attr("class", "node")

					.attr("r", function(d) {

						//	  return 10;//"r"表示半径

						return 5 + d.group % 20;
					})

					.style("fill", function(d) {
						return color(d.group % 20);
					})
					.on("mouseover",function(d){
												
						link.style("stroke-opacity",function(line){
							if(d===line.source||d===line.target){								
								node.style("fill-opacity",function(da){
									if(da===line.source||da===line.target){
										return 1.0;
									}															
								});							
							   return 1.0;
							}
							else{
								
								return 0.0 ;
							}
						});
					})
					.on("mouseout",function(d){
							node.style("fill-opacity",function(da){																	
								return 1.0;								
							});
							link.style("stroke-opacity",function(line){							
								return 1.0;								
							});
					});			

					//标记鼠标悬停的标签

					node.append("title")

					.text(function(d) {
						return "degree=" + d.group;
					});

					//节点上显示的姓名

					node.append("text")

					.attr("dy", ".3em")

					.attr("class", "nodetext")

					.style("text-anchor", "middle")

					.text(function(d) {
						return d.name;
					});

					//开始力学动作

					force.on("tick", function() {

						link.attr("x1", function(d) {
							return d.source.x;
						})

						.attr("y1", function(d) {
							return d.source.y;
						})

						.attr("x2", function(d) {
							return d.target.x;
						})

						.attr("y2", function(d) {
							return d.target.y;
						});

						node.attr("transform", function(d) {
							return "translate(" + d.x + "," + d.y + ")";
						});

					});

				});
			</script>
		</div>
	</div>

</body>
</html>