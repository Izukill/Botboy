package Entidades;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import static Utilz.Constants.BotboyConstants.*;
import static Utilz.Constants.Directions.*;

public abstract class Model {
    protected float x,y;
    private BufferedImage img;
    private List<BufferedImage[]> animation;
    private int aniTick,aniIndex,aniSpeed=20;
    private int BotboyAction= running;
    private int BotboyDir = -1;
    private boolean isMoving;


    public Model(float x,float y){
        this.x=x;
        this.y=y;
        this.importImg();
        this.loadAnimation();

    }

    public void update(){
        this.updateAnimation();
        this.setAniAction();
        this.updatePos();

    }

    public void drawModel(Graphics g){

        g.drawImage(this.getAnimation(), (int)this.getX(),(int)this.getY(),150,150,null);
    }



    //Função para importar a imagem da animação na variável img
    private void importImg() {
        InputStream is= getClass().getResourceAsStream("/Botboy.png");
        try {
            img= ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }





    //Função para carregar a animação dentro do Animation
    protected void loadAnimation() {

        animation =new ArrayList<>();
        int frameWidth = 300;
        int frameHeight = 400;
        int colunas = 3;
        int linhas = 3;

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
            }
        }

    }



    //Função que muda as animações de acordo com o movimento do Botboy
    public void Moves(int direction){
        this.BotboyDir= direction;
        isMoving=true;


    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    protected void setAniAction(){
        if(isMoving){
            setBotboyAction(running);
        }else {
            setBotboyAction(standing);
        }
    }

    protected void updatePos(){
        if(isMoving){
            switch (BotboyDir){
                case 0:
                    y-=5;
                    break;
                case 1:
                    x-=5;
                    break;
                case 2:
                    x+=5;
                    break;
                case 3:
                    y+=5;
            }
        }
    }

    public BufferedImage getAnimation(){
        if(BotboyAction == running ){
            return animation.get(0)[aniIndex];

        } else if (BotboyAction == standing) {
            return animation.get(0)[0];

        }else return animation.get(0)[0];
    }






    public void setBotboyAction(int botboyAction) {
        BotboyAction = botboyAction;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
