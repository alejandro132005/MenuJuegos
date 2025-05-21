/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

/**
 *
 * @author aleja
 */
public class HiloMoverSnake implements Runnable{
    private Snake snake;
    /** 
    * bandera que indica si el hilo se esta ejecutando
    */
    private boolean running;
    
    /** 
    * bandera que indica si el hilo esta temporalmente pausado
    */
    private boolean paused;

    /**
    * Inicializa los atributos de la clase HiloMoverPulgas
    * @param snake
    */
    public HiloMoverSnake(Snake snake) {
        this.snake = snake;
        running = false;
        paused = false;
    }
    
    /**
    * Sobrescribe el metodo run() de la interfaz Runnable
    */
    @Override
    public void run() {
        running = true;
        
        while(isRunning())
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
            
            if(isPaused())
                continue;
            
            this.snake.getSerpiente().move();
            this.snake.refresh();
//            for (Sprite sprite : this.snake.getCopiaSprites()) {
//                if (sprite instanceof Pulga) {
//                    ((Pulga) sprite).move();
//                }
//            }
        }
    }
    
    /**
     * Retorna el estado de ejecucion
     * @return running
    */
     public boolean isRunning() {
        return running;
    }

    /**
    * detiene la ejecucion del hilo
    */
    public void stop() {
        this.running = false;
    }

    /**
     * retorna si el hilo esta en pausa
     * @return paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
    * Pausa la ejecucion del hilo
    */
    public void pause() {
        this.paused = true;
    }

     /**
     * Reanuda la ejecucion del hilo
     */
    public void unpause() {
        this.paused = false;
    }
}
