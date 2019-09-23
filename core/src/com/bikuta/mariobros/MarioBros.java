package com.bikuta.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bikuta.mariobros.screens.PlayScreen;

public class MarioBros extends Game {

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100; //Pixels por metro

	private SpriteBatch batch;

	private boolean debugMode = false;

	public SpriteBatch getBatch(){
		return this.batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public boolean getDebugMode(){
	    return debugMode;
    }

    public void setDebugMode(boolean debugMode){
	    this.debugMode = debugMode;
    }
}