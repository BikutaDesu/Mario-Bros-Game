package com.bikuta.mariobros.sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.screens.PlayScreen;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap tiledMap;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    protected Fixture fixture;

    protected AssetManager assetManager;

    public InteractiveTileObject(PlayScreen screen, Rectangle bounds, AssetManager assetManager){
        this.tiledMap = screen.getTiledMap();
        this.world = screen.getWorld();
        this.bounds = bounds;
        this.assetManager = assetManager;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBros.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioBros.PPM);

        body = world.createBody(bodyDef);

        shape.setAsBox((bounds.getWidth() / 2) / MarioBros.PPM, (bounds.getHeight() / 2) / MarioBros.PPM);
        fixtureDef.shape = shape;

        fixture = body.createFixture(fixtureDef);
    }

    public abstract void onHeadHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    // Dentro de uma layer no tiledmap existem várias células
    public TiledMapTileLayer.Cell getCell(){
        // Pegando a segunda layer
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        // Retornando a célula dentro da layer, para pegar a posição correta é necessário multiplicar pelo PPM (pixels por metro)
        // Como o Box2D trabalha com metros um tile de 16x16 ficaria com 0,16 metros, então multiplicamos por 100 para ajustar para 16
        return layer.getCell((int)(body.getPosition().x * MarioBros.PPM / 16),
                (int)(body.getPosition().y * MarioBros.PPM / 16));
    }

}
