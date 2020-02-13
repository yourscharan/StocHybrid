package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropFileUtil {
	
	public static String getValueForKey(String key) throws IOException{
		
		FileInputStream fis = new FileInputStream("D:\\nagjt\\myprograms\\StocAccHybrid_Maven\\propertiesFile\\config.properties");
		Properties prop=new Properties();
		prop.load(fis);
		return prop.getProperty(key);
		
	}

}
