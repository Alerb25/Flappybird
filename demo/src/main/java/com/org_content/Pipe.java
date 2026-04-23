package com.org_content;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import java.util.Random;

public class Pipe {

    public  static final double WIDTH = 64;
    private static final double GAP   = 160;   // space between pipes
    private static final double SPEED = 3.5;
    private static final Random RNG   = new Random();

    private double x;
    private final double gapTop;   // where the top pipe ends
    private boolean scored = false;

    public Pipe(double startX) {
        this.x = startX;
        // the gap can be in between the 15% and 65% of the game
        int playH = App.HEIGHT - 60; // without the floor
        this.gapTop = playH * 0.15 + RNG.nextDouble() * (playH * 0.50);
    }

    public void update() { x -= SPEED; }
    public boolean isOffScreen() { return x + WIDTH < 0; }

    public boolean passed(Bird b) {
        if (!scored && b.getX() > x + WIDTH) { scored = true; return true; }
        return false;
    }

    public boolean hits(Bird b) {
        double bx = b.getX(), by = b.getY();
        boolean overlapX = bx + Bird.SIZE > x && bx < x + WIDTH;
        if (!overlapX) return false;
        boolean topPipe    = by < gapTop;
        boolean bottomPipe = by + Bird.SIZE > gapTop + GAP;
        return topPipe || bottomPipe;
    }

    public void render(GraphicsContext gc) {
        //top pipe
        drawPipe(gc, x, 0, WIDTH, gapTop);
        // botton pipe 
        double botY = gapTop + GAP;
        drawPipe(gc, x, botY, WIDTH, App.HEIGHT - 60 - botY);
    }

    private void drawPipe(GraphicsContext gc, double px, double py, double pw, double ph) {
        // body
        gc.setFill(Color.web("#4CAF50"));
        gc.fillRect(px, py, pw, ph);

       //left color darker
        gc.setFill(Color.web("#388E3C"));
        gc.fillRect(px, py, 6, ph);

        // right color lighter
        gc.setFill(Color.web("#81C784"));
        gc.fillRect(px + pw - 6, py, 6, ph);

        // pipe head
        double capH = 20, capW = pw + 10;
        double capX = px - 5;
        double capY = (py == 0) ? ph - capH : py;   // abajo si es superior, arriba si es inferior
        gc.setFill(Color.web("#4CAF50"));
        gc.fillRect(capX, capY, capW, capH);
        gc.setFill(Color.web("#388E3C"));
        gc.fillRect(capX, capY, 7, capH);
        gc.setFill(Color.web("#81C784"));
        gc.fillRect(capX + capW - 7, capY, 7, capH);
    }
}