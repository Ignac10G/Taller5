package bilbiotech.Forms;

import bilbiotech.System.Sistema;
import bilbiotech.model.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Clase que representa el menu busqueda de libros en JFrame.
 *
 * @author Ignacio Gavia
 *         Vicente Castro.
 */

public class BuscarLibro extends JFrame{
    private JButton botonVolver;
    private JTextField isbnInput;
    private JTextArea libros;
    private JPanel BuscarLibro;
    private JButton botonBuscar;

    /**
     * The constructor.
     * @param sistema
     */
    public BuscarLibro (Sistema sistema) {
        setContentPane(BuscarLibro);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Busqueda de Libro");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarLibro(sistema);
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
     * Metodo encargado de verificar los libros.
     * @param sistema sistema en el que opera.
     */
    public void verificarLibro(Sistema sistema){
        try {
            String isbn = isbnInput.getText();
            if (!isbn.isEmpty()) {
                Libro libro = sistema.buscarLibro(isbn);
                libros.setText("");
                for (Libro aux : sistema.getLibros()) {
                    if (aux == libro) {
                        libros.append("******************************************* \n" +
                                "   Codigo ISBN: "+aux.getIsbn() +"\n" +
                                "   Tituo Libro: "+aux.getTitulo()+"\n" +
                                "   Autor Libro: "+aux.getAutor()+"\n" +
                                "   Categoria: "+aux.getCategoria()+"\n" +
                                "   Cantidad de paginas: "+aux.getCantPag()+"\n" +
                                "   Stock: "+aux.getStock()+"\n" +
                                "******************************************* \n");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(BuscarLibro, "Por favor, complete todos los campos.");
            }
        } catch (NumberFormatException a) {
            JOptionPane.showMessageDialog(BuscarLibro, "[!] Ha ocurrido un error [!]");
            limpiar();
        }
    }
    /**
     * Metodo que limpia los cuadros de texto.
     */
    private void limpiar(){
        isbnInput.setText("");
    }

}
