package Entity;

import Other.Board;
import Other.Coordinates;
import Other.Renderer;
import Other.Simulation;

import java.util.ArrayList;
import java.util.Random;
public class Wolf extends Creature{
    private final int attack;
    Random rand = new Random();
    Renderer renderer = new Renderer();
    public Wolf(Coordinates coordinates, int health,int velocity, int attack,int identityNumber,int turn ) {
        super(coordinates,health,velocity,identityNumber,turn);
        this.attack = attack;
    }

    @Override
    public void makeMove(Coordinates coordinates,Board board,Entity entity) {
        Wolf wolf = (Wolf) entity;
        int x = coordinates.getX();
        int y = coordinates.getY();
        int x1, y1;
        boolean check = false;
        int counterOfSteps = 0;
        while(counterOfSteps<wolf.getVelocity()){
            for (int i=-1; i<=1;i++){
                for (int j=-1;j<=1;j++) {
                    check = checkSheep(x + i, y + j, board);
                    x1 = x + i;
                    y1 = y + j;
                    if (check) {
                        Coordinates coordinates1 = new Coordinates(x1, y1);
                        Sheep sheep = (Sheep) board.getEntity(coordinates1);
                        if (sheep.getHealth() > wolf.getAttack()) {
                            int newHealth = sheep.getHealth() - wolf.getAttack();
                            sheep.setHealth(newHealth);
                        } else {
                            board.removeEntity(coordinates1);
                            board.removeEntity(wolf.coordinates);
                            Simulation.endOfTheSimulation = 1;
                            wolf.coordinates = coordinates1;
                            board.setEntity(wolf.coordinates,wolf);
                            renderer.render(board,board.getLength(),board.getWidth());
                            System.out.println("Волчара съел овечку и выжил");
                        }
                        break;
                    }
                }
                if (check){
                    break;
                }
            }
            if (!check){
                int randomTurn;
                int counterOfPossibleTurns = 0;
                ArrayList<Integer> possibleTurns = new ArrayList<>();
                for (int i=-1; i<=1;i++){
                    for (int j=-1;j<=1;j++) {
                        Coordinates coordinates1=new Coordinates(x+i,y+j);
                        if (!board.isSquareNotEmpty(coordinates1) &&
                                coordinates1.getX()< board.getLength() &&
                                coordinates1.getX()>=0 && coordinates1.getY()>=0 &&
                                coordinates1.getY()<board.getWidth()){
                            possibleTurns.add(i);
                            possibleTurns.add(j);
                            counterOfPossibleTurns+=1;
                        }
                    }
                }
                if (counterOfPossibleTurns==0){
                    Simulation.endOfTheSimulation = 1;
                } else{
                    randomTurn = rand.nextInt(counterOfPossibleTurns)+1;
                    board.removeEntity(wolf.coordinates);
                    x+=possibleTurns.get(randomTurn*2-2);
                    y+=possibleTurns.get(randomTurn*2-1);
                    wolf.coordinates= new Coordinates(x,y);
                    board.setEntity(wolf.coordinates,wolf);
                }
            }
            counterOfSteps++;
            if (Simulation.endOfTheSimulation==1){
                counterOfSteps=getVelocity();
            }
        }
        if (Simulation.endOfTheSimulation==0){
            renderer.render(board,board.getLength(),board.getWidth());
            if (Simulation.numberOfTurns>3 && wolf.getHealth()>1){
                    int newWolfHealth= wolf.getHealth()-1;
                    wolf.setHealth(newWolfHealth);
                    System.out.println("Волк голоден, у него осталось: "+ wolf.getHealth()+ " хп");
            }  else if (wolf.getHealth()==1){
                Simulation.endOfTheSimulation =1;
                System.out.println("Волк погиб от голода");
            }
        }
        if (Simulation.endOfTheSimulation==0){
            System.out.println("Волк сделал свой ход");
        }
    }
    public boolean checkSheep (int X,int Y, Board board) {
        Coordinates coordinates = new Coordinates(X,Y);
        if (board.isSquareNotEmpty(coordinates)){
            return board.getEntity(coordinates).identityNumber == 2;
        }
        return false;
    }

    public int getAttack() {
        return attack;
    }
}
