package Game;

import Entidades.Model;
import Entidades.Shoot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Utilz.Constants.GameSizes.scale;

public class Botboy extends Model {

    // Posição/Hitbox
    private static float x =1000,y =200;
    private float hitboxX =10.0f, hitboxY =10.0f;
    private static final int width = 120;
    private static final int height = 120;

    // Ataque
    private List<Shoot> shoots;





    Botboy() {
        super(x, y, width, height, "/Botboy.png");
        startHitbox(x, y, 48*scale, (float)67.5*scale );

        shoots=new ArrayList<Shoot>();

    }


    //Funcao para desenhar o botboy tem que ser diferente dos outros modelos
    public void drawBotboy(Graphics g, int lvlOffset) {
        int drawX = (int)(hitbox.x - hitboxX) - lvlOffset;
        int drawY = (int)(hitbox.y - hitboxY);

        if (this.isFacingLeft()) {
            g.drawImage(this.getAnimation(), drawX + width, drawY, -width, height, null);
        } else {
            g.drawImage(this.getAnimation(), drawX, drawY, width, height, null);
        }
    }


    public void loadShoot(int lvlOffset){
        int direction;
        if(this.isFacingLeft()){
            direction=-1;
        }else{
            direction=1;
        }

        shoots.add(new Shoot((int)((hitbox.x-hitboxX)-lvlOffset)+50,(int)(hitbox.y-hitboxY)+50,direction));
    }

    public List<Shoot> getShoots() {
        return shoots;
    }



}





