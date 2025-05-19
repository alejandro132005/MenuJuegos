/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import snake.SnakeGame.SnakeGame;


/**
 *
 * @author Camila
 */
public class Snake{
    
    Random random;
    
    Cuadro food;
    
    Serpiente snakeHead;
    
   
    ArrayList<Serpiente> snakeBody;
    
    
    public Snake() {
         
        snakeHead = new Serpiente(5, 5);
        snakeBody = new ArrayList<Serpiente>();
 
        food = new ComidaSnake(10, 10);
        random = new Random();
//        placeFood();

    }	
    
    public void move() {
         
         if (collision(snakeHead, food)) {
             snakeBody.add(new ComidaSnake(food.getX(), food.getY()));
//             placeFood();
         }
 
         
         for (int i = snakeBody.size()-1; i >= 0; i--) {
             Cuadro snakePart = snakeBody.get(i);
             if (i == 0) { 
                 snakePart.x = snakeHead.x;
                 snakePart.y = snakeHead.y;
             }
             else {
                 Cuadro prevSnakePart = snakeBody.get(i-1);
                 snakePart.x = prevSnakePart.x;
                 snakePart.y = prevSnakePart.y;
             }
         }
         //move snake head
         snakeHead.x += this.snakeHead.getVelocidadX();
         snakeHead.y += this.snakeHead.getVelocidadY();
 
         //game over conditions
         for (int i = 0; i < snakeBody.size(); i++) {
             Cuadro snakePart = snakeBody.get(i);
 
             //collide with snake head
             if (collision(snakeHead, snakePart)) {
                 gameOver = true;
             }
         }
 
         if (snakeHead.getX()*CuadroSize < 0 || snakeHead.getX()*CuadroSize > boardWidth || //passed left border or right border
             snakeHead.getY()*CuadroSize < 0 || snakeHead.getY()*CuadroSize > boardHeight ) { //passed top border or bottom border
             gameOver = true;
         }
     }
    
//    public void placeFood(){
//        food.x = random.nextInt(boardWidth/tileSize);
//            food.y = random.nextInt(boardHeight/tileSize);
//    }
 
    public boolean collision(Cuadro tile1, Cuadro tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
   
    
}
