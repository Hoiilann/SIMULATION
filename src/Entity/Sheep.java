package Entity;

import Other.Board;
import Other.Coordinates;
import Other.Renderer;
import Other.Simulation;

import java.util.ArrayList;
import java.util.Random;

public class Sheep extends Creature{
    Random rand = new Random();
    Renderer renderer = new Renderer();
    public Sheep(Coordinates coordinates, int health,int velocity,int identityNumber, int turn) {
        super(coordinates, health,velocity,identityNumber,turn);
    }

    @Override
    public void makeMove(Coordinates coordinates,Board board, Entity entity) {
        Sheep sheep = (Sheep) entity;
        int x = coordinates.getX();
        int y = coordinates.getY();
        int x1, y1;
        boolean check = false;
        boolean isSheepEscape = false;
        int counterOfSteps = 0;
        while(counterOfSteps<sheep.getVelocity()) {
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    check = checkWolfOrGrass(x + i, y + j, board,1);
                    x1 = x + i;
                    y1 = y + j;
                    if (check) {
                        isSheepEscape = Escape(x1, y1, x, y, board, sheep);
                        break;
                    }
                }
                if (check) {
                    break;
                }
            }
            if (!isSheepEscape){
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        check = checkWolfOrGrass(x + i, y + j, board,3);
                        x1 = x + i;
                        y1 = y + j;
                        if (check) {
                            Coordinates coordinates1 = new Coordinates(x1,y1);
                            board.removeEntity(coordinates1);
                            board.removeEntity(coordinates);
                            sheep.coordinates = coordinates1;
                            board.setEntity(sheep.coordinates,sheep);
                            Simulation.numberOfGrass--;
                            if (Simulation.numberOfGrass ==0){
                                Simulation.endOfTheSimulation =1;
                                renderer.render(board, board.getLength(), board.getWidth());
                                System.out.println("Овца выжила! Она съела всю траву!");
                            }
                            break;
                        }
                    }
                    if (check) {
                        break;
                    }
                }
            }
            if (!isSheepEscape && !check){
                int counterOfPossibleTurns=0;
                ArrayList<Integer> possibleTurns = new ArrayList<>();
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        Coordinates coordinates1 = new Coordinates(x+i,y+j);
                        if(!board.isSquareNotEmpty(coordinates1) &&
                                coordinates1.getX()< board.getLength() &&
                                coordinates1.getX()>=0 && coordinates1.getY()>=0 &&
                                coordinates1.getY()<board.getWidth()){
                            possibleTurns.add(i);
                            possibleTurns.add(j);
                            counterOfPossibleTurns++;
                        }
                    }
                }
                if (counterOfPossibleTurns==0){
                    Simulation.endOfTheSimulation = 1;
                } else {
                    int randomTurn;
                    randomTurn = rand.nextInt(counterOfPossibleTurns)+1;
                    board.removeEntity(sheep.coordinates);
                    x+=possibleTurns.get(randomTurn*2-2);
                    y+=possibleTurns.get(randomTurn*2-1);
                    sheep.coordinates= new Coordinates(x,y);
                    board.setEntity(sheep.coordinates,sheep);
                }
            }
            counterOfSteps++;
            if (Simulation.endOfTheSimulation==1){
                counterOfSteps=getVelocity();
            }
        }
        if (Simulation.endOfTheSimulation==0) {
            renderer.render(board, board.getLength(), board.getWidth());
            System.out.println("Овечка сделала свой ход");
        }
    }
    public boolean Escape(int x1, int y1,int x, int y, Board board,Sheep sheep){
        int counterOfPossibleTurnsBest=0;
        int counterOfPossibleTurnsNormal = 0;
        int counterOfPossibleTurnsReasonable = 0;
        int randomTurn;
        ArrayList<Integer> possibleTurnsBest = new ArrayList<>();
        ArrayList<Integer> possibleTurnsNormal = new ArrayList<>();
        ArrayList<Integer> possibleTurnsReasonable = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coordinates coordinates1 = new Coordinates(x+i,y+j);
                if(!board.isSquareNotEmpty(coordinates1) && (Math.abs(x1-x-i)>=3 || Math.abs(y1-y-j)>=3) &&
                        coordinates1.getX()< board.getLength() &&
                        coordinates1.getX()>=0 && coordinates1.getY()>=0 &&
                        coordinates1.getY()<board.getWidth()){
                    possibleTurnsBest.add(i);
                    possibleTurnsBest.add(j);
                    counterOfPossibleTurnsBest++;
                } else if (board.isSquareNotEmpty(coordinates1)) {
                    if(board.getEntity(coordinates1).identityNumber==3 && (Math.abs(x1-x-i)>=2 || Math.abs(y1-y-j)>=2) &&
                            coordinates1.getX()< board.getLength() &&
                            coordinates1.getX()>=0 && coordinates1.getY()>=0 &&
                            coordinates1.getY()<board.getWidth()){
                        possibleTurnsNormal.add(i);
                        possibleTurnsNormal.add(j);
                        counterOfPossibleTurnsNormal++;
                    }
                } else if (!board.isSquareNotEmpty(coordinates1) && (Math.abs(x1 - x - i) == 2 || Math.abs(y1 - y - j) == 2) &&
                        coordinates1.getX() < board.getLength() &&
                        coordinates1.getX() >= 0 && coordinates1.getY() >= 0 &&
                        coordinates1.getY() < board.getWidth()) {
                    possibleTurnsReasonable.add(i);
                    possibleTurnsReasonable.add(j);
                    counterOfPossibleTurnsReasonable++;
                }
            }
        }
        if (counterOfPossibleTurnsBest!=0){
            randomTurn = rand.nextInt(counterOfPossibleTurnsBest)+1;
            board.removeEntity(sheep.coordinates);
            x+= possibleTurnsBest.get(randomTurn*2-2);
            y+= possibleTurnsBest.get(randomTurn*2-1);
            sheep.coordinates= new Coordinates(x,y);
            board.setEntity(sheep.coordinates,sheep);
            return true;
        } else if (counterOfPossibleTurnsNormal!=0){
            randomTurn = rand.nextInt(counterOfPossibleTurnsNormal)+1;
            board.removeEntity(sheep.coordinates);
            x+= possibleTurnsNormal.get(randomTurn*2-2);
            y+= possibleTurnsNormal.get(randomTurn*2-1);
            sheep.coordinates= new Coordinates(x,y);
            board.setEntity(sheep.coordinates,sheep);
            return true;
        } else if (counterOfPossibleTurnsReasonable!=0){
            randomTurn = rand.nextInt(counterOfPossibleTurnsReasonable)+1;
            board.removeEntity(sheep.coordinates);
            x+= possibleTurnsReasonable.get(randomTurn*2-2);
            y+= possibleTurnsReasonable.get(randomTurn*2-1);
            sheep.coordinates= new Coordinates(x,y);
            board.setEntity(sheep.coordinates,sheep);
            return true;
        }
        return false;
    }
    public boolean checkWolfOrGrass (int X,int Y, Board board,int identification) {
        Coordinates coordinates = new Coordinates(X,Y);
        if (board.isSquareNotEmpty(coordinates) && identification==1){
            return board.getEntity(coordinates).identityNumber == 1;
        }
        if (board.isSquareNotEmpty(coordinates) && identification==3){
            return board.getEntity(coordinates).identityNumber == 3;
        }
        return false;
    }

}


