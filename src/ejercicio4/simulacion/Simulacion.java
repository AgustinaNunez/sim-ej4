/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio4.simulacion;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Agustina
 */
public class Simulacion {

    public static final String[] CABECERA = {"N°", "RND", "Pasajeros", "Utilidad", "Costo", "Ut. Neta", "Ut. Neta Ac."};
    
    private static long utilidadPromedioSimulacion = 0;
    private static Distribucion distribucion;

    private static final int ASIENTOS = 60, // cantidad de asientos disponibles en un vuelo aéreo
            UTILIDAD_UNITARIA = 200, // utilidad que obtiene la empresa por cada pasajero de un vuelo
            PERDIDA_UNITARIA = 300; // pérdida que tiene la empresa por cada pasajero al que debe programarse su vuelo por haber implementado sobreventa de pasajes

    public static final int UTILIDAD_PROMEDIO_ACTUAL = (ASIENTOS - 3) * UTILIDAD_UNITARIA;

    public static long getUtilidadPromedio() {
        return utilidadPromedioSimulacion;
    }
    
    public static void cargarModeloDistribucion(DefaultTableModel modelo) {
        distribucion = new Distribucion(modelo);
    }    
    
    public static Distribucion getDistribucion() {
        return distribucion;
    }

    public static DefaultTableModel generar(int exp, int desde, int hasta) {
        DefaultTableModel modelo = new DefaultTableModel(CABECERA, 0);

        Object[] row = new Object[CABECERA.length];
        double random;
        int pasajerosPresentes, pasajerosAceptados, pasajerosRechazados, utUnit, costoUnit, utNetaUnit;
        long utNetaAc = 0;
        for (int i = 1; i <= exp; i++) {
            random = Math.round(Math.random() * 100D) / 100D;

            pasajerosPresentes = distribucion.getPasajerosPresentes(random);
            if (pasajerosPresentes > ASIENTOS) {
                pasajerosAceptados = ASIENTOS;
                pasajerosRechazados = pasajerosPresentes - ASIENTOS;
            } else {
                pasajerosAceptados = pasajerosPresentes;
                pasajerosRechazados = 0;
            }

            utUnit = pasajerosAceptados * UTILIDAD_UNITARIA;
            costoUnit = pasajerosRechazados * PERDIDA_UNITARIA;
            utNetaUnit = utUnit - costoUnit;
            utNetaAc += utNetaUnit;

            row[0] = i;
            row[1] = random;
            row[2] = pasajerosPresentes;
            row[3] = "$" + utUnit;
            row[4] = "$" + costoUnit;
            row[5] = "$" + utNetaUnit;
            row[6] = "$" + utNetaAc;

            // rango a mostrar de la simulación
            if (i >= desde && i <= hasta) {
                modelo.addRow(row);
            }
        }
        utilidadPromedioSimulacion = (long) (utNetaAc / exp);
        return modelo;
    }
}
