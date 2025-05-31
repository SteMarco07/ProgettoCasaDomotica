package stellino.marco.casajavafx.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A line that can be drawn on a JavaFX canvas.
 */
public class FXLine {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private Color color;

    /**
     * Constructs a line between two points.
     * @param x1 x-coordinate of the first point
     * @param y1 y-coordinate of the first point
     * @param x2 x-coordinate of the second point
     * @param y2 y-coordinate of the second point
     */
    public FXLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = Color.BLACK;
    }

    /**
     * Gets the leftmost x-position of the line.
     * @return the leftmost x-position
     */
    public double getX() {
        return Math.min(x1, x2);
    }

    /**
     * Gets the topmost y-position of the line.
     * @return the topmost y-position
     */
    public double getY() {
        return Math.min(y1, y2);
    }

    /**
     * Gets the width of the bounding box.
     * @return the width
     */
    public double getWidth() {
        return Math.abs(x2 - x1);
    }

    /**
     * Gets the height of the bounding box.
     * @return the height
     */
    public double getHeight() {
        return Math.abs(y2 - y1);
    }

    /**
     * Moves this line by a given amount.
     * @param dx the amount by which to move in x-direction
     * @param dy the amount by which to move in y-direction
     */
    public void translate(double dx, double dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
    }

    /**
     * Resizes this line by moving its endpoints.
     * @param dw the amount by which to resize horizontally
     * @param dh the amount by which to resize vertically
     */
    public void grow(double dw, double dh) {
        if (x1 <= x2) {
            x1 -= dw;
            x2 += dw;
        } else {
            x1 += dw;
            x2 -= dw;
        }
        if (y1 <= y2) {
            y1 -= dh;
            y2 += dh;
        } else {
            y1 += dh;
            y2 -= dh;
        }
    }

    /**
     * Sets the color of this line.
     * @param newColor the new color
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Draws this line on the given graphics context.
     * @param gc the graphics context to draw on
     */
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public String toString() {
        return "FXLine[x1=" + x1 + ",y1=" + y1 + ",x2=" + x2 + ",y2=" + y2 + "]";
    }
}
