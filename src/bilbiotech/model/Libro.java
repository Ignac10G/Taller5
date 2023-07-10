package bilbiotech.model;

/**
 * Clase que representa un Libro.
 *
 * @author Ignacio Gavia
 *         Vicente Castro.
 */
public final class Libro {

    /**
     * The ISBN.
     */
    private String isbn;

    /**
     * The Titulo.
     */
    private String titulo;

    /**
     * The Author.
     */
    private String autor;

    /**
     * The Categoria
     */
    private String categoria;
    /**
     * The cantidad de paginas
     */
    private int cantPag;
    /**
     * The Stock
     */
    private int stock;

    /**
     * The Constructor.
     *
     * @param isbn      del libro.
     * @param titulo    del libro.
     * @param autor     del libro
     * @param categoria del libro.
     */
    public Libro(final String isbn, final String titulo, final String autor, final String categoria,int stock, int cantPag) {
        // validacion de ISBN
        if (isbn == null || isbn.length() == 0) {
            throw new IllegalArgumentException("ISBN no valido!");
        }
        this.isbn = isbn;

        // validacion del titulo
        if (titulo == null || titulo.length() == 0) {
            throw new IllegalArgumentException("Titulo no valido!");
        }
        this.titulo = titulo;

        // validacion de autor
        if (autor == null || autor.length() == 0) {
            throw new IllegalArgumentException("Autor no valido");
        }
        this.autor = autor;

        // validacion categoria
        if (categoria == null || categoria.length() == 0) {
            throw new IllegalArgumentException("Categoria no valida");
        }
        this.categoria = categoria;

        // validacion de stock
        if (stock < 0) {
            throw new IllegalArgumentException("Stock no valido!");
        }
        this.stock = stock;
        // validacion de paginas
        if (cantPag < 0) {
            throw new IllegalArgumentException("Paginas no validas!");
        }
        this.cantPag = cantPag;
    }

    /**
     * @return the ISBN.
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * @return the titulo.
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * @return the autor.
     */
    public String getAutor() {
        return this.autor;
    }

    /**
     * @return the categoria.
     */
    public String getCategoria() {
        return this.categoria;
    }

    /**
     * @return the Stock.
     */
    public int getStock() {
        return this.stock;
    }
    /**
     * @return cantidad de paginas.
     */
    public int getCantPag() {
        return cantPag;
    }

    /**
     * Actualiza el stock de los libros
     * @param stock Stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}