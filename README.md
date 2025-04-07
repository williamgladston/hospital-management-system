# hospital-management-system

🏥 Hospital Management System

A comprehensive Java Swing-based desktop application for managing hospital operations such as patient registration, appointments, doctor records, room allocation, billing, lab reports, and feedback. Data is stored and managed using a MySQL database.

📦 Features

📝 Add New Patient with ID verification

📏 Room Management (Free/Occupied status)

👨‍⚕️ Doctor Management (Add/View Doctors)

🗕 Appointments (Auto-doctor assignment)

💰 Billing System (Auto calculate & generate bills)

🦢 Lab Reports (Upload and track reports)

📋 Feedback Collection

💻 Java Swing Modern UI (Blue-themed interface)

💾 MySQL Database Connectivity

🛠 Technologies Used

Java (Swing & JDBC)

MySQL

NetBeans / IntelliJ IDEA / Eclipse

JDBC Driver: mysql-connector-java

Optional: Launch4j / Inno Setup (for packaging)


🚀 How to Run

Install MySQL and import the hospital_management_system.sql file

Edit conn.java with your MySQL credentials:

conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system", "root", "password");

Compile the project:

javac -cp .;lib\mysql-connector-java-8.0.xx.jar src\*.java

Run the project:

java -cp .;lib\mysql-connector-java-8.0.xx.jar hospital.management.system.Login

