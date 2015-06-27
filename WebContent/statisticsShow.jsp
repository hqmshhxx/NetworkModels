<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<title>statistics</title>
<link rel="shortcut icon" href="../favicon.ico"> 
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/slicebox.css" />
<link rel="stylesheet" type="text/css" href="css/custom.css" />
<link href="css/kuang.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="d3.js"></script>
</head>
<style type="text/css">

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
 <h1>Network Models <span>图的出度统计</span></h1>
	<div id="main" class="pos">
		<div id="put" class="pos1">
			<form action="statisticsShow" method="post" enctype="multipart/form-data">
				please select the graph<input type="file" name="upfile"> 
				<input type="submit" name="submit" value="submit">			
			</form>			
		</div>

		<div id="svg" class="pos3">
			
			<script type="text/javascript">

			//Width and height
			var w = 400;
			var h = 400;
			var padding = 40;
			
			var dataset=[];
			var twonodes=[];

			d3.json("outDegree.json", function(error, ph){								
				dataset=ph.dataset;
				twonodes=ph.twonodes;

				//Create scale functions
				var xScale = d3.scale.linear()
									 .domain([0, d3.max(dataset, function(d) { return d[0]; })])
									 .range([padding, w-padding]);
				
				var yScale = d3.scale.linear()
									 .domain([0, d3.max(dataset, function(d) { return d[1]; })])
									 .range([h-padding, padding]);

				var rScale = d3.scale.linear()
									 .domain([0, d3.max(dataset, function(d) { return d[1]; })])
									 .range([2, 5]);
				

				//Define X axis
				var xAxis = d3.svg.axis()
								  .scale(xScale)
								  .orient("bottom")
								  .ticks(10);

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
				   		return rScale(1);
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
				.attr("transform", "translate(" + (w - padding)+"," + "0)");
			
			//Create Y axis
			svg.append("g")
				.attr("class", "axis")
				.attr("transform", "translate(" + padding + ",0)")
				.call(yAxis)
				.append("text")
				.text("count")
				.attr("transform", "translate(0,"+(padding-10)+")");	
			
			//Define line
			var lineFun = d3.svg.line()
			.x(function(d) { return xScale(d[0]); })
			.y(function(d) { return yScale(d[1]); })
			.interpolate("linear");
						
			var path = svg.append("path")
			.attr("d", lineFun(twonodes))
			.attr("stroke", "blue")
            .attr("stroke-width", 2)
            .attr("fill", "none");
			console.log(twonodes);
			
			});
			
		</script>
		</div>
	</div>

</body>
</html>