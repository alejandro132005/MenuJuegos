/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

/**
 *
 * @author Camila
 */
public class HiloMoverFantasmas implements Runnable{
    private Pacman pacman;
    private boolean running = false;
    private int velocidad = 100;
    private Sonido sonido;

    public HiloMoverFantasmas(Pacman pacman) {
        this.pacman = pacman;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (!pacman.isGameOver()) {
                
                for (int i = 0; i < pacman.getFantasmas().size(); i++){
                    pacman.getFantasmas().get(i).move(this.pacman.getJugador());
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
