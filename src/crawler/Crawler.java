package crawler;

import java.text.*;
import java.util.*;
import java.util.logging.Level;
import prop.Config;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class Crawler {

	private HtmlTable table;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private ArrayList<Exam> examList = new ArrayList<Exam>();
	private WebClient webClient = new WebClient();

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
	 * 
	 * @throws Exception
	 */

	public void login() throws Exception {
		
			System.out.println("connecting...");
			HtmlPage cas = webClient.getPage(
					"https://cas.uni-mannheim.de/cas/login?service=https%3A%2F%2Fportal.uni-mannheim.de%2Fqisserver%2Frds%3Fstate%3Duser%26type%3D1");
			HtmlForm form = cas.getForms().get(0);
			
			form.getInputByName("username").setValueAttribute(Config.loadProp("PORTAL_NAME"));
			form.getInputByName("password").setValueAttribute(Config.loadProp("PORTAL_PW"));
			
			HtmlPage portalStart = form.getInputByName("submit").click();
			
			System.out.println("logging in...");
			HtmlPage notenspiegel = ((HtmlPage)portalStart.getAnchors().get(14).click()).getAnchors().get(23).click();
			Iterator<HtmlElement> it = notenspiegel.getHtmlElementDescendants().iterator();
			System.out.println("finding table...");
			while (it.hasNext()) {
				HtmlElement ele = it.next();
				if (ele.getAttribute("cellspacing").equals("0") && ele.getAttribute("border").equals("1")) {
					this.table = (HtmlTable) ele;
				}
			}
			notenspiegel.getAnchors().get(4).click();
			System.out.println("logging out...");
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
	
	public void logout(){
		
	}

	/**
	 * compares different the old number of exams with the new number
	 */

	public void compare() {
		int old = this.load();
		int current = examList.size();
		System.out.println("old: " + old + " new: " + current);
		if (old == -1) {
			save(current);
			return;
		}
		if (current > old) {
			int diff = current - old;
			System.out.println(diff + " neue Ergebnisse!");
			for (int i = 0; i < diff; i++) {
				String subject = "Klausurergebnis " + examList.get(i).getName();
				System.out.println("Sent: " + subject);
				mailer.Mailer.sendMail(examList.get(i).toString(), subject);
			}

			this.save(current);
		} else {
			System.out.println("Nothing new");
		}
		
		SimpleDateFormat ff = new SimpleDateFormat("HH:mm dd.MM.YYY");
		System.out.println("Finished: " + ff.format(new Date()));
	}
	
	private int load() {
		return Integer.parseInt(Config.loadProp("COUNT"));
	}
	
	public void save(int i) {
		Config.writeProp("COUNT", i + "");
	}
}
