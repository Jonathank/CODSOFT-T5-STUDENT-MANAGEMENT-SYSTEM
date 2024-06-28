package ui.model.student.login;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.conn.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {

	@FXML
	private Button btnlogin;
	@FXML
	private TextField txtuser,txtpass;
	@FXML
	private Label message;
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	
	static Connection conn = DatabaseConnection.getConnection();
	
	public void loginbutton() {
		
		if(txtuser.getText().isEmpty()) {
			showalert(AlertType.ERROR,(Stage)txtuser.getScene().getWindow(),"ERROR","PLEASE ENTER USERNAME");
			return;
			}
			if(txtpass.getText().isEmpty()) {
				showalert(AlertType.ERROR,(Stage)txtpass.getScene().getWindow(),"ERROR","PLEASE ENTER PASSWORD");
				return;
				}
			
			try {
				String sql = "SELECT * FROM users";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					if((rs.getString(1).equals(txtuser.getText())) && (rs.getString(2).equals(txtpass.getText()))){
						closeStage();
						loadpage();
					}
					else {
						message.setText("WRONG USERNAME OR PASSWORD!");
						//message.setStyle("-fx-text-fill: red");
					}
				}
					
			}catch(SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		public void loadpage() throws IOException {
			
			root = FXMLLoader.load(getClass().getResource("/ui/model/student/dashboard/Dashboard.fxml"));
			scene = new Scene(root);
			stage = new Stage();
			stage.setScene(scene);
			Image image = new Image("/images/mylogo.png");
			stage.getIcons().add(image);
			stage.show();
			stage.setOnCloseRequest(event -> {
				event.consume();
				exit(stage);
				
			});
		}
		
		//exit confirmation message
		public void exit(Stage stage) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("DO YOU WANT TO  EXIT ");
			alert.setTitle("EXIT");
			alert.setContentText("YOU ARE ABOUT TO EXIT");
			
			if(alert.showAndWait().get() == ButtonType.OK) {
				stage.close();
			}
		}
		
		public void closeStage() {
			stage = (Stage)btnlogin.getScene().getWindow();
			stage.close();
			
		}
		
		
		private static void showalert(AlertType alerttype,Window owner,String title,String message) {
			Alert alert = new Alert(alerttype);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.initOwner(owner);
			alert.show();
		}
		
		public void closebutton(ActionEvent event) {
			stage = (Stage)btnlogin.getScene().getWindow();
			stage.close();
			
		}
}
