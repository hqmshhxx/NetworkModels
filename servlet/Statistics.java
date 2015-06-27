package servlet;

import bean.Network;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import bean.GenerateNetwork;

public class Statistics extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");// 如果少了这句，就会出现乱码。
		GenerateNetwork gn=new GenerateNetwork();
		request.setAttribute("statistics", gn);
		
		
		SmartUpload su = new SmartUpload();
		su.initialize(this.getServletConfig(), request, response);
		String realPath = this.getServletContext().getRealPath("/");
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
		com.jspsmart.upload.File file=su.getFiles().getFile(0);
		String fileName=file.getFileName();
		System.out.println("fileName is "+fileName);
		
		Network network=gn.initNetwork(realPath+fileName);
		gn.outDegree(network);
		
		
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/statisticsShow.jsp");
		dispatcher.forward(request, response);

		
		
	}
	
	

	

}
