/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.SpriteContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class Pacman extends SpriteContainer implements GraphicContainer {

    private int cantidadFilas = 21;
    private int cantidadColumnas = 19;
    private int tamanoCuadro = 32;
    private int boardWidth = cantidadColumnas * tamanoCuadro;
    private int boardHeight = cantidadFilas * tamanoCuadro;
    private int puntaje = 0;
    private int vidas = 3;
    private boolean gameOver = false;

    private Jugador jugador;
    private List<Fantasma> fantasmas = new ArrayList<>();
    private List<Bloque> bloques = new ArrayList<>();

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

    public Pacman(int x, int y, int height, int width, Color color, GraphicContainer gameContainer) {
        super(x, y, height, width, color, gameContainer);
        inicializarMapa();
    }
    

    private void inicializarMapa() {
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int col = 0; col < mapa[fila].length(); col++) {
                char c = mapa[fila].charAt(col);
                int x = col * tamanoCuadro;
                int y = fila * tamanoCuadro;

                switch (c) {
                    case 'X':
                        bloques.add(new Bloque("/autonoma/menuJuego/images/bloque.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'P':
                        jugador = new Jugador("/autonoma/menuJuego/images/pacmanRight.png", x, y, tamanoCuadro, tamanoCuadro);
                        break;
                    case 'r':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaRojo.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'b':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaAzul.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'p':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaRosado.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'o':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaNaranja.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                }
            }
        }
    }

    public void handleKey(KeyEvent e) throws IOException {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                jugador.updateDirection('U');
                break;
            case KeyEvent.VK_DOWN:
                jugador.updateDirection('D');
                break;
            case KeyEvent.VK_LEFT:
                jugador.updateDirection('L');
                break;
            case KeyEvent.VK_RIGHT:
                jugador.updateDirection('R');
                break;
        }

    }


    private void detectarColisiones() {
        for (Bloque bloque : bloques) {
            if (jugador.getBounds().intersects(bloque.getBounds())) {
                jugador.reset();
                break;
            }
        }

        for (Fantasma fantasma : fantasmas) {
            if (jugador.getBounds().intersects(fantasma.getBounds())) {
                vidas--;
                jugador.reset();
                if (vidas <= 0) {
                    gameOver = true;
                }
                break;
            }
        }
    }

//    @Override
//    public void paint(Graphics g) {
//        for (Bloque bloque : bloques) {
//            bloque.draw(g);
//        }
//        jugador.paint(g);
//        for (Fantasma fantasma : fantasmas) {
//            fantasma.paint(g);
//        }
//    }
    @Override
    public void paint(Graphics g) {
    for (Bloque bloque : bloques) {
        bloque.paint(g);
    }

    for (Fantasma fantasma : fantasmas) {
        fantasma.paint(g);
    }

    if (jugador != null) { // <- esto evita el NullPointerException
        jugador.paint(g);
    }
}

}
