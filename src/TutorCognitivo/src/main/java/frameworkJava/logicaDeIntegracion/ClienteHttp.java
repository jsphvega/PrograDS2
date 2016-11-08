package frameworkJava.logicaDeIntegracion;

import java.net.URI;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import com.ibm.watson.developer_cloud.retrieve_and_rank.v1.RetrieveAndRank;

/**
 * Clase que se encarga de crear un cliente de acuerdo a las configuraciones
 * HTTP segun un usuario y una contrasena dadas por el servicio.
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public class ClienteHttp {

    private static String usuario;
    private static String contrasena;
    private static String clusterID;

    /**
     * Metodo que asigna las credenciales del servicio en caso de cambios
     * 
     * @param pUsuario
     * @param pContrasena
     * @param pClusterID
     */
    public static void setCredenciales(String pUsuario, String pContrasena, String pClusterID) {
	usuario = pUsuario;
	contrasena = pContrasena;
	clusterID = pClusterID;
    }

    /**
     * Metodo que se encarga de retornar las configuraciones de un cliente de
     * contexto HTTP segun la URI del servicio y su autenticacion
     * 
     * @param pServicio
     *            Contiene la conexion con el servicio
     * @param pURL
     * 
     * @return Las configuraciones del clente segun el servicio
     */
    public static HttpSolrClient getCliente(RetrieveAndRank pServicio) {
	// Atributo que contiene la direccion del cluster
	String pURL = pServicio.getSolrUrl(clusterID);

	// Retorna las configuraciones del clente segun el servicio
	return new HttpSolrClient(pURL, crearClienteHttp(pURL));
    }

    /**
     * Metodo que se encarga de crear el cliente segun el cluster creado
     * 
     * @param pURL
     *            La direccion del cluster
     * @return Las configuraciones del clente creadas en el servicio
     */
    private static HttpClient crearClienteHttp(String pURL) {
	// Atributo que se encarga de crear la URI segun la URL del cluster
	final URI sUri = URI.create(pURL);

	// Atributo que se encarga de almacenar las credenciales segun la URI
	// obtenida
	final BasicCredentialsProvider pCredenciales = new BasicCredentialsProvider();
	pCredenciales.setCredentials(new AuthScope(sUri.getHost(), sUri.getPort()),
		new UsernamePasswordCredentials(usuario, contrasena));

	// Atributo que se encarga de almacenar el constructor con las
	// configuraciones creadas anteriormente
	final HttpClientBuilder sConstructor = HttpClientBuilder.create().setMaxConnTotal(128).setMaxConnPerRoute(32)
		.setDefaultRequestConfig(RequestConfig.copy(RequestConfig.DEFAULT).setRedirectsEnabled(true).build())
		.setDefaultCredentialsProvider(pCredenciales).addInterceptorFirst(new Interceptor());

	// Retorna el constructor
	return sConstructor.build();
    }

}