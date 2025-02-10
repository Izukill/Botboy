package Entidades;

import Utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

import static Utilz.Constants.BotboyConstants.*;

public abstract class Model {
    protected float x,y;

    private BufferedImage img;
    private List<BufferedImage[]> animation;
    private int aniTick,aniIndex,aniSpeed=20;
    private int BotboyAction= running;

    private boolean up,down,left,right;
    private float modelSpeed=3.0f;
    private boolean isMoving, isAttacking;


    public Model(float x,float y,String imagem){
        this.x=x;
        this.y=y;
        this.loadAnimation(imagem);

    }

    public void update(){
        this.updatePos();
        this.updateAnimation();
        this.setAniAction();


    }

    public void drawModel(Graphics g){

        g.drawImage(this.getAnimation(), (int)this.getX(),(int)this.getY(),150,150,null);
    }







    //Função para carregar a animação dentro do Animation
    protected void loadAnimation(String fileName) {

        BufferedImage img= LoadSave.getImageAtlas(fileName);

        animation =new ArrayList<>();
        int frameWidth = 163;
        int frameHeight = 163;
        int colunas = 5;
        int linhas = 6;

        // Loop para extrair cada frame em um Array bidimensional
        for (int lin = 0; lin < linhas; lin++) {
            BufferedImage[] animFrames = new BufferedImage[colunas];
            for (int col = 0; col < colunas; col++) {
                animFrames[col] = img.getSubimage(
                        col * frameWidth,
                        lin * frameHeight,
                        frameWidth,
                        frameHeight
                );
            }
            animation.add(animFrames);
        }
    }

    //Função para controlar a animação
    protected void updateAnimation(){

        aniTick++;
        if(aniTick>= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= GetTotalSprites(BotboyAction)){
                aniIndex=0;
                isAttacking=false;
            }
        }

    }







    protected void setAniAction(){

        int start= BotboyAction;


        if(isMoving){
            if(isAttacking){
                this.setBotboyAction(movingAttack);

            }else setBotboyAction(running);

        }else if(isAttacking){
            this.setBotboyAction(attack);


        }else setBotboyAction(standing);

        if (start != BotboyAction){
            this.resetAniTick();
        }
    }

    private void resetAniTick() {
        this.aniTick=0;
        if (BotboyAction != movingAttack){
            this.aniIndex=0;
        }
    }

    protected void updatePos(){

        this.isMoving=false; //Colocando o isMoving=false inicialmente para não bugar a animação


        if(left && !right){
            this.x-=modelSpeed;
            this.isMoving=true;

        } else if (right && !left) {
            this.x+=modelSpeed;
            this.isMoving=true;
        }

        if(up && !down){
            this.y-=modelSpeed;
            this.isMoving=true;

        }else if (down && !up){
            this.y+=modelSpeed;
            this.isMoving=true;

        }

    }


    //Função para controlar a animação a depender da ação
    public BufferedImage getAnimation(){
        if(BotboyAction == running ){
            return animation.get(1)[aniIndex];

        } else if (BotboyAction == standing) {
            return animation.get(0)[aniIndex];

        } else if (BotboyAction == attack) {
            return animation.get(1)[4];

        } else if (BotboyAction == movingAttack){
            int[] frames = {1, 2, 3, 4}; // Índices corretos dos frames
            return animation.get(2)[frames[aniIndex % frames.length]];

        }else return animation.get(0)[0];
    }






    public void setBotboyAction(int botboyAction) {
        BotboyAction = botboyAction;
    }



    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }


}
