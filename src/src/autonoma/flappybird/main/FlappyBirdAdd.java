/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.autonoma.flappybird.main;

/**
 *
 * @author aleja
 */
import javax.swing.*;
 
 public class FlappyBirdAdd {
     public static void main(String[] args) throws Exception {
         int boardWidth = 360;
         int boardHeight = 640;
 
         JFrame frame = new JFrame("Flappy Bird");
         // frame.setVisible(true);
 		frame.setSize(boardWidth, boardHeight);
         frame.setLocationRelativeTo(null);
         frame.setResizable(false);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
         FlappyBird flappyBird = new FlappyBird();
         frame.add(flappyBird);
         frame.pack();
         flappyBird.requestFocus();
         frame.setVisible(true);
     }
 }