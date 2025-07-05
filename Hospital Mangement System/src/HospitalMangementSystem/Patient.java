package HospitalMangementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patient {
    private final Connection connection;
    private final Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        scanner.nextLine(); // clear newline
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Gender: ");
        scanner.nextLine();
        String gender = scanner.nextLine();
        System.out.print("Enter Weight: ");
        int weight = scanner.nextInt();

        String query = "INSERT INTO patient(name, age, gender, weight) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setInt(4, weight);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Patient added successfully!" : "Failed to add patient.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "SELECT * FROM patient";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n=== Patient List ===");
            System.out.printf("%-5s %-20s %-5s %-10s %-6s\n", "ID", "Name", "Age", "Gender", "Weight");
            System.out.println("--------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-5d %-10s %-6d\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getInt("weight"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT id FROM patient WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
