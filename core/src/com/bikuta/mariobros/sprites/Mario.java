package com.bikuta.mariobros.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.screens.PlayScreen;

public class Mario extends Sprite {

    public enum State{
        STADING, FALLING, JUMPING, RUNNING
    };
    public State currentState;
    public State previousState;

    public World world;
    public Body body;

    private TextureRegion marioStand;
    private Animation marioRun, marioJump;
    private float stateTimer;
    private boolean isRunningRight;

    public Mario(World world, PlayScreen playScreen) {
        super(playScreen.getTextureAtlas().findRegion("little_mario"));

        this.world = world;

        currentState = State.STADING;
        previousState = State.STADING;
        stateTimer = 0;
        isRunningRight = true;

        // Criando as animações
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 11,16,16));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 11,16,16));
        }
        marioJump = new Animation(0.1f, frames);
        frames.clear();

        marioStand = new TextureRegion(getTexture(), 0,11,16,16);

        defineMario();

        setBounds(0,0,16 / MarioBros.PPM,16 / MarioBros.PPM);
        setRegion(marioStand);
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion textureRegion;

        switch (currentState){
            case JUMPING:
                textureRegion = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                textureRegion = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STADING:
            default:
                textureRegion = marioStand;
                break;
        }
        if ((body.getLinearVelocity().x < 0 || !isRunningRight) && !textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isRunningRight = false;
        }else if ((body.getLinearVelocity().x > 0 || isRunningRight) && textureRegion.isFlipX()){
            textureRegion.flip(true, false);
            isRunningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return textureRegion;
    }

    public State getState(){
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STADING;
    }

    public void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / MarioBros.PPM);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        EdgeShape headEdgeShape = new EdgeShape();
        // Como a edgeShape é apenas uma linha, é necessário definir o ponto inicial e o final da linha
        headEdgeShape.set(new Vector2(-2 / MarioBros.PPM, 6 / MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 6 / MarioBros.PPM));
        fixtureDef.shape = headEdgeShape;
        // Quando um fixture é um sensor, ele não colide com os objetos, parecido com o isTrigger do Unity
        fixtureDef.isSensor = true;
        // Além de criar a fixture nova ela também é identificada com o nome marioHead
        body.createFixture(fixtureDef).setUserData("marioHead");
    }

}
