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

	public void setPrüfungsnummer(int pruefungsnummer) {
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

	public void setPruefungsname(String prüfungsname) {
		pruefungsname = prüfungsname;
	}

	public String getPrüfer() {
		return pruefer;
	}

	public void setPrüfer(String pruefer) {
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

	public Date getPrüfungsdatum() {
		return pruefungsdatum;
	}

	public void setPrüfungsdatum(Date prüfungsdatum) {
		this.pruefungsdatum = prüfungsdatum;
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
