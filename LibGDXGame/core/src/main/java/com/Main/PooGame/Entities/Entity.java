/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Main.PooGame.Entities;

/**
 *
 * @author Crisl
 */

import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected Rectangle rectangulo; // Representa el objeto en el mundo (como un sprite)

    public Entity(float x, float y, float width, float height) {
        rectangulo = new Rectangle(x, y, width, height);
    }

    public Rectangle getRectangulo() {
        return rectangulo;
    }

    // Método abstracto para actualizar la lógica de la entidad (a ser implementado por las clases hijas)
    public abstract void update(float deltaTime);

    // Método para detectar colisiones
    public boolean overlaps(Entity otraEntidad) {
        return rectangulo.overlaps(otraEntidad.getRectangulo());
    }
}