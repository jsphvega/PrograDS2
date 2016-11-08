package frameworkJava.dto;

/**
 * DTO que recibe los parametros de pregunta y respuesta de la vista que se
 * desea agregar al tutor.
 * 
 * @author ChuckyBueno
 * @since 11-Octubre-2016
 */

public class DTOEntrenamiento {

    private String titulo;
    private String descripcion;
    private String categoria;

    /**
     * Metodo que retorna el titulo del tema
     * 
     * @return El titulo
     */
    public String getTitulo() {
	return titulo;
    }

    /**
     * Metodo que asigna el titulo del tema
     * 
     * @param titulo
     *            El titulo del tema
     */
    public void setTitulo(String titulo) {
	this.titulo = titulo;
    }

    /**
     * Metodo que retorna la descripcion del tema
     * 
     * @return El titulo
     */
    public String getDescripcion() {
	return descripcion;
    }

    /**
     * Metodo que asigna la descripcion del tema
     * 
     * @param titulo
     *            El titulo del tema
     */
    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    /**
     * Metodo que retorna la categoria del tema
     * 
     * @return El titulo
     */
    public String getCategoria() {
	return categoria;
    }

    /**
     * Metodo que asigna la categoria del tema
     * 
     * @param titulo
     *            El titulo del tema
     */
    public void setCategoria(String categoria) {
	this.categoria = categoria;
    }

}