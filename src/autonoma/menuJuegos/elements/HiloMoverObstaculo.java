/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria
 */
public class HiloMoverObstaculo implements Runnable{
        private FlappyBird flappyBird;
    private boolean running = false;
    private int velocidad = 50; 
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
                try {
                    flappyBird.actualizarJuego();
                } catch (IOException ex) {
                    Logger.getLogger(HiloMoverObstaculo.class.getName()).log(Level.SEVERE, null, ex);
                }
                flappyBird.refresh();
            }
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    
    //                int nivelActual = flappyBird.getPuntaje() / 10;
//                if (nivelActual > ultimoNivel) {
//                    velocidad -= 20;
//                    if (velocidad < 50) velocidad = 50;
//                    ultimoNivel = nivelActual;
//                }
    
    public void start() {
        running = true;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    public void stop() {
        running = false;
    }
}
