/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegos.gui.SnakeGameWindow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleja
 */
public class HiloMoverSnake implements Runnable{
    private Snake snake;
    private boolean running = false;
    private int velocidad = 250; 
    private int ultimoNivel = 0;
    private Sonido sonido;
    
    public HiloMoverSnake(Snake snake) {
        this.snake = snake;
        this.sonido = new Sonido();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (!snake.isGameOver()) {
//                this.sonido.reproducir("SoundSnake.wav");
                snake.getSerpiente().move();
                
                if (snake.getSerpiente().collidesWithSelf()) {
                    snake.setGameOver(true);
                }
                
                int nivelActual = snake.getPuntaje() / 10;
                if (nivelActual > ultimoNivel) {
                    velocidad -= 20;
                    if (velocidad < 50) velocidad = 50;
                    ultimoNivel = nivelActual;
                }
                snake.verificarComida();
                snake.verificarColisionConBordes();
                snake.refresh();
            } 
            else {
                try {
                    this.snake.verificarJuego();
                } catch (IOException ex) {
                    
                }
            }
            
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void start() {
        running = true;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    public void stop() {
        running = false;
    }
}


