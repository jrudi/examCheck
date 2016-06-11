package crawler;

import java.text.*;
import java.util.*;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class Crawler {

	private HtmlTable table;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private ArrayList<Exam> examList = new ArrayList<Exam>();

	public static void main(String[] args) throws Exception {
		Crawler c = new Crawler();
		c.crawl();
		for(Exam e:c.examList){
			System.out.println(e.intTest());
		}
	}

	public void crawl() throws Exception {
		try (final WebClient webClient = new WebClient()) {

			HtmlPage cas = webClient.getPage(
					"https://cas.uni-mannheim.de/cas/login?service=https%3A%2F%2Fportal.uni-mannheim.de%2Fqisserver%2Frds%3Fstate%3Duser%26type%3D1");
			HtmlForm form = cas.getForms().get(0);
			HtmlSubmitInput button = form.getInputByName("submit");
			HtmlTextInput name = form.getInputByName("username");
			HtmlPasswordInput pw = form.getInputByName("password");

			name.setValueAttribute("jrudi");
			pw.setValueAttribute("a39mxx");

			HtmlPage portalStart = button.click();
			HtmlPage pruefungen = portalStart.getAnchors().get(14).click();
			HtmlPage notenspiegel = pruefungen.getAnchors().get(23).click();
			Iterator<HtmlElement> it = notenspiegel.getHtmlElementDescendants().iterator();

			while (it.hasNext()) {
				HtmlElement ele = it.next();
				if (ele.getAttribute("cellspacing").equals("0") && ele.getAttribute("border").equals("1")) {
					table = (HtmlTable) ele;
				}
			}

			for (final HtmlTableRow row : table.getRows()) {
				if(table.getRows().indexOf(row)==0)
					continue;
			
				Exam exam = new Exam();
				// for (final HtmlTableCell cell : row.getCells()) {
				// System.out.println(" Found cell: " + cell.asText());
				// }
								
				try{
					String a = row.getCell(0).asText();
					exam.setPrüfungsnummer(Integer.parseInt(a.substring(0, a.length()-1)));
				}catch(NumberFormatException e){
					exam.setPrüfungsnummer(0);
				}
								
				try{
					exam.setBonus(Integer.parseInt(row.getCell(8).asText()));
				}catch(NumberFormatException e){
					exam.setBonus(-1);
				}
				
				try{
				exam.setPrüfungsdatum(sdf.parse(row.getCell(3).asText()));
				}catch (ParseException pe){
					exam.setPrüfungsdatum(sdf.parse("01.01.2016"));
				}
				
				try{
					double n = Double.parseDouble(row.getCell(7).asText().replace(",", "."));
					exam.setNote(n);
				}catch(NumberFormatException e){
					exam.setNote(0.0);
				}

				exam.setSemester(row.getCell(1).asText());
				exam.setPruefungsname(row.getCell(4).asText());
				exam.setPrüfer(row.getCell(5).asText());
				exam.setForm(row.getCell(6).asText());
				exam.setStatus(row.getCell(9).asText());
				exam.setVersuch(row.getCell(10).asText());
				examList.add(exam);
			}
		}
	}
	public void writeFile() {
		/*
		 * File f = new File("notenspielgel.txt"); FileWriter bw = new
		 * FileWriter(f);
		 * 
		 * BufferedReader in = new BufferedReader(new
		 * InputStreamReader(page2.getWebResponse().getContentAsStream()));
		 * String inputLine; StringBuffer response = new StringBuffer();
		 * 
		 * while ((inputLine = in.readLine()) != null) { boolean b = false;
		 * if(inputLine.contains("<!-- BeginnMannheim -->")){ b=true; } if(b){
		 * bw.write(inputLine); } // // response.append(inputLine); //
		 * System.out.println(inputLine); }
		 * 
		 * in.close(); bw.close();
		 */
		// System.out.println(page2.getElementsByTagName("table").get(0).asText());

	}
}
