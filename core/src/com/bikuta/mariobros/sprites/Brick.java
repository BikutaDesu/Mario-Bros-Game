package com.bikuta.mariobros.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.scenes.Hud;

public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap tiledMap, Rectangle bounds, AssetManager assetManager) {
        super(world, tiledMap, bounds, assetManager);
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(100);
        assetManager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
