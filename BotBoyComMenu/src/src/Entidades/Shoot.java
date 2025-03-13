package Entidades;

import Utilz.LoadFase;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Utilz.Constants.GameSizes.game_width;

public class Shoot {
    private float x,y;
    protected Rectangle2D.Float hitbox;
    private float speed= 6.5f;
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

    public void drawShoot(Graphics g, int lvlOffset){
        BufferedImage img= LoadFase.getSpriteImage("/shoot.png");



        g.drawImage(img,(int)this.x,(int)this.y,20,20,null);

    }

    public void shoot(){
        this.x+=speed * direction;
        hitbox.x= this.x;

        if (this.x > game_width) {
            this.isVisible = false; // Tiro invisível após ultrapassar a tela
        }


    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
