package cl.ucn.disc.pa.bibliotech.Forms;

import cl.ucn.disc.pa.bibliotech.System.Sistema;
import cl.ucn.disc.pa.bibliotech.model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class inicio extends JFrame{
    private JTextField contrasenia;
    private JTextField rut;
    private JButton button;
    private JPanel FormDinicio;
    private JButton cerrarButton;
    private Usuario usuario;
    public inicio(){
        setContentPane(FormDinicio);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Inicio de sesion.");
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarUsuario();
            }
        });

        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }
    private void verificarUsuario() {

        try {
            String rut = this.rut.getText();
            String contrasenia = this.contrasenia.getText();

            if (!rut.isEmpty() && !contrasenia.isEmpty()) {
                usuario = Sistema.buscarUsuarioRut(rut);
                System.out.println("inicio exitoso");
                limpiar();
                menuPrincipal menu = new menuPrincipal();

            } else {
                JOptionPane.showMessageDialog(FormDinicio, "Por favor, complete todos los campos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(FormDinicio, "[!] Ha ocurrido un error [!]");
            limpiar();
        }

    }
    private void limpiar(){
        rut.setText("");
        contrasenia.setText("");
    }
    private void close(){
        System.exit(0);
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
