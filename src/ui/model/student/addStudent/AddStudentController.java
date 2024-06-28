package ui.model.student.addStudent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import database.studentUtilities.StudentUtilities;
import datamodel.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AddStudentController implements Initializable {

	@FXML
	private Label txtid;
	@FXML
	private TextField txtfname,txtlname,txtoname,txtemail,txtpemail,txtptele,txtaddress,txtnat;
	@FXML
	private ComboBox<String> combogender,comboclass,combohouse;
	@FXML
	private DatePicker datepicker;
	@FXML
	private ImageView  myimageview;
	@FXML
	private Button btnupload,btnclear,btnsave;
	@FXML
	private TableView<Student>table;
	@FXML
	private TableColumn<Student,String>CID;
	@FXML
	private TableColumn<Student,String>CFN;
	@FXML
	private TableColumn<Student,String>CLN;
	@FXML
	private TableColumn<Student,String>CG;
	@FXML
	private TableColumn<Student,String>CB;
	@FXML
	private TableColumn<Student,String>CC;
	@FXML
	private TableColumn<Student,String>CH;
	@FXML
	private TableColumn<Student,String>CN;
	
	static Connection conn = DatabaseConnection.getConnection();
	  static  PreparedStatement pstmt = null;
	   static Statement stmt = null;
	    int myindex;
	    
	    Image image;
	  static  File myfile;
	    FileChooser chooser = new FileChooser();
	    
	    ObservableList<Student> students = FXCollections.observableArrayList();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboclass.setItems(FXCollections.observableArrayList("S.1","S.2","S.3","S.4","S.5","S.6"));
		combogender.setItems(FXCollections.observableArrayList("MALE","FEMALE"));
		combohouse.setItems(FXCollections.observableArrayList("TURKER","SILK","MULUMBA"));
		table();
	}

	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	//button uploadphoto codes
		@FXML
			public void uploadphoto(ActionEvent event) throws IOException {
				myfile = chooser.showOpenDialog((Stage)myimageview.getScene().getWindow());
				
				if(myfile != null) {
					image = new Image(myfile.toURI().toString());
					myimageview.setImage(image); 
				}
				else {
					showalert(AlertType.WARNING,(Stage)myimageview.getScene().getWindow(),"CANCLING PROCESS","IMAGE SELECTION FAILED");
				}
			}
			//save button
			@FXML
		public void savestudent() {
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
				
				Student std = new Student(txtfname.getText());
				std.setLname(txtlname.getText());
				if(!(txtoname.getText() == null)) {
				std.setOname(txtoname.getText());
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
				
				StudentUtilities.addStudentToDB(std, myfile);
				
				table();
				
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	//
			//
			@FXML
			public void table() {
				table.getItems().clear();
				try {
					String status = "ACTIVE";
					String SQL = "SELECT Student_ID,first_Name,last_name,gender,Birth_Date,std_class ,house,nationality FROM STUDENTS WHERE status = '"+ status +"'";
					
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(SQL);
					{
					while(rs.next()) {
						try {
						Student std = new Student(rs.getString("student_id"),rs.getString("first_name"));
						std.setLname(rs.getString("last_name"));
						std.setGender(rs.getString("gender"));
						std.setBirthdate(rs.getString("birth_date"));
						std.setClas(rs.getString("std_class"));
						std.setHouse(rs.getString("house"));
						std.setNationality(rs.getString("nationality"));
						
						students.add(std);
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
						table.setItems(students);

					     CID.setCellValueFactory(new PropertyValueFactory<>("stdid"));
						 CFN.setCellValueFactory(new PropertyValueFactory<>("fname"));
						 CLN.setCellValueFactory(new PropertyValueFactory<>("lname"));
						 CG.setCellValueFactory(new PropertyValueFactory<>("gender"));
						CB.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
						CC.setCellValueFactory(new PropertyValueFactory<>("clas"));
						 CH.setCellValueFactory(new PropertyValueFactory<>("house"));
						 CN.setCellValueFactory(new PropertyValueFactory<>("nationality"));
					}
					}
					
					table.setRowFactory(tv ->{
						
						TableRow<Student> myrow = new TableRow<>();
						myrow.setOnMouseClicked(event -> {
							if(event.getClickCount() == 1 && (!myrow.isEmpty())) {
								
								myindex = table.getSelectionModel().getSelectedIndex();
								
								txtid.setText(table.getItems().get(myindex).getStdid());
								 txtfname.setText(table.getItems().get(myindex).getFname());
								 txtlname.setText(table.getItems().get(myindex).getLname());
							 	combogender.setValue(table.getItems().get(myindex).getGender().toString());
							 	datepicker.setValue(LocalDate.parse( table.getItems().get(myindex).getBirthdate()));
								comboclass.setValue(table.getItems().get(myindex).getClas().toString());
								combohouse.setValue(table.getItems().get(myindex).getHouse().toString());
							    txtnat.setText(table.getItems().get(myindex).getNationality());
								
								
									try {
										String SQL1 = "SELECT other_name,email,parent_contact,parent_Email,Address ,photo FROM STUDENTS WHERE STUDENT_ID =?";
										PreparedStatement pstmt = conn.prepareStatement(SQL1);
										pstmt.setString(1,table.getItems().get(myindex).getStdid());
										pstmt.execute();
										ResultSet rs1 = pstmt.executeQuery();
										while(rs1.next()) {
											if(!(rs1.getString("other_name") == null)) {
											txtoname.setText(rs1.getString("other_name"));
											}
											txtemail.setText(rs1.getString("email"));
											txtptele.setText(rs1.getString("parent_contact"));
											txtpemail.setText(rs1.getString("parent_Email"));
											txtaddress.setText(rs1.getString("address"));
											
											image = new Image(rs1.getBlob("photo").getBinaryStream());
											myimageview.setImage(image);
												
										}
										
									}catch(SQLException e) {
										//e.printStackTrace();
										}
							}
						});
						return myrow;
					});
					
			}catch(Exception e) {
				//e.printStackTrace();
				}
			}
	
			//
			@FXML
			public void clearbutton() {
				txtid.setText("");
				 txtfname.setText("");
				 txtlname.setText("");
			 	combogender.setValue(null);
			 	datepicker.setValue(null);
				comboclass.setValue(null);
				combohouse.setValue(null);
			    txtnat.setText("");
			    txtoname.setText("");
			txtemail.setText("");
			txtptele.setText("");
			txtpemail.setText("");
			txtaddress.setText("");
			
			image = new Image("/images/PROFILE.png");
			myimageview.setImage(image);
			
			}
}
