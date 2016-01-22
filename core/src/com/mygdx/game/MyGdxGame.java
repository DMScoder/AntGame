package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	Stage stage;
	World world;
	OrthographicCamera camera;
	FPSLogger fps = new FPSLogger();

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
	public boolean keyDown(int keycode) {
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
			world.touchDown(screenX,screenY);

		else if(button == Input.Buttons.LEFT)
			world.clearPopUps();

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
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
