package ui.model.student.deleteStudent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import database.studentUtilities.StudentUtilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DeleteStudentController implements Initializable{

	private String StudentId;
	@FXML
	private Button btndelete;
	@FXML
	private ImageView myimageview;
	@FXML
	private Label txtid,txtname,txtgen,txtdob,txtclass,txtemail,txtpemail,txthouse,txtptele,txtadd,txtnat;
	
	
	private Image image;
	private Stage stage;
	
	static Connection conn = DatabaseConnection.getConnection();
	
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
		search(StudentId);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setStudentId(StudentId);
	
	}
	
	//@ search
		public void search(String id) {
			
			try {
				String sql = "SELECT Student_ID,first_Name,last_name,other_name,gender,Birth_Date,std_class ,house,email,parent_contact,parent_Email,Address,nationality ,photo FROM students WHERE student_id = ?";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, id);
				stmt.execute();
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					txtid.setText(rs.getString("student_id"));
					if(!(rs.getString("other_name") == null)) {
					txtname.setText(rs.getString("first_name")+" "+rs.getString("last_name")+" "+rs.getString("other_name"));
					}
					txtgen.setText(rs.getString("gender"));
					txtdob.setText(rs.getString("Birth_date").toString());
					txtclass.setText(rs.getString("std_class"));
					txtemail.setText(rs.getString("email"));
					txtpemail.setText(rs.getString("parent_Email"));
					txthouse.setText(rs.getString("house"));
					txtptele.setText(rs.getString("parent_contact"));
					txtadd.setText(rs.getString("address"));
					txtnat.setText(rs.getString("nationality"));
					
					image = new Image(rs.getBlob("photo").getBinaryStream());
					myimageview.setImage(image);
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		//delete button
		public void deleteStudent() {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("YOU ARE ABOUT TO DELETE "+txtid.getText());
			alert.setTitle("EXIT");
			alert.setContentText("RECORD "+txtid.getText()+"  IS TO DELETED PERMANENTLY");
			
			if(alert.showAndWait().get() == ButtonType.OK) {
				StudentUtilities.deletestudent(txtid.getText());
				clear();
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("DELETE RECORD");
				alert1.setContentText("RECORD DELETED SUCCESSFULLY");
				
				if(alert.showAndWait().get() == ButtonType.OK) {
				closeStage();
				}
			}
			
		}

		public void closeStage() {
			stage = (Stage)btndelete.getScene().getWindow();
			stage.close();
			
		}
		
		public void clear() {
			txtid.setText("");
			txtname.setText("");
			txtgen.setText("");
			txtdob.setText("");
			txtclass.setText("");
			txtemail.setText("");
			txtpemail.setText("");
			txthouse.setText("");
			txtptele.setText("");
			txtadd.setText("");
			txtnat.setText("");
			
			image = new Image("/images/PROFILE.png");
			myimageview.setImage(image);
		}

		
}
