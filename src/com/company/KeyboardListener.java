package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    Game game;
    public KeyboardListener(Game game) {
        this.game= game;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(game.direction!='d'){
                    game.direction='a';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(game.direction!='a'){
                    game.direction='d';
                }
                break;
            case KeyEvent.VK_UP:
                if(game.direction!='s'){
                    game.direction='w';
                }
                break;
            case KeyEvent.VK_DOWN:
                if(game.direction!='w'){
                    game.direction='s';
                }
                break;
                case KeyEvent.VK_ENTER:
                    game.move();
            default:
                break;
        }
    }
}
