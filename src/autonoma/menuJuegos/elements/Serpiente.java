/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.util.ArrayList;

public class Serpiente{
    public Cuadro cabeza;
    public ArrayList<Cuadro> cuerpo = new ArrayList<>();
    public int velocidadX = 1;
    public int velocidadY = 0;

    public Serpiente(int w, int h) {
        this.cabeza = new Cuadro(w, h);
    }

    public void move() {
        if (!cuerpo.isEmpty()) {
            for (int i = cuerpo.size() - 1; i > 0; i--) {
                cuerpo.get(i).setX(cuerpo.get(i - 1).getX());
                cuerpo.get(i).setY(cuerpo.get(i - 1).getY());
            }
            cuerpo.get(0).setX(cabeza.getX());
            cuerpo.get(0).setY(cabeza.getY());
        }

        cabeza.setX(cabeza.getX() + velocidadX);
        cabeza.setY(cabeza.getY() + velocidadY);
    }

    public void grow(Cuadro comida) {
        cuerpo.add(new Cuadro(comida.getX(), comida.getY()));
    }

    public boolean collidesWithSelf() {
        for (Cuadro t : cuerpo) {
            if (t.getX() == cabeza.getX() && t.getY() == cabeza.getY()) {
                return true;
            }
        }
        return false;
    } 

    public Cuadro getCabeza() {
        return cabeza;
    }

    public void setCabeza(Cuadro cabeza) {
        this.cabeza = cabeza;
    }

    public ArrayList<Cuadro> getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(ArrayList<Cuadro> cuerpo) {
        this.cuerpo = cuerpo;
    }

    public int getVelocidadX() {
        return velocidadX;
    }

    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }

    public int getVelocidadY() {
        return velocidadY;
    }

    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }
}
