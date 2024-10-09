package Entity;

import Other.Coordinates;

public abstract class Entity {
    public Coordinates coordinates;
    public final int identityNumber;
    public Entity(Coordinates coordinates,int identityNumber) {
        this.coordinates = coordinates;
        this.identityNumber = identityNumber;
    }
}
