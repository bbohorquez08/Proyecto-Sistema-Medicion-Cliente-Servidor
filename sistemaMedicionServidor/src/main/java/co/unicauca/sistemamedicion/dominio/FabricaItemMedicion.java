package co.unicauca.sistemamedicion.dominio;

import co.unicauca.sistemamedicion.servidor.acceso.ClienteItemMedicionRepositorioImplArchivos;
import co.unicauca.sistemamedicion.commons.infra.Utilidades;
import co.unicauca.sistemamedicion.servidor.acceso.IClienteItemMedicionRepositorio;

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
     * Métodos
     */
    public IitemMedicion crearItemMedicion(){
        String type = Utilidades.loadProperty("cliente.ItemMedicion");
        if (type.isEmpty()) {
            type = "default";
        }
        IitemMedicion objItemMedicion = null;
        switch (type) {
            case "medidorCerveza":
                objItemMedicion = new ItemMedicionElemento();
                break;
        }
        return objItemMedicion;
    }
    /**
     * 
     * @return 
     */
    public IClienteItemMedicionRepositorio obtenerRepositorio(){
        String type = Utilidades.loadProperty("cliente.repositorio");
        if (type.isEmpty()) {
            type = "default";
        }
        IClienteItemMedicionRepositorio objResult = null;

        switch (type) {
            case "default":
                objResult = new ClienteItemMedicionRepositorioImplArchivos();
                break;
        }

        return objResult;
    }
}
