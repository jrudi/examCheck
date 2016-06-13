package crawler;

import java.text.*;
import java.util.Date;
/**
 * representation of an Exam, only name, grade, ECTS, date and passed are saved
 * @author JRudi
 *
 */
public class Exam {
	private String name;
	private double note;
	private int ects;
	private boolean bestanden;
	private Date datum;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	public int getECTS() {
		return ects;
	}

	public void setECTS(int bonus) {
		this.ects = bonus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date date) {
		this.datum = date;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}
	
	public void setBestanden(boolean b){
		this.bestanden = b;
	}
	
	public boolean bestanden(){
		return this.bestanden;
	}
	
	public String toString(){
		String s = (this.bestanden?"":"Nicht") + " bestanden";
		return this.name + " am: " + sdf.format(this.datum)+ " Note: "+ this.note + ". " + s;
	}
	
	public String save(){
		return this.name + "//" + sdf.format(this.datum) + "//" + this.note + "//" + this.ects + "//" + this.bestanden;
	}
	
	public static Exam readFile(String in){
		Exam e = new Exam();
		String[] list = in.split("//");
		
		if(list.length==5){
		e.name = list[0];
		try {
			e.datum = sdf.parse(list[1]);
		} catch (ParseException e1) {
			e.datum = new Date();
			e1.printStackTrace();
		}
		e.note = Double.parseDouble(list[2]);
		e.ects = Integer.parseInt(list[3]);
		e.bestanden = Boolean.parseBoolean(list[4]);
		}
		return e;
	}
}
