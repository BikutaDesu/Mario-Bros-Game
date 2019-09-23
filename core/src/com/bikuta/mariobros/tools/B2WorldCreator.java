package com.bikuta.mariobros.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.sprites.Brick;
import com.bikuta.mariobros.sprites.Coin;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap tiledMap) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;


        for (int i = 2; i < 6; i++) {
            // Criando os corpos do chão, dos canos, blocos e moedas
            if (i == 4) {
                for (MapObject object : tiledMap.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    new Coin(world, tiledMap, rect);
                }
            } else if (i == 5) {
                for (MapObject object : tiledMap.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    new Brick(world, tiledMap, rect);
                }
            } else {
                for (MapObject object : tiledMap.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();

                    bodyDef.type = BodyDef.BodyType.StaticBody;
                    bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);

                    body = world.createBody(bodyDef);

                    shape.setAsBox((rect.getWidth() / 2) / MarioBros.PPM, (rect.getHeight() / 2) / MarioBros.PPM);
                    fixtureDef.shape = shape;

                    body.createFixture(fixtureDef);
                }
            }
        }
    }

}
