package Entity;

import Other.Board;
import Other.Coordinates;

public abstract class Creature extends Entity {
    private int health;
    private final int velocity;
    public int turn;
    public Creature(Coordinates coordinates,int health, int velocity,int identityNumber,int turn) {
        super(coordinates,identityNumber);
        this.health = health;
        this.velocity = velocity;
        this.turn = turn;
    }
    public abstract void makeMove(Coordinates coordinates,Board board,Entity entity);

    public int getVelocity() {
        return velocity;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
