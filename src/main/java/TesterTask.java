import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;

public class TesterTask {

	public static void main(String[] args) {
		if(args.length != 10){
			System.out.println("参数用法如下：");
			StringBuilder sb = new StringBuilder();
			sb.append("-bat 'bat文件地址'");
			sb.append("-url '目标api地址'");
			sb.append("-chatId '目标chatId'");
			sb.append("-sFilePath '源文件保存路径'");
			sb.append("-tFilename '目标文件完整路径'");
			System.out.println(sb.toString());
			return;
		}
		Map<String,String> params = convertParam(args);
		
		//检查bat文件地址
		if(!params.containsKey("-bat")){
			System.out.println("缺少参数：bat文件地址(-bat)");
			return;
		}
		String batPath = params.get("-bat");
		//检查目标api地址
		if(!params.containsKey("-url")){
			System.out.println("缺少参数：目标api地址(-url)");
			return;
		}
		String apiUrl = params.get("-url");
		//检查目标chatId
		if(!params.containsKey("-chatId")){
			System.out.println("缺少参数：目标chatId(-chatId)");
			return;
		}
		String chatId = params.get("-chatId");
		//检查源文件保存路径
		if(!params.containsKey("-sFilePath")){
			System.out.println("缺少参数：源文件保存路径(-sFilePath)");
			return;
		}
		String sourcePath = params.get("-sFilePath");
		//检查目标文件保存路径
				if(!params.containsKey("-tFilename")){
					System.out.println("缺少参数：目标文件完整路径(-tFilename)");
					return;
				}
				String targetPath = params.get("-tFilename");
		
		if(!sourcePath.endsWith("/")){
			sourcePath = sourcePath + "/";
		}
		
		//执行bat文件并上传生成的结果文件
		try{
			callCmd(batPath);
			getTargetHtml(sourcePath,targetPath);
			upload(apiUrl,chatId,targetPath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Map<String,String> convertParam(String[] args){
		if(args.length%2 !=0){
			System.out.println("参数传递不完整");
		}
		Map<String,String> map = new HashMap<>();
		for(int i=0;i<args.length;i+=2){
			map.put(args[i], args[i+1]);
		}
		return map;
	}
	
	public static void callCmd(String batLocation) throws Exception{
		Process process = Runtime.getRuntime().exec(batLocation);
		// 启动一个线程监控newman的运行状况
        new Thread(()->{
        	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        	String line = null;
        	try {
				while((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }).start();            
        
		process.waitFor();

	}

    public static void upload(String apiUrl,String chatId,String targetPath) {
    	
        // 配置客户端
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig config = RequestConfig
                                .custom()
                                .setConnectTimeout(5000)
                                //.setSocketTimeout(20000)
                                .build();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(config);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        
        // 参数1
        multipartEntityBuilder.addTextBody("chat_id", chatId);
        // 参数2：添加要上传的文件
        multipartEntityBuilder.addBinaryBody("document", new File(targetPath));

        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        try {            
            CloseableHttpResponse httpResponse = client.execute(httpPost);
            System.out.println("响应状态码：" + httpResponse.getStatusLine().getStatusCode());
            String rspTxt = EntityUtils.toString(httpResponse.getEntity());
            System.out.println("API响应内容：\r\n" + rspTxt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File findNewFile(String sourcePath){
        File dir = new File(sourcePath);
        if(!dir.isDirectory()){
            System.out.println(sourcePath + "不是目录");
            return null;
        }
        String[] fns = dir.list();
        if(fns.length == 0){
            System.out.println(sourcePath + "目录为空");
            return null;
        }

        // 根据文件名排序，时间最大的会排在最后
        Arrays.sort(fns);
        String filename = fns[fns.length - 1];
        System.out.println("找到最新文件：" + filename);
        
        File file = new File(sourcePath + filename);
        if(file.isFile()){
            return file;
        }else{
            System.out.println(filename + "不是文件");
            return null;
        }
    }

    public static void getTargetHtml(String sourcePath,String targetPath){
    	Map<String, List<Map<String, String>>> expections = getExpectionSection(sourcePath);
    	List<Map<String, String>> listErrors = expections.get("listErrors");
    	List<Map<String, String>> listFailures = expections.get("listFailures");
    	
    	Engine engine = Engine.use();
		engine.setDevMode(true);
		engine.setToClassPathSourceFactory();
		
		Kv kv = Kv.by("key", 123);
		kv.set("listErrors", listErrors);
		kv.set("listFailures", listFailures);
		Template template = engine.getTemplate("E:/report_template/report_template.html");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		template.render(kv,baos);
		
		try{
			FileOutputStream fos = new FileOutputStream(targetPath);
			fos.write(baos.toByteArray());
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public static Map<String, List<Map<String, String>>> getExpectionSection(String sourcePath){
    	Map<String, List<Map<String, String>>> expections = new HashMap<>();

        // 要处理的文件
        File sourceFile = findNewFile(sourcePath);
        if(sourceFile == null){
            return null;
        }
        
		List<Map<String, String>> listErrors = new ArrayList<Map<String,String>>();
		List<Map<String, String>> listFailures = new ArrayList<Map<String, String>>();
		String title=null;
		String request=null;
		String description=null;
		try{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(sourceFile);
			Element root = doc.getRootElement();
			
			List<Element> testsuites = root.elements("testsuite");
			for(Element testsuite : testsuites){
				//当Status=500,显示error信息
				if(testsuite.attributeValue("errors").equals("1")){
					title = "JSONError: Unexpected token";
					request = testsuite.attributeValue("name");
					description = "Status code:500";
					Map<String, String> mapError = new HashMap<>();
					mapError.put("title", title);
					mapError.put("description", description);
					mapError.put("request", request);
					listErrors.add(mapError);
				}
				//当TestScript结果为false,显示failure信息
				List<Element> testcases = testsuite.elements("testcase");
				for(Element testcase : testcases){
					if(testcase.hasContent()){
						Element failure = testcase.element("failure");
						title = failure.attributeValue("type") + ":" + testcase.attributeValue("name");
						request = testsuite.attributeValue("name");
						description = failure.attributeValue("message");
						
						Map<String, String> mapFailure = new HashMap<String, String>();
						mapFailure.put("title", title);
						mapFailure.put("description", description);
						mapFailure.put("request", request);
						listFailures.add(mapFailure);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		expections.put("listErrors", listErrors);
		expections.put("listFailures", listFailures);
		return expections;
    }
}