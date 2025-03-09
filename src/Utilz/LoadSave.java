package Utilz;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {



    //Função que importa imagens para a váriavel img
    public static BufferedImage getSpriteImage(String fileName){
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


        BufferedImage img= getSpriteImage("/faseboss.png");
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

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
