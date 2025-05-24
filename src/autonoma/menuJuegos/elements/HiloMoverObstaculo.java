/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.IOException;
import javax.swing.SwingUtilities;

/**
 *
 * @author maria
 */
public class HiloMoverObstaculo implements Runnable{

    private FlappyBird flappyBird;
    private boolean running = false;
    private int velocidad = 250; 
    private int ultimoNivel = 0;
    private Sonido sonido;
    
    public HiloMoverObstaculo(FlappyBird flappyBird) {
        this.flappyBird = flappyBird;
        this.sonido = new Sonido();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (!flappyBird.isGameOver()) {
//                this.sonido.reproducir("SoundSnake.wav");
                
                flappyBird.actualizarJuego();
                flappyBird.refresh();
                
                int nivelActual = flappyBird.getPuntaje() / 10;
                if (nivelActual > ultimoNivel) {
                    velocidad -= 20;
                    if (velocidad < 50) velocidad = 50;
                    ultimoNivel = nivelActual;
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
