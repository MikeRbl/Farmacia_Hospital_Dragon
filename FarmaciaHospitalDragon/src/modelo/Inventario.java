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
public class Inventario {
    
    // Método para Guardar un Medicamento
    public boolean guardar(String nombre, String nombrePresentacion, int stock, int min, String dist, String fecha) {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        
        // Fíjate en la subconsulta: Busca el ID de la presentación basándose en el nombre que elegiste en el combo
        String sql = "INSERT INTO tbl_inventario (nombre_medicamento, id_presentacion, stock, nivel_minimo, tipo_distribucion, fecha_vencimiento) "
                   + "VALUES (?, (SELECT id_presentacion FROM tbl_presentacion WHERE nombre_presentacion = ? LIMIT 1), ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, nombrePresentacion);
            ps.setInt(3, stock);
            ps.setInt(4, min);
            ps.setString(5, dist);
            ps.setString(6, fecha);
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar inventario: " + e.toString());
            return false;
        } finally {
            try { conexion.close(); } catch (SQLException e) { System.err.println(e); }
        }
    }
    
    // Método para Modificar (Update)
    public boolean modificar(int id, String nombre, String nombrePresentacion, int stock, int min, String dist, String fecha) {
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        
        String sql = "UPDATE tbl_inventario SET nombre_medicamento=?, "
                   + "id_presentacion=(SELECT id_presentacion FROM tbl_presentacion WHERE nombre_presentacion=? LIMIT 1), "
                   + "stock=?, nivel_minimo=?, tipo_distribucion=?, fecha_vencimiento=? "
                   + "WHERE id_inventario=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, nombrePresentacion);
            ps.setInt(3, stock);
            ps.setInt(4, min);
            ps.setString(5, dist);
            ps.setString(6, fecha);
            ps.setInt(7, id);
            
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
        String sql = "DELETE FROM tbl_inventario WHERE id_inventario=?";
        
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
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Medicamento");
        modeloTabla.addColumn("Presentación");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Mínimo");
        modeloTabla.addColumn("Distribución");
        modeloTabla.addColumn("Vencimiento");
        Conexion con = new Conexion();
        Connection conexion = con.getConnection();
        String sql = "SELECT i.id_inventario, i.nombre_medicamento, p.nombre_presentacion, i.stock, i.nivel_minimo, i.tipo_distribucion, i.fecha_vencimiento "
                   + "FROM tbl_inventario i INNER JOIN tbl_presentacion p ON i.id_presentacion = p.id_presentacion";
        try {
            java.sql.Statement st = conexion.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id_inventario");
                fila[1] = rs.getString("nombre_medicamento");
                fila[2] = rs.getString("nombre_presentacion");
                fila[3] = rs.getInt("stock");
                fila[4] = rs.getInt("nivel_minimo");
                fila[5] = rs.getString("tipo_distribucion");
                fila[6] = rs.getDate("fecha_vencimiento");
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) { System.err.println(e); }
        return modeloTabla;
    }
    
}
