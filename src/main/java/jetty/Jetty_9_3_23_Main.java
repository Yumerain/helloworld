//package jetty;
//
//import java.io.File;
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.tomcat.InstanceManager;
//import org.apache.tomcat.SimpleInstanceManager;
//import org.eclipse.jetty.server.Request;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.handler.AbstractHandler;
//import org.eclipse.jetty.server.handler.AllowSymLinkAliasChecker;
//import org.eclipse.jetty.webapp.WebAppContext;
//
//public class Jetty_9_3_23_Main {
//	
//	public static void main(String[] args) {
//		mainWebapp();
//	}
//
//	
//	public static void mainWebapp() {
//		Server server = new Server(8080);
//		WebAppContext webapp = new WebAppContext();
//		webapp.setContextPath("/");
//		
//		// 必须设置，否则报错
//		webapp.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());
//		
//		// 如果不设置，则每次在临时目录生成随机名称的文件夹
//		webapp.setAttribute("javax.servlet.context.tempdir", "/tmp/jetty_tempdir");
//		
//		//webapp.setDescriptor("src/main/webapp/WEB-INF/web.xml");
//		//webapp.setResourceBase("src/main/web/");
//		
//        File warFile = new File("./src/main/web/");
//        webapp.setWar(warFile.getAbsolutePath());
//        webapp.addAliasCheck(new AllowSymLinkAliasChecker());
//        server.setHandler(webapp);
//        try {
//        	server.start();
//            server.dumpStdErr();
//            server.join();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	
//	
//	
//	
//	
//	
//	public static void mainHTTP() {
//		
//		try {
//			Server server = new Server(8080);
//			server.setHandler(new AbstractHandler() {
//			    public void handle( String target,
//				            Request baseRequest,
//				            HttpServletRequest request,
//				            HttpServletResponse response ) throws IOException,
//				                                          ServletException
//				{
//					response.setContentType("text/html; charset=utf-8");
//					response.setStatus(HttpServletResponse.SC_OK);
//					response.getWriter().println("<h1>Hello World</h1>");
//					baseRequest.setHandled(true);
//				}
//			});
//			
//			server.start();
//			server.join();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//}
