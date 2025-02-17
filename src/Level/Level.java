package Level;

import java.util.ArrayList;

public class Level {
    private int[][] lvlData;


    public Level(int[][] lvlData){
        this.lvlData=lvlData;


    }


    public int getTotalindex(int x,int y){
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }



}
