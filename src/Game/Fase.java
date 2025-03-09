package Game;

import Entidades.Boss;
import Entidades.Botboy;
import Entidades.Shoot;
import Level.LevelManager;
import Utilz.Collision;
import Utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import static Utilz.Constants.GameSizes.*;

public class Fase extends JPanel implements Runnable {
    private Botboy botboy;
    private Boss boss;
    private LevelManager levelManager;
    private Thread gameLoop;
    private boolean inGame=true;
    private boolean endGame=false;



    // Renderização do mapa sobre o personagem
    private int xLvlOffset;
    private int leftBord= (int) (0.3*game_width), rightBord = (int) (0.5*game_width);
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
        boss=new Boss();
        boss.loadLevelData(levelManager.getLevel().getLvlData());

    }

    private void restartGame(){
        inGame = true;
        endGame = false;
        botboy = new Botboy();
        botboy.loadLevelData(levelManager.getLevel().getLvlData());
        boss = new Boss();
        boss.loadLevelData(levelManager.getLevel().getLvlData());




        //Métodos para repintar a tela mostrando o game over
        loadGame();
        repaint();
        revalidate();
    }







    //método para "pintar" os elementos sobre o JPanel com a biblioteca graphics
    public void paintComponent(Graphics g){
        super.paintComponent(g); //sem chamar a superClasse podem ocorrer alguns bugs durante a pintura da fase

        render(g);


    }


    //Função que chama toda a lógica do jogo
    public void loadGame(){

        if(inGame & !endGame){

        botboy.update();
        boss.update();
        levelManager.update();
        //checkBoder();


        boss.moveToBotboy(botboy.getHitbox().x);
        boss.checkAttack(botboy.getHitbox().x);
        if(boss.healthCheck()==0){
            endGame=true;
        }
        if(Collision.isHitboxIntersect(boss.getHitbox(),botboy.getHitbox())){
            botboy.takeDamage(1);
        }



        List<Shoot> shoots= botboy.getShoots();
        Iterator<Shoot> iterator = shoots.iterator();
        while (iterator.hasNext()) {
            Shoot m = iterator.next();
            if (m.isVisible()) {
                    m.shoot();
                if (Collision.isHitboxIntersect(m.getHitbox(),boss.getHitbox())) {
                    boss.takeDamage(1);
                    m.setVisible(false);
                    iterator.remove();
                }
            } else {
                iterator.remove(); // Remover tiros que saíram da tela//
                m=null;
            }
        }


        if(botboy.healthCheck() <=0){//Terminar jogo
            inGame=false;

        }

        }else{
            repaint();
        }









    }


    //Função que muda a borda do mapa no personagem
//    private void checkBoder() {
//        int botBoyX= (int) botboy.getHitbox().x;
//        int diff = botBoyX - xLvlOffset;
//
//
//
//        if(diff > rightBord){
//            xLvlOffset +=diff - rightBord;
//        } else if (diff < leftBord) {
//            xLvlOffset +=diff - leftBord;
//        }
//
//        if(xLvlOffset > maxLvlOffsetX){
//            xLvlOffset= maxLvlOffsetX;
//        } else if (xLvlOffset<0) {
//            xLvlOffset=0;
//        }
//
//    }


    //Função que desenha os modelos no jogo
    public void render(Graphics g){
        //renderBackground(g, xLvlOffset);
        levelManager.draw(g, xLvlOffset);
        botboy.drawModel(g, xLvlOffset);
        boss.drawModel(g, xLvlOffset);


        List<Shoot> shoots= botboy.getShoots();
        for (int i = 0; i < shoots.size(); i++) {
            Shoot m = shoots.get(i);
            m.drawShoot(g, xLvlOffset);
        }

        botboy.drawHealth(g);
        boss.renderBossHealthBar(g);




        endGame(g);

        gameOver(g);






    }


    //Função para carregar a tela de gameOver
    public void gameOver(Graphics g){
        if(!inGame & !endGame){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, game_width, game_height);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", game_width / 2 - 100, game_height / 2 - 100);

            g.setColor(Color.RED);
            g.fillRect(game_width / 2 - 75, game_height / 2, 150, 50);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Play Again", game_width / 2 - 55, game_height / 2 + 35);
        }
    }

    //Função para carregar a tela de endGame
    private void endGame(Graphics g){
        if(endGame){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, game_width, game_height);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("You win", game_width / 2 - 100, game_height / 2 - 100);

            g.setColor(Color.GREEN);
            g.fillRect(game_width / 2 - 90, game_height / 2, 150, 50);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Play Again", game_width / 2 - 87, game_height / 2 + 35);
        }

    }

    public void renderBackground(Graphics g, int xLvlOffset){
        BufferedImage background=LoadSave.getSpriteImage("/background.png");

        int imgWidth= 1248;
        int imgHeight= 672;

        for (int x = -xLvlOffset; x < game_width; x += imgWidth) {
            g.drawImage(background, x, 0, imgWidth, imgHeight, null);
        }


    }



    //Função que vai ser usada para fazer o jogo rodar chamado na Thread
    @Override
    public void run() {


        int fpsLimite = 120;
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

    private class MouseInputs implements MouseListener, MouseMotionListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!inGame || endGame) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int btnX = game_width / 2 - 75;
                int btnY = game_height / 2;
                int btnWidth = 150;
                int btnHeight = 50;

                if (mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY && mouseY <= btnY + btnHeight) {
                    restartGame();
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!inGame || endGame) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int btnX = game_width / 2 - 75;
                int btnY = game_height / 2;
                int btnWidth = 150;
                int btnHeight = 50;

                if (mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY && mouseY <= btnY + btnHeight) {
                    restartGame();
                }
            }

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
