package com.mygdx.game;


import Creature.Nexus;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Immortan on 1/19/2016.
 */
public class Marker extends Entity{

    int type;
    boolean set = false;
    MarkerButton move;
    MarkerButton attack;
    MarkerButton forage;
    World world;
    int nexuscount=0;



    public Marker(Vector3 vector, World world) {
        super(vector.x/25*25, vector.y/25*25);
        this.world = world;
        move = new MarkerButton(vector.x-50f*world.getZoom(),vector.y,this,"MoveMarker");
        attack = new MarkerButton(vector.x,vector.y,this,"AttackMarker");
        forage = new MarkerButton(vector.x+50f*world.getZoom(),vector.y,this,"ForageMarker");
        world.addActor(move);
        world.addActor(attack);
        world.addActor(forage);
        setUiScale(0);
    }

    public void confirmMarker(String string)
    {
        this.setTexture(string);
        if(string.equals("MoveMarker"))
            type = Nexus.MOVE;
        else if(string.equals("AttackMarker"))
            type = Nexus.ATTACK;
        removeSelf();
        world.addMarker(this);
        set = true;
        setUiScale(0);
    }

    public void setUiScale(int amount)
    {
        float zoom = world.getZoom()+1;
        if(set)
        {
            this.setScale(zoom);
            return;
        }

        int positive = 1;
        if(amount<0)
            positive=-1;

        move.setScale(zoom);
        attack.setScale(zoom);
        forage.setScale(zoom);
        move.setX(move.getX()-zoom*5*positive);
        forage.setX(forage.getX()+zoom*5*positive);
    }

    public void removeSelf()
    {
        move.remove();
        attack.remove();
        forage.remove();
    }

    public void delete()
    {
        this.remove();
    }

    public void removenexus()
    {
        nexuscount--;
        if(nexuscount<=0)
            this.delete();
    }

    public void addNexus()
    {
        nexuscount++;
    }

    public int getType()
    {
        return type;
    }

    public class MarkerButton extends Entity {

        Marker host;
        String type;

        public MarkerButton(float x, float y,Marker host, String type) {
            super(x, y);
            this.host = host;
            this.type=type;
            this.setTexture(type);
            this.addListener(new InputListener()
            {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    confirmMarker();
                    return true;
                }
            });
        }

        private void confirmMarker()
        {
            host.confirmMarker(type);
        }
    }
}
