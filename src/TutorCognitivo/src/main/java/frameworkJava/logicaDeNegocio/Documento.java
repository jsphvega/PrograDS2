package frameworkJava.logicaDeNegocio;

import java.io.File;
import java.util.*;

import javax.swing.JFileChooser;

/**
 * Esta clase permite gestionar la creacion de documentos de acuerdo al tipo de
 * archivo que desee usar
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public abstract class Documento {

    private String nombreArchivo;

    /**
     * Metodo que crea un archivo txt
     */
    public abstract void crearDocumento();

    /**
     * Metodo que toma un arraylist y transcribe todos los elementos en un
     * archivo
     */
    public abstract boolean escribirEnDocumento(ArrayList<Consulta> pTexto);

    /**
     * Metodo que se encarga de añadir el nombre del documento a la clase para
     * que se reconozca
     * 
     * @param pNombre
     *            El nombre del Documento
     */
    public void setNombreArchivo(String pNombre) {
	nombreArchivo = pNombre;
    }

    /**
     * Metodo que se encarga de retornar el nombre del archivo a sus clases
     * hijas
     */
    public String getNombreArchivo() {
	return nombreArchivo;
    }

    /**
     * Metodo que se encarga de encinrrar la ruta requerrida por el usuario
     * 
     * @return La ruta seleccionada
     */
    public String retornarRuta() {
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setSelectedFile(new File(getNombreArchivo()));
	int seleccion = fileChooser.showSaveDialog(null);

	// Comprueba si ha presionado el boton de aceptar
	if (seleccion == JFileChooser.APPROVE_OPTION) {

	    // obtenemos el path del archivo a guardar
	    File JFC = fileChooser.getSelectedFile();
	    return JFC.getAbsolutePath();
	}

	return "";
    }

}