package Other;

import Entity.*;
import java.util.Random;
import java.util.HashMap;
public class Board {
    HashMap<Coordinates, Entity> entities = new HashMap<>();
    private final int length;
    private final int width;
    Random rand = new Random();

    public Board(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void setEntity (Coordinates coordinates, Entity entity) {
        entity.coordinates = coordinates;
        entities.put(coordinates,entity);
    }
    public boolean isSquareNotEmpty (Coordinates coordinates){
        return entities.containsKey(coordinates);
    }
    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }
    public void removeEntity(Coordinates coordinates){
        entities.remove(coordinates);
    }


    public void setDefaultEntitiesPositions() {
        int lengthRandom = rand.nextInt(length);
        int widthRandom = rand.nextInt(width);
        int firstTurn = rand.nextInt(2);
        int turnOfWolf;
        int turnOfSheep;
        int foundEmptySquare = 0;
        if (firstTurn == 1){
            turnOfWolf=1;
            turnOfSheep=0;
        } else {
            turnOfWolf = 0;
            turnOfSheep=1;
        }
        setEntity(
                new Coordinates(lengthRandom, widthRandom),
                new Wolf(new Coordinates(lengthRandom,widthRandom),
                        5,3,3,1,turnOfWolf)
        );
        while (foundEmptySquare==0){
            lengthRandom = rand.nextInt(length);
            widthRandom = rand.nextInt(width);
            if (!isSquareNotEmpty(new Coordinates(lengthRandom,widthRandom))) {
                foundEmptySquare=1;
            }
        }
        foundEmptySquare=0;
        setEntity(new Coordinates(lengthRandom, widthRandom),
                new Sheep(new Coordinates(lengthRandom, widthRandom),
                        4,1,2, turnOfSheep
                ));
        for (int i = 0; i<5;i++) {
            while (foundEmptySquare==0){
                lengthRandom = rand.nextInt(length);
                widthRandom = rand.nextInt(width);
                if (!isSquareNotEmpty(new Coordinates(lengthRandom,widthRandom))) {
                    foundEmptySquare=1;
                }
            }
            foundEmptySquare=0;
            setEntity(new Coordinates(lengthRandom, widthRandom),
                    new Grass(new Coordinates(lengthRandom, widthRandom),3)
            );
        }
        for (int i = 0; i<5;i++) {
            while (foundEmptySquare==0){
                lengthRandom = rand.nextInt(length);
                widthRandom = rand.nextInt(width);
                if (!isSquareNotEmpty(new Coordinates(lengthRandom,widthRandom))) {
                    foundEmptySquare=1;
                }
            }
            foundEmptySquare=0;
            setEntity(new Coordinates(lengthRandom, widthRandom),
                    new Tree(new Coordinates(lengthRandom, widthRandom),4)
            );
        }
        for (int i = 0; i<5;i++) {
            while (foundEmptySquare==0){
                lengthRandom = rand.nextInt(length);
                widthRandom = rand.nextInt(width);
                if (!isSquareNotEmpty(new Coordinates(lengthRandom,widthRandom))) {
                    foundEmptySquare=1;
                }
            }
            foundEmptySquare=0;
            setEntity(new Coordinates(lengthRandom, widthRandom),
                    new Rock(new Coordinates(lengthRandom, widthRandom),5)
            );
        }
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}
