/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Main.PooGame.Entities;

/**
 *
 * @author Crisl
 */

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    private Sprite sprite;
    private String tipo;
    private boolean collected = false; // NUEVO: para eliminarlo si el jugador lo toca

    public Enemy(float x, float y, float width, float height, String tipo) {
        this.tipo = tipo;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        if (tipo.equals("enemigo")) {
            pixmap.setColor(0, 0, 1, 1); // azul
        } else {
            pixmap.setColor(0, 1, 0, 1); // verde
        }
        pixmap.fill();
        Texture tempTexture = new Texture(pixmap);
        pixmap.dispose();

        sprite = new Sprite(tempTexture);
        sprite.setSize(width, height);
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!collected) {
            sprite.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    
    public String getTipo() {
    return tipo;
}

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
        this.remove(); // lo elimina del stage visualmente
    }
}






