package Creature;

/**
 * Created by Immortan on 1/23/2016.
 */
public class Cricket extends Creature{

    public Cricket(float x, float y)
    {
        super(x,y);
        this.setTexture("Cricket");
        this.setTeam(2);
        this.setSpeed(10);
        this.setGridSize(2);
    }
}
