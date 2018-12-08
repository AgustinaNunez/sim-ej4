/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio4.interfaces;

/**
 *
 * @author Agustina
 */
public class Interfaz {

    public static final java.awt.Color COLOR_ROJO = new java.awt.Color(198, 40, 40);
    public static final java.awt.Color COLOR_VERDE = new java.awt.Color(46, 125, 50);
    
    public static int validarCampoNumericoTxt(javax.swing.JTextField txt) {
        int n = -1;
        try {
            n = Integer.parseInt(txt.getText());
        } catch (NumberFormatException e) {
            return n;
        }
        return n;
    }
}
