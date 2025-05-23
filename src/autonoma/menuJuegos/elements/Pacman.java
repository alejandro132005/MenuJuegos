/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.SpriteContainer;

/**
 *
 * @author maria
 */
public class Pacman extends SpriteContainer implements GraphicContainer{
    private int cantidadFilas = 21;
    private int cantidadColumnas = 19;
    private int tamanoCuadro = 32;
    private int boardWidth = cantidadColumnas * tamanoCuadro;
    private int boardHeight = cantidadFilas * tamanoCuadro;
    private int puntaje = 0;
    private int vidas = 3;
    boolean gameOver = false;

    
    private String[] mapa = {
         "XXXXXXXXXXXXXXXXXXX",
         "X        X        X",
         "X XX XXX X XXX XX X",
         "X                 X",
         "X XX X XXXXX X XX X",
         "X    X       X    X",
         "XXXX XXXX XXXX XXXX",
         "X    X       X    X",
         "XXXX X XXrXX X XXXX",
         "X       bpo       X",
         "XXXX X XXXXX X XXXX",
         "X    X       X    X",
         "XXXX X XXXXX X XXXX",
         "X        X        X",
         "X XX XXX X XXX XX X",
         "X  X     P     X  X",
         "XX X X XXXXX X X XX",
         "X    X   X   X    X",
         "X XXXXXX X XXXXXX X",
         "X                 X",
         "XXXXXXXXXXXXXXXXXXX" 
     };

    public Pacman(int x, int y, int height, int width) {
        super(x, y, height, width);
    }
    
}
