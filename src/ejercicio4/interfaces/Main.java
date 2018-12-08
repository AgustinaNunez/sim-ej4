/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio4.interfaces;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Agustina
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Inicio ventana = new Inicio();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

}
