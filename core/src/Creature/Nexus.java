package Creature;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Entity;
import com.mygdx.game.Marker;
import com.mygdx.game.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Immortan on 1/18/2016.
    This class represents a focal point for squads of creatures.
    All creature orders are given through this class.
    Creatures should never be directly interfaced with from outside this class.
 */
public class Nexus extends Entity {

    World world;
    private Random r = new Random();

    public Entity targetEntity = null;
    public Vector2 targetVector = null;
    Marker targetMarker;

    public static final int IDLE = 0;
    public static final int ATTACK = 1;
    public static final int MOVE = 2;
    //public static final int FORAGE = 3;
    public int command = 0;
    public boolean isForaging = false;

    public boolean isSelected = false;
    private ArrayList<Creature> members = new ArrayList<Creature>();

    public Nexus(ArrayList<Creature> creatures,World world,int team)
    {
        super(0,0);
        this.world=world;
        members.addAll(creatures);
        initialize();
    }

    public Nexus(World world,int team)
    {
        super(0,0,2,team);
        this.world=world;
        initialize();
    }

    public void initialize()
    {
        if(getTeam()==1) {
            this.setTexture("SelectionCircle");
            setUiScale(0);
            this.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                    if(buttons == Input.Buttons.LEFT)
                        select();
                    return true;
                }
            });
        }
    }




    public void select()
    {
        world.addNexus(this);
        isSelected = true;
    }

    public void deselect()
    {
        isSelected = false;
    }

    public void setTargetVector(Vector3 vector)
    {
        if(targetVector==null)
            targetVector = new Vector2();
        targetVector.set(vector.x,vector.y);
        command = MOVE;
    }

    public void forage()
    {
        if(isForaging)
        {
            isForaging=false;

        }

    }

    public void setMarker(Marker marker)
    {
        targetMarker = marker;
        targetVector = new Vector2(marker.getX(),marker.getY());
        command = marker.getType();
    }

    public void draw(Batch batch, float alpha)
    {
        if(getTeam()!=1)
            return;
        if(isSelected)
            batch.setColor(Color.GREEN);
        else if(isForaging)
            batch.setColor(Color.YELLOW);
        else if(command==MOVE)
            batch.setColor(Color.BLUE);
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);
        batch.setColor(1f,1f,1f,1f);
        BitmapFont font = world.getFont();
        font.setColor(Color.RED);
        font.getData().setScale(world.getZoom()+1);
        font.draw(batch,""+members.size(),getX()+texture.getWidth()/4-world.getZoom()*10,getY()+texture.getHeight()/(3f/2f));
    }

    public void addCreature(Creature creature)
    {
        members.add(creature);
    }

    public void setUiScale(int amount)
    {
        this.setScale(world.getZoom()*.5f);
    }

    public void update(long ticks)
    {
        this.setPosition(getCenter().x,getCenter().y);
        order(ticks);
    }

    public void spawnResource(String name, Creature creature)
    {
        if(name.equals("Creature.Cricket"))
        {
            Resource resource1 = new Resource(creature.getX(),creature.getY(),"CricketBody",8,creature.getRotation(),world.getFootPrint(creature));
            Resource resource2 = new Resource(creature.getX(),creature.getY(),"CricketHead",4,creature.getRotation(),world.getFootPrint(creature));
            Resource resource3 = new Resource(creature.getX(),creature.getY(),"CricketLeg1",2,creature.getRotation(),world.getFootPrint(creature));
            Resource resource5 = new Resource(creature.getX(),creature.getY(),"CricketLeg2",2,creature.getRotation(),world.getFootPrint(creature));
            Resource resource6 = new Resource(creature.getX(),creature.getY(),"CricketLeg3",2,creature.getRotation(),world.getFootPrint(creature));
            Resource resource7 = new Resource(creature.getX(),creature.getY(),"CricketLeg4",2,creature.getRotation(),world.getFootPrint(creature));
            Resource resource8 = new Resource(creature.getX(),creature.getY(),"CricketLeg5",2,creature.getRotation(),world.getFootPrint(creature));
            world.addResource(resource1);
            world.addResource(resource2);
            world.addResource(resource3);
            world.addResource(resource5);
            world.addResource(resource6);
            world.addResource(resource7);
            world.addResource(resource8);
        }
        else
        {
            Resource resource = new Resource(creature.getX(),creature.getY(),"Default_Ant_Corpse",2,creature.getRotation(),world.getFootPrint(creature));
            world.addResource(resource);
        }
    }

    private void order(long ticks)
    {
        ArrayList remove = new ArrayList();
        for(Creature creature : members)
        {
            if(!creature.isAlive)
            {
                remove.add(creature);
                world.clearFootPrint(creature);
                world.removeEntity(creature);
                spawnResource(creature.getClass().getName(),creature);
                continue;
            }
            if(ticks%5==0)
                creature.checkSurroundings(world.grid);
            world.setFootPrint(creature);
        }

        members.removeAll(remove);
        if(members.isEmpty())
        {
            world.removeEntity(this);
            return;
        }

        if(ticks%30==0)
            for(Creature creature : members)
                creature.dealDamage();

        switch(command)
        {
            case(IDLE):
                    for(Creature creature : members)
                    {
                        if(creature.isAttacking)
                        {
                            command = MOVE;
                            targetVector = new Vector2(creature.getX(),creature.getY());
                        }

                        if(r.nextInt(30)==0)
                        {
                            world.clearFootPrint(creature);
                            creature.moveTo(r.nextInt(3)-1,r.nextInt(3)-1);
                            world.setFootPrint(creature);
                        }
                    }

                break;
            case(MOVE):
                if(targetVector!=null&&compareVectors(getCenter(),targetVector,100f))
                {
                    command = IDLE;
                    targetVector = null;
                }
                else if(targetVector!=null)
                    for(Creature creature : members)
                    {
                        if(creature.isAttacking)
                            continue;
                        world.clearFootPrint(creature);
                        creature.moveTowards(targetVector);
                        world.setFootPrint(creature);
                    }
                break;
        }
    }

    public ArrayList<Creature> getMembers()
    {
        return members;
    }

    public void merge(Nexus otherNexus)
    {
        members.addAll(otherNexus.getMembers());
        otherNexus.members.clear();
        world.removeEntity(otherNexus);
    }

    private boolean compareVectors(Vector2 one, Vector2 two, float margin)
    {
        Vector2 difference = one.sub(two);
        if(Math.abs(difference.x)<margin&&Math.abs(difference.y)<margin)
            return true;
        return false;
    }

    public Vector2 getCenter()
    {
        float sx=0;
        float sy=0;
        for(Creature creature : members) {
            sy += creature.getY();
            sx += creature.getX();
        }
        return new Vector2(sx/members.size(),sy/members.size());
    }
}
