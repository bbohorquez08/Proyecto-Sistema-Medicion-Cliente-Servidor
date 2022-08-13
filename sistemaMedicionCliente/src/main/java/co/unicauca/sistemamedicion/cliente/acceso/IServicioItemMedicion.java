
package co.unicauca.sistemamedicion.cliente.acceso;

import co.unicauca.sistemamedicion.comun.dominio.Disparador;
import co.unicauca.sistemamedicion.comun.dominio.Elemento;
import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;

/**
 *
 * @author mfcaicedo
 */
public interface IServicioItemMedicion {
    
    /**
     * Enviar una alerta al servidor indicando que llegó un elemento al sistema. 
     * @param alerta alerta que se envía al servidor
     * @return String indicando que no hubo error en la conexión. 
     */
    public String deteccionElemento(Disparador disparador)throws Exception;
    public Elemento obtenerItemMedicion(String ref);
    public String recoleccionDatos(LataCerveza cerveza)throws Exception;
    
}
