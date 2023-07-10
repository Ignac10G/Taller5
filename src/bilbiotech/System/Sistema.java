/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package bilbiotech.System;

import bilbiotech.Forms.inicio;
import bilbiotech.model.Libro;
import bilbiotech.model.Usuario;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Sistema.
 *
 * @author .
 */
public final class Sistema {

    /**
     * Lista de usuarios.
     */
    private final List<Usuario> usuarios;

    /**
     * Lista de Libros.
     */
    private final List<Libro> libros;

    /**
     * usuario en el sistema.
     */
    private Usuario usuario;

    /**
     * The Sistema.
     */
    public Sistema() throws IOException {
        usuarios = new ArrayList<>();
        libros = new ArrayList<>();
        // carga de los usuarios y libros.
        try {
            this.cargarInformacion();
            inicio inici = new inicio(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Activa (inicia sesion) de un usuario en el sistema.
     */
    public void iniciarSession(Usuario usuario) {
        if (!usuarios.contains(usuario)){
            throw new RuntimeException("No se encontro el usuario.");
        }
        this.usuario = usuario;
    }

    /**
     * Busca usuario por el rut de usuario
     */
    public Usuario buscarUsuarioRut(String rut) {
        for (Usuario aux : this.usuarios) {
            if (aux.getRut().equals(rut)) {
                System.out.println(aux);
                return aux;
            }
        }
        return null;
    }

    /**
     * Cierra la session del usuario.
     */
    public void cerrarSession() {
        usuario = null;
    }

    /**
     * Metodo que mueve un libro de los disponibles y lo ingresa a un usuario.
     *
     * @param libro libro a prestar.
     */
    public void realizarPrestamoLibro(final Libro libro) throws IOException {
        // el usuario debe estar activo.
        if (this.usuario == null) {
            throw new IllegalArgumentException("usuario no se ha logeado!");
        }

        // si no lo encontre, lo informo.
        if (libro == null) {
            throw new IllegalArgumentException("Libro con isbn " + libro.getIsbn() + " no existe o no se encuentra disponible.");
        }

        // agrego el libro al usuario.
        this.usuario.agregarLibro(libro);

        // bajar el stock del libro disponible.
        if (libro.getStock() <= 0){
            this.libros.remove(libro);
        } else {
            for(Libro libro2: libros){
                if (libro2 == libro){
                    libro2.setStock(libro.getStock()-1);
                }
            }
        }


        // se actualiza la informacion de los archivos
        guardarInformacion();

    }
    /**
     * Metodo que mueve un libro del usuario y lo ingresa al sistema.
     *
     * @param libro libro a devolver.
     */
    public void realizarDevolucionLibro(final Libro libro) throws IOException {
        // el usuario debe estar activo.
        if (this.usuario == null) {
            throw new IllegalArgumentException("usuario no se ha logeado!");
        }

        // si no lo encontre, lo informo.
        if (libro == null) {
            throw new IllegalArgumentException("Libro con isbn " + libro.getIsbn() + " no existe o no se encuentra disponible.");
        }

        // elimino el libro del usuario.
        usuario.devolverLibro(libro);

        if (this.libros.contains(libro)){
            for(Libro libro2: libros){
                if (libro2 == libro){
                    libro2.setStock(libro.getStock()+1);
                }
            }
        } else {
            this.libros.add(libro);
        }
        // se actualiza la informacion de los archivos
        guardarInformacion();
    }


    /**
     * Metodo que busca un libro en los libros disponibles.
     *
     * @param isbn a buscar.
     * @return el libro o null si no fue encontrado.
     */
    public Libro buscarLibro(final String isbn) {
        // recorro el arreglo de libros.
        for (Libro libro : this.libros) {
            // si lo encontre, retorno el libro.
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        // no lo encontre, retorno null.
        return null;
    }
    public void leerArchivoLibros() {

        // Leer el archivo "libros.txt"
        try (BufferedReader br = new BufferedReader(new FileReader("libros.txt", StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] chain = line.split(",");
                String isbn = chain[0];
                String title = chain[1];
                String author = chain[2];
                String category = chain[3];
                int stock = Integer.parseInt(chain[4]);
                int cantPag = Integer.parseInt(chain[5]);


                Libro libro = new Libro(isbn, title, author, category,stock,cantPag);
                this.libros.add(libro);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Método encargado de leer el archivo de "usuarios.txt".
     */
    public void leerArchivoUsuarios() {
        // Leer el archivo "usuarios.txt"
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt",StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] chain = line.split(",");
                String rut = chain[0];
                String name = chain[1];
                String lastname = chain[2];
                String password = chain[3];

                Usuario usuario = new Usuario(rut,name,lastname,password);
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    public void leerArchivoReservas() {
        // Leer el archivo "usuarios.txt"
        try (BufferedReader br = new BufferedReader(new FileReader("reservas.txt",StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] chain = line.split(",");
                String isbn = chain[3];
                String tipoTransaccion = chain[5];
                if (Objects.equals(tipoTransaccion, "prestamo")){
                    Libro libro = buscarLibro(isbn);
                    usuario.agregarLibro(libro);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Lee los archivos libros.txt y usuarios.txt.
     */
    private void cargarInformacion(){
        // trato de leer los usuarios y los libros desde el archivo.
        leerArchivoLibros();
        leerArchivoUsuarios();
        leerArchivoReservas();
    }

    /**
     * Guarda los arreglos libros y usuarios en los archivos libros.txt y usuarios.txt.
     *
     * @throws IOException en caso de algun error.
     */
    public void guardarInformacion() throws IOException {

        // guardo los usuarios.
        try (FileWriter writer = new FileWriter("usuarios.txt")) {
            //for para recorrer la lista
            PrintWriter line = new PrintWriter(writer);
            for (Usuario usuario : this.usuarios){
                //Obtenemos los datos
                String linea = usuario.getRut() +","+ usuario.getNombre()+","+ usuario.getApellido()+","+ usuario.getContrasenia();
                //Escribimos en el archivo
                line.println(linea);
            }
        }

        // guardo los libros.
        try (FileWriter writer = new FileWriter("libros.txt")) {
            PrintWriter line = new PrintWriter(writer);
            //for para recorrer la lista
            for (Libro libro : this.libros){
                //Obtenemos los datos
                String linea = libro.getIsbn() +","+ libro.getTitulo()+","+libro.getAutor()+","+libro.getCategoria()+","+libro.getStock()+","+libro.getCantPag();
                //Escribimos en el archivo
                line.println(linea);
            }
        }
    }

    public Usuario getusuario() {
        return usuario;
    }

    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public List<Libro> getLibros() {
        return this.libros;
    }
    public void agregarLibro(Libro libro){
        this.libros.add(libro);
    }
}
