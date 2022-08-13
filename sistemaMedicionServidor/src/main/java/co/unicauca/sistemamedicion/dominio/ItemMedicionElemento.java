package co.unicauca.sistemamedicion.dominio;

import co.unicauca.sistemamedicion.comun.dominio.LataCerveza;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Milthon F Caicedo, Brandon Nicolas Bohorquez 
 */
public class ItemMedicionElemento implements IitemMedicion {
    /**
     * Atributos 
     */
    private ArrayList<Sensor> lstSensores;
    private ArrayList<Actuador> lstActuadores;  
    private ArrayList<Float> lstValoresReales;
    private ArrayList<Float> lstValoresIdeales; 
    /**
     * Constructor
     */
    public ItemMedicionElemento(){
        lstSensores = new  ArrayList<Sensor>();
        lstActuadores = new  ArrayList<Actuador>();
        lstValoresReales = new  ArrayList<Float>();
        lstValoresIdeales = new  ArrayList<Float>();
    }
    /**
     * Getters and setters 
     * @return 
     */
    public ArrayList<Sensor> getLstSensores() {
        return lstSensores;
    }

    public void setLstSensores(ArrayList<Sensor> lstSensores) {
        this.lstSensores = lstSensores;
    }

    public ArrayList<Actuador> getLstActuadores() {
        return lstActuadores;
    }

    public void setLstActuadores(ArrayList<Actuador> lstActuadores) {
        this.lstActuadores = lstActuadores;
    }

    public ArrayList<Float> getLstValoresReales() {
        return lstValoresReales;
    }

    public void setLstValoresReales(ArrayList<Float> lstValoresReales) {
        this.lstValoresReales = lstValoresReales;
    }

    public ArrayList<Float> getLstValoresIdeales() {
        return lstValoresIdeales;
    }

    public void setLstValoresIdeales(ArrayList<Float> lstValoresIdeales) {
        this.lstValoresIdeales = lstValoresIdeales;
    }
    
    
    /**
     * Métodos
     */
    @Override
    public LataCerveza procesarMedicion(LataCerveza cerveza) {
        ArrayList<Float> lstErrores = new ArrayList<>();
        //1. Recolectar datos (agregamos el sensor y el actuador)
        cerveza = recolectarDatos(cerveza);
        //2. Analisis de datos y transformación de datos 
        analisisDatos(cerveza);
        //3. Compara los valores 
        lstErrores = compararValores();
        //4. Clasificación de los elementos 
        cerveza = clasificarElemento(cerveza, lstErrores);
        return cerveza;
    }

    @Override
    public Sensor leerSensor(Sensor sensor) {
        //se haría la lectura. 
        return sensor;
    }

    @Override
    public void almacenarResultados() {
        System.out.println("Atención: en construcción..");
    }

    @Override
    public void ejecutarAcciones() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Recolecta los datos por medio del sensor 
     * @param cerveza
     * @return 
     */
    @Override
    public LataCerveza recolectarDatos(LataCerveza cerveza) {
        SensorCamara objCamara = new SensorCamara(cerveza);
        objCamara = (SensorCamara) leerSensor(objCamara); //mirar si lo hace bien
        cerveza = objCamara.recoleccionDatos(cerveza);
        this.lstSensores.add(objCamara);
        return cerveza;
    } 
    /**
     * analis de los datos y transformación 
     * @param cerveza 
     */
    @Override
    public void analisisDatos(LataCerveza cerveza) {
        //Agregamos a la lista de valores reales la información capturada por los sensores
        //
        this.lstValoresReales.add(cerveza.getAltura());
        this.lstValoresReales.add(cerveza.getAncho());
        this.lstValoresReales.add(cerveza.getPeso());
        cargarValoresIdeales();
    }
    private void cargarValoresIdeales(){
        //Consulta al archivo 
        LataCerveza cerveza = new LataCerveza("", "", "",12.22f, 5.24f, 0.33f);
        this.lstValoresIdeales.add(cerveza.getAltura());
        this.lstValoresIdeales.add(cerveza.getAncho());
        this.lstValoresIdeales.add(cerveza.getPeso());
    }
    /**
     * Compara los valores reales y los ideales 
     * @return 
     */
    @Override
    public ArrayList<Float> compararValores() {
        ArrayList<Float> lstErrores = new ArrayList<>();
        float errorAltura = 0.0f;
        float errorAncho = 0.0f;
        float errorPeso = 0.0f;
        for (int i = 0; i<lstValoresReales.size(); i+=3) {
            //Comparo
            //Toleracia a un error de 3%
            errorAltura = Math.abs((lstValoresIdeales.get(i) - lstValoresReales.get(i))/(lstValoresIdeales.get(i)));
            errorAncho = Math.abs((lstValoresIdeales.get(i+1) - lstValoresReales.get(i+1))/(lstValoresIdeales.get(i+1)));
            errorPeso = Math.abs((lstValoresIdeales.get(i+2) - lstValoresReales.get(i+2))/(lstValoresIdeales.get(i+2)));
        }
        lstErrores.add(errorAltura);
        lstErrores.add(errorAncho);
        lstErrores.add(errorPeso);
        
        return lstErrores;
    }
    /**
    * Clasifica el elemento según valores arrojados (optimo o detectuoso)
    * @param cerveza 
    * @return  
    */
    @Override
    public LataCerveza clasificarElemento(LataCerveza cerveza, ArrayList<Float> lstComparaciones){
            
        if (lstComparaciones.get(0) <= 0.03 && lstComparaciones.get(1) <= 0.03 && lstComparaciones.get(2) <= 0.03) {
            cerveza.setEstado("optimo");
        }else{
            cerveza.setEstado("defectouso");
        }
        return cerveza;
    }

    
}
