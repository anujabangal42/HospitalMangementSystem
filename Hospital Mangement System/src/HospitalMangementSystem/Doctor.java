package HospitalMangementSystem;

import java.sql.*;
import java.util.Scanner;

public class Doctor {
    private final Connection connection;
    private final Scanner scanner;

    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void viewDoctors() {
        String query = "SELECT * FROM doctor";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n=== Doctor List ===");
            System.out.printf("%-5s %-20s %-20s %-15s\n", "ID", "Name", "Specialization", "Contact");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-20s %-15s\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("Specilization"),
                        rs.getString("contact_details"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT id FROM doctor WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void updateDoctor() {
        try {
            System.out.print("Enter Doctor ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (!getDoctorById(id)) {
                System.out.println("Doctor ID not found.");
                return;
            }

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new specialization: ");
            String specialization = scanner.nextLine();

            System.out.print("Enter new contact details: ");
            String contact = scanner.nextLine();

            String query = "UPDATE doctor SET name = ?, Specilization = ?, contact_details = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, specialization);
                ps.setString(3, contact);
                ps.setInt(4, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Doctor updated successfully.");
                } else {
                    System.out.println("Failed to update doctor.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteDoctor() {
        try {
            System.out.print("Enter Doctor ID to delete: ");
            int id = scanner.nextInt();

            if (!getDoctorById(id)) {
                System.out.println("Doctor ID not found.");
                return;
            }

            String query = "DELETE FROM doctor WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Doctor deleted successfully.");
                } else {
                    System.out.println("Failed to delete doctor.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



