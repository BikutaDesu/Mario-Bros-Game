package com.bikuta.mariobros.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bikuta.mariobros.MarioBros;
import com.bikuta.mariobros.scenes.Hud;
import com.bikuta.mariobros.sprites.Goomba;
import com.bikuta.mariobros.sprites.Mario;
import com.bikuta.mariobros.tools.B2WorldCreator;
import com.bikuta.mariobros.tools.WorldContactListener;

public class PlayScreen implements Screen {

    private MarioBros game;
    private TextureAtlas textureAtlas;

    private OrthographicCamera gameCamera;
    private Viewport viewport;
    private Hud hud;

    // Variáveis tiled map
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    // Variáveis Box2d
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Mario player;
    private Goomba goomba;

    private Music levelMusic;

    public PlayScreen(MarioBros game) {
        textureAtlas = new TextureAtlas("mario_and_enemies.pack");

        this.game = game;
        gameCamera = new OrthographicCamera();

        // Cria um FitViewport, mantém o aspect ratio da tela
        viewport = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCamera);

        // Cria a HUD do jogo (utilizada para mostrar score, tempo e level)
        hud = new Hud(game.getBatch());

        // Carrega o mapa e configura o map renderer
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / MarioBros.PPM);

        // Seta o camera no centro da tela
        gameCamera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        new B2WorldCreator(this);

        player = new Mario(this, game.getAssetManager());

        goomba = new Goomba(this, 64f, 32f);

        world.setContactListener(new WorldContactListener());

        levelMusic = game.getAssetManager().get("audio/music/mario_music.ogg", Music.class);
        levelMusic.setLooping(true);
        levelMusic.play();
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            game.setDebugMode(!game.getDebugMode());
        }
    }


    public void update(float dt) {
        // Método para verificar os inputs
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);
        goomba.update(dt);

        hud.update(dt);

        gameCamera.position.x = player.body.getPosition().x;

        // Atualiza a camera com as cordenadas
        gameCamera.update();
        // Fala para o renderer desenhar apenas oq a câmera pode ver no mundo
        mapRenderer.setView(gameCamera);
    }

    @Override
    public void render(float delta) {
        update(delta);

        // Limpa a tela do jogo com a cor preta
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renderiza o mapa do jogo
        mapRenderer.render();

        // Renderiza o Box2DDebugLines
        if (game.getDebugMode()) {
            box2DDebugRenderer.render(world, gameCamera.combined);
        }


        game.getBatch().setProjectionMatrix(gameCamera.combined);
        game.getBatch().begin();
        player.draw(game.getBatch());
        goomba.draw(game.getBatch());
        game.getBatch().end();

        // Desenhando oq esta no stage da Hud
        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public TiledMap getTiledMap(){
        return this.tiledMap;
    }

    public World getWorld(){
        return this.world;
    }

    public MarioBros getGame(){
        return this.game;
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();
    }
}
