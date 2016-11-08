package frameworkJava.logicaDeIntegracion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.RetrieveAndRank;

import frameworkJava.dto.DTOEntrenamiento;

/**
 * Clase que se encarga de interactuar con el servicio RetrieveAndRank para
 * indexar y consultar documentos
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public class Conversacion implements ConexionWatson {

    private static String USUARIO = "6cb678e6-bbf7-4877-8899-72b8b4394105";
    private static String CONTRASENA = "x4RGJESaGIOp";
    private static String IDCLUSTER = "sc9343cef3_c06b_41b3_a825_d34af6cb1861";

    private static SolrClient cliente;
    private static Conversacion instance;
    private RetrieveAndRank servicio;

    /**
     * Constructor de la clase que inicializa el servicio y carga el contador
     */
    private Conversacion() {
	autenticarServicio();
    }

    /**
     * clase que se encarga de instanciar la clase conversacion y solo mantener
     * una instancia
     * 
     * @return la clase instanciada
     */
    public static Conversacion getInstance() {
	// Condicion que revisa si esta instanciada o no
	if (instance == null)
	    instance = new Conversacion();

	// Retorna la clase
	return instance;
    }

    /**
     * Metodo que obtiene el ID de documentos agregados
     */
    private String asignarID() {
	Calendar calendario = new GregorianCalendar();

	// Extrae cada parte de la fecha del sistema
	String[] ID = new String[6];
	ID[0] = String.valueOf(calendario.get(Calendar.YEAR));
	ID[1] = String.valueOf(calendario.get(Calendar.MONTH));
	ID[2] = String.valueOf(calendario.get(Calendar.DAY_OF_YEAR));
	ID[3] = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
	ID[4] = String.valueOf(calendario.get(Calendar.MINUTE));
	ID[5] = String.valueOf(calendario.get(Calendar.SECOND));

	String printID = "";
	for (int i = 0; i < ID.length; i++)
	    printID += (ID[i].length() < 2 ? "0" : "") + ID[i] + "";

	return printID;
    }

    /**
     * Metodo que se encarga de crear la instancia del servicio con sus
     * respectivas credenciales
     */
    public void autenticarServicio() {
	servicio = new RetrieveAndRank();
	servicio.setUsernameAndPassword(USUARIO, CONTRASENA);
    }

    /**
     * Metodo que se encarga de almacenar un cliente de acuerdo a las
     * configuraciones HTTP
     * 
     * @return un cliente de tipo SolrClient con las configuraciones HTTP
     */
    private SolrClient getCliente() {
	if (cliente == null) {
	    ClienteHttp.setCredenciales(USUARIO, CONTRASENA, IDCLUSTER);

	    cliente = ClienteHttp.getCliente(servicio);
	}
	return cliente;
    }

    /**
     * Metodo que se encarga de indexar el documento nuavo a la coleccion del
     * servicio
     * 
     * @param pCurso
     *            El nombre del curso al que pertenece el tema
     * @param pCategoria
     *            La categoria asociada al curso
     * @param pDescripcion
     *            La descripcion del titulo consultado
     * @param ptitulo
     *            Los sinonimos asociados a la descripcion
     */
    private void indexarInformacion(String pCategoria, String pDescripcion, String ptitulo) {
	try {
	    // Atributo que almacena la informacion de un documento
	    SolrInputDocument sDocumento = new SolrInputDocument();
	    sDocumento.addField("id", asignarID());
	    sDocumento.addField("title", ptitulo);
	    sDocumento.addField("category", pCategoria);
	    sDocumento.addField("description", pDescripcion);

	    // Agrega la informaciona la coleccion e incementa el contador
	    cliente.add("ColeccionDS", sDocumento);
	} catch (SolrServerException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Metodo que se encarga de añadir la informacion segun el usuario
     * 
     * @param pCurso
     *            El nombre del curso al que pertenece el tema
     * @param pCategoria
     *            La categoria asociada al curso
     * @param pDescripcion
     *            La descripcion del titulo consultado
     * @param ptitulo
     *            Los sinonimos asociados a la descripcion
     * @return Un valor booleano que notifica si se realizo la indexacion
     */
    public void agregarInformacion(String pCategoria, String pDescripcion, String ptitulo) {
	// Validacion de codigo
	try {
	    // Atributo que almacena un cliente de acuerdo a las configuraciones
	    // HTTP
	    cliente = getCliente();

	    // Se modifica el contador y se indexan los valores
	    indexarInformacion(pCategoria, pDescripcion, ptitulo);

	    // Se guardan los cambios realizados
	    cliente.commit("ColeccionDS");
	} catch (Exception e) {

	}
    }

    /**
     * Metodo que realiza la consulta en el servicio para obtener la informacion
     * requerida
     * 
     * @param pPalabra
     *            La palabra requerida para la consulta
     * @return Una lista de respuestas relacionadas a la consulta
     */
    private SolrDocumentList nuevaConsulta(String pPalabra) {
	// Validacion de codigo
	try {
	    // Atributo que almacena un cliente de acuerdo a las configuraciones
	    // HTTP
	    cliente = getCliente();

	    // Atributos que almacenan la consulta realizada y la respuesta
	    // obtenida de la informaci�n almacenada en el servicio
	    SolrQuery sConsulta = new SolrQuery(pPalabra);
	    QueryResponse sRespuesta = cliente.query("ColeccionDS", sConsulta);

	    // Retorna lo obtenido en la consulta
	    return sRespuesta.getResults();
	} catch (Exception e) {
	    return null;
	}
    }

    /**
     * Metodo que realiza las consultas necesarias para obtener la informacion
     * de acuerdo a un grupo de palabras
     * 
     * @param pConsulta
     *            El grupo de palabras a buscar
     * @return Una lista con las respuestas correctas
     */

    public ArrayList<DTOEntrenamiento> consultarInformacion(String pConsulta) {
	// Atributo que almacena la lista de las respuestas
	ArrayList<DTOEntrenamiento> sRespuestas = new ArrayList<DTOEntrenamiento>();

	// Atrtibutos que almacena la respuesta a la consulta realizada al
	// servicio y la cantidad que se obtuvieron
	SolrDocumentList sRespuesta = nuevaConsulta(pConsulta);

	if (sRespuesta != null) {
	    long pos = sRespuesta.getNumFound();

	    // Ciclo que instancia la clase Informacion y la agrega a la lista
	    // de respuestas
	    for (int i = 0; i < pos; i++) {
		DTOEntrenamiento dto = new DTOEntrenamiento();
		dto.setCategoria(sRespuesta.get(i).getFirstValue("category").toString());
		dto.setDescripcion(sRespuesta.get(i).getFirstValue("description").toString());
		dto.setTitulo(sRespuesta.get(i).getFirstValue("title").toString());

		sRespuestas.add(dto);
	    }
	}
	// Retorna la lista con las consultas respectivas
	return sRespuestas;
    }

}