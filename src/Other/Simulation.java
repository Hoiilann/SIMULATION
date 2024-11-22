package Other;
import Entity.*;

public class Simulation {
    Board board;
    Renderer renderer;
    public static int numberOfTurns = 0;
    Wolf wolf;
    Sheep sheep;
    private final int length;
    private final int width;
    Entity entity;
    public static int endOfTheSimulation=0;
    public static int numberOfGrass = 5;

    public Simulation(Board board, Renderer renderer, int length, int width) {
        this.board = board;
        this.renderer = renderer;
        this.length = length;
        this.width = width;
    }

    public void entityAction() throws ClassCastException {
        int turnFinished;
        for (int k=0;k<2;k++){
            turnFinished =0;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    if (endOfTheSimulation==0){
                        Coordinates coordinates = new Coordinates(i, j);
                        if (board.isSquareNotEmpty(coordinates)) {
                            entity = board.getEntity(coordinates);
                            switch (entity.identityNumber) {
                                case 1: {
                                    wolf = (Wolf) entity;
                                    if (wolf.turn == k){
                                        System.out.println("Волк делает ход");
                                        turnFinished=1;
                                        wolf.makeMove(coordinates,board,entity);
                                    }
                                    break;
                                }
                                case 2: {
                                    sheep = (Sheep) entity;
                                    if (sheep.turn==k) {
                                        System.out.println("Овечка делает ход");
                                        turnFinished=1;
                                        sheep.makeMove(coordinates,board,entity);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (turnFinished==1){
                        break;
                    }
                }
                if (turnFinished==1){
                    break;
                }
            }
        }
        numberOfTurns++;
    }


}