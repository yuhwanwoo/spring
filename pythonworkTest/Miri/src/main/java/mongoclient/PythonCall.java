package mongoclient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PythonCall {
	@Autowired 
	private ResourceLoader resourceLoader;
	@RequestMapping(value="/crawler.do",method=RequestMethod.GET)
	public String callMycrawler(){
		System.out.println("test");
		String url = "http://127.0.0.1:5000/test";
		 String sb="";  
		try {
			HttpURLConnection  conn = 
					(HttpURLConnection)new URL(url).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			conn.setRequestMethod("GET");
			
			BufferedReader br = 
					new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  

            String line = null;  

            while ((line = br.readLine()) != null) {  
             sb = sb+line + "\n";  
            }  
            System.out.println("========br======"+sb.toString());
            String path = resourceLoader.getResource("classpath:myflask").getURI().getPath();
            System.out.println("path:"+path);
			BufferedReader file = null;
            if(sb.toString().contains("ok")) {
            	System.out.println("test");
            	file = new BufferedReader(
            			new InputStreamReader(
            					new FileInputStream(path+"result_20180325.txt"),"UTF-8"));
            		
            	while(true) {
            		String fileLine = file.readLine();
            		if(fileLine==null) {
            			break;
            		}
            		String[] lines = fileLine.split(",");
            		System.out.println(lines[0]+"....."+lines[1]);
            	}
            }
            br.close();  

            System.out.println(""+sb.toString());  
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/admin/index.do";
	}
}
