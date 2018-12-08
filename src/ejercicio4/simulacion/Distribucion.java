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
public class Distribucion {

    public static final String[] CABECERA = {"Pasajeros", "Prob.", "Prob. Ac."};

    private static final int COL_PASAJEROS = 0,
            COL_PROB = 1,
            COL_PROB_ACUMULADA = 2;

    private final double[][] probabilidades;
    private DefaultTableModel modelo;
    private final int maxPasajerosPresentes;
    private final int minPasajerosPresentes;

    // verifica que el modelo recibido por parámetro, su sumatoria de probabilidades sea igual a 1
    public static boolean verificarModelo(DefaultTableModel modelo) {
        double suma = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            double aux;
            try {
                aux = Double.parseDouble(modelo.getValueAt(i, COL_PROB).toString());
            } catch (Exception e) {
                return false;
            }
            if (aux < 0) {
                return false;
            }
            suma += aux;
        }
        if (suma == 1.0) {
            return true;
        }
        return false;
    }

    private void setProbAc() {
        // carga de probabilidades acumuladas
        probabilidades[0][COL_PROB_ACUMULADA] = probabilidades[0][COL_PROB];
        for (int i = 1; i < probabilidades.length; i++) {
            double acum = probabilidades[i - 1][COL_PROB_ACUMULADA] + probabilidades[i][COL_PROB];
            probabilidades[i][COL_PROB_ACUMULADA] = Math.round((acum) * 100D) / 100D;
        }
    }

    // devuelve modelo con la distribucion por defecto de 64 reservaciones
    public static DefaultTableModel getDistribucionPredeterminada() {
        int maxPasajerosPresentes = 64;
        int minPasajerosPresentes = 58;
        String[] cabecera = {"Pasajeros", "Prob."};
        int rows = maxPasajerosPresentes - minPasajerosPresentes + 1;
        DefaultTableModel modelo = new DefaultTableModel(cabecera, rows);
        int aux = minPasajerosPresentes;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.setValueAt(aux++, i, COL_PASAJEROS);            
        }
        // carga de probabilidades de que se presenten X cantidad de pasajeros
        modelo.setValueAt(0.05, 0, COL_PROB);
        modelo.setValueAt(0.15, 1, COL_PROB);
        modelo.setValueAt(0.3, 2, COL_PROB);
        modelo.setValueAt(0.2, 3, COL_PROB);
        modelo.setValueAt(0.15, 4, COL_PROB);
        modelo.setValueAt(0.1, 5, COL_PROB);
        modelo.setValueAt(0.05, 6, COL_PROB);
        return modelo;
    }

    public Distribucion(DefaultTableModel modelo) {
        maxPasajerosPresentes = Integer.parseInt(modelo.getValueAt(modelo.getRowCount() - 1, COL_PASAJEROS).toString());
        minPasajerosPresentes = Integer.parseInt(modelo.getValueAt(0, COL_PASAJEROS).toString());
        int rows = maxPasajerosPresentes - minPasajerosPresentes + 1;
        probabilidades = new double[rows][CABECERA.length];
        for (int i = 0; i < modelo.getRowCount(); i++) {
            probabilidades[i][COL_PASAJEROS] = Double.parseDouble(modelo.getValueAt(i, COL_PASAJEROS).toString());
            probabilidades[i][COL_PROB] = Double.parseDouble(modelo.getValueAt(i, COL_PROB).toString());
        }
        setProbAc();
    }

    public int getMaxPasajerosPresentes() {
        return maxPasajerosPresentes;
    }

    // devuelve un DefaultTableModel con las distribuciones
    public DefaultTableModel getModeloDistribucion() {
        DefaultTableModel modelo = new DefaultTableModel(CABECERA, 0);

        Object[] row = new Object[CABECERA.length];
        for (int i = 0; i < probabilidades.length; i++) {
            row[COL_PASAJEROS] = (int) probabilidades[i][COL_PASAJEROS];
            row[COL_PROB] = probabilidades[i][COL_PROB];
            row[COL_PROB_ACUMULADA] = probabilidades[i][COL_PROB_ACUMULADA];
            modelo.addRow(row);
        }
        return modelo;
    }

    // obtiene la cantidad de pasajeros que se presentaron el día del vuelo
    public int getPasajerosPresentes(double random) {
        if (random < probabilidades[0][COL_PROB_ACUMULADA]) {
            return (int) probabilidades[0][COL_PASAJEROS];
        }
        for (int i = 1; i < probabilidades.length - 1; i++) {
            if (random < probabilidades[i][COL_PROB_ACUMULADA]) {
                return (int) probabilidades[i][COL_PASAJEROS];
            }
        }
        return (int) probabilidades[probabilidades.length - 1][COL_PASAJEROS];
    }
}
