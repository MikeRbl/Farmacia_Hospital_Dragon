/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author roble
 */
public class Conexion {
    private final String bd = "hospital_dragon";
    private final String url = "jdbc:mysql://localhost:3306/" + bd + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private final String user = "root";
    private final String password = "admin";

    public Connection getConnection() {
        Connection con = null;
        try {
            // Cargar el driver en memoria
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el driver de MySQL. Revisa la carpeta Libraries.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión a la BD: " + e.getMessage());
        }
        return con;
    }
    
}
