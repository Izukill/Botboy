package Game;

import Utilz.Constants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static Utilz.Constants.BotboyConstants.*;

public class Botboy {
    private BufferedImage img;
    private List<BufferedImage> Animation;
    private int aniTick,aniIndex,aniSpeed=15;
    private int BotboyAction= running;




    Botboy(){

        importImg();
        loadAnimation();


    }




    //Função para carregar a animação dentro do Animation
    protected void loadAnimation() {

        Animation =new ArrayList<BufferedImage>();
        int frameWidth = 300;
        int frameHeight = 400;
        int colunas = 3;
        int linhas = 3;

        // Loop para extrair cada frame
        for (int row = 0; row < linhas; row++) {
            for (int col = 0; col < colunas; col++) {
                BufferedImage frame = img.getSubimage(
                        col * frameWidth, // Posição X
                        row * frameHeight, // Posição Y
                        frameWidth,
                        frameHeight
                );
                Animation.add(frame);
                if(Animation.size()>8){
                    break;
                }
            }
        }

    }


    //Função para controlar a animação
    protected void updateAnimation(){

        aniTick++;
        if(aniTick>= aniSpeed){
            aniTick=0;
            aniIndex++;
            if(aniIndex >= GetTotalSprites(BotboyAction)){
                aniIndex=0;
            }
        }

    }







    private void importImg() {
        InputStream is= getClass().getResourceAsStream("/Botboy.png");
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

    }








    public BufferedImage getImg() {
        return img;
    }

    public List<BufferedImage> getAnimation() {
        return Animation;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getBotboyAction() {
        return BotboyAction;
    }
}
