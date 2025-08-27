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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private final MainGame game;
    private final Stage stage;
    private final Skin skin;
    private final Label titleLabel;

    private boolean gameStarted = false;

    public MainMenuScreen(MainGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Título
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), skin.getColor("white"));
        titleLabel = new Label("PooGame", labelStyle);
        titleLabel.setFontScale(2);
        titleLabel.setAlignment(Align.center);

        // Botones
        TextButton startButton = new TextButton("Start", skin);
        TextButton configButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Aumentar tamaño de botones
        startButton.getLabel().setFontScale(1.5f);
        configButton.getLabel().setFontScale(1.5f);
        exitButton.getLabel().setFontScale(1.5f);

        // Agregar listeners
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!gameStarted) {
                    gameStarted = true;
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new GameScreen(game));
                            System.out.println("Start clicked");
                            dispose();  // Llamar a dispose después de la transición
                        }
                    });
                }
            }
        });

        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Settings clicked");
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Cerrar la aplicación
            }
        });

        // Layout
        Table table = new Table();
        table.setFillParent(true);
        table.top().padTop(80);

        table.add(titleLabel).colspan(3).center().padBottom(50).row();
        table.add(exitButton).pad(10);
        table.add(startButton).pad(10);
        table.add(configButton).pad(10);

        stage.addActor(table);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        System.out.println("MainMenuScreen disposed");
        stage.dispose();
        skin.dispose();
    }
}

