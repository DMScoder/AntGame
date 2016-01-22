package Creature;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Entity;
import com.mygdx.game.Marker;
import com.mygdx.game.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Immortan on 1/18/2016.
 */
public class Nexus extends Entity {

    World world;
    private Random r = new Random();
    public int creatureCount=0;

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

    public Nexus(ArrayList<Creature> creatures,World world)
    {
        super(0,0);
        this.world=world;
        creatureCount+=creatures.size();
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
        this.setTexture("AntIcon");
        setUiScale(0);
        if(getTeam()==1)
            this.addListener(new InputListener()
            {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                    select();
                    return true;
                }
            });
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

    public void setMarker(Marker marker)
    {
        targetMarker = marker;
        targetVector = new Vector2(marker.getX(),marker.getY());
        command = marker.getType();
    }

    public void draw(Batch batch, float alpha)
    {
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
        creatureCount++;
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
        switch(command)
        {
            case(IDLE):
                if(ticks%15==0)
                    for(Creature creature : members)
                        creature.moveTo(r.nextInt(3)-1,r.nextInt(3)-1);
                break;
            //case(ATTACK)
            //    if(targetEntity!=null)
            //        for(Creature creature : members)
            //            creature.moveTowards(targetEntity);
            //case(FORAGE)
            //    if(targetVector!=null)
            //        for(Creature creature : members)
            //            creature.moveTowards(targetVector);
            //     else
            //         for(Creature creature : members)
            //             creature.forage();
            case(MOVE):
                if(targetVector!=null&&compareVectors(getCenter(),targetVector,50f))
                {
                    command = IDLE;
                    targetVector = null;
                }
                if(targetVector!=null)
                    for(Creature creature : members)
                        creature.moveTowards(targetVector);
                break;
        }
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
        return new Vector2(sx/creatureCount,sy/creatureCount);
    }
}
