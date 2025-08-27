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

public class Platform extends Actor {
    private final Sprite sprite;

    public Platform(float x, float y, float width, float height) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 1, 0, 1); // verde
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    public float getTop() {
        return sprite.getY() + sprite.getHeight();
    }
}
