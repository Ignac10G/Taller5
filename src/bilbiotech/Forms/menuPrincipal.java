package bilbiotech.Forms;

import bilbiotech.System.Sistema;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

public class menuPrincipal extends JFrame{
    private JButton buscarLibroButton;
    private JButton prestarLibro;
    private JButton agregarLibro;
    private JButton devolverLibro;
    private JPanel menuPrincipal;
    private JButton cerrarSesiónButton;

    public menuPrincipal(Sistema sistema){
        setContentPane(menuPrincipal);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setTitle("Menu principal.");
        setSize(500,500);
        setVisible(true);
        setLocationRelativeTo(null);
        buscarLibroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarLibro bl = new BuscarLibro(sistema);
                bl.setVisible(true);
                setVisible(false);
            }
        });

        prestarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrestarLibro pl = new PrestarLibro(sistema);
                pl.setVisible(true);
                setVisible(false);
            }
        });

        agregarLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarLibro Al = new AgregarLibro(sistema);
                Al.setVisible(true);
                setVisible(false);
            }
        });

        devolverLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DevolverLibro dL = new DevolverLibro(sistema);
                dL.setVisible(true);
                setVisible(false);
            }
        });
        menuPrincipal.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
        cerrarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sistema.guardarInformacion();
                    sistema.cerrarSession();
                    inicio ini = new inicio(sistema);
                    ini.setVisible(true);
                    setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
