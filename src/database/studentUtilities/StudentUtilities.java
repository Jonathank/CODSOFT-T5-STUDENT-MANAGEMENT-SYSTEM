package database.studentUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;

import database.conn.DatabaseConnection;
import datamodel.student.Student;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class StudentUtilities {
	
static Connection conn = DatabaseConnection.getConnection();

/**
 * Saves the last id in the database
 * @param id
 */
public static void saveId(int id) {
	try {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO auto_id (ID_NO) VALUES(?)");
		stmt.setInt(1, id);
		//execute
		stmt.execute();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}	
}

/**
 * Returns the auto id's for the student that was created last
 * @return id numb
 */
public static int getLaststudentID() {
	String sql ="SELECT * FROM auto_id ORDER BY ID_NO DESC LIMIT 1";
	int id = 0;
	try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		//Checks if the table is empty
		if(rs.next() == false) {
			id =99;
			
		}
		else {
			 id =rs.getInt(1);
			 
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	id++; //increment by 1
	
	return id;	
}

//checks if student exists
public static boolean isStudentIDExists(String id) {
	
    try {
        String checkstmt = "SELECT COUNT(student_id) FROM students WHERE student_id =?";
        PreparedStatement stmt = conn.prepareStatement(checkstmt);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return (count > 0);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return false;
}

//addstudent
	public static void addStudentToDB(Student std,File file) throws IOException{
		try {
			String status = "ACTIVE";
			String date = LocalDate.now().toString();
			
			String sql = "INSERT INTO students(Student_ID,first_Name,last_name,other_name,gender,Birth_Date,std_class ,house,email,parent_contact,parent_Email,Address,nationality,registration_Date,status ,photo)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, std.getStdid());
			stmt.setString(2, std.getFname());
			stmt.setString(3, std.getLname());
			stmt.setString(4, std.getOname());
			stmt.setString(5, std.getGender());
			stmt.setString(6, std.getBirthdate());
			stmt.setString(7, std.getClas());
			stmt.setString(8, std.getHouse());
			stmt.setString(9, std.getEmail());
			stmt.setString(10, std.getParent_con());
			stmt.setString(11, std.getParent_email());
			stmt.setString(12, std.getAddress());
			stmt.setString(13, std.getNationality());
			stmt.setString(14, date);
			stmt.setString(15, status);
			
			FileInputStream fis = new FileInputStream(file);
			stmt.setBinaryStream(16, fis, (int)file.length());
			
			stmt.execute();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("SAVING RECORD");
			alert.setContentText("RECORD SAVED SUCCESSFULLY");
			alert.showAndWait();
		}catch(SQLException e) {
			
		}
	}

	public static boolean isAdminPasswordExist(String pass) {
		 try {
		        String checkstmt = "SELECT COUNT(password) FROM admin WHERE password =?";
		        PreparedStatement stmt = conn.prepareStatement(checkstmt);
		        stmt.setString(1, pass);
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            int count = rs.getInt(1);
		            return (count > 0);
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		return false;
	}
	
	//delete button
		public static void deletestudent(String id) {
				
				try {
					String SQL = "DELETE FROM STUDENTS WHERE STUDENT_ID = ?";
					PreparedStatement pstmt = conn.prepareStatement(SQL);
					
					pstmt.setString(1, id);
					pstmt.execute();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}

		//addstudent
		public static void updateStudent(Student std) throws IOException{
			try {
				String sql = "UPDATE students SET first_Name = ?, last_name = ?, other_name = ?, gender = ?, Birth_Date = ?, std_class = ?, house = ?, email = ?, parent_contact = ?, parent_Email = ?, Address = ?, nationality = ? WHERE Student_ID = ?";

				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1, std.getFname());
				stmt.setString(2, std.getLname());
		        stmt.setString(3, std.getOname());
				stmt.setString(4, std.getGender());
				stmt.setString(5, std.getBirthdate());
				stmt.setString(6, std.getClas());
				stmt.setString(7, std.getHouse());
				stmt.setString(8, std.getEmail());
				stmt.setString(9, std.getParent_con());
				stmt.setString(10, std.getParent_email());
				stmt.setString(11, std.getAddress());
				stmt.setString(12, std.getNationality());
				
				stmt.setString(13, std.getStdid());
				
				stmt.executeUpdate();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

	public static void addAttendance(String id,String clas,String term) {
		try {
			LocalDate currentdate = LocalDate.now();
			String weekday = currentdate.getDayOfWeek().toString();
			Month month = currentdate.getMonth();
			Year theyear = Year.now();
			LocalTime time = LocalTime.now();
			String sql = "INSERT INTO attendance (student_id,class,term,weekday,themonth,theyear,time,fulldate) VALUES(?,?,?,?,?,?,?,? )";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, clas);
			stmt.setString(3, term);
			stmt.setString(4, weekday);
			stmt.setString(5, month.toString());
			stmt.setString(6, theyear.toString());
			stmt.setString(7, time.toString());
			stmt.setString(8, currentdate.toString());
			
			stmt.execute();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("STUDENT "+id+" HAS BEEN CHECKED IN");
			alert.show();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//if student is exist in attendance
	public static boolean isstudentExistInAttendace(String id) {
		LocalDate date = LocalDate.now();
	        try {
	            String checkstmt = "SELECT COUNT(*) FROM attendance WHERE student_id = ? AND fulldate ='"+date.toString()+"'";
	            PreparedStatement stmt = conn.prepareStatement(checkstmt);
	            stmt.setString(1, id);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return (count > 0);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }
	
	public static boolean isYearExistInAttendace(String id,String term) {
	        try {
	            String checkstmt = "SELECT COUNT(theyear) FROM attendance_view WHERE student_id = ? AND term = ? ";
	            PreparedStatement stmt = conn.prepareStatement(checkstmt);
	            stmt.setString(1, id);
	            stmt.setString(2, term);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return (count > 0);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }
}
