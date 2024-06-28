
CREATE DATABASE StudentManagementSystem;

use StudentManagementSystem;

create table Students(
	Student_ID varchar(100) primary key,
	first_Name varchar(150),
	last_name varchar(150),
	other_name varchar(150),
	gender varchar(15),
	Birth_Date date,
	std_class varchar(100),
	house varchar(100),
	email varchar(100),
	parent_contact varchar(100),
	parent_Email varchar(100),
	Address varchar(100),
    nationality varchar(100),
	registration_Date varchar(100),
	status varchar(100),
	photo longblob
);

CREATE TABLE attendance(
id int primary key auto_increment,
student_id varchar(100),
class varchar(255),
term varchar(100),
weekday varchar(255),
themonth varchar(255),
theyear varchar(255),
Time time,
fulldate varchar(255)
);

ALTER table Attendance ADD FOREIGN KEY (student_id) REFERENCES students(Student_ID);

create view attendance_view AS 
SELECT s.student_id, s.first_name, s.last_name, s.gender,s.house,s.photo,a.class, a.term,a.weekday,a.themonth,a.theyear
FROM students s
JOIN Attendance a ON s.student_id = a.student_id;

SELECT Student_ID,first_Name,last_name,gender,house,photo,class,term,theyear,COUNT(*) AS days_present  FROM attendance_view WHERE student_id = 'JN00001072024' AND term = '1' AND theyear = '2024'
GROUP BY Student_ID,first_Name,last_name,gender,house,photo,class,term,theyear;

SELECT Student_ID,first_Name,last_name,gender,house,photo,class,term,theyear,COUNT(*) AS days_present
FROM attendance_view 
WHERE Student_ID = 'JN00001072024' 
AND term = '1' 
AND theyear = '2024'
GROUP BY Student_ID,first_Name,last_name,gender,house,photo,class,term,theyear;



CREATE TABLE users (
	username varchar(100) primary key,
	password varchar (100) unique
);

INSERT INTO users(username,password)VALUES("admin","123");

CREATE TABLE admin(
	password varchar(100)
);
INSERT INTO admin(password)VALUES("admin123");

CREATE TABLE auto_id(
	ID_NO int primary key
);
