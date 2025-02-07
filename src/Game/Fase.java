package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fase extends JPanel implements Runnable {
    private final Botboy botboy;
    private Thread gameLoop;

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


        botboy.drawModel(g);




    }


    //Função que chama toda a lógica do jogo
    public void loadGame(){

        botboy.update();

    }



    //Função que vai ser usada para fazer o jogo rodar chamado na Thread
    @Override
    public void run() {


        int fpsLimite = 60;
        int upsLimite = 200;

        //É utilizado nanosegundos/fpsLimite por causa do System.nanoTime()
        double timePerFrame= 1000000000 / fpsLimite; //variavel que decidi o tempo máximo de cada frame
        double timePerUps= 1000000000 / upsLimite;   //variavel que decidi o tempo máximo de cada update da lógica


        //Inicialização do Tempo
        long tempoAnterior= System.nanoTime();

        int gameFrames=0;
        int gameUpdates=0;

        long lastCheck= System.currentTimeMillis();//marca o tempo para calcular fps e ups

        //variaveis que guardam o tempo para renderizar e atualizar o jogo
        double deltaU=0;
        double deltaF=0;

        while(true){
            long tempoAtual= System.nanoTime();


            deltaU+= (tempoAtual - tempoAnterior)/ timePerUps;
            deltaF+= (tempoAtual - tempoAnterior)/ timePerFrame;
            tempoAnterior= tempoAtual;

            if(deltaU >= 1){
                loadGame();
                gameUpdates++;
                deltaU--;
            }

            if(deltaF >=1){
                repaint();
                gameFrames++;
                deltaF--;

            }



            //Contador de Fps do jogo
            if (System.currentTimeMillis() - lastCheck >=1000){
                lastCheck= System.currentTimeMillis();
                System.out.println("Frames= "+ gameFrames +" | Ups= "+ gameUpdates);
                gameFrames =0;
                gameUpdates=0;
            }

            //Pausa da thread
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

                    botboy.Moves(0);
                    break;
                case KeyEvent.VK_A:

                    botboy.Moves(1);
                    break;
                case KeyEvent.VK_S:

                    botboy.Moves(3);
                    break;
                case KeyEvent.VK_D:

                    botboy.Moves(2);
                    break;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:

                    botboy.setMoving(false);
                    break;
                case KeyEvent.VK_A:

                    botboy.setMoving(false);
                    break;
                case KeyEvent.VK_S:

                    botboy.setMoving(false);
                    break;
                case KeyEvent.VK_D:

                    botboy.setMoving(false);
                    break;
            }

        }

    }

    private static class MouseInputs implements MouseListener, MouseMotionListener{

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

        }
    }

}
