/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.main.pooGame;

/**
 *
 * @author CristianV
 */

import com.badlogic.gdx.Game;


public class JuegoPrincipal extends Game {

    @Override
    public void create() {
        // Aquí se establece la primera pantalla que verá el jugador
        setScreen(new MainMenuScreen(this));
    }
}

