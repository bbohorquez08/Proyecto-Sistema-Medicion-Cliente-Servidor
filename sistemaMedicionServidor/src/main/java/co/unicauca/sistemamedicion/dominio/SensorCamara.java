package co.unicauca.sistemamedicion.dominio;

import co.unicauca.sistemamedicion.comun.dominio.Elemento;
import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;

/**
 *
 * @author mfcaicedo 
 */
public class SensorCamara extends Sensor{
    
    private LataCerveza cerveza;
    
    public SensorCamara(LataCerveza cerveza){
        this.cerveza = cerveza;
    }
    @Override
    public LataCerveza recoleccionDatos(LataCerveza cerveza){
        
        double ref = Math.random()*100;
        cerveza.setReferencia(String.valueOf(ref));
        cerveza.setTipo("lata");
        
        return cerveza;
    }
}
