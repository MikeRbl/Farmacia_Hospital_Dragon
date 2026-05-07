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
import modelo.Presentacion;
import vista.FrmInventario;

/**
 *
 * @author roble
 */
public class CtrlPresentacion implements ActionListener {

    private Presentacion mod;
    private FrmInventario view;
    private int idSeleccionado = 0;

    public CtrlPresentacion(Presentacion mod, FrmInventario view) {
        this.mod = mod;
        this.view = view;
        
        // Ponemos al controlador a escuchar los botones
        this.view.PresentacionGuardar.addActionListener(this);
        this.view.PresentacionModificar.addActionListener(this);
        this.view.PresentacionEliminar.addActionListener(this);
        this.view.PresentacionNuevo.addActionListener(this);
        
        // Escuchar clics en la tabla
        this.view.tblPresentaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = view.tblPresentaciones.getSelectedRow();
                if (fila >= 0) {
                    idSeleccionado = Integer.parseInt(view.tblPresentaciones.getValueAt(fila, 0).toString());
                    view.txtNombrePresentacion.setText(view.tblPresentaciones.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.PresentacionGuardar) {
            String nombre = view.txtNombrePresentacion.getText();
            if (!nombre.isEmpty() && mod.guardar(nombre)) {
                limpiarYCargar();
            }
        }
        
        if (e.getSource() == view.PresentacionModificar) {
            if (idSeleccionado != 0 && mod.modificar(idSeleccionado, view.txtNombrePresentacion.getText())) {
                limpiarYCargar();
            }
        }
        
        if (e.getSource() == view.PresentacionEliminar) {
            if (idSeleccionado != 0 && mod.eliminar(idSeleccionado)) {
                limpiarYCargar();
            }
        }
        
        if (e.getSource() == view.PresentacionNuevo) {
            view.txtNombrePresentacion.setText("");
            idSeleccionado = 0;
        }
    }

    public void limpiarYCargar() {
        view.txtNombrePresentacion.setText("");
        idSeleccionado = 0;
        view.tblPresentaciones.setModel(mod.cargarTabla());
        view.cbxPresentacion.setModel(mod.cargarCombo());
    }
}
    

