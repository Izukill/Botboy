package Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Botboy {
    private BufferedImage img;
    private List<BufferedImage> MovAnimation;



    Botboy(){

        importImg();
        loadAnimation();


    }

    private void loadAnimation() {

        MovAnimation=new ArrayList<BufferedImage>();

        for (int i = 0; i < MovAnimation.size(); i++) {
            MovAnimation.add(i,img.getSubimage(i*300,i*400,300,400));
            
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

    public List<BufferedImage> getMovAnimation() {
        return MovAnimation;
    }
}
