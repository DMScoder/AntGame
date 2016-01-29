package Creature;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.mygdx.game.Entity;
import Grid.*;
import com.mygdx.game.World;

import java.util.Random;

/**
 * Created by Immortan on 1/18/2016.
 */
public abstract class Creature extends Entity implements Attackable,DamageCapable{

    public boolean isAttacking = false;
    public boolean isForaging = false;
    public boolean isAlive = true;
    public boolean isFlyer = false;

    private float damage;
    private float armorPiercing;
    private int poisonType = 0;
    public Resource resource = null;

    private float health;
    private float armor;

    private Creature targetCreature = null;
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
            //rotateTowards(targetCreature);
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
    }

    public void attacked(Creature creature)
    {
        if(isAttacking)
            return;
        isAttacking = true;
        targetCreature = creature;
        int d = 1;
        if(creature.getX()<this.getX())
            d=-1;
        rotateTowards(-getX()+creature.getX(),-getY()+creature.getY());
    }

    public void dealDamage()
    {
        if(!isAttacking)
            return;
        targetCreature.attacked(this);
        targetCreature.takeDamage(damage,armorPiercing);
        if(!targetCreature.isAlive) {
            targetCreature = null;
            isAttacking = false;
        }
    }

    public void checkSurroundings(Grid grid)
    {
        int x = ((int)getX())/25+grid.size/2;
        int y = ((int)getY())/25+grid.size/2;

        if(grid.cells[x][y].entity!=this&&grid.cells[x][y].entity!=null&&
                grid.cells[x][y].entity.getPlayer().getTeam()==this.getPlayer().getTeam())
        {
            Random r = new Random();
            moveTo(r.nextInt(3)-1,r.nextInt(3)-1);
        }
        if(!isAttacking)
        {
            for(int i=-1;i<2;i++)
            {
                for (int j = -1; j < 2; j++)
                {
                    checkEnemy(i, j, x, y, grid);
                    if(isAttacking)
                        break;
                }
                if (isAttacking)
                    break;
            }
            if(!isAttacking&&isForaging&&resource==null)
            {
                for(int i=-1;i<2;i++)
                {
                    for (int j = -1; j < 2; j++)
                    {
                        checkResource(i, j, x, y, grid);
                        if (resource!=null)
                            break;
                    }
                    if (resource!=null)
                        break;
                }
            }
        }
    }

    public void update()
    {
        if(resource!=null)
            resource.setPosition(getX()-5,getY()-5);
    }

    public void checkResource(int i, int j, int x, int y, Grid grid)
    {
        if(!grid.cells[i+x][j+y].resources.isEmpty())
        {
            resource = grid.cells[i+x][j+y].getResource();
            resource.pickedUp(this);
        }
    }

    public void checkEnemy(int i, int j, int x, int y, Grid grid)
    {
        if(i!=0&&j!=0&&grid.cells[i+x][j+y].entity!=null)
            if(grid.cells[i+x][j+y].entity instanceof Attackable &&
                    grid.cells[i+x][j+y].entity.getPlayer().getTeam()!=this.getPlayer().getTeam()) {
                if (!((Creature) grid.cells[i + x][j + y].entity).isAlive)
                    return;
                isAttacking = true;
                targetCreature = (Creature) grid.cells[i + x][j + y].entity;
                int d = 1;
                if(targetCreature.getX()<this.getX())
                    d=-1;
                rotateTowards(targetCreature.getX()-this.getX(), targetCreature.getY()-this.getY());
                if (targetCreature instanceof Creature)
                    targetCreature.attacked(this);
            }
    }

    public void forage(World world)
    {
        if(isForaging)
        {
            isForaging=false;
            if(resource!=null)
            {
                resource.drop(this,world);
                resource = null;
            }
        }

        else
            isForaging=true;
    }

    public void setSpeed(float s){speed=s;}

    public void moveTo(float x, float y)
    {
        move(x*25,y*25);
    }

    @Override
    public void takeDamage(float damage, float armorPiercing) {
        float penetration = armor-armorPiercing;

        if(penetration<0)
            penetration=0;
        if(armor>damage)
        {
            penetration=0;
            damage=1;
        }
        health -= damage - penetration;
        if(health<0)
            this.die();
    }

    @Override
    public void takePoisonDamage(float damage, int poisonType) {

    }

    @Override
    public void die() {
        this.isAlive = false;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float getArmor() {
        return armor;
    }

    @Override
    public float getDamage()
    {
        return damage;
    }

    @Override
    public float getArmorPiercing()
    {
        return armorPiercing;
    }

    @Override
    public int getPoison()
    {
        return 0;
    }

    @Override
    public void setDamage(float f)
    {
        damage=f;
    }

    @Override
    public void setArmorPiercing(float f)
    {
        armorPiercing=f;
    }

    @Override
    public void setPoison(int i)
    {
        poisonType=i;
    }

    public void setHealth(float f)
    {
        health=f;
    }

    public void setArmor(float f)
    {
        armor = f;
    }
}
