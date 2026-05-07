/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package farmaciahospitaldragon;
import vista.FrmInventario;
/**
 *
 * @author roble
 */
public class FarmaciaHospitalDragon {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        vista.FrmInventario vista = new vista.FrmInventario();
        
        modelo.Presentacion modP = new modelo.Presentacion();
        modelo.Inventario modI = new modelo.Inventario();
        
        controlador.CtrlPresentacion ctrlP = new controlador.CtrlPresentacion(modP, vista);
        controlador.CtrlInventario ctrlI = new controlador.CtrlInventario(modI, vista);
        
        // Arrancamos los datos iniciales de las tablas
        ctrlP.limpiarYCargar();
        ctrlI.limpiarYCargar();
        
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
}
