package Game;

import Entidades.Model;

import java.awt.*;

import static Utilz.Constants.GameSizes.scale;

public class Botboy extends Model {

    // Posição/Hitbox
    private static float x =200,y =200;
    private float hitboxX =10.0f, hitboxY =10.0f;
    private static int width = 120, height = 120;




    Botboy() {
        super(x, y, width, height, "/Botboy.png");
        startHitbox(x, y, 48*scale, (float)67.5*scale );


    }


    @Override
    public void drawModel(Graphics g) {
        g.drawImage(this.getAnimation(), (int)(hitbox.x-hitboxX),(int)(hitbox.y-hitboxY),this.width,this.height,null);
        super.drawModel(g);
    }



}





