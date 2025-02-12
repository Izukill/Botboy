package Utilz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static Utilz.Constants.GameSizes.*;

public class LoadSave {



    //Função que importa imagens para a váriavel img
    public static BufferedImage getImageAtlas(String fileName){
        BufferedImage img;
        InputStream is= LoadSave.class.getResourceAsStream(fileName);

        try {
            img= ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return img;

    }


    //Função para criar o Level Design
    public static int[][] getLevelData(){


        BufferedImage img= getImageAtlas("/level_one_data.png");
        int[][] lvlData = new int[tiles_in_height][tiles_in_width];

        for (int j = 0; j < img.getHeight() ; j++) {
            for (int i = 0; i < img.getWidth() ; i++) {
                Color color=new Color(img.getRGB(i,j));
                int value=color.getRed();
                if(value >=48){
                    value=0;
                }
                lvlData[j][i]= value;

            }
        }

        return lvlData;
    }
}
