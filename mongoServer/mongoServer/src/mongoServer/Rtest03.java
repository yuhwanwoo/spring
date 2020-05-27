package mongoServer;

import java.io.File;
import java.io.IOException;

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
			code.addRCode("library(RMongo)");
			code.addRCode("con <- mongoDb");
			code.addRCode("plot(x)");
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
