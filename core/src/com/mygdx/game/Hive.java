package com.mygdx.game;

import Creature.*;
import Grid.Cell;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Immortan on 1/18/2016.
 */
public class Hive extends Entity{

    boolean buttonsCreated = false;
    boolean deserted = false;
    World world;
    SpawnButton[] buttons = new SpawnButton[6];
    public Player player;
    int hiveLevel = 1;

    public Hive(float x, float y,World world, Player player) {
        super(x, y,player);
        this.player = player;
        this.world = world;
        this.setTexture(Player.factionString[player.getFaction()]+"_Hive"+hiveLevel);
        this.scaleBy(2f);
        this.setGridSize(4);

        if(player.getControl()==Player.HUMAN)
            this.addListener(new InputListener()
        {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                    if(!buttonsCreated)
                    {
                        createButton();
                        buttonsCreated = true;
                    }

                    return true;
                }
        });
    }

    public Nexus spawnAnts(int type)
    {
        Nexus nexus = new Nexus(world,player);
        for(int i=0;i<10;i++) {
            ColonyUnit worker = new ColonyUnit(getX(),getY(),player,type);
            nexus.addCreature(worker);
            world.addCreature(worker);
        }
        world.addEntity(nexus);
        return nexus;
    }

    public void deleteButton()
    {
        int i= 0;
        if (buttons[0]!=null) {
            for(SpawnButton button : buttons)
            {
                world.removeEntity(button);
                buttons[i] = null;
                i++;
            }
        }
        buttonsCreated = false;
    }

    public void setUiScale(int amount)
    {
        if(buttons[0]!=null) {
            for (int i = 0; i < buttons.length; i++)
                buttons[i].setScale(world.getZoom() * .5f + .5f);

            buttons[1].setX(buttons[0].getX() + buttons[0].getWidth()*world.getZoom()*.5f+15f);

            buttons[2].setX(buttons[1].getX() + buttons[1].getWidth()*world.getZoom()*.5f+15f);

            buttons[3].setY(buttons[0].getY()- buttons[0].getHeight()*world.getZoom()*.5f-15f);

            buttons[4].setX(buttons[3].getX() + buttons[3].getWidth()*world.getZoom()*.5f+15f);
            buttons[4].setY(buttons[0].getY()- buttons[0].getHeight()*world.getZoom()*.5f-15f);

            buttons[5].setX(buttons[4].getX() + buttons[4].getWidth()*world.getZoom()*.5f+15f);
            buttons[5].setY(buttons[0].getY() - buttons[0].getHeight()*world.getZoom()*.5f-15f);
        }
    }

    private void createButton()
    {
        buttons[0] = new SpawnButton(this.getX(),this.getY()-this.getHeight()*3,ColonyUnit.WORKER,this);
        buttons[1] = new SpawnButton(this.getX()+64,this.getY()-this.getHeight()*3,ColonyUnit.SOLDIER,this);
        buttons[2] = new SpawnButton(this.getX()+128,this.getY()-this.getHeight()*3,ColonyUnit.FLYER,this);
        buttons[3] = new SpawnButton(this.getX(),-64+this.getY()-this.getHeight()*3,ColonyUnit.FORAGER,this);
        buttons[4] = new SpawnButton(this.getX()+64,-64+this.getY()-this.getHeight()*3,ColonyUnit.SCOUT,this);
        buttons[5] = new SpawnButton(this.getX()+128,-64+this.getY()-this.getHeight()*3,ColonyUnit.QUEEN,this);
        for(int i=0;i<buttons.length;i++)
            world.addFirstTier(buttons[i]);
        setUiScale(0);
    }

    public void update(long ticks)
    {
        if(ticks%10==0)
        {
            Cell[] cells = world.getFootPrints(this);
            for(int i=0;i<cells.length;i++)
            {
                if(cells[i].entity!=null&&cells[i].entity instanceof ColonyUnit)
                    if(((ColonyUnit)cells[i].entity).getPlayer().getFaction()==this.player.getFaction()&&((Creature) cells[i].entity).resource!=null)
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
        int type;

        public SpawnButton(float x, float y,int type,Hive host) {
            super(x, y);
            this.host = host;
            this.type=type;
            this.setTexture("Icon_"+ColonyUnit.unitString[type]);
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
            host.spawnAnts(type);
        }
    }
}
