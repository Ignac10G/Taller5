/*
 * Copyright (c) 2023. Programacion Avanzada, DISC, UCN.
 */

package cl.ucn.disc.pa.bibliotech.System;

import cl.ucn.disc.pa.bibliotech.Forms.inicio;
import cl.ucn.disc.pa.bibliotech.model.Libro;
import cl.ucn.disc.pa.bibliotech.model.Usuario;

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
    private static List<Usuario> usuarios;

    /**
     * Lista de Libros.
     */
    private static List<Libro> libros;

    /**
     * usuario en el sistema.
     */
    private static Usuario usuario;

    /**
     * The Sistema.
     */
    public Sistema() throws IOException {
        usuarios = new ArrayList<>();
        libros = new ArrayList<>();

        // carga de los usuarios y libros.
        try {
            this.cargarInformacion();
            iniciarSession();

        } finally {
            // guardo la informacion.
            this.guardarInformacion();
        }

    }

    /**
     * Activa (inicia sesion) de un usuario en el sistema.
     */
    public static void iniciarSession() {
        inicio inici = new inicio();
        usuario = inici.getUsuario();
    }

    /**
     * Busca usuario por el rut de usuario
     */
    public static Usuario buscarUsuarioRut(String rut) {
        for (Usuario aux : usuarios) {
            if (Objects.equals(aux.getRut(), rut)) {
                return usuario;
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
     * @param isbn del libro a prestar.
     */
    public void realizarPrestamoLibro(final String isbn) throws IOException {
        // el usuario debe estar activo.
        if (usuario == null) {
            throw new IllegalArgumentException("usuario no se ha logeado!");
        }

        // busco el libro.
        Libro libro = this.buscarLibro(isbn);

        // si no lo encontre, lo informo.
        if (libro == null) {
            throw new IllegalArgumentException("Libro con isbn " + isbn + " no existe o no se encuentra disponible.");
        }

        // agrego el libro al usuario.
        usuario.agregarLibro(libro);

        // eliminar el libro de los disponibles.
        for (Libro libro2 : libros) {
            if (libro2 != null) {
                libro2 = null;
            }
        }

        // se actualiza la informacion de los archivos
        this.guardarInformacion();

    }


    /**
     * Metodo que busca un libro en los libros disponibles.
     *
     * @param isbn a buscar.
     * @return el libro o null si no fue encontrado.
     */
    private Libro buscarLibro(final String isbn) {
        // recorro el arreglo de libros.
        for (Libro libro : libros) {
            // si lo encontre, retorno el libro.
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        // no lo encontre, retorno null.
        return null;
    }
    public static void leerArchivoLibros() {

        // Leer el archivo "libros.txt"
        try (BufferedReader br = new BufferedReader(new FileReader("libros.txt", StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] chain = line.split(",");
                String isbn = chain[0];
                String title = chain[1];
                String author = chain[2];
                String category = chain[3];
                int cantPag = Integer.parseInt(chain[4]);
                int stock = Integer.parseInt(chain[5]);


                Libro libro = new Libro(isbn, title, author, category,cantPag,stock);
                libros.add(libro);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Método encargado de leer el archivo de "usuarios.txt".
     */
    public static void leerArchivoUsuarios() {
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

    /**
     * Lee los archivos libros.txt y usuarios.txt.
     */
    private void cargarInformacion(){
        // trato de leer los usuarios y los libros desde el archivo.
        leerArchivoLibros();
        leerArchivoUsuarios();
    }

    /**
     * Guarda los arreglos libros y usuarios en los archivos libros.txt y usuarios.txt.
     *
     * @throws IOException en caso de algun error.
     */
    private void guardarInformacion() throws IOException {

        // guardo los usuarios.
        try (FileWriter writer = new FileWriter("usuarios.txt",StandardCharsets.UTF_8,true)) {
            //for para recorrer la lista
            for (Usuario usuario : usuarios){
                //Obtenemos los datos
                String linea = usuario.getRut() +","+ usuario.getNombre()+","+ usuario.getApellido()+","+ usuario.getContrasenia();
                PrintWriter line = new PrintWriter(writer);

                //Escribimos en el archivo
                line.println(linea);
                line.close();
                writer.close();
            }
        }

        // guardo los libros.
        try (FileWriter writer = new FileWriter("libros.txt",StandardCharsets.UTF_8,true)) {
            //for para recorrer la lista
            for (Libro libro : libros){
                //Obtenemos los datos
                String linea = libro.getIsbn() +","+ libro.getTitulo()+","+libro.getAutor()+","+libro.getCategoria()+","+libro.getCantPag()+","+libro.getStock();
                PrintWriter line = new PrintWriter(writer);

                //Escribimos en el archivo
                line.println(linea);
                line.close();
                writer.close();
            }
        }
    }

    public Usuario getusuario() {
        return usuario;
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}
