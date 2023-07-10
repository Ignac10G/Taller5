package bilbiotech.Forms;

import bilbiotech.System.Sistema;
import bilbiotech.model.Libro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Clase que representa el menu de agregar libros a la biblioteca en JFrame.
 *
 * @author Ignacio Gavia
 *         Vicente Castro.
 */

public class AgregarLibro extends JFrame{
    private JTextField isbn;
    private JButton botonAgregar;
    private JTextField titulo;
    private JTextField autor;
    private JTextField categoria;
    private JTextField cantPaginas;
    private JTextField stock;
    private JPanel AgregarLibro;
    private JButton botonVolver;

    /**
     * The constructor.
     * @param sistema sistema en el que opera.
     */
    public AgregarLibro(Sistema sistema){
        setContentPane(AgregarLibro);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Agregar nuevo libro");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarLibro(sistema);
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPrincipal menu = new menuPrincipal(sistema);
                menu.setVisible(true);
                setVisible(false);
            }
        });
    }

    /**
     * Metodo encargado de agregar los libros a la lista de libros del sistema.
     * @param sistema sistema en el que opera.
     */
    public void agregarLibro(Sistema sistema){
        try {
            String codigoIsbn = isbn.getText();
            String tituloLibro = titulo.getText();
            String autorLibro = autor.getText();
            String categoriaLibro = categoria.getText();
            int cantidadPagLibro = Integer.parseInt(cantPaginas.getText());
            int stockLibro = Integer.parseInt(stock.getText());
            Libro libro = sistema.buscarLibro(codigoIsbn);
            if(libro != null){
                JOptionPane.showMessageDialog(AgregarLibro, "Libro disponible en el sistema, no se puede agregar.");
            }
            else if (!codigoIsbn.isEmpty() && !tituloLibro.isEmpty()&& !autorLibro.isEmpty()&& !categoriaLibro.isEmpty()&& cantidadPagLibro > 0 && stockLibro > 0) {
                libro = new Libro(codigoIsbn,tituloLibro,autorLibro,categoriaLibro,cantidadPagLibro,stockLibro);
                sistema.agregarLibro(libro);
                JOptionPane.showMessageDialog(AgregarLibro, "[!]Libro agregado con exito.[!]");
            } else {
                JOptionPane.showMessageDialog(AgregarLibro, "Por favor, complete todos los campos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(AgregarLibro, "[!] Ha ocurrido un error [!]");
            limpiar();
        }
    }
    /**
     * Metodo que limpia los cuadros de texto.
     */
    private void limpiar(){
        isbn.setText("");
        titulo.setText("");
        autor.setText("");
        cantPaginas.setText("");
        stock.setText("");
    }
}
