package com.bikuta.mariobros.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.bikuta.mariobros.MarioBros;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap tiledMap;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap tiledMap, Rectangle bounds){
        this.tiledMap = tiledMap;
        this.world = world;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBros.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioBros.PPM);

        body = world.createBody(bodyDef);

        shape.setAsBox((bounds.getWidth() / 2) / MarioBros.PPM, (bounds.getHeight() / 2) / MarioBros.PPM);
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
    }

}
