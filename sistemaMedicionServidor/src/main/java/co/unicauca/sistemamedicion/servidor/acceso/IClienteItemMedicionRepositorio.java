package co.unicauca.sistemamedicion.servidor.acceso;

import co.unicauca.sistemamedicion.comun.dominio.Elemento;

/**
 *
 * @author Milthon F Caicedo 
 */
public interface IClienteItemMedicionRepositorio {
    
    /**
     * Métodos 
     */
    public Elemento obtenerItemMedicion(String referencia);
//    public String enviarAlerta(String alerta);
    
}
