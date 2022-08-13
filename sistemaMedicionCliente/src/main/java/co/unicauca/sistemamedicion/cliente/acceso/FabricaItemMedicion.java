package co.unicauca.sistemamedicion.cliente.acceso;

import co.unicauca.sistemamedicion.commons.infra.Utilidades;

/**
 *
 * @author Milthon F Caicedo 
 */
public class FabricaItemMedicion {
    
    private static FabricaItemMedicion instance;
    /**
     * Constructor 
     */
    private FabricaItemMedicion() {
        
    }
    /**
     * Singleton 
     */
    public static FabricaItemMedicion getInstance() {

        if (instance == null) {
            instance = new FabricaItemMedicion();
        }
        return instance;
    }
    /**
      Método que crea una instancia concreta de la jerarquia ICustomerService
     *
     * @return una clase hija de la abstracción IRepositorioClientes
     */
    public IServicioItemMedicion obtenerItemMedicion(){
        
        IServicioItemMedicion objResult = null; 
        String tipo = Utilidades.loadProperty("customer.service");
        
        switch (tipo){
            case "default":
                objResult = new ServicioMedicionImplSocket();
                break;
        }
        return objResult; 
    }
    
}
