package ui.model.student.StudentAttendanceView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StudentAttedanceView implements Initializable{

	@FXML
	private Button btnsearch,btncheckin;
	@FXML
	private Label txtid,txtname,txtclass,txtgender,txtterm,txthouse,months,days,txtyear;
	
	private String StudentId;
	private String term;
	private String the_year;
	@FXML
	private ImageView myimageview;
	private Image image;

	static Connection conn = DatabaseConnection.getConnection();
	
	public void setStudentId(String stdid, String t,String y) {
		StudentId = stdid;
		term = t;
		the_year = y;
		
		search(StudentId,term,the_year);
		
	}
	
	//
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		setStudentId(StudentId, term,the_year);
	}

	
	
	//@ search
			public void search(String id,String term,String year) {
				
				try {
					String sql = "SELECT "
							+ "Student_ID,first_Name,last_name,gender,house,photo,class,term,theyear,COUNT(*) AS days_present "
							+ "FROM attendance_view "
							+ "WHERE student_id = ? "
							+ "AND term = ? "
							+ "AND theyear = ? "
							+ "GROUP BY "
							+ "Student_ID,first_Name,last_name,gender,house,photo,class,term,theyear";
					
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, id);
					stmt.setString(2, term);
					stmt.setString(3, year);
					
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						txtid.setText(rs.getString("student_id"));
						txtname.setText(rs.getString("first_name")+" "+rs.getString("last_name"));
						txtgender.setText(rs.getString("gender"));
						txtclass.setText(rs.getString("class"));
						txthouse.setText(rs.getString("house"));
						txtterm.setText(rs.getString("term"));
						txtyear.setText(rs.getString("theyear"));
						
						image = new Image(rs.getBlob("photo").getBinaryStream());
						myimageview.setImage(image);
						 
					    days.setText(String.valueOf(rs.getInt("days_present")));
					}
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
	
	
	
	

	/**
	 * @return the studentId
	 */
	public String getStudentId() {
		return StudentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		StudentId = studentId;
	}

	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}

	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return the theyear
	 */
	public String getTheyear() {
		return the_year;
	}

	/**
	 * @param theyear the theyear to set
	 */
	public void setTheyear(String theyear) {
		this.the_year = theyear;
	}
	
}
