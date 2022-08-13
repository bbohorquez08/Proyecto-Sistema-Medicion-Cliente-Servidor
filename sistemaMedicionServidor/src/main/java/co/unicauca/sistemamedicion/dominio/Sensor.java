/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.sistemamedicion.dominio;

import co.unicauca.sistemamedicion.comun.dominio.Elemento;
import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;

/**
 *
 * @author Milthon F Caicedo 
 */
public abstract class Sensor {
    
    /**
     * Atributos 
     */
    
    /**
     * Constructor
     */
    public Sensor(){
        
    }
    
    /**
     * Métodos
     */
    public abstract Elemento recoleccionDatos(LataCerveza cerveza);
    
}
