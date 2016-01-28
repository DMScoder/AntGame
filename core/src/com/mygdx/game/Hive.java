package com.mygdx.game;

import Creature.*;
import Grid.Cell;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Immortan on 1/18/2016.
 */
public class Hive extends Entity{

    boolean deserted = false;
    World world;
    SpawnButton button=null;
    public Player player;
    int hiveLevel = 1;

    public Hive(float x, float y,World world, Player player) {
        super(x, y,player.getTeam());
        this.player = player;
        this.world = world;
        this.setTexture(Player.factionString[player.getFaction()]+"_Hive"+hiveLevel);
        this.scaleBy(2f);
        this.setGridSize(9);
        this.setTeam(player.getTeam());

        if(player.getControl()==Player.HUMAN)
            this.addListener(new InputListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                    if(button==null)
                        createButton();
                    return true;
                }
        });
    }

    public Nexus spawnAnts()
    {
        Nexus nexus = new Nexus(world,player);
        for(int i=0;i<10;i++) {
            ColonyUnit worker = new ColonyUnit(getX(),getY(),player.getFaction(),ColonyUnit.WORKER);
            worker.setTeam(player.getTeam());
            nexus.addCreature(worker);
            world.addCreature(worker);
        }
        world.addEntity(nexus);
        return nexus;
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

    public void update(long ticks)
    {
        if(ticks%10==0)
        {
            Cell[] cells = world.getFootPrints(this);
            for(int i=0;i<cells.length;i++)
            {
                if(cells[i].entity!=null&&cells[i].entity instanceof Creature)
                    if(((ColonyUnit)cells[i].entity).getFaction()==this.player.getFaction()&&((Creature) cells[i].entity).resource!=null)
                    {
                        Resource resource = ((Creature) cells[i].entity).resource;
                        ((Creature) cells[i].entity).resource = null;
                        updateResource(resource);
                    }
            }
        }

    }

    public void updateResource(Resource resource)
    {;
        resource.remove();
    }

    public class SpawnButton extends Entity {

        Hive host;

        public SpawnButton(float x, float y,Hive host) {
            super(x, y,0);
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
