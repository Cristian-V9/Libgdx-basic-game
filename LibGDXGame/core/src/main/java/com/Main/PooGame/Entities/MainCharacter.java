/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Main.PooGame.Entities;

/**
 *
 * @author Crisl
 */
import com.Main.PooGame.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class MainCharacter extends com.badlogic.gdx.scenes.scene2d.Actor {

    private Sprite sprite;
    private List<Enemy> enemies;
    private List<Platform> platforms;
    private GameScreen gameScreen;
    private Sound jumpSound;

    private float groundY;
    private float velocityY = 0;
    private final float gravity = -0.5f;
    private final float jumpStrength = 12;
    private boolean jumping = false;
    private Enemy lastCollectedEnemy = null;

    public MainCharacter(float x, float y, float width, float height, List<Enemy> enemies, List<Platform> platforms, GameScreen gameScreen, Sound jumpSound) {
        this.enemies = enemies;
        this.platforms = platforms;
        this.gameScreen = gameScreen;
        this.groundY = 0;
        this.jumpSound = jumpSound;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1); // rojo
        pixmap.fill();
        Texture tempTexture = new Texture(pixmap);
        pixmap.dispose();
        sprite = new Sprite(tempTexture);
        sprite.setSize(width, height);
        sprite.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float oldY = sprite.getY();

        // Movimiento lateral
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.translateX(-5);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.translateX(5);
        }

        // Salto
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !jumping) {
            velocityY = jumpStrength;
            jumping = true;
            jumpSound.play();
        }

        // Gravedad
        velocityY += gravity;
        sprite.translateY(velocityY);

        Rectangle playerRect = getBounds();
        boolean onPlatform = false;

        // Colisiones con plataformas
        for (Platform platform : platforms) {
            Rectangle platRect = platform.getBounds();
            float platTop = platform.getTop();

            if (oldY >= platTop && sprite.getY() < platTop && playerRect.overlaps(platRect)) {
                sprite.setY(platTop);
                velocityY = 0;
                jumping = false;
                onPlatform = true;
            }

            if (velocityY > 0 && playerRect.overlaps(platRect)) {
                float playerTop = sprite.getY() + sprite.getHeight();
                float platY = platform.getY();

                if (playerTop > platY && oldY + sprite.getHeight() <= platY) {
                    sprite.setY(platY - sprite.getHeight());
                    velocityY = 0;
                }
            }
        }

        // Suelo
        if (sprite.getY() <= groundY) {
            sprite.setY(groundY);
            velocityY = 0;
            jumping = false;
            onPlatform = true;
        }

        if (!onPlatform) {
            jumping = true;
        }

        // Colisión con el único enemigo visible (GameScreen asegura que solo hay uno visible)
        for (Enemy enemy : enemies) {
            if (enemy.isVisible() && !enemy.isCollected() && playerRect.overlaps(enemy.getBounds())) {
                if (enemy != lastCollectedEnemy) {
                    gameScreen.enemyCollected(enemy);
                    gameScreen.increaseScore();
                    lastCollectedEnemy = enemy;
                    System.out.println("Recogió enemigo: " + enemy.getTipo());
                }
            }
        }

        // Límites laterales
        if (sprite.getX() < 0) {
            sprite.setX(0);
        }
        if (sprite.getX() + sprite.getWidth() > 800) {
            sprite.setX(800 - sprite.getWidth());
        }

        // Techo
        if (sprite.getY() + sprite.getHeight() > 480) {
            sprite.setY(480 - sprite.getHeight());
            velocityY = 0;
        }

        setPosition(sprite.getX(), sprite.getY());
    }

    public Rectangle getBounds() {
        return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override

    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
