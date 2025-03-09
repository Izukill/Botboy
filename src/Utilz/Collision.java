package Utilz;

import java.awt.geom.Rectangle2D;

import static Utilz.Constants.GameSizes.*;

public class Collision {





    public static boolean isHitboxIntersect(Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2) {
        return hitbox1.intersects(hitbox2);
    }


    //Função que verifica se o objeto pode se mover em uma posição
    public static boolean canMove(float x,float y, float width, float height,int[][] lvlData){


        //IFs que verificam se as quatro bortas do objeto podem se mover
        if(!isSolid(x,y,lvlData)){ //superior esquerdo
            if(!isSolid(x+width,y+height,lvlData)){ //inferior direito
                if(!isSolid(x+width,y,lvlData)){ //superior direito
                    if(!isSolid(x,y+height,lvlData)){ //inferior esquerdo
                        return true; //caso nenhuma posição ao redor das 4 for sólida retorna true
                    }
                }
            }
        }
        else return false;

        return false;
    }





    //Função que determina se o ponto do mapa é sólido ou não
    public static boolean isSolid(float x,float y,int[][] lvlData){

        int maxFaseWidth = lvlData[0].length*tiles_size;

        float xIndex= x/tiles_size;
        float yIndex= y/tiles_size;

        if(x<0 || x>=maxFaseWidth){
            return true;
        }
        if(y<0 || y>=game_height){
            return true;
        }

        int roof= lvlData[(int)yIndex][(int)xIndex];
        if (roof >=48 || roof <0 || roof!=11){
            return true;
        }else return false;


    }

    public static float getModelX(Rectangle2D.Float hitbox,float dX){
        if (dX > 0) { //indo para a direita

            int tileX = (int) ((hitbox.x + hitbox.width) / tiles_size);
            return tileX * tiles_size - hitbox.width;

        } else { //indo para a esquerda
            int tileX = (int) (hitbox.x / tiles_size);
            return tileX * tiles_size;
        }

    }

    public static float getModelY(Rectangle2D.Float hitbox,float AirSpeed){
        float targetY;
        if (AirSpeed > 0) { //Caindo
            targetY = (int) (hitbox.y / tiles_size) * tiles_size + tiles_size - hitbox.height;
        } else { //Pulando
            targetY = (int) (hitbox.y / tiles_size) * tiles_size;
        }
        return Math.max(targetY, hitbox.y); //Evita que o player afunde no chão da fase


    }

    public static boolean IsModelOnFloor(Rectangle2D.Float hitbox,int [][] lvldata){
        float bottomLeftX = hitbox.x +1;
        float bottomRightX = hitbox.x + hitbox.width -1;
        float bottomY = hitbox.y + hitbox.height + 1; //Verifica logo abaixo do model

        return isSolid(bottomLeftX, bottomY, lvldata) || isSolid(bottomRightX, bottomY, lvldata);
    }
}


