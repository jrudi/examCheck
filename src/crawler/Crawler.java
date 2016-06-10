package crawler;

import java.net.*;
import java.io.*;

public class Crawler {

	public static void main(String[] args) throws Exception {
		URL oracle = new URL("http://192.168.1.4");
		URLConnection yc = oracle.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}
}
