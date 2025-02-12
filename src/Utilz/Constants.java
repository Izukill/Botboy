package Utilz;

public class Constants {

    public static class BotboyConstants{
        public static final int standing = 0;
        public static final int running = 1;
        public static final int jumping = 2;
        public static final int hit = 3;
        public static final int falling = 4;
        public static final int attack = 5;
        public static final int airAttack = 6;
        public static final int movingAttack = 7;

        //Aqui é passado o total de imagens que são usadas para todas as animações a cima
        //A função é criada para não ocorrer nenhum OutOfBounds erro
        public static int GetTotalSprites(int BotboyActions) {

            switch (BotboyActions) {
                case running:
                    return 4;
                case standing:
                    return 3;
                case jumping:
                    return 1;
                case hit:
                    return 1;
                case falling:
                    break;
                case attack:
                    return 1;
                case airAttack:
                    return 1;
                case movingAttack:
                    return 4;


            }
            return 1;
        }

    }


    public static class Directions{
            public static final int up = 0;
            public static final int left = 1;
            public static final int right = 2;
            public static final int down = 3;



    }

    public static class GameSizes{
        public final static int tile_def_size=32;
        public final static float scale=1.5f;
        public final static int tiles_in_width=26;
        public final static int tiles_in_height=14;
        public final static int tiles_size= (int) (tile_def_size * scale);
        public final static int game_width= tiles_size * tiles_in_width;
        public final static int game_height= tiles_size * tiles_in_height;


    }
}
