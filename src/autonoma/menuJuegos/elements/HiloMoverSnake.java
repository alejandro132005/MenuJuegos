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
    private Thread thread;
    private final int FPS = 10;
    private volatile boolean running = false;

    public HiloMoverSnake(Snake snake) {
        this.snake = snake;
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long targetTime = 1000 / FPS;

        while (running) {
            long startTime = System.currentTimeMillis();

            this.snake.getSerpiente().move();
            this.snake.refresh();

            if (this.snake.isGameOver()) {
                running = false;
            }

            long elapsed = System.currentTimeMillis() - startTime;
            long waitTime = targetTime - elapsed;
            if (waitTime < 0) waitTime = 5;

            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//    private Snake snake;
//    /** 
//    * bandera que indica si el hilo se esta ejecutando
//    */
//    private boolean running;
//    
//    /** 
//    * bandera que indica si el hilo esta temporalmente pausado
//    */
//    private boolean paused;
//
//    /**
//    * Inicializa los atributos de la clase HiloMoverPulgas
//    * @param snake
//    */
//    public HiloMoverSnake(Snake snake) {
//        this.snake = snake;
//        running = false;
//        paused = false;
//    }
//    
//    /**
//    * Sobrescribe el metodo run() de la interfaz Runnable
//    */
//    @Override
//    public void run() {
//        running = true;
//        
//        while(isRunning())
//        {
//            try {
//                this.snake.getSerpiente().move();
////            this.snake.refresh();
//                Thread.sleep(300);
//            } catch (InterruptedException ex) {}
//            
//            if(isPaused())
//                continue;
//            
//        }
//    }
//    
//    /**
//     * Retorna el estado de ejecucion
//     * @return running
//    */
//     public boolean isRunning() {
//        return running;
//    }
//
//    /**
//    * detiene la ejecucion del hilo
//    */
//    public void stop() {
//        this.running = false;
//    }
//
//    /**
//     * retorna si el hilo esta en pausa
//     * @return paused
//     */
//    public boolean isPaused() {
//        return paused;
//    }
//
//    /**
//    * Pausa la ejecucion del hilo
//    */
//    public void pause() {
//        this.paused = true;
//    }
//
//     /**
//     * Reanuda la ejecucion del hilo
//     */
//    public void unpause() {
//        this.paused = false;
//    }

