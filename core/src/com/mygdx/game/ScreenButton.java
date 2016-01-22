package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Immortan on 1/19/2016.
 */
public class ScreenButton extends Actor{

    private Texture texture;
    private String name;

    public ScreenButton(float x, float y, String name, Stage stage)
    {
        texture = new Texture(Gdx.files.internal("assets/res/"+name+".png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        this.setPosition(x,y);
        this.name = name;

    }

    public void draw(Batch batch, float alpha)
    {
        /*Vector2 coord = new Vector2(SCREEN_X, SCREEN_Y);
        getStage().screenToStageCoordinates(coord);
        getParent().stageToLocalCoordinates(coord);
        setPosition(coord.x, coord.y);
        setScale(1/getParent().getScaleX());
        super.draw(batch, alpha);*/
    }
}
