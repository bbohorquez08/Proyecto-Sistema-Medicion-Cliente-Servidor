package co.unicauca.sistemamedicion.comun.dominio;
/**
 *
 * @author milthon F caicedo, Erika Camacho, Yazmin Gutierrez, Brandon Nicolas, Juan Medicis
 */
public class Disparador {
    private String peticion;
    
    public Disparador(){
        
    }
    public Disparador(String peticion){
        this.peticion = peticion;
    }

    public String getPeticion() {
        return peticion;
    }

    public void setPeticion(String peticion) {
        this.peticion = peticion;
    }
    
    /**
     * Detección de elemento. 
     * @param peticion 
     */
    public void deteccionElemento(String peticion){
        this.setPeticion(peticion);
    }
    
}
