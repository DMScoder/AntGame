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
public abstract class Creature extends Entity implements Attackable{

    public boolean isAttacking = false;
    public boolean isForaging = false;
    private Entity targetEntity = null;
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
        rotatetoAction.setDuration(.5f);
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

    public void moveTowards(Vector2 vector)
    {
        if(isAttacking)
        {
            //rotateTowards(targetEntity);
            return;
        }

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
        //return new Vector2(x,y);
    }

    public void checkSurroundings(Grid grid)
    {
        int x = ((int)getX())/25+grid.size/2;
        int y = ((int)getY())/25+grid.size/2;
        //System.out.println(this.getClass().toString()+" "+x+" "+y+" "+getX()+" "+getY());
        if(!isAttacking)
        {
            for(int i=-1;i<2;i++)
            {
                for(int j=-1;j<2;j++)
                {
                    if(i!=0&&j!=0&&grid.cells[i+x][j+y].entity!=null)
                        if(grid.cells[i+x][j+y].entity instanceof Attackable &&grid.cells[i+x][j+y].entity.getTeam()!=this.getTeam())
                        {
                            isAttacking = true;
                            targetEntity = grid.cells[i+x][j+y].entity;
                            System.out.println(targetEntity.getClass().toString());
                            rotateTowards(targetEntity);
                            break;
                        }
                }
                if(isAttacking)
                    break;
            }
        }
        /*if(isForaging&&!isAttacking)
        {
            for(int i=-1;i<2;i++)
            {
                for(int j=-1;j<2;j++)
                {
                    if(i!=0&&j!=0&&grid.cells[i][j].entity!=null)
                        if(grid.cells[i][j].entity instanceof Attackable &&grid.cells[i][j].entity.getTeam()!=this.getTeam())
                        {
                            isAttacking = true;
                            targetEntity = (Attackable)grid.cells[i][j].entity;
                            break;
                        }
                }
                if(isAttacking)
                    break;
            }
        }*/
    }

    public void setSpeed(float s){speed=s;}

    public void moveTo(float x, float y)
    {
        move(x*25,y*25);
    }

    @Override
    public void takeDamage(float damage) {

    }

    @Override
    public void takePoisonDamage(float damage, int poisonType) {

    }

    @Override
    public void die() {

    }

    @Override
    public float getHealth() {
        return 0;
    }

    @Override
    public float getArmor() {
        return 0;
    }
}
