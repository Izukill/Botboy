package Level;

import Game.Fase;
import Utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static Utilz.Constants.GameSizes.*;

public class LevelManager {
    private Fase fase;
    private List<BufferedImage> levelImg;
    private Level levelOne;

    public LevelManager(Fase fase){
        this.fase=fase;
        levelOne=new Level(LoadSave.getLevelData());

        importLevelimages();

    }


    //Função que importa a imagem do Level
    private void importLevelimages() {
        BufferedImage img= LoadSave.getImageAtlas("/Level.png");
        levelImg=new ArrayList<>();

        int lin= img.getHeight()/tiles_size;
        int col= img.getWidth()/tiles_size;

        for (int y = 0; y < lin; y++) {
            for (int x = 0; x < col; x++) {
                BufferedImage tile = img.getSubimage(x * 32, y * 32, 32, 32);
                levelImg.add(tile);
            }
        }

    }

    public void draw(Graphics g) {

        for (int lin = 0; lin < tiles_in_height; lin++) {
            for (int col = 0; col < tiles_in_width; col++) {
                int index = levelOne.getTotalindex(col, lin);


                if (index < 0 || index >= levelImg.size()) {
                    continue;
                }
                g.drawImage(levelImg.get(index), col * tiles_size, lin * tiles_size, tiles_size, tiles_size, null);
//                if (index == 2) {
//                    g.drawImage(levelImg.get(1), col * tiles_size, lin * tiles_size, tiles_size, tiles_size, null);
//                } else {
//                    g.drawImage(levelImg.get(index), col * tiles_size, lin * tiles_size, tiles_size, tiles_size, null);
//                }


            }

        }
    }

    public void update(){

    }





}
