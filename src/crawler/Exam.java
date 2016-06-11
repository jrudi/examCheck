package crawler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam {
	private int pruefungsnummer, bonus;
	
	private String pruefungsname,semester,pruefer,form,status,versuch;
		
	private Date pruefungsdatum;
		
	private double note;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	public int getPruefungsnummer() {
		return pruefungsnummer;
	}

	public void setPr�fungsnummer(int pruefungsnummer) {
		this.pruefungsnummer = pruefungsnummer;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public String getPruefungsname() {
		return pruefungsname;
	}

	public void setPruefungsname(String pr�fungsname) {
		pruefungsname = pr�fungsname;
	}

	public String getPr�fer() {
		return pruefer;
	}

	public void setPr�fer(String pruefer) {
		this.pruefer = pruefer;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersuch() {
		return versuch;
	}

	public void setVersuch(String versuch) {
		this.versuch = versuch;
	}

	public Date getPr�fungsdatum() {
		return pruefungsdatum;
	}

	public void setPr�fungsdatum(Date pr�fungsdatum) {
		this.pruefungsdatum = pr�fungsdatum;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}
	
	public String toString(){
		return this.pruefungsname + " Versuch: " + this.versuch + " Note: "+ this.note;
	}
	
	public String intTest(){
		return 	pruefungsnummer + " " +  semester + " " + bonus + " " + sdf.format(pruefungsdatum);

	}
	
	public static void main(String[] args) {
		
	}
}
