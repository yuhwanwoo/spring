package mongoServer;

import java.io.File;
import java.io.IOException;
import java.lang.management.PlatformManagedObject;
import java.nio.channels.ShutdownChannelGroupException;

import rcaller.RCaller;
import rcaller.RCode;

public class Rtest03 {

	public static void main(String[] args) {
		try {
			RCaller caller=new RCaller();
			caller.setRscriptExecutable("F:/Rdata/R-4.0.0/bin/x64/Rscript.exe");
			RCode code=new RCode();
			code.clear();
			
			File file;
			file=code.startPlot();
			System.out.println(file);
			
			code.addRCode("library(usethis)");
			code.addRCode("library(devtools)");
			code.addRCode("library(KoNLP)");
			code.addRCode("library(multilinguer)");
			code.addRCode("library(RColorBrewer)");
			code.addRCode("library(wordcloud)");
			
			
			code.addRCode("col<-brewer.pal(8,\"Dark2\")");
			code.addRCode("request<-read.csv(\"F://mongtcsv.csv\", header = FALSE, stringsAsFactors = FALSE,fileEncoding = \"utf-8\")");
			code.addRCode("a<-as.data.frame(table(request[1]))");
			
			code.addRCode("x=wordcloud(words = a$Var1, freq = a$Freq, min.freq = 2, scale = c(5.8, 0.3), col = col , random.order = FALSE)\r\n");
			//code.addRCode("plot(x)");
			code.endPlot();
			
			caller.setRCode(code);
			caller.runOnly();
			code.showPlot(file);
			
		
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
