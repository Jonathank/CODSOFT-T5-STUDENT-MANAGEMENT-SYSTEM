package ui.model.student.search;

import database.studentUtilities.StudentUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ui.model.student.deleteStudent.DeleteStudentController;

public class SearchController {

	@FXML
	private TextField txtsearch;
	@FXML
	private Button btnsearch;
	private Stage stage;
	
	
	public void searchbutton() {
		if(txtsearch.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtsearch.getScene().getWindow(),"ERROR","EMPTY FIELD \nPLEASE ENTER STUDENT ID");
			return;
		}
		else if(StudentUtilities.isStudentIDExists(txtsearch.getText())) {
			try {
				closeStage();
				String stdid = txtsearch.getText();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/model/student/deleteStudent/deletestudent.fxml"));
				Parent root = loader.load();
				DeleteStudentController  cont = loader.getController();
				
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

	public void closeStage() {
		stage = (Stage)btnsearch.getScene().getWindow();
		stage.close();
		
	}
}
