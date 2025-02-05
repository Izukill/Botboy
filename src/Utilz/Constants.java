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

        //Aqui é passado o total de imagens que são usadas para todas as animações a cima
        //A função é criada para não ocorrer nenhum OutOfBounds erro
        public static int GetTotalSprites(int BotboyActions){

            switch (BotboyActions){
                case running:
                    return 3;
                case standing:
                    return 1;
                case hit:
                    return 4;
                case jumping:
                    return 1;
                case falling:
                    break;
                case attack:
                    break;
                case airAttack:
                    break;


            }
            return 0;
        }

    }
}
