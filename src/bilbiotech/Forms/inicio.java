package bilbiotech.Forms;

import bilbiotech.System.Sistema;
import bilbiotech.model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class inicio extends JFrame{
    private JTextField rut;
    private JTextField contrasenia;
    private JButton button;
    private JPanel FormDinicio;
    private JButton cerrarButton;
    public inicio(Sistema sistema){
        setContentPane(FormDinicio);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Inicio de sesion.");
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarUsuario(sistema);
            }
        });

        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }
    private void verificarUsuario(Sistema sistema) {
        try {
            String rut2 = contrasenia.getText();
            String contrasenia = this.rut.getText();
            Usuario usuario = sistema.buscarUsuarioRut(rut2);
            if (usuario == null){
                JOptionPane.showMessageDialog(FormDinicio, "Usuario no registrado.");
            }
            if (!rut2.isEmpty() && !contrasenia.isEmpty()) {
                sistema.iniciarSession(usuario);
                menuPrincipal menu = new menuPrincipal(sistema);
                menu.setVisible(true);
                this.setVisible(false);

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

    public String getRut() {
        return this.rut.getText();
    }
    public String rutTo(String rut){
        rut = rut.replace(".", "").replace("-", "");
        String rutNumerico = rut.substring(0, rut.length());
        return rutNumerico;
    }
}
