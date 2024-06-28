package ui.model.Admin.password;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class AdminController implements Initializable {

	@FXML
	private TextField txtpass;
	@FXML
	private Button btnok;
	private Stage stage;
	private Parent root;
	private Scene scene;
	
	static Connection conn = DatabaseConnection.getConnection();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void okbutton() {
		if(txtpass.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtpass.getScene().getWindow(),"ERROR","EMPTY FIELD \nPLEASE ENTER ADMINISTRATOR PASSWORD");
			return;
		}
			try {
					String sql = "SELECT password FROM admin WHERE password = ?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, txtpass.getText());
					ResultSet rs = stmt.executeQuery();
					
					while(rs.next()) {
						if(rs.getString(1).equals(txtpass.getText())){
							closeStage();
							loadsearch();
						}
						else {
							showalert(AlertType.ERROR,(Stage)txtpass.getScene().getWindow(),"ERROR","ADMINISTRATOR PASSWORD NOT FOUND \nPLEASE ENTER VALID ADMINISTRATOR PASSWORD");
							return;
						}
					}
				
			}catch(SQLException e) {
				e.printStackTrace();
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

	@FXML
	public void loadsearch() {
		try {
		root = FXMLLoader.load(getClass().getResource("/ui/model/student/search/search.fxml"));
		scene = new Scene(root);
		stage = new Stage();
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
	
	public void closeStage() {
		stage = (Stage)btnok.getScene().getWindow();
		stage.close();
		
	}
	

	

	
}
