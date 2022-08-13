package co.unicauca.sistemamedicion.servidor.servicio;
import co.unicauca.sistemamedicion.comun.dominio.Elemento;
import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;
import co.unicauca.sistemamedicion.dominio.FabricaItemMedicion;
import co.unicauca.sistemamedicion.dominio.IitemMedicion;
import co.unicauca.sistemamedicion.dominio.ItemMedicionElemento;
import co.unicauca.sistemamedicion.servidor.acceso.IClienteItemMedicionRepositorio;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de Item de Medición. Da acceso a la lógica de negocio
 *
 * @author Milthon F Caicedo
 */
public class ServicioItemMedicion {

    /**
     * Repositorio de Items de medicion
     */
    IClienteItemMedicionRepositorio objRepo;
    IitemMedicion objItemMedicion;

    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param objRepo repositorio de tipo IClienteItemMedicionRepositorio
     */
    public ServicioItemMedicion(IClienteItemMedicionRepositorio objRepo,  IitemMedicion objItemMedicion) {
        this.objRepo = objRepo;
        this.objItemMedicion = objItemMedicion;
    }
    public ServicioItemMedicion(){}   
    /**
    * Petición del disparador al detectar un elemento en el sistema. 
    * @param peticion petición hecha por el disparador. 
    * @return 
    */
    public String respuestaIniciar(String peticion){
        if (peticion.equals("start")) {
            return "datos";
        }
        return "error";
    }
    /**
     * Proceso de medición
     * @param cerveza
     * @return 
     */
    public LataCerveza procesoMedicion(LataCerveza cerveza){
        cerveza = objItemMedicion.procesarMedicion(cerveza);
        return cerveza;
    }
     /**
     * Buscar un Elemento de Medición
     *
     * @param ref referencia del elemento que entra al sistema
     * @return objeto tipo Elemento
     */
    public Elemento obtenerItemMedicion(String ref) {
        return objRepo.obtenerItemMedicion(ref);
    }
    

}
