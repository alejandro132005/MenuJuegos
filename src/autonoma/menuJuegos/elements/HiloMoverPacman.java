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
 * @author Camila
 */
public class HiloMoverPacman implements Runnable{
    private Pacman pacman;
    private boolean running = false;
    private int velocidad = 100;
    private Sonido sonido;

    public HiloMoverPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (!pacman.isGameOver()) {
                pacman.getJugador().move(pacman.getBloques());
                try {
                    pacman.detectarColisiones();
                    this.pacman.verificarJuego();
                } catch (IOException ex) {
                    Logger.getLogger(HiloMoverPacman.class.getName()).log(Level.SEVERE, null, ex);
                }
                pacman.refresh();
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
