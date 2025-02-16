package Game;

import Entidades.Shoot;
import Level.LevelManager;
import Utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static Utilz.Constants.GameSizes.*;

public class Fase extends JPanel implements Runnable {
    private Botboy botboy;
    private LevelManager levelManager;
    private Thread gameLoop;
    private boolean inGame=true;



    // Renderização do mapa sobre o personagem
    private int xLvlOffset;
    private int leftBord= (int) (0.30*game_width), rightBord = (int) (0.50*game_width);
    private int lvlTilesWide= LoadSave.getLevelData()[0].length;
    private int maxtilesOffset= lvlTilesWide - tiles_in_width;
    private int maxLvlOffsetX= maxtilesOffset * tiles_size;




    public Fase(){

        setFocusable(true);
        setDoubleBuffered(true);

        startModels(); //Inicializando os modelos antes da criação do loop do game




        //Inputs do Jogo
        addKeyListener(new TecladoInputs());
        addMouseListener(new MouseInputs());
        addMouseMotionListener(new MouseInputs());

        setPanelSize();
        startGameLoop();



    }

    private void setPanelSize() {
        Dimension tamanhoTela=new Dimension(game_width,game_height);
        setPreferredSize(tamanhoTela);
        System.out.println("Size:"+ game_width+" | "+ game_height);
    }

    private void startGameLoop(){
            gameLoop=new Thread(this);
            gameLoop.start();

    }






    private void startModels(){
        levelManager=new LevelManager(this);
        botboy=new Botboy();
        botboy.loadLevelData(levelManager.getLevel().getLvlData());

    }











    //método para "pintar" os elementos sobre o JPanel com a biblioteca graphics
    public void paintComponent(Graphics g){
        super.paintComponent(g); //sem chamar a superClasse podem ocorrer alguns bugs durante a pintura da fase

        render(g);




    }


    //Função que chama toda a lógica do jogo
    public void loadGame(){

        if(inGame){

        botboy.update();
        levelManager.update();
        checkBoder();


        List<Shoot> shoots= botboy.getShoots();

            for (int i = 0; i < shoots.size(); i++) {
                Shoot m = shoots.get(i);
                if (m.isVisible()) {
                    m.shoot();
                } else {
                    shoots.remove(i);
                }
            }


        if(botboy.getHitbox().getY()>=569){
            botboy.setBotboyAction(4);//Animação de dano
            inGame = false;
        }

        }else{
            System.out.println("Game Over!");
            JOptionPane.showMessageDialog(this,"Game Over!","Fim de Jogo",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Encerra o jogo
        }









    }


    //Função que muda a borda do mapa no personagem
    private void checkBoder() {
        int botBoyX= (int) botboy.getHitbox().x;
        int diff = botBoyX - xLvlOffset;



        if(diff > rightBord){
            xLvlOffset +=diff - rightBord;
        } else if (diff < leftBord) {
            xLvlOffset +=diff - leftBord;
        }

        if(xLvlOffset > maxLvlOffsetX){
            xLvlOffset= maxLvlOffsetX;
        } else if (xLvlOffset<0) {
            xLvlOffset=0;
        }

    }


    //Função que desenha os modelos no jogo
    public void render(Graphics g){

        levelManager.draw(g, xLvlOffset);
        botboy.drawBotboy(g, xLvlOffset);

        List<Shoot> shoots= botboy.getShoots();
        for (int i = 0; i < shoots.size(); i++) {
            Shoot m = shoots.get(i);
            m.drawShoot(g);
        }




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

    public Botboy getBotboy() {
        return botboy;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
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
                    getBotboy().setUp(true);
                    break;

                case KeyEvent.VK_S:
                    getBotboy().setDown(true);
                    break;

                case KeyEvent.VK_A:
                    getBotboy().setLeft(true);
                    break;

                case KeyEvent.VK_D:
                    getBotboy().setRight(true);
                    break;
                case KeyEvent.VK_ENTER:
                    getBotboy().setAttacking(true);
                    break;

                case KeyEvent.VK_SPACE:
                    getBotboy().setJumping(true);
                    break;

            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    getBotboy().setUp(false);
                    break;

                case KeyEvent.VK_S:
                    getBotboy().setDown(false);
                    break;

                case KeyEvent.VK_A:
                    getBotboy().setLeft(false);
                    break;

                case KeyEvent.VK_D:
                    getBotboy().setRight(false);
                    break;
                case KeyEvent.VK_ENTER:
                    botboy.loadShoot(xLvlOffset);
                    getBotboy().setAttacking(false);
                    break;

                case KeyEvent.VK_SPACE:
                    getBotboy().setJumping(false);
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
