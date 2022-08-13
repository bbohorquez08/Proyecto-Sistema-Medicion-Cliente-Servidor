/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.sistemamedicion.comun.dominio;

/**
 *
 * @author Milthon F Caicedo 
 */
public abstract class Elemento {
    
    /**
     * Atributos 
     */
    private String tipo; 
    private String estado; 
    private String referencia;
    /**
     * Constructor 
     */
    public Elemento(){}
    public Elemento(String tipo, String estado, String referencia){
        this.tipo = tipo;
        this.estado = estado; 
        this.referencia = referencia; 
    }
    /**
     * Geters and setters
     * @return 
     */
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    /**
     * Métods 
     */
    public boolean comprobarCalidad(){
        return false; 
    }
    public void obtenerDatos(){
        
    }
    
}
