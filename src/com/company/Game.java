package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements ActionListener {
    static final int WIDTH=30;
    static final int HEIGHT=20;
    static final int UNIT=25;
    static final int DELAY=100;

    static final Color bgColor=Color.DARK_GRAY;
    static final Color snBdColor=Color.GREEN;
    static final Color snHdColor=new Color(30,140,0);
    static final Color apColor=Color.RED;

    List<Cell> snake=new ArrayList<>();
    boolean running=false;
    char direction='d';
    int startBodyTrail=9;
    Cell apple;
    Timer timer;

    static int score=0;
    public Game(){
        this.setPreferredSize(new Dimension(WIDTH*UNIT,HEIGHT*UNIT));
        this.setBackground(bgColor);
        this.setFocusable(true);
        this.addKeyListener(new KeyboardListener(this));
        startGame();
    }

    void startGame() {
        running=true;
        snake=new ArrayList<>();
        addApple();
        snake.add(new Cell(
                (int) ((Math.random() * ((WIDTH-startBodyTrail) - startBodyTrail)) + startBodyTrail),
                (int) ((Math.random() * ((HEIGHT-startBodyTrail) - startBodyTrail)) + startBodyTrail)
        ));
        timer=new Timer(DELAY,this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            drawGrid(g);
            g.setColor(apColor);
            g.fillOval(apple.getpX() * UNIT, apple.getpY() * UNIT, UNIT, UNIT);
            for (int i = 0; i < snake.size(); i++) {
                if (i == snake.size() - 1) {
                    g.setColor(snHdColor);
                } else {
                    g.setColor(snBdColor);
                }
                g.fillRect(snake.get(i).getpX() * UNIT, snake.get(i).getpY() * UNIT, UNIT, UNIT);
            }

            g.setColor(Color.ORANGE);
            g.setFont(new Font("Calibri",Font.BOLD,20));
            FontMetrics fm=getFontMetrics(g.getFont());
            g.drawString("Score: "+score,(WIDTH*UNIT-fm.stringWidth("Score: "+score))/2,g.getFont().getSize());

        }else {
            gameOver(g);
        }
    }

    public void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < WIDTH; i++) {
            g.drawLine(i * UNIT, 0, i * UNIT, HEIGHT*UNIT);
        }
        for (int i = 0; i < HEIGHT; i++) {
            g.drawLine(0, i*UNIT, WIDTH*UNIT, i*UNIT);
        }
    }

    public void move(){

        if (snake.size()<startBodyTrail){
            addBodyPart();
        }else {
            for (int i = 0; i < snake.size()-1; i++) {
                snake.set(i, snake.get(i + 1));
            }
            switch (direction) {
                case 'a':
                    snake.set(
                            snake.size() - 1,
                            new Cell(
                                    snake.get(snake.size() - 1).getpX() - 1,
                                    snake.get(snake.size() - 1).getpY()
                            )
                    );
                    break;
                case 'd':
                    snake.set(
                            snake.size() - 1,
                            new Cell(
                                    snake.get(snake.size() - 1).getpX() + 1,
                                    snake.get(snake.size() - 1).getpY()
                            )
                    );
                    break;
                case 'w':
                    snake.set(
                            snake.size() - 1,
                            new Cell(
                                    snake.get(snake.size() - 1).getpX(),
                                    snake.get(snake.size() - 1).getpY()-1
                            )
                    );
                    break;
                case 's':
                    snake.set(
                            snake.size() - 1,
                            new Cell(
                                    snake.get(snake.size() - 1).getpX(),
                                    snake.get(snake.size() - 1).getpY()+1
                            )
                    );
                    break;
            }
        }
        System.out.print(snake.size()+":");
        for (int i = 0; i < snake.size(); i++) {
            System.out.print(""+i+": ["+snake.get(i).pX+", "+snake.get(i).pY+"]; ");
        }
        System.out.println();
    }

    public void addApple(){
        apple=new Cell(
                (int)(Math.random()*WIDTH),
                (int)(Math.random()* HEIGHT)
        );
    }

    public void checkAppleBeingEaten(){

        if (
                (snake.get(snake.size()-1).getpX()==apple.getpX())&&
                        (snake.get(snake.size()-1).getpY()==apple.getpY())
        ){
            addBodyPart();
            startBodyTrail++;
            score++;
            addApple();
        }
    }

    private void addBodyPart() {
        switch (direction) {
            case 'a':
                snake.add(
                        new Cell(
                                snake.get(snake.size() - 1).getpX() - 1,
                                snake.get(snake.size() - 1).getpY()
                        )
                );
                break;
            case 'd':
                snake.add(
                        new Cell(
                                snake.get(snake.size() - 1).getpX() + 1,
                                snake.get(snake.size() - 1).getpY()
                        )
                );
                break;
            case 'w':
                snake.add(
                        new Cell(
                                snake.get(snake.size() - 1).getpX(),
                                snake.get(snake.size() - 1).getpY() - 1
                        )
                );
                break;
            case 's':
                snake.add(
                        new Cell(
                                snake.get(snake.size() - 1).getpX(),
                                snake.get(snake.size() - 1).getpY() + 1
                        )
                );
                break;
        }
    }

    public void checkCollision(){
        for (int i = 0; i < snake.size()-1; i++) {
            if (
                    (snake.get(snake.size()-1).getpX()==snake.get(i).getpX())&&
                    (snake.get(snake.size()-1).getpY()==snake.get(i).getpY())
            ){
                running=false;
                break;
            }
        }

        if (snake.get(snake.size()-1).getpX()<0){
            running=false;
        }

        if (snake.get(snake.size()-1).getpY()<0){
            running=false;
        }

        if (snake.get(snake.size()-1).getpX()>=WIDTH){
            running=false;
        }

        if (snake.get(snake.size()-1).getpY()>=HEIGHT){
            running=false;
        }

        if (!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Calibri",Font.BOLD,60));
        FontMetrics fm=getFontMetrics(g.getFont());
        g.drawString("Game Over",(WIDTH*UNIT-fm.stringWidth("Game Over"))/2,HEIGHT*UNIT/2);

        g.setFont(new Font("Calibri",Font.BOLD,20));
        FontMetrics fms=getFontMetrics(g.getFont());
        g.drawString("Score: "+score,(WIDTH*UNIT-fms.stringWidth("Score: "+score))/2,g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkAppleBeingEaten();
            checkCollision();
        }
        repaint();
    }
}
