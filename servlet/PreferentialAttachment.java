package servlet;

import java.io.IOException;

import bean.Network;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import bean.GenerateNetwork;

public class PreferentialAttachment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");// 如果少了这句，就会出现乱码。
		GenerateNetwork gn = new GenerateNetwork();
		request.setAttribute("attachement", gn);

		SmartUpload su = new SmartUpload();

		su.initialize(this.getServletConfig(), request, response);

		String realPath = this.getServletContext().getRealPath("/");
		// String path = realPath + "/images";
		System.out.println("realPath is "+realPath);

		su.setAllowedFilesList("txt,csv");
		try {
			su.upload();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			su.save(realPath);
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String nodeNumber = su.getRequest().getParameter("nodeNumber").trim();
		String jsonName = su.getRequest().getParameter("fileName").trim();
		String newNodeDegree=su.getRequest().getParameter("newNodeDegree").trim();
		gn.setNodeNumber(nodeNumber);
		gn.setNewNodeDegree(newNodeDegree);
		gn.setFileName(jsonName);
		if (nodeNumber.isEmpty()) {
			nodeNumber = "0";
		}
		if(newNodeDegree.isEmpty()){
			newNodeDegree="0";
		}
		com.jspsmart.upload.File file=su.getFiles().getFile(0);
		String fileName=file.getFileName();
		System.out.println("fileName is "+fileName);
		
		Network moniattachement = gn.AttachementModel(realPath+fileName, 
				Integer.valueOf(nodeNumber), Integer.valueOf(newNodeDegree));
		gn.outputNetwork(moniattachement, jsonName);
		float moniattachementAverageDegree = 0;
		moniattachementAverageDegree = gn.averageDegree(moniattachement);
		System.out.println("moniattachement network average degree is "
				+ moniattachementAverageDegree);
		System.out.println("moniattachement network degree distribution:");
	    gn.degreeDistribution(moniattachement);
		float monismallWorldCC = 0;
		monismallWorldCC = gn.clusteringCoefficient(moniattachement);
		System.out.println("moniattachement network clustering coefficient is "
				+ monismallWorldCC);
		float averagePath = 0;
		System.out.println("moniattachement network average shortest path: ");
		averagePath = gn.averageShortestPath(moniattachement);

		gn.setAverageDegree(String.valueOf(moniattachementAverageDegree));
		gn.setClusteringCoefficient(String.valueOf(monismallWorldCC));
		gn.setAveragePath(String.valueOf(averagePath));
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/preferentialAttachmentShow.jsp");
		dispatcher.forward(request, response);

	}

}
