package co.unicauca.sistemamedicion.servidor.infra;

import co.unicauca.sistemamedicion.commons.infra.JsonError;
import co.unicauca.sistemamedicion.commons.infra.Protocolo;
import co.unicauca.sistemamedicion.commons.infra.Utilidades;
import co.unicauca.sistemamedicion.comun.dominio.Disparador;
import co.unicauca.sistemamedicion.comun.dominio.Elemento;
import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;
import co.unicauca.sistemamedicion.dominio.FabricaItemMedicion;
import co.unicauca.sistemamedicion.dominio.IitemMedicion;
import co.unicauca.sistemamedicion.servidor.acceso.IClienteItemMedicionRepositorio;
import co.unicauca.sistemamedicion.servidor.servicio.ServicioItemMedicion;
import co.unicauca.sistemamedicion.servidor.servicio.ServicioMedicion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Servidor Socket que está escuchando permanentemente solicitudes de los
 * clientes. Cada solicitud la atiende en un hilo de ejecución
 *
 * @author Libardo, Julio
 */
public class SistemaMedicionServidorSocket implements Runnable {

    /**
     * Servicio de disparador
     */
    private final ServicioMedicion servicioMedicion;
    Disparador objDispador;
    /**
     * Servicio de clientes
     */
    private final ServicioItemMedicion servicioItem;
    /**
     * Server Socket
     */
    private static ServerSocket ssock;
    /**
     * Socket por donde se hace la petición/respuesta
     */
    private static Socket socket;
    /**
     * Permite leer un flujo de datos del socket
     */
    private Scanner input;
    /**
     * Permite escribir un flujo de datos del scoket
     */
    private PrintStream output;
    /**
     * Puerto por donde escucha el server socket
     */
    private static final int PORT = Integer.parseInt(Utilidades.loadProperty("server.port"));

    /**
     * Constructor
     */
    public SistemaMedicionServidorSocket() {
        // Se hace la inyección de dependencia
        IClienteItemMedicionRepositorio repositorio = FabricaItemMedicion.getInstance().obtenerRepositorio();
        IitemMedicion itemMedicion = FabricaItemMedicion.getInstance().crearItemMedicion();
        servicioItem = new ServicioItemMedicion(repositorio,itemMedicion);
        objDispador = new Disparador();
        servicioMedicion = new ServicioMedicion(objDispador);
    }
    /**
     * Arranca el servidor y hace la estructura completa
     */
    public void start() {
        openPort();
        while (true) {
            waitToClient(); //espera que el cliente haga una petición 
            throwThread(); //Crea un hilo por cada petición del cliente
        }
    }
    /**
     * Lanza el hilo
     */
    private static void throwThread() {
        new Thread(new SistemaMedicionServidorSocket()).start();
    }
    /**
     * Instancia el server socket y abre el puerto respectivo
     */
    private static void openPort() {
        try {
            ssock = new ServerSocket(PORT);
            Logger.getLogger("Server").log(Level.INFO, "Servidor iniciado, escuchando por el puerto {0}", PORT);
        } catch (IOException ex) {
            Logger.getLogger(SistemaMedicionServidorSocket.class.getName()).log(Level.SEVERE, "Error del server socket al abrir el puerto", ex);
        }
    }
    /**
     * Espera que el cliente se conecta y le devuelve un socket
     */
    private static void waitToClient() {
        try {
            socket = ssock.accept();
            Logger.getLogger("Socket").log(Level.INFO, "Socket conectado");
        } catch (IOException ex) {
            Logger.getLogger(SistemaMedicionServidorSocket.class.getName()).log(Level.SEVERE, "Eror al abrir un socket", ex);
        }
    }
    /**
     * Cuerpo del hilo
     */
    @Override
    public void run() {
        try {
            createStreams();
            readStream();
            closeStream();
        } catch (IOException ex) {
            Logger.getLogger(SistemaMedicionServidorSocket.class.getName()).log(Level.SEVERE, "Eror al leer el flujo", ex);
        }
    }
    /**
     * Crea los flujos con el socket
     *
     * @throws IOException
     */
    private void createStreams() throws IOException {
        output = new PrintStream(socket.getOutputStream());
        input = new Scanner(socket.getInputStream());
    }
    /**
     * Lee el flujo del socket
     */
    private void readStream() {
        if (input.hasNextLine()) {
            // Extrae el flujo que envió la aplicación cliente
            String request = input.nextLine();
            processRequest(request);

        } else {
            output.flush();
            String errorJson = generateErrorJson();
            output.println(errorJson);
        }
    }
    /**
     * Procesar la solicitud que proviene de la aplicación cliente
     *
     * @param requestJson petición que proviene del cliente socket en formato
     * json que viene de esta manera:
     * "{"resource":"disparador","action":"start","parameters":"
     */
    private void processRequest(String requestJson) {
        // Convertir la solicitud a objeto Protocol para poderlo procesar
        Gson gson = new Gson();
        Protocolo protocolRequest = gson.fromJson(requestJson, Protocolo.class);

        switch (protocolRequest.getResource()) {
            case "cliente":
                if (protocolRequest.getAction().equals("start")) {
                    // Activar acciones de los dispositivos del sistema 
                    processDeteccionElemento(protocolRequest);
                }
                if (protocolRequest.getAction().equals("post")) {
                    processRecoleccionDatos(protocolRequest);
                }
                if (protocolRequest.getAction().equals("get")) {
                    // Obtener un elemento    
                    processObtenerItemMedicion(protocolRequest);
                }
                break;
        }

    }
        
    /**
     *Proce la solicitud de deteccion elemento.  
     * @param protocolRequest 
     */
    private void processDeteccionElemento(Protocolo protocolRequest){
        objDispador.setPeticion(protocolRequest.getAction());
        String peticion = servicioMedicion.deteccionElemento(objDispador.getPeticion());
        if (peticion.equals("datos")) {
            output.println(peticion);
        }
        
    }
    private void processRecoleccionDatos(Protocolo protocolRequest){
        LataCerveza cerveza = new LataCerveza(); 
        cerveza.setAltura(Float.parseFloat(protocolRequest.getParameters().get(0).getValue()));
        cerveza.setAncho(Float.parseFloat(protocolRequest.getParameters().get(1).getValue()));
        cerveza.setPeso(Float.parseFloat(protocolRequest.getParameters().get(2).getValue()));
        
        cerveza = servicioItem.procesoMedicion(cerveza);
        String respuesta = objectToJSON(cerveza);
        output.println(respuesta);
        
    }
    private void processObtenerItemMedicion(Protocolo protocolRequest) {
        // Extraer la referencia del primer parámetro
        String ref = protocolRequest.getParameters().get(0).getValue();
        Elemento elemento = servicioItem.obtenerItemMedicion(ref);
        if (elemento == null) {
            String errorJson = generateNotFoundErrorJson();
            output.println(errorJson);
        } else {
            output.println(objectToJSON((LataCerveza) elemento));
        }
    }
    /**
     * Genera un ErrorJson de cliente no encontrado
     *
     * @return error en formato json
     */
    private String generateNotFoundErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("404");
        error.setError("NOT_FOUND");
        error.setMessage("Error, elemento inexistente");
        errors.add(error);
        Gson gson = new Gson();
        String errorsJson = gson.toJson(errors);
        return errorsJson;
    }

    /**
     * Genera un ErrorJson genérico
     *
     * @return error en formato json
     */
    private String generateErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("400");
        error.setError("BAD_REQUEST");
        error.setMessage("Error en la solicitud");
        errors.add(error);

        Gson gson = new Gson();
        String errorJson = gson.toJson(errors);

        return errorJson;
    }

    /**
     * Cierra los flujos de entrada y salida
     *
     * @throws IOException
     */
    private void closeStream() throws IOException {
        output.close();
        input.close();
        socket.close();
    }

    /**
     * Convierte el objeto LataCerveza a json para que el servidor lo envie como
     * respuesta por el socket
     *
     * @param elemento elemento de medición 
     * @return customer en formato json
     */
    private String objectToJSON(LataCerveza cerveza) {
        Gson gson = new Gson();
        String strObject = gson.toJson(cerveza);
        return strObject;
    }

}
