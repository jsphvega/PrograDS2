package frameworkJava.logicaDeNegocio;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Clase que se encarga de crear un documento cumpliendo con los principios de
 * diseño, ya que se encarga de crear el tipo de documento por medio de un
 * string
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public class FabricaDocumento {

    /**
     * Metodo que se encarga de crear el ripo de documento requerido por el
     * usuario
     * 
     * @param pTipoDoc
     *            El tipo de documento requerido
     * @param pConsultas
     *            Las consultas registradas
     * @throws ClassNotFoundException
     */
    private static String PAQUETE = "frameworkJava.logicaDeNegocio.";
    private Class documento;

    public void crearDocumento(String pTipoDoc, ArrayList<Consulta> pConsultas) {
	try {
	    // Nombre del documento
	    String nombreClase = PAQUETE + pTipoDoc;
	    documento = Class.forName(nombreClase);

	    // Se crea la instancia
	    Object docInstance = documento.newInstance();

	    // Se declaran los metodos que tendrá
	    Method setNombreArchivoMetodo = documento.getMethod("setNombreArchivo", String.class);
	    Method crearDocumentoMetodo = documento.getMethod("crearDocumento", null);
	    Method escribirEnDocumentoMetodo = documento.getMethod("escribirEnDocumento", ArrayList.class);

	    // Se invocan a los metodos
	    setNombreArchivoMetodo.invoke(docInstance, "");
	    crearDocumentoMetodo.invoke(docInstance, null);
	    escribirEnDocumentoMetodo.invoke(docInstance, pConsultas);

	} catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InstantiationException
		| InvocationTargetException | ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

}