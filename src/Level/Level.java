package Level;

import java.util.ArrayList;

public class Level {
    private int[][] lvlData;


    public Level(int[][] lvlData){
        this.lvlData=lvlData;
        startVoid();

    }

    private void startVoid() {
        //voids= LevelManager.getVoid(LoadSave.getSpriteImage("Botboy.png"));
    }

    public int getTotalindex(int x,int y){
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }



}
