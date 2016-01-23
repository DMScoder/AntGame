package com.mygdx.game;

import Creature.Ant;
import Creature.Attackable;
import Creature.Nexus;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Immortan on 1/18/2016.
 */
public class Hive extends Entity{

    boolean deserted = false;
    World world;
    SpawnButton button=null;

    public Hive(float x, float y,World world, int team) {
        super(x, y,team);
        this.world = world;
        this.setTexture("AntHill");
        this.scaleBy(5f);

        if(team==1)
            this.addListener(new InputListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                    if(button==null)
                        createButton();
                    return true;
                }
        });
    }

    public void spawnAnts()
    {
        Nexus nexus = new Nexus(world,getTeam());
        for(int i=0;i<1;i++) {
            Ant ant = new Ant(this.getX(), this.getY());
            ant.setTeam(1);
            nexus.addCreature(ant);
            world.addCreature(ant);
        }
        world.addEntity(nexus);
    }

    public void deleteButton()
    {
        if (button != null) {
            button.remove();
            button = null;
        }
    }

    public void setUiScale(int amount)
    {
        if(button!=null)
            button.setScale(world.getZoom()*.5f+1);
    }

    private void createButton()
    {
        button = new SpawnButton(this.getX(),this.getY()-this.getHeight()*4,this);
        world.addActor(button);
        setUiScale(0);
    }

    public class SpawnButton extends Entity {

        Hive host;

        public SpawnButton(float x, float y,Hive host) {
            super(x, y);
            this.host = host;
            this.setTexture("AntIcon");
            this.addListener(new InputListener()
            {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    spawnAnt();
                    return true;
                }
            });
        }

        private void spawnAnt()
        {
            host.spawnAnts();
        }
    }
}
