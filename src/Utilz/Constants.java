package Utilz;

public class Constants {

    public static class BotboyConstants{
        public static final int standing = 0;
        public static final int running = 1;
        public static final int jumping = 2;
        public static final int damage = 3;
        public static final int attack = 4;
        public static final int airAttack = 5;
        public static final int movingAttack = 6;

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
                case damage:
                    return 1;
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

    public static class MetalKnightConstants{
        public static final int idle=0;
        public static final int attacking=1;
        public static final int hurt=2;
        public static final int die=3;


        public static int GetTotalSpriteboss(int MetalKnightActions){

            switch (MetalKnightActions){
                case idle:
                    return 1;
                case attacking:
                    return 5;
                case hurt:
                    return 1;
                case die:
                    return 2;
            }
            return 1;
        }


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
