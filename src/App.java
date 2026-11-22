
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {

    private static String URL = "jdbc:mysql://localhost:3306/realtime_db";
    private static String USER = "root";
    private static String PASS = "";
    private static Connection connection = null;
    private static Scanner scanner = null;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);

        // CREATE
        System.out.println("=== TAMBAH DATA ===");
        System.out.print("NIM : ");
        String nim = scanner.nextLine();

        System.out.print("Nama : ");
        String nama = scanner.nextLine();

        System.out.print("Jurusan : ");
        String jurusan = scanner.nextLine();

        tambahData(nim, nama, jurusan);

        // READ
        System.out.println("\n=== DATA MAHASISWA ===");
        tampilData();

        // UPDATE
        System.out.println("\n=== UPDATE DATA ===");
        updateData(1, "09030027", "Amir Hasan", "Elektro");

        tampilData();

        // DELETE
        System.out.println("\n=== DELETE DATA ===");
        deleteData(2);

        tampilData();

        scanner.close();
    }

    // CREATE
    private static void tambahData(String nim, String nama, String jurusan) {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);

            String query = "INSERT INTO mahasiswas (nim, nama, jurusan) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, nim);
            preparedStatement.setString(2, nama);
            preparedStatement.setString(3, jurusan);

            preparedStatement.executeUpdate();

            System.out.println("Data berhasil ditambahkan.");

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Kesalahan SQL: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // READ
    private static void tampilData() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);

            String query = "SELECT * FROM mahasiswas";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(
                    resultSet.getInt(1) + " | " +
                    resultSet.getString(2) + " | " +
                    resultSet.getString("nama") + " | " +
                    resultSet.getString(4)
                );
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Koneksi DB gagal: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // UPDATE
    private static void updateData(int id, String nim, String nama, String jurusan) {

        String query = "UPDATE mahasiswas SET nim=?, nama=?, jurusan=? WHERE id=?";

        try {
            connection = DriverManager.getConnection(URL, USER, PASS);

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, nim);
            preparedStatement.setString(2, nama);
            preparedStatement.setString(3, jurusan);
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

            System.out.println("Data berhasil diubah.");

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE
    private static void deleteData(int id) {

        String query = "DELETE FROM mahasiswas WHERE id=?";

        try {
            connection = DriverManager.getConnection(URL, USER, PASS);

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Data berhasil dihapus.");

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
