/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.Inventario;
import vista.FrmInventario;

/**
 *
 * @author roble
 */
public class CtrlInventario implements ActionListener {

    private Inventario mod;
    private FrmInventario view;
    private int idSeleccionado = 0;

    public CtrlInventario(Inventario mod, FrmInventario view) {
        this.mod = mod;
        this.view = view;

        this.view.InventarioGuardar.addActionListener(this);
        this.view.InventarioModificar.addActionListener(this);
        this.view.InventarioEliminar.addActionListener(this);
        this.view.InventarioNuevo.addActionListener(this);

        this.view.tblInventario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = view.tblInventario.getSelectedRow();
                if (fila >= 0) {
                    idSeleccionado = Integer.parseInt(view.tblInventario.getValueAt(fila, 0).toString());
                    view.txtMedicamento.setText(view.tblInventario.getValueAt(fila, 1).toString());
                    view.cbxPresentacion.setSelectedItem(view.tblInventario.getValueAt(fila, 2).toString());
                    view.txtStock.setText(view.tblInventario.getValueAt(fila, 3).toString());
                    view.txtMinimo.setText(view.tblInventario.getValueAt(fila, 4).toString());
                    view.cbxDistribucion.setSelectedItem(view.tblInventario.getValueAt(fila, 5).toString());
                    view.txtVencimiento.setText(view.tblInventario.getValueAt(fila, 6).toString());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.InventarioGuardar) {
            ejecutarAccion("guardar");
        }
        if (e.getSource() == view.InventarioModificar) {
            ejecutarAccion("modificar");
        }
        if (e.getSource() == view.InventarioEliminar) {
            if (idSeleccionado != 0 && mod.eliminar(idSeleccionado)) {
                limpiarYCargar();
            }
        }
        if (e.getSource() == view.InventarioNuevo) {
            limpiarCampos();
        }
    }

    private void ejecutarAccion(String tipo) {
        try {
            String nom = view.txtMedicamento.getText();
            String pres = view.cbxPresentacion.getSelectedItem().toString();
            int st = Integer.parseInt(view.txtStock.getText());
            int min = Integer.parseInt(view.txtMinimo.getText());
            String dist = view.cbxDistribucion.getSelectedItem().toString();
            String fec = view.txtVencimiento.getText();
            
            java.time.LocalDate fechaVencimiento;
            try {
                // Esto fuerza a que el usuario escriba YYYY-MM-DD
                fechaVencimiento = java.time.LocalDate.parse(fec); 
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Usa el formato con guiones: YYYY-MM-DD");
                return; // Detenemos la ejecución aquí
            }
            
            // Validamos que la fecha no sea en el pasado
            if (fechaVencimiento.isBefore(java.time.LocalDate.now())) {
                JOptionPane.showMessageDialog(null, "No puedes registrar un medicamento que ya está caducado.");
                return; // Detenemos la ejecución aquí
            }

            boolean exito = tipo.equals("guardar") ? 
                mod.guardar(nom, pres, st, min, dist, fec) : 
                mod.modificar(idSeleccionado, nom, pres, st, min, dist, fec);

            if (exito) {
                JOptionPane.showMessageDialog(null, "Operación exitosa");
                limpiarYCargar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en los datos");
        }
    }

    public void limpiarYCargar() {
        limpiarCampos();
        view.tblInventario.setModel(mod.cargarTabla());
        view.resaltarStockBajo();
    }

    private void limpiarCampos() {
        view.txtMedicamento.setText("");
        view.txtStock.setText("");
        view.txtMinimo.setText("");
        view.txtVencimiento.setText("");
        view.cbxPresentacion.setSelectedIndex(0);
        view.cbxDistribucion.setSelectedIndex(0);
        idSeleccionado = 0;
    }
}
