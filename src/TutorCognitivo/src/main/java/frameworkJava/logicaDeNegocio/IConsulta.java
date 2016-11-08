package frameworkJava.logicaDeNegocio;

import java.util.ArrayList;

/**
 * Interfaz que maneja los procesos relacionados a la consulta
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public interface IConsulta {

    // Metodo que devuelve la pregunta
    public String getPregunta();

    // Metodo que devuelve todas las respuestas
    public ArrayList<String> getRespuestas();

    // Metodo que asigna en el atributo la pregunta segun el usuario
    public void setPregunta(String pPregunta);

    // Metodo que imprime las respuestas correctas
    public String imprimirRespuestas();
}