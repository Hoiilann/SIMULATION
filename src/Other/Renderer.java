package Other;


import Entity.*;
public class Renderer {
    public static final String WHITE_BACKGROUND = "\033[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";
    public static final String RED_BACKGROUND = "\033[41m";
    public void render (Board board, int length,int width){
        for (int i=0;i<length;i++){
            String result = "";
            for(int j = 0;j<width;j++){
                Coordinates coordinates = new Coordinates(i,j);
                if(!board.isSquareNotEmpty(coordinates)){
                    result+= WHITE_BACKGROUND+"   ";
                } else {
                    result+=whoIsOnSquare(board.getEntity(coordinates));

                }
            }
            System.out.println(result+ANSI_RESET);
        }
        System.out.println(" ");
    }

    private String whoIsOnSquare(Entity entity) {
        switch(entity.identityNumber){
            case 1: {
                if (Simulation.endOfTheSimulation==0){
                    return WHITE_BACKGROUND+"\uD83D\uDC3A ";
                } else {
                    return RED_BACKGROUND+"\uD83D\uDC3A ";
                }

            }
            case 2: {
                    return WHITE_BACKGROUND+"\uD83D\uDC11 ";
            }
            case 3: {
                return GREEN_BACKGROUND_BRIGHT+"   ";
            }
            case 4: {
                return WHITE_BACKGROUND+ " ↑ ";
            }
            case 5:{
                return WHITE_BACKGROUND+" ▲ ";
            }
        }
        return "";
    }


}
