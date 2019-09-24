package com.bikuta.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bikuta.mariobros.screens.PlayScreen;

public abstract class Enemy extends Sprite {

    protected PlayScreen screen;

    protected World world;
    public Body body;

    public Enemy(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

}
