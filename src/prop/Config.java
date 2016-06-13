package prop;

import java.io.*;
import java.util.Properties;

public class Config {

	public static String loadProp(String property) {

		Properties prop = new Properties();
		try {

			String path = findPath();
			prop.load(new FileInputStream(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return prop.getProperty(property);
	}

	public static void writeProp(String key, String value) {
		try {

		Properties prop = new Properties();

		String path = findPath();
		prop.load(new FileInputStream(path));
		prop.setProperty(key, value);

		OutputStream out = new FileOutputStream(path);
		prop.store(out, key + " updated");
		out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static  String findPath(){
		File jarPath = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String propertiesPath = jarPath.getParentFile().getAbsolutePath();
		return propertiesPath + "/config.properties";
	}

}
