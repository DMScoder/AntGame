package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

/*
Main class for all entrypoints
Handles inputs and a bit of stage stuff
Passes most of the work to the master class "World"

Things that should be added:
More creature types
Done! Foraging ability
Research
Biomass/resources in general
Integration with player object
Some sort of terrain
Weather
Animations!
Sounds
Hive options (like upgrade or different worker type)
AI hives
Zoom to cursor
Centipedes and/or worms
DONE! Fix selection box issue
*/

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	Stage stage;
	World world;
	OrthographicCamera camera;
	FPSLogger fps = new FPSLogger();
	ShapeRenderer shapeRenderer;
	boolean drawSelection = false;
	int touchX=0;
	int touchY=0;
	int lastX=0;
	int lastY=0;

	@Override
	public void create () {
		InputMultiplexer multiplexer = new InputMultiplexer();
		stage = new Stage();
		camera = new OrthographicCamera(1980,1080);
		world = new World(stage,camera);
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
		stage.getViewport().setCamera(camera);
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(255/255f, 239/255f, 213/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		handleInput();
		camera.update();
		world.update();
		fps.log();
		if(drawSelection)
			drawSelectionBox();
	}

	private void handleInput()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();

		if(Gdx.input.isKeyPressed(Input.Keys.W))
			camera.translate(0,10f*camera.zoom);
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			camera.translate(-10f*camera.zoom,0);
		if(Gdx.input.isKeyPressed(Input.Keys.S))
			camera.translate(0,-10f*camera.zoom);
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			camera.translate(10f*camera.zoom,0);
	}

	public void drawSelectionBox() {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(touchX,touchY,lastX-touchX,lastY-touchY);
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode)
	{
		if(keycode == Input.Keys.F)
			world.forage();
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if(button == Input.Buttons.RIGHT)
		{
			world.touchDown(screenX,screenY);
			drawSelection = false;
		}


		else if(button == Input.Buttons.LEFT)
		{
			if(!drawSelection)
				world.clearPopUps();
			drawSelection = true;
			touchX = screenX;
			touchY = Gdx.graphics.getHeight()-screenY;
			lastX = touchX;
			lastY = touchY;
		}


		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(drawSelection)
			world.selection(touchX,Gdx.graphics.getHeight()-lastY,Math.abs(lastX-touchX),Math.abs(lastY-touchY));
		drawSelection = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		lastX = screenX;
		lastY = Gdx.graphics.getHeight()-screenY;
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return true;
	}

	@Override
	public boolean scrolled(int amount) {

		if(amount<0&&camera.zoom>.5)
			camera.zoom+=amount*.5;
		else if(amount>0&&camera.zoom<14)
			camera.zoom+=amount*.5;
		else return false;

		world.setUiScale(camera.zoom,amount);
		return false;
	}
}
