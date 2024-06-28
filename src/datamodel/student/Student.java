package datamodel.student;

import java.time.LocalDate;

import database.studentUtilities.StudentUtilities;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Student {
	
	private String stdid;		//stands for student id
	private String fname;		//first name
	private String lname;		//last name
	private String oname; 		//other name
	private String gender;
	private String birthdate;
	private String clas;
	private String house;
	private String email;
	private String parent_con;
	private String parent_email;
	private String address;
	private String nationality;
	
private static int autoid;
	
	//inserts to db
	public Student(String fname) {
		this.fname = fname;
		int year = LocalDate.now().getYear();
		autoid = StudentUtilities.getLaststudentID();
		StudentUtilities.saveId(autoid);
		
		if(autoid<=999) {
			String idSt = "JN0000"+autoid+year;
			this.stdid = idSt;	
		}
		else if(autoid >= 1000) {
			String idSt = "JN000"+autoid+year;
			this.stdid = idSt;
		}
		else if(autoid >= 10000) {
			String idSt = "JN00"+autoid+year;
			this.stdid = idSt;
		}
		else if(autoid >= 100000) {
			String idSt = "JN0"+autoid+year;
			this.stdid = idSt;
		}
		else if(autoid >= 1000000) {
			String idSt = "JN"+autoid+year;
			this.stdid = idSt;
		}
		else if(autoid >= 10000000) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("UPDATE FOR ID IS NEEDED");
			alert.showAndWait();
			String idSt = "JN";
			this.stdid = idSt;
		}
	}
	
	//retrieves from db
	public Student(String id,String fname) {
		this.stdid = id;
		this.fname = fname;
	}
	//Default
	public Student() {
		
	}
	
	
	
	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @return the stdid
	 */
	public String getStdid() {
		return stdid;
	}
	/**
	 * @param stdid the stdid to set
	 */
	public void setStdid(String stdid) {
		this.stdid = stdid;
	}
	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	/**
	 * @return the oname
	 */
	public String getOname() {
		return oname;
	}
	/**
	 * @param oname the oname to set
	 */
	public void setOname(String oname) {
		this.oname = oname;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the birthdate
	 */
	public String getBirthdate() {
		return birthdate;
	}
	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * @return the clas
	 */
	public String getClas() {
		return clas;
	}
	/**
	 * @param clas the clas to set
	 */
	public void setClas(String clas) {
		this.clas = clas;
	}
	/**
	 * @return the house
	 */
	public String getHouse() {
		return house;
	}
	/**
	 * @param house the house to set
	 */
	public void setHouse(String house) {
		this.house = house;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the parent_con
	 */
	public String getParent_con() {
		return parent_con;
	}
	/**
	 * @param parent_con the parent_con to set
	 */
	public void setParent_con(String parent_con) {
		this.parent_con = parent_con;
	}
	/**
	 * @return the parent_email
	 */
	public String getParent_email() {
		return parent_email;
	}
	/**
	 * @param parent_email the parent_email to set
	 */
	public void setParent_email(String parent_email) {
		this.parent_email = parent_email;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
}
