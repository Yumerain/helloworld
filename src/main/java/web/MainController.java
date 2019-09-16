package web;

import javax.servlet.http.Cookie;

import com.jfinal.core.Controller;

public class MainController extends Controller {

	public void index() {
		cookieInfo();
		renderText("index");
	}

	public void test() {
		cookieInfo();
		renderText("test");
	}

	private void cookieInfo() {
		Cookie cookie = getCookieObject("MERCH_SESSIONID");
		if(cookie == null) {
			System.out.println("重定向前往登录，并在登录成功时设置cookie");
			cookie = new Cookie("MERCH_SESSIONID", "123456789");
			cookie.setMaxAge(10);
			cookie.setComment("this is cookie!");
			cookie.setDomain("localhost");
			cookie.setPath("/test");
			setCookie(cookie);
		}else {
			System.out.println("name:" + cookie.getName());
			System.out.println("value:" + cookie.getValue());
			System.out.println("Comment:" + cookie.getComment());
			System.out.println("domain:" + cookie.getDomain());
			System.out.println("MaxAge:" + cookie.getMaxAge());
			System.out.println("Path:" + cookie.getPath());
			System.out.println("Version:" + cookie.getVersion());
			System.out.println("Secure:" + cookie.getSecure());
		}
	}

}
