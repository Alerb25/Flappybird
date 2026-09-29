package com.org_content;
// GamePane.java  – canvas + game loop + main logic
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.text.*;


import java.util.*;

public class GamePane extends Pane{
    private final Canvas canvas = new Canvas(App.WIDTH, App.HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    
    private Bird bird;
    private final List<Pipe> pipes = new ArrayList<>();
    private AnimationTimer loop;

    private boolean started = false;
    private boolean gameOver = false;
    private int score = 0;

    // time in between pipes
    private long lastPipe = 0;
    private static final long PIPE_INTERVAL = 1_800_000_000L;

    public GamePane() {
        getChildren().add(canvas);
        init();

        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.UP) {
                if (gameOver) {
                    init();
                } else {
                    started = true;
                    bird.flap();
                }
            }
        });
    }

    private void init() {
        bird  = new Bird(100, App.HEIGHT / 2.0);
        pipes.clear();
        score    = 0;
        started  = false;
        gameOver = false;
        lastPipe = 0;

        if (loop != null) loop.stop();
        loop = new AnimationTimer() {
            @Override public void handle(long now) {
                update(now);
                render();
            }
        };
        loop.start();
    }

    /** 
     * @param now
     */
    // logic
    private void update(long now) {
        if (!started || gameOver) return;

        bird.update();

        // generate pipes
        if (lastPipe == 0) lastPipe = now;
        if (now - lastPipe > PIPE_INTERVAL) {
            pipes.add(new Pipe(App.WIDTH));
            lastPipe = now;
        }

        for (Pipe p : pipes) p.update();

        // colision and scores
        for (Pipe p : pipes) {
            if (p.hits(bird))        { triggerGameOver(); return; }
            if (p.passed(bird))      { score++; }
        }

        // Límites pantalla
        if (bird.getY() < 0 || bird.getY() > App.HEIGHT - Bird.SIZE) {
            triggerGameOver();
        }

        pipes.removeIf(Pipe::isOffScreen);
    }

    private void triggerGameOver() {
        gameOver = true;
        loop.stop();
    }

    //render
    private void render() {
        //baackground
        gc.setFill(Color.web("#70C5CE"));
        gc.fillRect(0, 0, App.WIDTH, App.HEIGHT);

        // floor
        gc.setFill(Color.web("#DEB887"));
        gc.fillRect(0, App.HEIGHT - 60, App.WIDTH, 60);
        gc.setFill(Color.web("#228B22"));
        gc.fillRect(0, App.HEIGHT - 65, App.WIDTH, 10);

        for (Pipe p : pipes) p.render(gc);
        bird.render(gc);

        // HUD
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(String.valueOf(score), App.WIDTH / 2.0, 60);

        if (!started) drawCenteredMsg("Pulsa ESPACIO para empezar");
        if (gameOver)  drawCenteredMsg("Game Over  –  ESPACIO para reiniciar");
    }

    /** 
     * @param msg
     */
    private void drawCenteredMsg(String msg) {
        gc.setFill(Color.color(0, 0, 0, 0.45));
        gc.fillRoundRect(40, App.HEIGHT / 2.0 - 50, App.WIDTH - 80, 80, 16, 16);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(msg, App.WIDTH / 2.0, App.HEIGHT / 2.0 + 8);
    }

}