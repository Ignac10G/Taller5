package bilbiotech.Forms;

import bilbiotech.System.Sistema;
import bilbiotech.model.Libro;
import bilbiotech.model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrestarLibro extends JFrame {
    private JTextField isbnInput;
    private JButton buscarButton;
    private JButton botonPrestar;
    private JPanel PrestarLibro;
    private JTextArea mostrarLibro;
    private Libro libro;

    public PrestarLibro(Sistema sistema){
        setContentPane(PrestarLibro);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Busqueda de Libro");
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String isbn = isbnInput.getText();
                    if (!isbn.isEmpty()) {
                        libro = sistema.buscarLibro(isbn);
                        mostrarLibro.setText("");
                        for (Libro aux : sistema.getLibros()) {
                            if (aux == libro) {
                                mostrarLibro.append("******************************************* \n" +
                                        "   Codigo ISBN: "+aux.getIsbn() +"\n" +
                                        "   Tituo Libro: "+aux.getTitulo()+"\n" +
                                        "   Autor Libro: "+aux.getAutor()+"\n" +
                                        "   Categoria: "+aux.getCategoria()+"\n" +
                                        "   Stock: "+aux.getStock()+"\n" +
                                        "   Cantidad de paginas: "+aux.getCantPag()+"\n" +
                                        "******************************************* \n");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(PrestarLibro, "Por favor, complete todos los campos.");
                    }
                }catch (NumberFormatException a) {
                    JOptionPane.showMessageDialog(PrestarLibro, "[!] Ha ocurrido un error [!]");
                    limpiar();
                }
            }
        });

        botonPrestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prestar(sistema);
            }
        });
    }
    private void limpiar(){
        isbnInput.setText("");
    }
    public void prestar(Sistema sistema){
        try {
            sistema.realizarPrestamoLibro(libro);
            Usuario usuario = sistema.getusuario();
            usuario.agregarLibro(libro);
            if (libro == null){
                JOptionPane.showMessageDialog(PrestarLibro, "Libro no encontrado.");
            }
            try (FileWriter writer = new FileWriter("reservas.txt",true)) {
                PrintWriter line = new PrintWriter(writer);
                //Obtenemos los datos
                String linea = usuario.getRut() +","+ usuario.getNombre()+","+ usuario.getApellido()+","+ libro.getIsbn()+","+ libro.getTitulo()+","+ "prestamo";
                //Escribimos en el archivo
                line.println(linea);
            }
            menuPrincipal menu = new menuPrincipal(sistema);
            menu.setVisible(true);
            setVisible(false);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
