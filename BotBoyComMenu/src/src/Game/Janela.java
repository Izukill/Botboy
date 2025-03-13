package Game;

import javax.swing.*;

public class Janela extends JFrame {
    private Fase fase;

    public Janela() {
        setTitle("Botboy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        showMenu(); // Começa no menu

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showMenu() {
        setContentPane(new Menu(this));
        pack();
        revalidate();
        repaint();
        setLocationRelativeTo(null);
    }

    public void showGame() {
        if (fase == null) {
            fase = new Fase(this);
        }
        setContentPane(fase);
        pack();
        revalidate();
        repaint();
        setLocationRelativeTo(null);

        fase.setFocusable(true);
        fase.requestFocusInWindow();
    }

    public Fase getFase() {
        return fase;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Janela();
        });
    }
}