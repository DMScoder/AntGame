package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Immortan on 1/18/2016.
 */
public abstract class Entity extends Actor{

    public Texture texture;
    private int team=0;
    private int size=0;

    public Entity(float x, float y)
    {
      this.setPosition(x,y);
    }
    public Entity(float x, float y, int team)
    {
        this.setPosition(x,y);this.team = team;
    }

    public void setTexture(String name)
    {
        texture = new Texture(Gdx.files.internal("assets/res/"+name+".png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        this.setOrigin(texture.getWidth()/2,texture.getHeight()/2);
        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
    }

    public int getTeam()
    {
        return team;
    }

    public void setUiScale(int amount) {}

    public int getGridSize()
    {
        return size;
    }

    public void setGridSize(int i)
    {
        size = i;
    }

    public void update(long ticks)
    {

    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        batch.draw(texture,this.getX(),getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);
    }
}
