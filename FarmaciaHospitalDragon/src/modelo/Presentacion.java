/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author roble
 */
public class Presentacion {
    // Método para Guardar (Create)
    public boolean guardar(String nombrePresentacion) {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        
        // Consulta SQL para insertar
        String sql = "INSERT INTO tbl_presentacion (nombre_presentacion) VALUES (?)";
        
        try {
            // Preparamos la consulta para evitar Inyección SQL
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombrePresentacion);
            
            // Ejecutamos la inserción
            ps.executeUpdate();
            return true; // Retorna verdadero si se guardó bien
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar presentación: " + e.toString());
            return false;
        } finally {
            try {
                conexion.close(); // Siempre cerramos la conexión por buenas prácticas
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    // Método para Modificar (Update)
    public boolean modificar(int id, String nuevoNombre) {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        String sql = "UPDATE tbl_presentacion SET nombre_presentacion = ? WHERE id_presentacion = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nuevoNombre);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + e.toString());
            return false;
        }
    }

    // Método para Eliminar (Delete)
    public boolean eliminar(int id) {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        String sql = "DELETE FROM tbl_presentacion WHERE id_presentacion = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.toString());
            return false;
        }
    }
    
    public javax.swing.table.DefaultTableModel cargarTabla() {
        javax.swing.table.DefaultTableModel modeloTabla = new javax.swing.table.DefaultTableModel();
        modeloTabla.addColumn("ID Único");
        modeloTabla.addColumn("Nombre de Presentación");
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM tbl_presentacion");
            while(rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getInt("id_presentacion");
                fila[1] = rs.getString("nombre_presentacion");
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) { System.err.println(e); }
        return modeloTabla;
    }

    public javax.swing.DefaultComboBoxModel cargarCombo() {
        javax.swing.DefaultComboBoxModel modeloCombo = new javax.swing.DefaultComboBoxModel();
        modeloCombo.addElement("Seleccione...");
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT nombre_presentacion FROM tbl_presentacion");
            while(rs.next()) {
                modeloCombo.addElement(rs.getString("nombre_presentacion"));
            }
        } catch (SQLException e) { System.err.println(e); }
        return modeloCombo;
    }
}
