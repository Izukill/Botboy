package Game;

import javax.swing.*;

public class Janela extends JFrame {

    public Janela() {
        setTitle("Botboy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Fase());
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
         Janela jogo = new Janela();
    }
}