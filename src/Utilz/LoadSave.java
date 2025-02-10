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


        BufferedImage img= getImageAtlas("/Level.png");
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];


        for (int lin = 0; lin < img.getHeight(); lin++) {
            for (int col = 0; col < img.getWidth(); col++) {
                int rgb = img.getRGB(col, lin);
                Color color = new Color(rgb, true); // Garante que está pegando a transparência corretamente
                int tileID = color.getRed(); // Pegando apenas o canal vermelho

                // 🔍 Teste para ver os valores
                System.out.println("Pixel (" + col + ", " + lin + ") -> RGB: " + rgb + " | Red: " + color.getRed());

                lvlData[lin][col] = tileID;
            }
        }



        return lvlData;
    }
}
