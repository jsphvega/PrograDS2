package frameworkJava.logicaDeNegocio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Clase que se encarga de crear un documento PDF de acuerdo a un texto dado
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */
public class PDF extends Documento {

    private Paragraph parrafo;
    private Document documento;
    public static String pathPDF;

    /**
     * Metodo que crea un archivo en una direccion especifica
     */
    public void crearDocumento() {
	pathPDF = retornarRuta();

	// Revisa que exista una ruta
	if (pathPDF != "") {
	    File file = new File(pathPDF + ".pdf");
	    file.getParentFile().mkdirs();
	    documento = new Document(PageSize.A4.rotate());
	}
    }

    /**
     * Metodo que toma un arraylist y transcribe todos los elementos en un
     * archivo
     * 
     * @param pTexto
     * @throws FileNotFoundException
     */
    public boolean escribirEnDocumento(ArrayList<Consulta> pTexto) {
	try {
	    configurarDocumento();

	    // Variable que contiene la respuesta correcta
	    String x;

	    // Abre el documento
	    documento.open();
	    for (int i = 0; i < pTexto.size(); i++) {
		x = pTexto.get(i).imprimirRespuestas();
		Chunk texto = new Chunk(x + "\n");
		parrafo.add(texto);
	    }

	    // Crea y cierra el documeto
	    documento.add(parrafo);
	    documento.close();
	    return true;
	} catch (DocumentException e) {
	    return false;
	}
    }

    /**
     * Metodo que crea un objeto para escribir en el archivo antes creado Se le
     * agregan caracteristicas como fuente y tamano de letra
     * 
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    private void configurarDocumento() {
	try {
	    PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(pathPDF + ".pdf"));
	    writer.setPdfVersion(PdfWriter.VERSION_1_7);
	    parrafo = new Paragraph();
	    parrafo.setFont(new Font(Font.FontFamily.HELVETICA, 20));
	} catch (DocumentException e) {
	    // TODO: handle exception
	    e.printStackTrace();
	} catch (FileNotFoundException e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
    }
}