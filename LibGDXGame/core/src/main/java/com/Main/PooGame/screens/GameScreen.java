/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Main.PooGame.screens;

/**
 *
 * @author Crisl
 */
import com.Main.PooGame.MainGame;
import com.Main.PooGame.Entities.MainCharacter;
import com.Main.PooGame.Entities.Enemy;
import com.Main.PooGame.Entities.Platform;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {

    private MainGame game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

    private float timeLeft = 60;
    private int score = 0;
    private boolean timeOver = false;

    private MainCharacter player;
    private List<Platform> platforms;
    private List<Enemy> enemies;

    private Enemy currentEnemy;
    private Random random = new Random();

    private Sound jumpSound;
    private Sound collectSound;
    private Sound finalSound;

    public GameScreen(MainGame game) {
        this.game = game;

        finalSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Final.wav"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Jump.wav"));
        collectSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Collect.wav"));

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        font = new BitmapFont();

        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JOptionPane.showMessageDialog(null, "Atrapa la mayor cantidad de objetos antes de que se acabe el tiempo.");
        });

        enemies = new ArrayList<>();
        enemies.add(new Enemy(160, 205, 25, 25, "enemigo"));
        enemies.add(new Enemy(360, 325, 25, 25, "enemigo"));
        enemies.add(new Enemy(560, 205, 25, 25, "enemigo"));
        enemies.add(new Enemy(100, 75, 25, 25, "enemigo"));
        enemies.add(new Enemy(350, 75, 25, 25, "enemigo"));
        enemies.add(new Enemy(650, 75, 25, 25, "enemigo"));

        // Ocultamos todos los enemigos al inicio
        for (Enemy enemy : enemies) {
            enemy.setVisible(false);
            stage.addActor(enemy);
        }

        platforms = new ArrayList<>();
        platforms.add(new Platform(0, 50, 800, 20));
        platforms.add(new Platform(150, 180, 100, 20));
        platforms.add(new Platform(550, 180, 100, 20));
        platforms.add(new Platform(350, 300, 100, 20));

        for (Platform platform : platforms) {
            stage.addActor(platform);
        }

        // Creamos al jugador después de plataformas y enemigos
        player = new MainCharacter(10, 70, 50, 50, enemies, platforms, this, jumpSound);
        stage.addActor(player);

        // Activamos el primer enemigo
        spawnNewEnemy();
    }

    private void spawnNewEnemy() {
        if (currentEnemy != null) {
            currentEnemy.setVisible(false);
        }

        Enemy newEnemy;
        do {
            newEnemy = enemies.get(random.nextInt(enemies.size()));
        } while (newEnemy == currentEnemy); // Evita repetir el mismo

        currentEnemy = newEnemy;
        currentEnemy.setVisible(true);
    }

    public void enemyCollected(Enemy e) {
        if (e == currentEnemy) {
            collectSound.play();
            spawnNewEnemy();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Tiempo: " + (int) timeLeft + "s", 10, 580);
        font.draw(batch, "Puntuacion: " + score, 10, 560);
        batch.end();

        if (!timeOver) {
            timeLeft -= delta;
            if (timeLeft <= 0) {
                timeLeft = 0;
                timeOver = true;
                finalSound.play();
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(null, "Se acabo el tiempo! Tu puntuacion: " + score);
                });
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
    public void show() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        batch.dispose();
        jumpSound.dispose();
        collectSound.dispose();
        finalSound.dispose();
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    public void increaseScore() {
        score++;
    }
}
