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
public class Snake{
    public Tile head;
    public ArrayList<Tile> body = new ArrayList<>();
    public int velocityX = 1;
    public int velocityY = 0;

    public Snake(int startX, int startY) {
        head = new Tile(startX, startY);
    }

    public void move() {
        if (!body.isEmpty()) {
            for (int i = body.size() - 1; i > 0; i--) {
                body.get(i).x = body.get(i - 1).x;
                body.get(i).y = body.get(i - 1).y;
            }
            body.get(0).x = head.x;
            body.get(0).y = head.y;
        }
        head.x += velocityX;
        head.y += velocityY;
    }

    public void grow(Tile foodTile) {
        body.add(new Tile(foodTile.x, foodTile.y));
    }

    public boolean collidesWithSelf() {
        for (Tile t : body) {
            if (t.x == head.x && t.y == head.y) {
                return true;
            }
        }
        return false;
    }
}
