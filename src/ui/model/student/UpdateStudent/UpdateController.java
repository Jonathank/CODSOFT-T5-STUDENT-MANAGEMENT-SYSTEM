package ui.model.student.UpdateStudent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import database.studentUtilities.StudentUtilities;
import datamodel.student.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UpdateController implements Initializable {

	@FXML
	private Label txtid;
	@FXML
	private TextField txtsearch,txtfname,txtlname,txtoname,txtemail,txtpemail,txtptele,txtaddress,txtnat;
	@FXML
	private ComboBox<String> combogender,comboclass,combohouse;
	@FXML
	private DatePicker datepicker;
	@FXML
	private Button btnsearch,btnupdate;
	@FXML
	private ImageView  myimageview;
	private Stage stage;
	
	static Connection conn = DatabaseConnection.getConnection();
	
	   Image image;
	    
	   static  File myfile;
	   FileChooser chooser = new FileChooser();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboclass.setItems(FXCollections.observableArrayList("S.1","S.2","S.3","S.4","S.5","S.6"));
		combogender.setItems(FXCollections.observableArrayList("MALE","FEMALE"));
		combohouse.setItems(FXCollections.observableArrayList("TURKER","SILK","MULUMBA"));
		
	}

	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	//@ search
			public void search() {
				if(StudentUtilities.isStudentIDExists(txtsearch.getText())) {
				try {
					String sql = "SELECT Student_ID,first_Name,last_name,other_name,gender,Birth_Date,std_class ,house,email,parent_contact,parent_Email,Address,nationality ,photo FROM students WHERE student_id = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, txtsearch.getText());
					stmt.execute();
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						txtid.setText(rs.getString("student_id"));
						txtfname.setText(rs.getString("first_name"));
						txtlname.setText(rs.getString("last_name"));
						if(!(rs.getString("other_name") == null)) {
						txtoname.setText(rs.getString("other_name"));
						}
						combogender.setValue(rs.getString("gender").toString());
						datepicker.setValue(LocalDate.parse(rs.getString("Birth_date").toString()));
						comboclass.setValue(rs.getString("std_class").toString());
						txtemail.setText(rs.getString("email"));
						txtpemail.setText(rs.getString("parent_Email"));
						combohouse.setValue(rs.getString("house").toString());
						txtptele.setText(rs.getString("parent_contact"));
						txtaddress.setText(rs.getString("address"));
						txtnat.setText(rs.getString("nationality"));
						
						image = new Image(rs.getBlob("photo").getBinaryStream());
						myimageview.setImage(image);
						
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				}else {
					showalert(AlertType.ERROR,(Stage)txtsearch.getScene().getWindow(),"ERROR","STUDENT ID NOT FOUND \nPLEASE ENTER VALID STUDENT ID");
					return;
				}
			}
	
		
		//update button
		@FXML
	public void updatestudent() {
		try {
			if(txtfname.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER FIRST-NAME");
				return;
			}
			if(txtlname.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER LAST-NAME");
				return;
			}
			
			if(combogender.getValue()==null) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE SELECT GENDER");
				return;
			}
			
			if(comboclass.getValue()==null) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE SELECT CLASS");
				return;
			}
			if(combohouse.getValue()==null) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE SELECT HOUSE");
				return;
			}
			
			if(datepicker.getValue()==null) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER BIRTH DATE");
				return;
			}
			
			if(myimageview.getImage()==null) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE LOAD STUDENT PHOTO");
				return;
			}
			if(txtemail.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER STUDENT EMAIL");
				return;
			}
			if(txtpemail.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER PARENT EMAIL");
				return;
			}
			if(txtptele.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER PARENT TELEPHONE");
				return;
			}
			
			if(txtaddress.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER ADDRESS");
				return;
			}
			if(txtnat.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtfname.getScene().getWindow(),"ERROR","PLEASE ENTER NATIONALITY");
				return;
			}
			
			Student std = new Student();
			std.setStdid(txtid.getText());
			std.setFname(txtfname.getText());
			std.setLname(txtlname.getText());
			if(!(txtoname.getText() == null)) {
			std.setOname(txtoname.getText());
			}else {
				std.setOname("");
			}
			std.setGender(combogender.getValue().toString());
			std.setBirthdate(datepicker.getValue().toString());
			std.setClas(comboclass.getValue().toString());
			std.setHouse(combohouse.getValue().toString());
			std.setEmail(txtemail.getText());
			std.setParent_con(txtptele.getText());
			std.setParent_email(txtpemail.getText());
			std.setAddress(txtaddress.getText());
			std.setNationality(txtnat.getText());
			
			StudentUtilities.updateStudent(std);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("UPDATE RECORD");
			alert.setContentText("RECORD UPDATED SUCCESSFULLY");
			if(alert.showAndWait().get() == ButtonType.OK) {
			closeStage();
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		public void closeStage() {
			stage = (Stage)btnsearch.getScene().getWindow();
			stage.close();
			
		}
}
