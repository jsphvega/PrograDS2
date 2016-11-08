package frameworkJava.controlador;

import java.util.ArrayList;

import frameworkJava.dto.DTOEntrenamiento;
import frameworkJava.logicaDeIntegracion.Conversacion;
import frameworkJava.logicaDeIntegracion.Traductor;
import frameworkJava.logicaDeNegocio.Consulta;
import frameworkJava.logicaDeNegocio.FabricaDocumento;

public class Controlador {

    private void generarDocumento(String pTipo, ArrayList<Consulta> pConsultas) {
	FabricaDocumento sFabrica = new FabricaDocumento();
	sFabrica.crearDocumento(pTipo, pConsultas);
    }

    private Consulta traducirRespuestas(Consulta pConsulta) {
	/*Traductor sTraductor = new Traductor();
	Consulta sCambiada = new Consulta(sTraductor.traducirTexto(pConsulta.getPregunta()));
	for (int i = 0; i < pConsulta.getRespuestas().size(); i++)
	    sCambiada.agregarRespuesta(sTraductor.traducirTexto(pConsulta.getRespuestas().get(i)));

	return sCambiada;*/
	return null;
    }

    private void agregarInformacion(DTOEntrenamiento pEntrenamiento) {
	Conversacion cc = Conversacion.getInstance();

	cc.agregarInformacion(pEntrenamiento.getCategoria(), pEntrenamiento.getDescripcion(),
		pEntrenamiento.getTitulo());
    }

    public ArrayList<DTOEntrenamiento> obtenerRespuestas(String pConsulta) {
	Conversacion sConversacion;
	sConversacion = Conversacion.getInstance();

	return sConversacion.consultarInformacion(pConsulta);
    }

    public static void main(String[] args) {
	Controlador SC = new Controlador();

	Consulta x = new Consulta("#TDiseño");
	x.generarRespuestas();
	
	ArrayList<Consulta> c = new ArrayList<>();
	c.add(x);
	
	SC.generarDocumento("PDF", c);
    }
}
