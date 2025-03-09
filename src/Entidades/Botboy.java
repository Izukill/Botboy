package Entidades;

import Utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Utilz.Constants.BotboyConstants.damage;
import static Utilz.Constants.GameSizes.scale;

public class Botboy extends Model {

    // Posição/Hitbox
    private static float x =200,y =100;
    private float hitboxX =10.0f, hitboxY =10.0f;
    private static final int width = 120;
    private static final int height = 120;

    // Ataque
    private List<Shoot> shoots;
    private long lastShotTime = 0;
    private final long shotCooldown = 500; //Variaveis para controlar o cooldown do ataque
    private boolean isShooting=false;







    public Botboy() {
        super(x, y, width, height, "/Botboy.png");
        startHitbox(x, y, 48*scale, (float)70.5*scale );
        this.loadAnimation("/Botboy.png");

        shoots=new ArrayList<Shoot>();
        this.Health=3;
    }





    //Função que manipula a vida do Botboy
    public int healthCheck(){
        if(this.hitbox.y >= 564){ //Altura do void do jogo
            this.Health=0;
        }

        return this.Health;
    }




    public void drawHealth(Graphics g){
        //Diferentes imagens para cada estágio da vida
        BufferedImage health3= LoadSave.getSpriteImage("/vidabotboy3.png");
        BufferedImage health2= LoadSave.getSpriteImage("/vidabotboy2.png");
        BufferedImage health1= LoadSave.getSpriteImage("/vidabotboy1.png");
        BufferedImage health0= LoadSave.getSpriteImage("/vidabotboy0.png");

        switch (healthCheck()){
            case 3:
                g.drawImage(health3,0,0,350,100,null);
                break;

            case 2:

                g.drawImage(health2,0,0,350,100,null);
                break;

            case 1:

                g.drawImage(health1,0,0,350,100,null);
                break;
            case 0:
                this.setBotboyAction(damage);
                g.drawImage(health0,0,0,350,100,null);
        }
    }



    //Função para carregar os Ataques na tela com cooldown inserido
    public void loadShoot(int lvlOffset) {

        long currentTime = System.currentTimeMillis();

        int direction; //Controlador da diração que o tiro sai

        if (currentTime - lastShotTime >= shotCooldown) {
            if (this.isFacingLeft()) {
                direction = -1;
            } else {
                direction = 1;
            }

            shoots.add(new Shoot((int) ((hitbox.x - hitboxX) - lvlOffset) + 50, (int) (hitbox.y - hitboxY) + 50, direction));
            lastShotTime= currentTime;

            isShooting = true;
        }else isShooting=false;
    }

    public List<Shoot> getShoots() {
        return shoots;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public float botBoyX(){
        return this.hitbox.x;
    }

}





