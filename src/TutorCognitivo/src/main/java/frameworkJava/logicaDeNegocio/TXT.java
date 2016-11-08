package frameworkJava.logicaDeNegocio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Clase que se encarga de crear un documento TXT de acuerdo a un texto dado
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public class TXT extends Documento {

    private File archivo;
    public static String pathTXT;

    /**
     * Metodo que crea un archivo txt
     */
    public void crearDocumento() {
	pathTXT = retornarRuta();

	if (pathTXT != "") {
	    archivo = new File(pathTXT + ".txt");
	}
    }

    /**
     * Metodo que toma un arraylist y transcribe todos los elementos en un
     * archivo
     */
    public boolean escribirEnDocumento(ArrayList<Consulta> pTexto) {
	// atributos que mantienen y crean el documento
	String temporal;
	String[] temp;
	BufferedWriter output = null;

	try {
	    output = new BufferedWriter(new FileWriter(archivo));

	    // Ciclo para añadir el texto en la variable para ejecutar el
	    // documento
	    for (int i = 0; i < pTexto.size(); i++) {
		// porque o sino no reconoce el salto de linea
		temp = pTexto.get(i).imprimirRespuestas().split("\n");

		for (int j = 0; j < temp.length; j++) {
		    temporal = temp[j];
		    output.write(temporal);
		    output.newLine();
		}

		output.newLine();
	    }

	    output.close();
	    return true;
	} catch (IOException e) {
	    return false;
	}
    }
}