package cl.ucn.disc.pa.bibliotech.Forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class menuPrincipal extends JFrame{
    private JButton buscarLibroButton;
    private JButton prestarLibro;
    private JButton agregarLibro;
    private JButton devolverLibro;
    private JPanel menuPrincipal;

    public menuPrincipal(){
        setContentPane(menuPrincipal);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Menu principal.");
        setSize(500,500);
        setVisible(true);
        buscarLibroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        prestarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        agregarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        devolverLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        menuPrincipal.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
    }
}
