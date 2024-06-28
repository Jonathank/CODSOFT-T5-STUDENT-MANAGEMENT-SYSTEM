package ui.model.student.StudentAttendanceView;

import java.net.URL;
import java.util.ResourceBundle;

import database.studentUtilities.StudentUtilities;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AttendanceSearchController implements Initializable {

	@FXML
	private TextField txtsearch;
	@FXML
	private TextField txtyear;
	@FXML
	private ComboBox<String>comboterm;
	@FXML
	private Button btnsearch;
	private Stage stage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboterm.setItems(FXCollections.observableArrayList("1","2","3"));
	}
	
	public void searchbutton() {
		if(txtsearch.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtsearch.getScene().getWindow(),"ERROR","EMPTY FIELD \n\nPLEASE ENTER STUDENT ID");
			return;
		}
		if(comboterm.getValue().toString().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)comboterm.getScene().getWindow(),"ERROR","EMPTY FIELD \n\nPLEASE SELECT TERM");
			return;
		}
		if(txtyear.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtsearch.getScene().getWindow(),"ERROR","EMPTY FIELD \n\nPLEASE ENTER ACADEMIC YEAR");
			return;
		}
		if(!(StudentUtilities.isYearExistInAttendace(txtsearch.getText(),comboterm.getValue().toString())) ){
				showalert(AlertType.ERROR,(Stage)txtyear.getScene().getWindow(),"ERROR","ACADEMIC YEAR NOT FOUND \nPLEASE ENTER VALID ACADEMIC YEAR");
				return;
		}
		 if(StudentUtilities.isStudentIDExists(txtsearch.getText()) && StudentUtilities.isYearExistInAttendace(txtsearch.getText(),comboterm.getValue().toString())) {
			
			try {
				closeStage();
				String stdid = txtsearch.getText();
				String term = comboterm.getValue().toString();
				String theyear = txtyear.getText();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/model/student/StudentAttendanceView/StudentAttendanceView.fxml"));
				Parent root = loader.load();
				StudentAttedanceView  cont = loader.getController();
				
			   cont.setStudentId(stdid,term,theyear);
			  
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

	public void closeStage() {
		stage = (Stage)btnsearch.getScene().getWindow();
		stage.close();
		
	}

	
}
