package frameworkJava.logicaDeIntegracion;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;

/**
 * Clase que se encarga de Interceptar toda la informacion relacionada a un
 * contexto de tipo HTTP de acuerdo a una consulta y un contexto dado.
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public class Interceptor implements HttpRequestInterceptor {

    /**
     * Método que realiza los procesos de estado del cliente de acuerdo a una
     * consulta y un contexto dado.
     */
    public void process(HttpRequest pConsulta, HttpContext pContexto) {
	// Atributo que almacena el estado del contexto del cliente
	final AuthState pEstado = (AuthState) pContexto.getAttribute(HttpClientContext.TARGET_AUTH_STATE);

	// Condicion que revisa si el esquema del contexto existe
	if (pEstado.getAuthScheme() == null) {

	    // Atributo que almacena las credenciales el proveedor
	    final CredentialsProvider pProveedor = (CredentialsProvider) pContexto
		    .getAttribute(HttpClientContext.CREDS_PROVIDER);

	    // Atributo que almacena el host segun el contexto dado
	    final HttpHost HostObjetivo = (HttpHost) pContexto.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);

	    // Atributo que almacena las credenciales segun el host y el
	    // proveedor obtenido
	    final Credentials pCredenciales = pProveedor
		    .getCredentials(new AuthScope(HostObjetivo.getHostName(), HostObjetivo.getPort()));

	    // Se actualiza el estado del contexto del cliente
	    pEstado.update(new BasicScheme(), pCredenciales);
	}
    }

}