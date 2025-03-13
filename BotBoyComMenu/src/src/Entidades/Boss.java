package Entidades;

import Utilz.LoadFase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilz.Constants.BotboyConstants.*;
import static Utilz.Constants.GameSizes.game_width;
import static Utilz.Constants.MetalKnightConstants.*;

public class Boss extends Model {

    private static float x=600,y=300;
    private static final int width=350,height=400;
    private float moveSpeed=0.6f;
    
    
    private int MetalKnightAction=idle;
    private boolean takingDamage=false;
    private boolean isDying=false;
    private BufferedImage img= LoadFase.getSpriteImage("/MetalKnight.png");
    




    public Boss() {
        super(x, y, width, height, "/MetalKnight.png");
        startHitbox(x, y, 210, 300);

        this.Health=6;
    }




    //TakeDamage diferente do model pois ele não pode ter invecibilidade implementada
    public void takeDamage(int damage) {
        this.takingDamage = true;
        this.Health -= damage;

    }



    //Função pra se mover sempre em direção ao botboy
    public void moveToBotboy(float playerX) {


        //Calcula a direção
        float deltaX = playerX - this.hitbox.x;

        //Calcula a distância e reduz a velocidade em que o boss anda
        float distance = (float) Math.sqrt(deltaX * deltaX);
        if (distance != 0) {
            deltaX /= distance;
        }

        if(playerX>this.hitbox.x){
            setFacingLeft(true);
        }else{
            setFacingLeft(false);
        }


        //Atualiza a posição do boss
        this.hitbox.x += deltaX * moveSpeed;
    }

    public void attack() {
        if (!isAttacking) {
            setMetalKnightAction(attacking);
            isAttacking = true;
        }
    }




    //Função para checar se o boss pode ou não atacar o botboy
    public void checkAttack(float playerX) {

        float attackRange;
        if(isFacingLeft()){
             attackRange=400.0f;
        }else attackRange=200.0f;

        // Calcula a distância entre o Botboy e o Boss
        float deltaX = playerX - this.hitbox.x;
        float distance = (float) Math.abs(deltaX);


        if (distance < attackRange) {
            attack();
        }
    }











    protected void loadAnimation(String fileName) {

        BufferedImage img= LoadFase.getSpriteImage(fileName);

        animation =new ArrayList<>();
        int frameWidth = 76;
        int frameHeight = 70;
        int colunas = 5;
        int linhas = 2;

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

    protected void updateAnimation(){

        aniTick++;
        if(aniTick>= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= GetTotalSprites(MetalKnightAction)){
                aniIndex=0;
                isAttacking=false;
                if(takingDamage){
                    takingDamage = false;
                    setMetalKnightAction(idle);
                }

            }
        }

    }

    protected void resetAniTick() {
        this.aniTick=0;
        if (MetalKnightAction != attack){
            this.aniIndex=0;
        }
    }

    protected void setAniAction(){

        int start = MetalKnightAction;

        if (takingDamage) {
            setMetalKnightAction(hurt);
        } else if (isAttacking) {
            setMetalKnightAction(attacking);
        } else if (isDying) {
            setMetalKnightAction(die);
        } else {
            setMetalKnightAction(idle);
        }

        if (start != MetalKnightAction) {
            this.resetAniTick();
        }
    }

    public BufferedImage getAnimation(){
        if(MetalKnightAction == attacking){
            return animation.get(0)[aniIndex];
        } else if (MetalKnightAction == hurt) {
            return animation.get(1)[4];
        } else if (MetalKnightAction == die) {
            return animation.get(1)[aniIndex];
        }else return animation.get(0)[0];
    }

    public void renderBossHealthBar(Graphics g) {
        int barWidth = 300;
        int barHeight = 20;
        int x = (game_width / 2) - (barWidth / 2);
        int y = 20;

        // Cores da barra
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        // Calcular vida restante
        int healthWidth = (int) ((healthCheck() / (float) getMaxHealth()) * barWidth);

        g.setColor(Color.RED);
        g.fillRect(x, y, healthWidth, barHeight); // Vida do Boss

        // Contorno da barra
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
    }

    private int getMaxHealth() {
        return 6;
    }


    public void setMetalKnightAction(int metalKnightAction) {
        MetalKnightAction = metalKnightAction;
    }


    public void setTakingDamage(boolean takingDamage) {
        this.takingDamage = takingDamage;
    }

    public void setDying(boolean dying) {
        isDying = dying;
    }
}
