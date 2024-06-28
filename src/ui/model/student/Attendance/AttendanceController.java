package ui.model.student.Attendance;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import database.studentUtilities.StudentUtilities;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AttendanceController implements Initializable {

	@FXML
	private Button btnsearch,btncheckin;
	@FXML
	private Label txtfname,txtlname,txtid,txtclass,txtgender;
	@FXML
	private TextField txtsearch;
	@FXML
	private ComboBox<String>comboterm;
	@FXML
	private ImageView myimageview;
	private Image image;

	static Connection conn = DatabaseConnection.getConnection();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboterm.setItems(FXCollections.observableArrayList("1","2","3"));
		
	}
	LocalDate currentdate = LocalDate.now();

	//
	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

	//search button
			public void search() {
				 if(!(StudentUtilities.isStudentIDExists(txtsearch.getText()))) {
						showalert(AlertType.ERROR,(Stage)txtsearch.getScene().getWindow(),"INVALID STUDENT ID","STUDENT ID DOESN'T EXIST!!");
						return;
					}
			
				try {
					String sql = "SELECT student_id,first_name,last_name,gender,std_class,photo FROM students WHERE student_id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, txtsearch.getText());
					stmt.execute();
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						
						txtid.setText(rs.getString("student_id"));
						txtfname.setText(rs. getString("first_name"));
						txtlname.setText(rs.getString("last_name"));
						txtgender.setText(rs.getString("gender"));
						txtclass.setText(rs.getString("std_class"));
						image = new Image(rs.getBlob("photo").getBinaryStream());
						myimageview.setImage(image);
					}
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}

			public void checkin() {
				try {
					
					if(StudentUtilities.isstudentExistInAttendace(txtid.getText().toString())) {
						showalert(AlertType.WARNING,(Stage)txtid.getScene().getWindow(),"STUDENT ALREADY CHECKED IN","YOU CAN'T CHECK IN MORE THAN ONCE");
						return;
					}
					
					StudentUtilities.addAttendance(txtid.getText(), txtclass.getText(), comboterm.getValue().toString());
					
			}catch(Exception e) {
				e.printStackTrace();
			}
			}
}
