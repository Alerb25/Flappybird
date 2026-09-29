package com.org_content;

// Bird.java  – simple physics for the bird
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Bird {
    public static final double SIZE    = 30;
    private static final double GRAVITY = 0.45;
    private static final double FLAP    = -8.5;

    private double x, y, vy;

    public Bird(double x, double y){
        this.x = x;
        this.y = y;
        this.vy = 0;
    }

    public void flap()   { vy = FLAP; }
    public double getX() { return x;  }
    public double getY() { return y;  }


    public void update() {
        vy += GRAVITY;
        y  += vy;
    }

     /** 
      * @param gc
      */
     public void render(GraphicsContext gc) {
        double angle = Math.toDegrees(Math.atan2(vy, 6));
        angle = Math.max(-30, Math.min(angle, 90));

        gc.save();
        gc.translate(x + SIZE / 2, y + SIZE / 2);
        gc.rotate(angle);

        // Body
        gc.setFill(Color.web("#F7DC6F"));
        gc.fillOval(-SIZE / 2, -SIZE / 2, SIZE, SIZE);

        // Wing
        gc.setFill(Color.web("#F0B429"));
        gc.fillOval(-SIZE * 0.1, -SIZE * 0.05, SIZE * 0.5, SIZE * 0.3);

        // eye
        gc.setFill(Color.WHITE);
        gc.fillOval(SIZE * 0.15, -SIZE * 0.3, SIZE * 0.3, SIZE * 0.3);
        gc.setFill(Color.BLACK);
        gc.fillOval(SIZE * 0.22, -SIZE * 0.24, SIZE * 0.14, SIZE * 0.14);

        // Peak
        gc.setFill(Color.web("#E67E22"));
        double[] bx = { SIZE * 0.45,  SIZE * 0.7,  SIZE * 0.45 };
        double[] by = { -SIZE * 0.1,  0.0,         SIZE * 0.1  };
        gc.fillPolygon(bx, by, 3);

        gc.restore();
    }

}
