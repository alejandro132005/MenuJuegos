/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.util.Random;

/**
 *
 * @author Camila
 */
public class ComidaSnake{
    public Cuadro posicion;
    private int maxWidth;
    private int maxHeight;
    private int tamanoCuadro;
    private Random random = new Random();

    public ComidaSnake(int maxWidth, int maxHeight, int tamanoCuadro) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tamanoCuadro = tamanoCuadro;
        posicion = new Cuadro(0, 0);
        respawn();
    }

    public void respawn() {
        posicion.setX(random.nextInt(maxWidth / tamanoCuadro));
        posicion.setX(random.nextInt(maxHeight / tamanoCuadro));
    }

    public Cuadro getPosition() {
        return posicion;
    }

    public void setPosition(Cuadro position) {
        this.posicion = position;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getTamanoCuadro() {
        return tamanoCuadro;
    }

    public void setTamanoCuadro(int tamanoCuadro) {
        this.tamanoCuadro = tamanoCuadro;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
