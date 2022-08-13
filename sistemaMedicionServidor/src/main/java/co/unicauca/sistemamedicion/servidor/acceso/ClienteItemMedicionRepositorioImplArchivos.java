package co.unicauca.sistemamedicion.servidor.acceso;

import co.unicauca.sistemamedicion.comun.dominio.Elemento;

/**
 *
 * @author Milthon F Caicedo Jurado 
 */
public class ClienteItemMedicionRepositorioImplArchivos implements IClienteItemMedicionRepositorio{

    @Override
    public Elemento obtenerItemMedicion(String referencia) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Devuelve una respuesta a la alerta generada por el cliente. 
     * @param alerta alerta que ha recibido desde el cliente 
     * @return respuesta de que si recibio la alerta para realizar acciones
     */
//    @Override
//    public String enviarAlerta(String alerta) {
//        if(alerta.equals("alerta")){
//            String respuesta = "correcto";
//            return respuesta;
//        }
//      return "incorrecto";
//    }
    
}
