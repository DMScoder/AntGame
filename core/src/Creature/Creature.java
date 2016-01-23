package Creature;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.mygdx.game.Entity;
import Grid.*;

/**
 * Created by Immortan on 1/18/2016.
 */
public abstract class Creature extends Entity {

    public boolean attacking = false;
    private MoveToAction moveToAction = new MoveToAction();
    private RotateToAction rotatetoAction = new RotateToAction();
    private float speed=10;

    public Creature(float x, float y)
    {
        super(x,y);
    }

    private void move(float x, float y)
    {
        if(this.hasActions())
            return;
        moveToAction.restart();
        moveToAction.setPosition(this.getX()+x,this.getY()+y);
        moveToAction.setDuration(10/speed);
        rotateTowards(x,y);
        this.addAction(moveToAction);
    }

    public void rotateTowards(Actor actor)
    {
        rotatetoAction.setTarget(actor);
        rotatetoAction.setDuration(10/speed);
        this.addAction(rotatetoAction);
    }

    private void rotateTowards(float x, float y)
    {
        rotatetoAction.restart();
        float angle = (float)Math.atan2(y,x);
        angle*=(180/Math.PI);
        rotatetoAction.setRotation(angle-90);
        rotatetoAction.setDuration(.5f);
        this.addAction(rotatetoAction);
    }

    public Vector2 moveTowards(Vector2 vector)
    {
        float x=0;
        float y=0;

        if(vector.x>getX()+25)
            x=1;
        else if(vector.x<getX()-25)
            x=-1;
        if(vector.y>getY()+25)
            y=1;
        else if(vector.y<getY()-25)
            y=-1;
        moveTo(x,y);
        return new Vector2(x,y);
    }

    public void checkSurroundings(Grid grid)
    {

    }

    public void setSpeed(float s){speed=s;}

    public void moveTo(float x, float y)
    {
        move(x*25,y*25);

    }
}
