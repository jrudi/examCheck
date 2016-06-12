package crawler;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import prop.Props;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class Crawler {

	private HtmlTable table;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private ArrayList<Exam> examList = new ArrayList<Exam>();

	public static void main(String[] args) throws Exception {
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		Crawler c = new Crawler();
		
		c.login();
		c.find();
		c.compare();
		
}
	/**
	 * Logs in to the Portal and finds the Table on the Notenspiegel page
	 * @throws Exception
	 */

	public void login() throws Exception {
		try (final WebClient webClient = new WebClient()) {

			HtmlPage cas = webClient.getPage(
					"https://cas.uni-mannheim.de/cas/login?service=https%3A%2F%2Fportal.uni-mannheim.de%2Fqisserver%2Frds%3Fstate%3Duser%26type%3D1");
			HtmlForm form = cas.getForms().get(0);
			HtmlSubmitInput button = form.getInputByName("submit");
			HtmlTextInput name = form.getInputByName("username");
			HtmlPasswordInput pw = form.getInputByName("password");

			name.setValueAttribute(Props.PORTAL_NAME);
			pw.setValueAttribute(Props.PORTAL_PW);

			HtmlPage portalStart = button.click();
			HtmlPage pruefungen = portalStart.getAnchors().get(14).click();
			HtmlPage notenspiegel = pruefungen.getAnchors().get(23).click();
			Iterator<HtmlElement> it = notenspiegel.getHtmlElementDescendants().iterator();

			while (it.hasNext()) {
				HtmlElement ele = it.next();
				if (ele.getAttribute("cellspacing").equals("0") && ele.getAttribute("border").equals("1")) {
					this.table = (HtmlTable) ele;
				}
			}
		}
	}
/**
 * finds all Exams and stores them as Exam Objects
 */
	public void find() {
		for (final HtmlTableRow row : table.getRows()) {
			if (table.getRows().indexOf(row) < 2)
				continue;

			Exam exam = new Exam();

			try {
				exam.setECTS(Integer.parseInt(row.getCell(8).asText()));
			} catch (NumberFormatException e) {
				exam.setECTS(-1);
			}

			try {
				exam.setDatum(sdf.parse(row.getCell(3).asText()));
			} catch (ParseException pe) {
				exam.setDatum(new Date());
			}

			try {
				double n = Double.parseDouble(row.getCell(7).asText().replace(",", "."));
				exam.setNote(n);
			} catch (NumberFormatException e) {
				exam.setNote(0.0);
			}

			exam.setName(row.getCell(4).asText());
			exam.setBestanden(!row.getCell(9).asText().contains("nicht"));
			examList.add(exam);
		}
	}

	/**
	 * compares different the old number of exams with the new number
	 */
	
	public void compare(){
		int old = this.load();
		int current = examList.size();
		if(old==-1){
			save(current);
			return;
		}
		if(current>old){
			int diff = current-old;
			System.out.println(diff + " neue Ergebnisse!");
			for(int i=0;i<diff;i++){
				String subject = "Klausurergebnis " + examList.get(i).getName();
				System.out.println("Sent: " + subject);
				mailer.Mailer.sendMail(examList.get(i).toString(), subject);
			}
			
			this.save(current);
		}else{
			System.out.println("Nothing new");
		}
		
	}
	
	/**
	 * loads the int saved in notenspiegel.txt
	 * @return
	 */
	private int load() {
		int a = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					new File(getClass().getClassLoader().getResource("resources/notenspiegel.txt").getFile())));
			a = Integer.parseInt(br.readLine());
			br.close();
		} catch (IOException | NumberFormatException e) {
			a = 0;
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * overwrites the int at notenspiegel.txt
	 * @param i
	 */
	public void save(int i) {
		File f = new File(getClass().getClassLoader().getResource("resources/notenspiegel.txt").getFile());
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(i + "");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
