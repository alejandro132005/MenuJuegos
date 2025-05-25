/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Camila
 */
public class Fantasma extends Personaje {
    private BufferedImage image;
    private int velocidad;
    
    public Fantasma(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        this.velocidad = 2 + new Random().nextInt(4); 
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Character> direcciones = Arrays.asList('U', 'D', 'L', 'R');
        Collections.shuffle(direcciones);
        this.direccion = direcciones.get(0);
    }
    
    @Override
    public void move(List<Bloque> walls) {
//        int nextX = x;
//        int nextY = y;
//
//        switch (direccion) {
//            case 'U': nextY -= velocidad; break;
//            case 'D': nextY += velocidad; break;
//            case 'L': nextX -= velocidad; break;
//            case 'R': nextX += velocidad; break;
//        }
//
//        Rectangle futuro = new Rectangle(nextX, nextY, width, height);
//        boolean colision = false;
//
//        for (Bloque b : walls) {
//            if (futuro.intersects(b.getBounds())) {
//                colision = true;
//                break;
//            }
//        }
//
//        if (colision) {
//            List<Character> direcciones = Arrays.asList('U', 'D', 'L', 'R');
//            Collections.shuffle(direcciones);
//            for (char nueva : direcciones) {
//                int testX = x, testY = y;
//                switch (nueva) {
//                    case 'U': testY -= velocidad; break;
//                    case 'D': testY += velocidad; break;
//                    case 'L': testX -= velocidad; break;
//                    case 'R': testX += velocidad; break;
//                }
//                Rectangle prueba = new Rectangle(testX, testY, width, height);
//                boolean choca = false;
//                for (Bloque b : walls) {
//                    if (prueba.intersects(b.getBounds())) {
//                        choca = true;
//                        break;
//                    }
//                }
//                if (!choca) {
//                    direccion = nueva;
//                    break;
//                }
//            }
//        } else {
//            x = nextX;
//            y = nextY;
//        }
    }

    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(color != null ? color : Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }    

    @Override
    public void move(List<Bloque> walls, Jugador jugador) {
        int dx = jugador.getX() - x;
        int dy = jugador.getY() - y;

        List<Character> direcciones = new ArrayList<>();

        if (Math.abs(dx) > Math.abs(dy)) {
            direcciones.add(dx > 0 ? 'R' : 'L');
            direcciones.add(dy > 0 ? 'D' : 'U');
        } else {
            direcciones.add(dy > 0 ? 'D' : 'U');
            direcciones.add(dx > 0 ? 'R' : 'L');
        }

        for (char d : Arrays.asList('U', 'D', 'L', 'R')) {
            if (!direcciones.contains(d)) {
                direcciones.add(d);
            }
        }

        for (char nuevaDir : direcciones) {
            int nextX = x, nextY = y;
            switch (nuevaDir) {
                case 'U': nextY -= velocidad; break;
                case 'D': nextY += velocidad; break;
                case 'L': nextX -= velocidad; break;
                case 'R': nextX += velocidad; break;
            }

            Rectangle futuro = new Rectangle(nextX, nextY, width, height);
            boolean colision = false;

            for (Bloque b : walls) {
                if (futuro.intersects(b.getBounds())) {
                    colision = true;
                    break;
                }
            }

            if (!colision) {
                direccion = nuevaDir;
                x = nextX;
                y = nextY;
                break;
            }
        }
    }
}
