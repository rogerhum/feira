package Runner ;

//http://www.nutt.net/2013/11/25/create-scrolling-background-java/

import java.awt.Component;

import javax.swing.JFrame;

import Game.ScrollingBackground;
 
public class Runner extends JFrame {
 
    public Runner() {
        super("Scrolling Background Demo");
        setSize(550, 250);
 
        ScrollingBackground back = new ScrollingBackground();
        ((Component)back).setFocusable(true);
        getContentPane().add(back);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
 
    public static void main(String[] args) {
        new Runner();
    }
 
}