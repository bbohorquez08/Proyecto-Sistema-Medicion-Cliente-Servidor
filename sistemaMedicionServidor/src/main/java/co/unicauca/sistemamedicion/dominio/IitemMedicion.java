/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.sistemamedicion.dominio;

import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;
import java.util.ArrayList;

/**
 *
 * @author Miltohn F Caicedo 
 */
public interface IitemMedicion {
    
    /**
     * Métodos 
     */
    public LataCerveza procesarMedicion(LataCerveza cerveza);
    public Sensor leerSensor(Sensor sensor);
    public void almacenarResultados();
    public ArrayList<Float> compararValores();
    public void ejecutarAcciones();
    public LataCerveza recolectarDatos(LataCerveza cerveza);
    public void analisisDatos(LataCerveza cerveza);
    public LataCerveza clasificarElemento(LataCerveza cerveza, ArrayList<Float> lstComparaciones);
    
}
