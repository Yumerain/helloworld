///**
// * 
// */
//package jetty;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author rainleaf
// *
// */
//public class HelloServlet extends HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
//		if(req.getParameter("jsp") != null) {
//			req.setAttribute("msg", "汤姆AND杰瑞");
//			req.getRequestDispatcher("/index.jsp").forward(req, response);
//		}else {
//			response.setContentType("text/html; charset=utf-8");
//			response.setStatus(HttpServletResponse.SC_OK);
//			response.getWriter().println("<h1>Hello World，这是由Servlet输出的内容。</h1>");
//		}
//	}
//	
//	
//}
