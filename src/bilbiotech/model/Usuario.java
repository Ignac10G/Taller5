package bilbiotech.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un usuario.
 *
 * @author Ignacio Gavia
 *         Vicente Castro.
 */
public final class Usuario {

    /**
     * Número maximo de libros que puede tener el usuario.
     */
    private static final int NUMERO_LIBROS_MAXIMO = 5;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     */
    private String apellido;

    /**
     * Rut del usuario.
     */
    private String rut;

    /**
     * Contrasenia del usuario.
     */
    private String contrasenia;

    /**
     * Libros que el usuario tiene en prestamo (maximo 10).
     */
    private List<Libro> librosEnPrestamo = new ArrayList<>();

    /**
     * The Constructor.
     *
     * @param nombre            del usuario.
     * @param apellido          del usuario.
     * @param rut               del usuario.
     * @param contrasenia       del usuario.
     */
    public Usuario(String rut, String nombre, String apellido, String contrasenia) {

        // validacion nombre.
        if (nombre == null || nombre.length() == 0) {
            throw new IllegalArgumentException("Nombre no valido!");
        }
        this.nombre = nombre;

        // validacion apellido.
        if (apellido == null || apellido.length() == 0) {
            throw new IllegalArgumentException("Apellido no valido!");
        }
        this.apellido = apellido;

        this.rut = rut;

        // validacion contraseña
        if (contrasenia == null || contrasenia.length() == 0) {
            throw new IllegalArgumentException("Contraseña no valida!");
        }
        this.contrasenia = contrasenia;
    }

    /**
     * @return el nombre del usuario.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * @return el apellido del usuario.
     */
    public String getApellido() {
        return this.apellido;
    }

    /**
     * @return el nombre completo del usuario.
     */
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }

    /**
     * @return el rut del usuario.
     */
    public String getRut() {
        return this.rut;
    }

    /**
     * @return la contrasenia del usuario.
     */
    public String getContrasenia() {
        return this.contrasenia;
    }

    /**
     * Agrega un libro en prestamo al usuario.
     *
     * @param libro a agregar.
     */
    public void agregarLibro(final Libro libro) {
        // validacion
        if (this.librosEnPrestamo.size() == NUMERO_LIBROS_MAXIMO) {
            throw new IllegalArgumentException("El usuario ya tiene la maxima cantidad de libros en prestamo: " + NUMERO_LIBROS_MAXIMO);
        }
        // agrego el libro
        this.librosEnPrestamo.add(libro);
    }
    /**
     * Eliminar un libro en prestamo al usuario.
     *
     * @param libro a Eliminar.
     */
    public void devolverLibro(final Libro libro) {
        //Validacion
        if (!librosEnPrestamo.contains(libro)) {
            throw new IllegalArgumentException("El usuario no tiene este libro en prestamo");
        }
        // elimino el libro
        this.librosEnPrestamo.remove(libro);
    }

    /**
     * Cambiar contraseña.
     *
     * @param contrasenia a agregar
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Libro> getLibrosEnPrestamo() {
        return librosEnPrestamo;
    }
}
