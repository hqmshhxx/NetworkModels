package servlet;

import bean.Network;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.GenerateNetwork;

public class SmallWorld extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");// 如果少了这句，就会出现乱码。
		GenerateNetwork gn=new GenerateNetwork();
		request.setAttribute("smallWorld", gn);
		String nodeNumber= request.getParameter("nodeNumber").trim();
		String averageDegree= request.getParameter("averageDegree").trim();
		String probability=request.getParameter("probability").trim();
		String fileName=request.getParameter("fileName").trim();
		gn.setNodeNumber(nodeNumber);
		gn.setAverageDegree(averageDegree);
		gn.setFileName(fileName);
		gn.setProbability(probability);
		
		Network monismallWorld = gn.smallWorld(Integer.valueOf(nodeNumber), 
				Float.valueOf(probability), Integer.valueOf(averageDegree));
		gn.outputNetwork(monismallWorld, fileName);
		float monismallWorldAverageDegree = 0;
		monismallWorldAverageDegree = gn.averageDegree(monismallWorld);
		System.out.println("monismallworld network average degree is "
				+ monismallWorldAverageDegree);
		System.out.println("monismallworld network degree distribution:");
		gn.degreeDistribution(monismallWorld);
		float monismallWorldCC = 0;
		monismallWorldCC = gn.clusteringCoefficient(monismallWorld);
		System.out.println("monismallworld network clustering coefficient is "
		               + monismallWorldCC);
		float averagePath=0;
		System.out.println("monismallworld network average shortest path: ");		
		averagePath=gn.averageShortestPath(monismallWorld);
		
		
		
		
		gn.setNewAverageDegree(String.valueOf(monismallWorldAverageDegree));
		gn.setClusteringCoefficient(String.valueOf(monismallWorldCC));
		gn.setAveragePath(String.valueOf(averagePath));
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/smallWorldShow.jsp");
		dispatcher.forward(request, response);

		
		
	}
	
	

	

}
