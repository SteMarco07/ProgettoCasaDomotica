package stellino.marco.casajavafx.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An ellipse that can be drawn on a JavaFX canvas.
 */
public class FXEllipse {
    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;
    private boolean filled;

    /**
     * Constructs an empty ellipse.
     */
    public FXEllipse() {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs an ellipse.
     * @param x the leftmost x-coordinate
     * @param y the topmost y-coordinate
     * @param width the width
     * @param height the height
     */
    public FXEllipse(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.BLACK;
        this.filled = false;
    }

    /**
     * Gets the leftmost x-position of this ellipse.
     * @return the leftmost x-position
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the topmost y-position of this ellipse.
     * @return the topmost y-position
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the width of this ellipse.
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of this ellipse.
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Moves this ellipse by a given amount.
     * @param dx the amount by which to move in x-direction
     * @param dy the amount by which to move in y-direction
     */
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    /**
     * Resizes this ellipse both horizontally and vertically.
     * @param dw the amount by which to resize the width on each side
     * @param dh the amount by which to resize the height on each side
     */
    public void grow(double dw, double dh) {
        width += 2 * dw;
        height += 2 * dh;
        x -= dw;
        y -= dh;
    }

    /**
     * Sets the color of this ellipse.
     * @param newColor the new color
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Sets whether this ellipse is filled.
     * @param filled true if the ellipse should be filled, false if it should be outlined
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     * Draws this ellipse on the given graphics context.
     * @param gc the graphics context to draw on
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.setStroke(color);
        if (filled) {
            gc.fillOval(x, y, width, height);
        } else {
            gc.strokeOval(x, y, width, height);
        }
    }

    @Override
    public String toString() {
        return "FXEllipse[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
    }
}
