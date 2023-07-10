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

public class DevolverLibro extends JFrame{
    private JTextField isbnInput;
    private JButton devolverButton;
    private JPanel devolverLibro;
    public DevolverLibro(Sistema sistema){
        setContentPane(devolverLibro);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Busqueda de Libro");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                devolverLibroPrestado(sistema);

            }
        });
    }
    public void devolverLibroPrestado(Sistema sistema){
        try {
            Usuario aux = sistema.getusuario();
            String codigoIsbn = isbnInput.getText();
            Libro libro = sistema.buscarLibro(codigoIsbn);
            if (!codigoIsbn.isEmpty() && aux.getLibrosEnPrestamo().contains(libro)) {
                sistema.realizarDevolucionLibro(libro);
                try (FileWriter writer = new FileWriter("reservas.txt",true)) {
                    PrintWriter line = new PrintWriter(writer);
                    //Obtenemos los datos
                    String linea = sistema.getusuario().getRut() +","+ sistema.getusuario().getNombre()+","+ sistema.getusuario().getApellido()+","+ libro.getIsbn()+","+ libro.getTitulo()+","+ "devolucion";
                    //Escribimos en el archivo
                    line.println(linea);
                }
                JOptionPane.showMessageDialog(devolverLibro, "Libro devuelto con exito.");
                menuPrincipal menu = new menuPrincipal(sistema);
                menu.setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(devolverLibro, "[!] ISBN no encontrado, ya esta en el sistema [!]");
                menuPrincipal menu = new menuPrincipal(sistema);
                menu.setVisible(true);
                setVisible(false);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(devolverLibro, "[!] Ha ocurrido un error [!]");
            limpiar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void limpiar(){
        isbnInput.setText("");
    }
}
