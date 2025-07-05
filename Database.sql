CREATE DATABASE if not exists HospitalManagement;

use HospitalManagement;


CREATE TABLE patient(
ID int auto_increment primary key,
NAME varchar(280),
AGE int not null,
GENDER varchar(20),
Weight double
);



CREATE TABLE Doctor(
ID INT auto_increment PRIMARY KEY,
NAME VARCHAR(255),
Specilization varchar(290),
Contact_details int not null
);


CREATE TABLE appointments (
    ID INT auto_increment PRIMARY KEY,
    Patient_id INT,
    Doctor_id INT,
    FOREIGN KEY (Patient_id) REFERENCES patient(ID),
    FOREIGN KEY (Doctor_id) REFERENCES Doctor(ID),
    appointment_Date date not null
);
 
