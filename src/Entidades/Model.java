package Entidades;

import Utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

import static Utilz.Collision.*;
import static Utilz.Constants.BotboyConstants.*;
import static Utilz.Constants.GameSizes.scale;

public abstract class Model {
    //Posição
    protected float x,y;

    //Hitbox

    protected Rectangle2D.Float hitbox;
    protected int width,height;
    private int[][] lvlData;


    //Animação
    private List<BufferedImage[]> animation;
    private int aniTick,aniIndex,aniSpeed=20;
    private int BotboyAction= running;


    //Movimento
    private boolean up,down,left,right;
    private float modelSpeed=3.0f;
    private boolean isMoving, isAttacking;

    //Gravidade
    private float airSpeed = 0f;
    private float gravity = 0.04f*scale;
    private float jumpSpeed = -3.25f * scale;
    private float fallSpeed = 0.5f *scale;
    private boolean inAir = false;
    private boolean jump;


    public Model(float x,float y,int width,int height, String imagem){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.loadAnimation(imagem);

    }

    //Método usado para debugar a hitbox
    private void drawHitbox(Graphics g){
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x,(int)hitbox.y,(int)hitbox.width, (int)hitbox.height);
    }

    protected void startHitbox(float x,float y, float width, float height) {
        hitbox= new Rectangle2D.Float(x,y, width,height );
    }

//    protected void updateHitbox(){
//        hitbox.x= (int) x;
//        hitbox.y= (int) y;
//
//    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public void loadLevelData(int[][] lvlData){
        this.lvlData= lvlData;
        if(!IsModelOnFloor(this.hitbox,lvlData)){ //If que faz o personagem cair no começo do jogo
            this.inAir=true;
        }
    }

    public void update(){
        this.updatePos();
        //this.updateHitbox();
        this.updateAnimation();
        this.setAniAction();


    }

    public void drawModel(Graphics g){

    }







    //Função para carregar a animação dentro do Animation
    protected void loadAnimation(String fileName) {

        BufferedImage img= LoadSave.getSpriteImage(fileName);

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

        if(jump){
            this.setBotboyAction(jumping);
            if (isAttacking){
                this.setBotboyAction(airAttack);
            }
        }

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

    protected void updatePos() {
        this.isMoving = false; //Inicida como false

        if (jump) {
            jump();
        }

        // Se não há movimento horizontal e não está no ar, retorna
        if (!left && !right && !inAir) {
            return;
        }

        // Reseta as mudaças de posição para não atualizar infinito
        float dX = 0;

        if (left) {
            dX -= modelSpeed;
            this.isMoving = true;
        }
        if (right) {
            dX += modelSpeed;
            this.isMoving = true;
        }

        //Verificando se está no ar
        if (!inAir) {
            if (!IsModelOnFloor(this.hitbox, lvlData)) {
                this.inAir = true;
            }
        }

        if (inAir) {
            if (canMove(this.hitbox.x, this.hitbox.y + airSpeed, this.hitbox.width, this.hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                UpdateXpos(dX);
            } else {
                hitbox.y = getModelY(this.hitbox, airSpeed);
                if (airSpeed > 0) {
                    this.resetInAir();
                } else {
                    this.airSpeed = fallSpeed;
                }
                UpdateXpos(dX);
            }
        } else {
            UpdateXpos(dX);
        }

        // Se após as verificações ele não está no ar, resetamos inAir
        if (IsModelOnFloor(this.hitbox, lvlData)) {
            resetInAir();
        }else {
            this.inAir = true;
        }


    }


    private void jump() {
        if(inAir){
            return;
        }else{
            this.inAir=true;
            this.airSpeed= this.jumpSpeed;
        }
    }

    private void resetInAir(){
        this.inAir=false;
        this.airSpeed=0;
        this.jump=false;
    }

    private void UpdateXpos(float dX) {
        if(canMove(this.hitbox.x+dX,hitbox.y,hitbox.width,hitbox.height,lvlData)){
            this.hitbox.x+=dX;
        }else{
            this.hitbox.x= getModelX(hitbox, dX);
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
            int[] frames = {1, 2, 3, 4}; //Index específicos da animação
            return animation.get(2)[frames[aniIndex % frames.length]];

        }else if (BotboyAction == jumping){
            return animation.get(0)[4];

        }else if (BotboyAction == airAttack){
            return animation.get(2)[0];
        }else if (BotboyAction == damage){
            return animation.get(3)[1];
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

    public void setJumping(boolean jump){
        BotboyAction= jumping;
        this.jump=true;

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

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public float getAirSpeed() {
        return airSpeed;
    }

    public void setAirSpeed(float airSpeed) {
        this.airSpeed = airSpeed;
    }

}
