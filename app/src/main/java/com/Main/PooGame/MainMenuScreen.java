/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.pooGame;

/**
 *
 * @author Crisl
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

    private final JuegoPrincipal juego;
    private Stage stage;
    private Skin skin;

    public MainMenuScreen(JuegoPrincipal juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Tabla principal para layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Título
        Label titleLabel = new Label("PooGame", skin);
        titleLabel.setFontScale(2);
        titleLabel.setAlignment(Align.center);

        // Botones
        TextButton btnSalir = new TextButton("Salir", skin);
        TextButton btnEmpezar = new TextButton("Empezar", skin);
        TextButton btnConfig = new TextButton("Configuración", skin);

        // Eventos
        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        btnEmpezar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Aquí luego iría el primer nivel
                System.out.println("Iniciar juego...");
            }
        });

        btnConfig.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Abrir configuración...");
            }
        });

        // Agregar componentes a la tabla
        table.top().padTop(50);
        table.add(titleLabel).colspan(3).padBottom(50);
        table.row();
        table.add(btnSalir).pad(10);
        table.add(btnEmpezar).pad(10);
        table.add(btnConfig).pad(10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}





