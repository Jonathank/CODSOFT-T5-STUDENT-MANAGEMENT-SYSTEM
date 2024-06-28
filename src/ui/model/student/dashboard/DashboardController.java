package ui.model.student.dashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import database.conn.DatabaseConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController implements Initializable{

	@FXML
	private Label datetime,total_students,abscent,present;
	@FXML
	private Button btnaddStudent,btnlogout;
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	@FXML
	private BarChart <String,Integer>barchart;
	
	static Connection conn = DatabaseConnection.getConnection();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTime();
		countallStudents();
		handleBarChart();
		countallStudentspresent();
		countallStudentsabscent();
	}
	
	//
	//method fetches counts from attendance DB and returns the counted value for the line chart
		
	private  int fetchchartdata(String month) {
				Integer count = 0;
			try {
		
				Year year = Year.now();
				String query = "SELECT COUNT(themonth) FROM attendance WHERE themonth = '"+month.toString()+"' AND theyear = '"+year.toString() +"'";
				Statement stmtp = conn.createStatement();
				ResultSet rs1 = stmtp.executeQuery(query);
				while(rs1.next()) {
				count = new Integer(rs1.getInt(1));
		}

				}catch(SQLException e) {
					e.printStackTrace();
				}
			
			return count;
			
		}
		
	
	//handles content of the BAR CHART
		private void handleBarChart() {
			barchart.getData().clear();
			
			XYChart.Series <String,Integer>series = new XYChart.Series<>();
			series.setName("Number Of Students");
			
			series.getData().add(new XYChart.Data<>("JAN",fetchchartdata("JANUARY")));
			series.getData().add(new XYChart.Data<>("FEB",fetchchartdata("FEBRUARY")));
			series.getData().add(new XYChart.Data<>("MAR",fetchchartdata("MARCH")));
			series.getData().add(new XYChart.Data<>("APR", fetchchartdata("APRIL")));
			series.getData().add(new XYChart.Data<>("MAY",fetchchartdata("MAY")));
			series.getData().add(new XYChart.Data<>("JUN",fetchchartdata("JUNE")));
			series.getData().add(new XYChart.Data<>("JUL",fetchchartdata("JULY")));
			series.getData().add(new XYChart.Data<>("AUG",fetchchartdata("AUGUST")));
			series.getData().add(new XYChart.Data<>("SEP",fetchchartdata("SEPTEMBER")));
			series.getData().add(new XYChart.Data<>("OCT",fetchchartdata("OCTOBER")));
			series.getData().add(new XYChart.Data<>("NOV",fetchchartdata("NOVEMBER")));
			series.getData().add(new XYChart.Data<>("DEC",fetchchartdata("DECEMBER")));
		
			barchart.getData().add(series);
		}
	
	
	//set running time 
		private void setTime() {
			
			Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
			
					datetime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss a")))),
					new KeyFrame(Duration.seconds(1)));
			clock.setCycleCount(Animation.INDEFINITE);
			clock.play();
			
		}

		//counts all enrolled students from database
		public void countallStudents() {
			Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event ->{
				try {
					String status = "Active";
					String sql = "SELECT COUNT(*) FROM students WHERE status = '"+status.toString()+"'";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()) {
						int count = rs.getInt(1);
						total_students.setText(String.valueOf(count));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}),new KeyFrame(Duration.seconds(1)));
					clock.setCycleCount(Animation.INDEFINITE);
					clock.play();
			
		}
		//count all students present in aday
			public void countallStudentspresent() {
				Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event ->{
					try {
						LocalDate date = LocalDate.now();
						String query = "SELECT COUNT(*) FROM attendance WHERE fulldate = '"+date.toString()+"'";
						Statement stmtp = conn.createStatement();
						ResultSet rs1 = stmtp.executeQuery(query);
						while(rs1.next()) {
							int num = rs1.getInt(1);
							
							present.setText(String.valueOf(num));	
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
				}),new KeyFrame(Duration.seconds(1)));
						clock.setCycleCount(Animation.INDEFINITE);
						clock.play();
			}
		//count all students abscent in aday
		public void countallStudentsabscent() {
			Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event ->{
				int count;
				try {
					String status = "Active";
					String sql = "SELECT COUNT(*) FROM students WHERE status = '"+status.toString()+"'";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()) {
						count = rs.getInt(1);
						try {
							LocalDate date = LocalDate.now();
							String query = "SELECT COUNT(*) FROM attendance WHERE fulldate = '"+date.toString()+"'";
							Statement stmtp = conn.createStatement();
							ResultSet rs1 = stmtp.executeQuery(query);
							while(rs1.next()) {
								int num = rs1.getInt(1);
								int newval = count - num;
								abscent.setText(String.valueOf(newval));	
							}
						}catch(SQLException e) {
						e.printStackTrace();	
						}
						
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}),new KeyFrame(Duration.seconds(1)));
					clock.setCycleCount(Animation.INDEFINITE);
					clock.play();
			
		}
		
		//calls add new student form
		@FXML
		public void addstudent() {
			try {
			root = FXMLLoader.load(getClass().getResource("/ui/model/student/addStudent/AddStudent.fxml"));
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
		
		//calls delete student form
				@FXML
				public void deletestudent() {
					try {
					root = FXMLLoader.load(getClass().getResource("/ui/model/Admin/password/adminpassword.fxml"));
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
				//calls update student form
				@FXML
				public void Updatetudent() {
					try {
					root = FXMLLoader.load(getClass().getResource("/ui/model/student/UpdateStudent/UpdateStudent.fxml"));
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
				
				//calls viewstudent form
				@FXML
				public void viewStudent() {
					try {
					root = FXMLLoader.load(getClass().getResource("/ui/model/student/ViewStudent/ViewStudent.fxml"));
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
				
				//calls attendance form
				@FXML
				public void attendance() {
					try {
					root = FXMLLoader.load(getClass().getResource("/ui/model/student/Attendance/Attendance.fxml"));
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
				
				//calls view attendance form
				@FXML
				public void viewattendance() {
					try {
					root = FXMLLoader.load(getClass().getResource("/ui/model/student/StudentAttendanceView/AttendanceSearch.fxml"));
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
				
				//calls login form
				@FXML
				public void logoout() {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setHeaderText("DO YOU WANT TO LOGOUT");
					alert.setTitle("EXIT");
					alert.setContentText("YOU ARE ABOUT TO LOGOUT ");
					
					if(alert.showAndWait().get() == ButtonType.OK) {
					closeStage();
					try {
					root = FXMLLoader.load(getClass().getResource("/ui/model/student/login/Login.fxml"));
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
				}
				
				public void closeStage() {
					stage = (Stage)btnlogout.getScene().getWindow();
					stage.close();
				}
}
