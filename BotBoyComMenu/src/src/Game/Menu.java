package Game;

import Utilz.LoadFase;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu extends JPanel {
    private Janela janela;
    private BufferedImage backgroundImage;

    public Menu(Janela janela) {
        this.janela = janela;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1248, 672));

        // Carregar a imagem de fundo
        backgroundImage= LoadFase.getSpriteImage("/background.png");


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);


        JLabel titleLabel = new JLabel("B O T B O Y");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);


        JButton startButton = new JButton("Iniciar Jogo");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setMaximumSize(new Dimension(200, 50));

        startButton.addActionListener(e -> {
            janela.showGame();
            janela.getFase().restartGame();
        });


        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalGlue());


        add(centerPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}