package com.bikuta.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.screens.PlayScreen;

public class Goomba extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getTextureAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / MarioBros.PPM, 16 / MarioBros.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getTextureAtlas().findRegion("goomba"), 32, 0, 16, 16));
        }else if (!destroyed){
            setPosition(body.getPosition().x - (getWidth() / 2), body.getPosition().y - (getHeight() / 2));
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX() / MarioBros.PPM, getY() / MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(7 / MarioBros.PPM);
        fixtureDef.filter.categoryBits = MarioBros.ENEMY_BIT;
        fixtureDef.filter.maskBits = MarioBros.GROUND_BIT |
                MarioBros.COIN_BIT |
                MarioBros.BRICK_BIT |
                MarioBros.ENEMY_BIT |
                MarioBros.MARIO_BIT |
                MarioBros.OBJECT_BIT;
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);

        PolygonShape headShape = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-5, 8).scl(1 / MarioBros.PPM);
        vertices[1] = new Vector2(5, 8).scl(1 / MarioBros.PPM);
        vertices[2] = new Vector2(-3, 3).scl(1 / MarioBros.PPM);
        vertices[3] = new Vector2(3, 3).scl(1 / MarioBros.PPM);
        headShape.set(vertices);

        fixtureDef.shape = headShape;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = MarioBros.ENEMY_HEAD_BIT;
        body.createFixture(fixtureDef).setUserData(this);

    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }


}
