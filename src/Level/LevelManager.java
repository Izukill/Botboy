package Level;

import Game.Fase;
import Utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilz.Constants.GameSizes.*;


public class LevelManager {
    private Fase fase;
    private BufferedImage[] levelImg;
    private Level levelOne;

    public LevelManager(Fase fase){
        this.fase=fase;
        levelOne=new Level(LoadSave.getLevelData());

        importLevelimages();

    }




    //Função que importa a imagem do Level
    private void importLevelimages() {
        BufferedImage img= LoadSave.getSpriteImage("/Level.png");
        levelImg= new BufferedImage[48];

        for(int j=0; j < 4; j++){
            for (int i = 0; i < 12 ; i++) {
                int index= j*12 +i;
                levelImg[index]=img.getSubimage(i*32,j*32,32,32);

            }
        }



    }

    public void draw(Graphics g, int lvlOffset) {

        int faseLenght= levelOne.getLvlData()[0].length;

        for (int j = 0; j < tiles_in_height ; j++) {
            for (int i = 0; i < faseLenght ; i++) {
                int index= levelOne.getTotalindex(i,j);
                g.drawImage(levelImg[index],tiles_size * i - lvlOffset,tiles_size * j,tiles_size,tiles_size,null);

            }

        }


    }






    public void update(){

    }


    public Level getLevel(){
        return levelOne;
    }


}






