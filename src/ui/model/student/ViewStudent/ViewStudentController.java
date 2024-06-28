package ui.model.student.ViewStudent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import database.studentUtilities.StudentUtilities;
import datamodel.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ViewStudentController implements Initializable{

	@FXML
	private TextField txtsearch;
	@FXML
	private Button btnsearch;
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
	
	   static Statement stmt = null;
	    int myindex;
	    
	    
	    ObservableList<Student> students = FXCollections.observableArrayList();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table();
	}

	
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
								
								txtsearch.setText(table.getItems().get(myindex).getStdid());
							}
							
						});
						return myrow;
					});
					
			}catch(Exception e) {
				//e.printStackTrace();
				}
			}
			
			public void searchbutton() {
				if(txtsearch.getText().isEmpty()) {
					showalert(AlertType.ERROR,(Stage)txtsearch.getScene().getWindow(),"ERROR","EMPTY FIELD \nPLEASE ENTER STUDENT ID");
					return;
				}
				else if(StudentUtilities.isStudentIDExists(txtsearch.getText())) {
					try {
						
						String stdid = txtsearch.getText();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/model/student/ViewStudent/StudentView.fxml"));
						Parent root = loader.load();
						StudentView  cont = loader.getController();
						
					   cont.setStudentId(stdid);
					  
						Scene scene =  new Scene(root);
						Stage stage = new Stage();
						stage.setScene(scene);
						Image image = new Image("/images/mylogo.png");
						stage.getIcons().add(image);
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.setResizable(false);
						stage.show();
						
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {
					showalert(AlertType.ERROR,(Stage)txtsearch.getScene().getWindow(),"ERROR","STUDENT ID NOT FOUND \nPLEASE ENTER VALID STUDENT ID");
					return;
				}

				}
				
			private static void showalert(AlertType alerttype,Window owner,String title,String message) {
				Alert alert = new Alert(alerttype);
				alert.setTitle(title);
				alert.setHeaderText(null);
				alert.setContentText(message);
				alert.initOwner(owner);
				alert.show();
			}

			
}
