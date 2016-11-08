package frameworkJava.logicaDeNegocio;

import java.util.ArrayList;

import frameworkJava.dto.DTOEntrenamiento;
import frameworkJava.logicaDeIntegracion.Conversacion;

/**
 * Clase que maneja todos los procesos relacionados a la consulta
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public class Consulta implements IConsulta {

    private ArrayList<String> respuestas;
    private String pregunta;

    /**
     * Metodo costructor de la clase
     * 
     * @param pPregunta
     *            Registra la pregunta dicha por el usuario
     */
    public Consulta(String pPregunta) {
	setPregunta(pPregunta);
	respuestas = new ArrayList<>();
    }

    /**
     * Metodo que devuelve la pregunta
     * 
     * @return la pregunta
     */
    public String getPregunta() {
	return pregunta;
    }

    /**
     * Metodo que devuelve todas las respuestas
     * 
     * @return las respuestas
     */
    public ArrayList<String> getRespuestas() {
	return respuestas;
    }

    /**
     * Metodo que asigna en el atributo la pregunta segun el usuario
     * 
     * @param pPregunta
     *            La pregunta escrita por el usuario
     */
    public void setPregunta(String pPregunta) {
	pregunta = pPregunta;
    }

    /**
     * Metodo que imprime las respuestas correctas
     * 
     * @return Las respuestas correctas
     */
    public String imprimirRespuestas() {
	String resultado;
	resultado = getPregunta() + " \n";
	for (int i = 0; i < respuestas.size(); i++) {
	    resultado = resultado + respuestas.get(i) + " \n";
	}
	return resultado;
    }
    
    /**
     * Metodo que genera las respuestas de acuerdo a la clase conversacion
     */
    public void generarRespuestas(){
	//Instancia la clase conversacion
	Conversacion sConversacion;
	sConversacion = Conversacion.getInstance();
	
	//Conexion a la base de datos
	//Aqui
	
	//Realiza la consulta 
	ArrayList<DTOEntrenamiento> pConsulta = sConversacion.consultarInformacion(pregunta);
	for(int i=0;i<pConsulta.size();i++)
	    agregarRespuesta(pConsulta.get(i).getDescripcion());
    }

    /**
     * Metodo que agrega la respuesta al arreglo
     * 
     * @param pRespuesta
     *            La nueva respuesta por almacenar
     */
    private void agregarRespuesta(String pRespuesta) {
	respuestas.add(pRespuesta);
    }
}