package com.mygdx.game;

import Creature.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
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

    public World(Stage stage,OrthographicCamera camera)
    {
        this.stage = stage;
        this.camera = camera;
        level_script = new Script(1,this);
    }

    public void setUiScale(float zoom,int amount)
    {
        for(Entity entity : entities)
            entity.setUiScale(amount);
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
        stage.addActor(hive);
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

    public void clearFootPrint(Entity entity)
    {
        int x = (int)entity.getX()/25+grid.size/2;
        int y = (int)entity.getY()/25+grid.size/2;

        for(int i=0;i<entity.getGridSize();i++)
            for(int j=0;j<entity.getGridSize();j++)
                grid.cells[i+x][j+y].entity = null;
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
        Vector3 vector2 = new Vector3(w,h,0);
        screenToStageCoordinates(vector1);
        screenToStageCoordinates(vector2);
        Rectangle rectangle = new Rectangle(vector1.x,vector1.y,vector2.x*50,vector2.y*50);
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

    public void addActor(Actor actor)
    {
        stage.addActor(actor);
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
