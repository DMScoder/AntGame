package com.mygdx.game;

import Creature.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;

import Grid.*;
import Script.*;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Immortan on 1/18/2016.
 */
public class World {

    private OrthographicCamera camera;
    private Stage stage;
    private Script level_script;
    public Grid grid = new Grid(1000);
    ArrayList <Marker> markers = new ArrayList<Marker>();
    ArrayList <Entity> entities = new ArrayList<Entity>();
    ArrayList <Hive> Hives = new ArrayList<Hive>();
    ArrayList <Nexus> nexi = new ArrayList<Nexus>();
    long ticks=0;
    Group resources = new Group();
    Group everythingElse = new Group();

    BitmapFont font = new BitmapFont();

    public World(Stage stage,OrthographicCamera camera)
    {
        this.stage = stage;
        this.camera = camera;
        stage.addActor(resources);
        stage.addActor(everythingElse);
        level_script = new Script(1,this);
    }

    public void setUiScale(float zoom,int amount)
    {
        for(Entity entity : entities)
            entity.setUiScale(amount);
    }

    public BitmapFont getFont()
    {
        return font;
    }

    public void clearPopUps()
    {
        ArrayList remove = new ArrayList();
        for(Hive hill : Hives)
            hill.deleteButton();
        int n = markers.size();
        for(int i = 0; i<n;i++)
        {
            Marker marker = markers.get(i);
            marker.removeSelf();
            remove.add(marker);
        }
        markers.removeAll(remove);
        remove.clear();

        n = nexi.size();
        for(int i = 0; i< n;i++)
        {
            Nexus nexus = nexi.get(i);
            nexus.deselect();
            remove.add(nexus);
        }
        nexi.removeAll(remove);
    }

    public void addEntity(Entity entity)
    {
        addActor(entity);
        entities.add(entity);
    }

    public Hive getClosestHive(Entity entity)
    {
        Hive closest = null;

        for(Hive hive : Hives)
        {
            if(hive.getTeam()==entity.getTeam())
            {
                if(closest==null)
                {
                    closest = hive;
                }

                else if(getDistance(closest,entity)>getDistance(hive,entity))
                {
                    closest = hive;
                }
            }
        }
        return closest;
    }

    public float getDistance(Actor actor1, Actor actor2)
    {
        float distanceX = Math.abs(actor1.getX()-actor2.getX());
        float distanceY = Math.abs(actor2.getY()-actor2.getY());

        return distanceX + distanceY;
    }

    public void addNexus(Nexus nexus)
    {
        nexi.add(nexus);
    }

    public void touchDown(int screenX, int screenY)
    {
        Vector3 vector = new Vector3(screenX,screenY,0);
        screenToStageCoordinates(vector);
        if(nexi.size()>1) {
            Nexus newNexus = new Nexus(this,1);
            for (Nexus nexus : nexi) {
                newNexus.merge(nexus);
            }
            nexi.clear();
            addEntity(newNexus);
            newNexus.setTargetVector(vector);
            newNexus.select();
        }
        else if(!nexi.isEmpty())
            nexi.get(0).setTargetVector(vector);
    }

    public void createHive(float x, float y, int team)
    {
        Hive hive = new Hive(x,y,this,team);
        Hives.add(hive);
        entities.add(hive);
        addResource(hive);
    }

    public void createMarker(float screenX,float screenY)
    {
        Vector3 vector = new Vector3(screenX,screenY,0);
        screenToStageCoordinates(vector);
        Marker marker = new Marker(vector,this);
        markers.add(marker);
    }

    public void addMarker(Marker marker)
    {
        for(Nexus nexus : nexi)
        {
            nexus.setMarker(marker);
        }
        stage.addActor(marker);
    }

    public void addCreature(Creature creature)
    {
        setFootPrint(creature);
        entities.add(creature);
        addActor(creature);
    }

    public void removeEntity(Entity entity)
    {
        entities.remove(entity);
        entity.remove();
    }

    public void forage()
    {
        for(Nexus nexus : nexi)
            nexus.forage();
    }

    public void clearFootPrint(Entity entity)
    {
        int x = (int)entity.getX()/25+grid.size/2;
        int y = (int)entity.getY()/25+grid.size/2;

        for(int i=0;i<entity.getGridSize();i++)
            for(int j=0;j<entity.getGridSize();j++)
                grid.cells[i+x][j+y].entity = null;
    }

    public Cell[] getFootPrints(Entity entity)
    {
        Cell[] temp = new Cell[entity.getGridSize()*entity.getGridSize()];

        int x = (int)entity.getX()/25+grid.size/2;
        int y = (int)entity.getY()/25+grid.size/2;

        int k=0;
        for(int i=0;i<entity.getGridSize();i++)
            for(int j=0;j<entity.getGridSize();j++) {
                temp[k] =  grid.cells[i + x][j + y];
                k++;
            }
        return temp;
    }

    public Cell getFootPrint(Entity entity)
    {
        int x = (int)entity.getX()/25+grid.size/2;
        int y = (int)entity.getY()/25+grid.size/2;

        return grid.cells[x][y];
    }

    public void setFootPrint(Entity entity)
    {
        int x = (int)entity.getX()/25+grid.size/2;
        int y = (int)entity.getY()/25+grid.size/2;

        for(int i=0;i<entity.getGridSize();i++)
            for(int j=0;j<entity.getGridSize();j++)
                grid.cells[x+i][y+j].entity = entity;
    }

    public void selection(int x, int y, int w, int h)
    {
        Vector3 vector1 = new Vector3(x,y,0);
        screenToStageCoordinates(vector1);
        Rectangle rectangle = new Rectangle(vector1.x,vector1.y,w*camera.zoom,h*camera.zoom);
        for(Entity entity : entities)
        {
            if(entity instanceof Nexus && entity.getTeam()==1)
            {
                if(rectangle.contains(entity.getX(),entity.getY()))
                    ((Nexus) entity).select();
            }
        }
    }

    public void addCreatures(ArrayList<Creature> creatures)
    {
        for(Creature creature : creatures)
        {
            stage.addActor(creature);
            setFootPrint(creature);
        }
        this.entities.addAll(creatures);
    }

    public void addResource(Actor actor)
    {
        resources.addActor(actor);
    }

    public void addActor(Actor actor)
    {
        everythingElse.addActor(actor);
    }

    public Vector3 screenToStageCoordinates (Vector3 screenCoords) {
        stage.getViewport().unproject(screenCoords);
        return screenCoords;
    }

    public float getZoom()
    {
        return camera.zoom;
    }

    public void update()
    {
        ticks+=1;
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.update(ticks);
        }

        if(ticks%120==0)
            for(int i=0;i<grid.size;i++)
                for(int j=0;j<grid.size;j++)
                    grid.cells[i][j].entity=null;
    }
}
