package Entidades;

import Game.Botboy;
import Utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Utilz.Constants.GameSizes.game_width;

public class Shoot {
    private float x,y;
    protected Rectangle2D.Float hitbox;
    private float speed= 4.0f;
    private boolean isVisible;
    private int direction;


    public Shoot(float x, float y, int direction) {
        this.x = x;
        this.y = y;
        this.direction=direction;


        this.isVisible=true;
        startHitbox(x,y,20,20);
    }

    private void startHitbox(float x,float y, float width, float height) {
        hitbox= new Rectangle2D.Float(x,y, width,height );
    }

    private void drawHitbox(Graphics g){
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x,(int)hitbox.y,(int)hitbox.width, (int)hitbox.height);
    }

    public void drawShoot(Graphics g){
        BufferedImage img= LoadSave.getSpriteImage("/shoot.png");
        g.drawImage(img,(int)this.x,(int)this.y,20,20,null);
        drawHitbox(g);

    }

    public void shoot(){
        this.x+=speed * direction;
        hitbox.x= this.x;

        if (this.x > game_width) {
            this.isVisible = false; // Tiro invisível após ultrapassar a tela
        }


    }

    public boolean isVisible() {
        return isVisible;
    }
}
