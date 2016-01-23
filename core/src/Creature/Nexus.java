package Creature;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    public static final int FORAGE = 3;
    public int command = 0;

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
        super(0,0,team);
        this.world=world;
        initialize();
    }

    public void initialize()
    {
        if(getTeam()==1) {
            this.setTexture("AntIcon");
            setUiScale(0);
            this.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
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
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);
        batch.setColor(1f,1f,1f,1f);
    }

    public void addCreature(Creature creature)
    {
        members.add(creature);
    }

    public void setUiScale(int amount)
    {
        this.setScale(world.getZoom()*.5f+1);
    }

    public void update(long ticks)
    {
        this.setPosition(getCenter().x,getCenter().y);
        order(ticks);
    }

    private void order(long ticks)
    {
        for(Creature creature : members)
        {
            creature.checkSurroundings(world.grid);
            world.setFootPrint(creature);
        }

        switch(command)
        {
            case(IDLE):
                if(ticks%60==0)
                    for(Creature creature : members)
                    {
                        if(creature.isAttacking)
                            continue;
                        world.clearFootPrint(creature);
                        creature.moveTo(r.nextInt(3)-1,r.nextInt(3)-1);
                        world.setFootPrint(creature);
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
