package com.bikuta.mariobros.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.scenes.Hud;

public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(World world, TiledMap tiledMap, Rectangle bounds, AssetManager assetManager) {
        super(world, tiledMap, bounds, assetManager);
        tileSet = tiledMap.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        if (getCell().getTile().getId() == BLANK_COIN){
            assetManager.get("audio/sounds/bump.wav", Sound.class).play();
        }else{
            setCategoryFilter(MarioBros.DEFAULT_BIT);
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(200);
            assetManager.get("audio/sounds/coin.wav", Sound.class).play();
        }

    }
}
