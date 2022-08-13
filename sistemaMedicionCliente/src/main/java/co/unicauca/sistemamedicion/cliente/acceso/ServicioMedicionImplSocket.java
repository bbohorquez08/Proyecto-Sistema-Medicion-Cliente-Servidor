package co.unicauca.sistemamedicion.cliente.acceso;

import co.unicauca.sistemamedicion.comun.dominio.Disparador;
import co.unicauca.sistemamedicion.cliente.infra.SistemaMedicionClienteSocket;
import co.unicauca.sistemamedicion.commons.infra.JsonError;
import co.unicauca.sistemamedicion.commons.infra.Protocolo;
import co.unicauca.sistemamedicion.comun.dominio.Elemento;
import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfcaicedo, yavigutierrez, elcamacho
 */
public class ServicioMedicionImplSocket implements IServicioItemMedicion {

    /**
     * El servicio utiliza un socket para comunicarse con la aplicación server
     */
    private SistemaMedicionClienteSocket miSocket;

    public ServicioMedicionImplSocket() {
        miSocket = new SistemaMedicionClienteSocket();
    }
    
    @Override
    public String deteccionElemento(Disparador disparador) throws Exception{
        String jsonResponse = null;
        String requestJson = peticionRequestJson(disparador.getPeticion());
        
        try {
            miSocket.connect();
            jsonResponse = miSocket.sendStream(requestJson);
            miSocket.closeStream();
            miSocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ServicioMedicionImplSocket.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            throw new Exception("No se pudo conectar con el servidor");
        } else {

            if (jsonResponse.contains("error")) {
                //Devolvió algún error                
                Logger.getLogger(SistemaMedicionClienteSocket.class.getName()).log(Level.INFO, jsonResponse);
                throw new Exception(extractMessages(jsonResponse));
            } else {
                //Agregó correctamente, devuelve una respuesta
                return jsonResponse;
            }
        }
    }
    
    @Override
    public String recoleccionDatos(LataCerveza cerveza) throws Exception {
        String jsonResponse = null;
        String requestJson = recoleccionDatosRequestJson(cerveza);
        System.out.println("resquesJson reco: " + requestJson);
        
        try {
            miSocket.connect();
            jsonResponse = miSocket.sendStream(requestJson);
            miSocket.closeStream();
            miSocket.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ServicioMedicionImplSocket.class.getName()).log(Level.SEVERE, "No hubo conexión con el servidor", ex);
        }
        if (jsonResponse == null) {
            throw new Exception("No se pudo conectar con el servidor");
        } else {

            if (jsonResponse.contains("error")) {
                //Devolvió algún error                
                Logger.getLogger(SistemaMedicionClienteSocket.class.getName()).log(Level.INFO, jsonResponse);
                throw new Exception(extractMessages(jsonResponse));
            } else {
                //Agregó correctamente, devuelve una respuesta
                return jsonResponse;
            }
        }
    }
    
    
    @Override
    public Elemento obtenerItemMedicion(String ref) {
        return null; 
    }
    /**
    * Crea una solicitud json para ser enviada por el socket
    *
    *
    * @param peticion enviamos la petición de cliente a servidor de iniciar acciones 
    * @return solicitud de consulta del cliente en formato Json, algo como:
    * {"resource":"disparador","action":"start"}
    */
    private String peticionRequestJson(String peticion) {
        Protocolo protocolo = new Protocolo();
        protocolo.setResource("cliente");
        protocolo.setAction(peticion);

        Gson gson = new Gson();
        String requestJson = gson.toJson(protocolo);
        System.out.println("requestJson: " + requestJson);

        return requestJson;
    }
    private String recoleccionDatosRequestJson(LataCerveza cerveza){
            
        Protocolo protocolo = new Protocolo();
        protocolo.setResource("cliente");
        protocolo.setAction("post");
        protocolo.addParameter("altura",String.valueOf(cerveza.getAltura()));
        protocolo.addParameter("ancho",String.valueOf(cerveza.getAncho()));
        protocolo.addParameter("peso",String.valueOf(cerveza.getPeso()));
        
        Gson gson = new Gson();
        String requestJson = gson.toJson(protocolo);
        System.out.println("requestJson: " + requestJson);

        return requestJson;
    }
    /**
     * Extra los mensajes de la lista de errores
     * @param jsonResponse lista de mensajes json
     * @return Mensajes de error
     */
    private String extractMessages(String jsonResponse) {
        JsonError[] errors = jsonToErrors(jsonResponse);
        String msjs = "";
        for (JsonError error : errors) {
            msjs += error.getMessage();
        }
        return msjs;
    }
    /**
    * Convierte el jsonError a un array de objetos jsonError
    *
    * @param jsonError
    * @return objeto MyError
    */
    private JsonError[] jsonToErrors(String jsonError) {
        Gson gson = new Gson();
        JsonError[] error = gson.fromJson(jsonError, JsonError[].class);
        return error;
    }

    
}
