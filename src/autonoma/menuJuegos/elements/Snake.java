/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.SpriteContainer;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Camila
 */
public class Snake extends SpriteContainer{
    
    Serpiente snakeHead;
    ArrayList<Serpiente> snakeBody;
    ComidaSnake food;
    

    public Snake(int x, int y) {
        super(x, y);
        this.snakeHead = new Serpiente(5,5);
        this.snakeBody = new ArrayList<Serpiente>();
        this.food = food;
    }

   
     public void move() {
         //eat food
         if (collision(snakeHead, food)) {
             snakeBody.add(new ComidaSnake(food.getX(), food.getY()));
             placeFood();
         }
 
         //move snake body
         for (int i = snakeBody.size()-1; i >= 0; i--) {
             Tile snakePart = snakeBody.get(i);
             if (i == 0) { //right before the head
                 snakePart.x = snakeHead.x;
                 snakePart.y = snakeHead.y;
             }
             else {
                 Tile prevSnakePart = snakeBody.get(i-1);
                 snakePart.x = prevSnakePart.x;
                 snakePart.y = prevSnakePart.y;
             }
         }
         //move snake head
         snakeHead.x += velocidadX;
         snakeHead.y += velocidadY;
 
         //game over conditions
         for (int i = 0; i < snakeBody.size(); i++) {
             Tile snakePart = snakeBody.get(i);
 
             //collide with snake head
             if (collision(snakeHead, snakePart)) {
                 gameOver = true;
             }
         }
 
         if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || //passed left border or right border
             snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight ) { //passed top border or bottom border
             gameOver = true;
         }
     }
 
     public boolean collision(Tile tile1, Tile tile2) {
         return tile1.x == tile2.x && tile1.y == tile2.y;
     }

    
    
}
