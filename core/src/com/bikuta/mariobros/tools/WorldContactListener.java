package com.bikuta.mariobros.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.sprites.Enemy;
import com.bikuta.mariobros.sprites.InteractiveTileObject;

// Um Contact Listener é chamado sempre que dois fixtures colidem
public class WorldContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        // O atributo contact possui dois fixtures que estão se colidindo
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        // Vendo se o edgeShape do mario está em um dos fixtures da colisão
        if (fixA.getUserData() == "marioHead" || fixB.getUserData() == "marioHead") {
            // Identificando os objetos
            Fixture marioHead = (fixA.getUserData() == "marioHead") ? fixA : fixB;
            Fixture object = (marioHead == fixA) ? fixB : fixA;

            // Checando se o userData do objeto não é null, se o objeto extende a classe abstrata InteractiveTileObject
            // e se caso o objeto seja o bloco de moeda, verifica se ele já teve alguma colisão antes
            if (object.getUserData() != null
                    && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                // Fazendo o cast e chamando o método abstrato onHeadHit da classe
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef){
            case MarioBros.ENEMY_HEAD_BIT | MarioBros.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT){
                    ((Enemy)fixA.getUserData()).hitOnHead();
                }else if (fixB.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT){
                    ((Enemy)fixB.getUserData()).hitOnHead();
                }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
