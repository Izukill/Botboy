package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fase extends JPanel implements Runnable {
    private Botboy botboy;
    private int dX,dY;
    private Thread gameLoop;
    private final int fpsLimite=60;
    private int gameFrames;
    private long lastCheck;

    public Fase(){

        setFocusable(true);
        setDoubleBuffered(true);

        botboy=new Botboy();



        //Inputs do Jogo
        addKeyListener(new TecladoInputs());
        addMouseListener(new MouseInputs());
        addMouseMotionListener(new MouseInputs());

        setPanelSize();
        IniciarGameLoop();


    }

    private void setPanelSize() {
        Dimension tamanhoTela=new Dimension(1200,800);
        setPreferredSize(tamanhoTela);
    }

    private void IniciarGameLoop(){
        gameLoop=new Thread(this);
        gameLoop.start();
    }









    //método para "pintar" os elementos sobre o JPanel com a biblioteca graphics
    public void paintComponent(Graphics g){
        super.paintComponent(g); //sem chamar a superClasse podem ocorrer alguns bugs durante a pintura da fase

        botboy.updateAnimation();

        g.drawImage(botboy.getAnimation().get(botboy.getBotboyAction()),dX,dY,150,150,null);





        //Contador de Fps do jogo
        gameFrames++;
        if (System.currentTimeMillis() - lastCheck >=1000){
            lastCheck= System.currentTimeMillis();
            System.out.println("Frames= "+ gameFrames);
            gameFrames =0;
        }


    }



    //Função que vai ser usada para fazer o jogo rodar
    @Override
    public void run() {

        //É utilizado nanosegundos/fpsLimite para que o jogo n "trancar" durante o processo
        double frames= 1000000000 / fpsLimite; //variavel que decidi em quantos frames o jogo vai rodar
        long ultimoFrame= System.nanoTime();
        long agora;

        while(true){

            agora= System.nanoTime();
            if(agora - ultimoFrame >= frames){
                repaint();
                ultimoFrame=agora;
            }

        }

    }


    //Área onde as classes dos inputs são criadas para o contrutor da fase

    private class TecladoInputs implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()){
                case KeyEvent.VK_W:

                    dY-=20;
                    break;
                case KeyEvent.VK_A:

                    dX-=20;
                    break;
                case KeyEvent.VK_S:

                    dY+=20;
                    break;
                case KeyEvent.VK_D:

                    dX+=20;
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:

                    dY+=0;
                    break;
                case KeyEvent.VK_A:

                    dX+=0;
                    break;
                case KeyEvent.VK_S:

                    dY+=0;
                    break;
                case KeyEvent.VK_D:

                    dX+=0;
                    break;
            }

        }

    }

    private class MouseInputs implements MouseListener, MouseMotionListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            dX=e.getX();
            dY=e.getY();


        }
    }

}
