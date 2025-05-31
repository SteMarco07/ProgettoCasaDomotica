package stellino.marco.casajavafx.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Text that can be drawn on a JavaFX canvas.
 */
public class FXText {
    private double x;
    private double y;
    private String message;
    private Color color;
    private double fontSize;

    /**
     * Constructs a text at a given location.
     * @param x the leftmost x-position
     * @param y the topmost y-position
     * @param message the text string
     */
    public FXText(double x, double y, String message) {
        this.x = x;
        this.y = y;
        this.message = message;
        this.color = Color.BLACK;
        this.fontSize = 12;
    }

    /**
     * Gets the leftmost x-position of the text.
     * @return the leftmost x-position
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the topmost y-position of the text.
     * @return the topmost y-position
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the width of the bounding box.
     * @return the width
     */
    public double getWidth() {
        Text text = new Text(message);
        text.setFont(Font.font(fontSize));
        return text.getBoundsInLocal().getWidth();
    }

    /**
     * Gets the height of the bounding box.
     * @return the height
     */
    public double getHeight() {
        Text text = new Text(message);
        text.setFont(Font.font(fontSize));
        return text.getBoundsInLocal().getHeight();
    }

    /**
     * Changes the text message.
     * @param message the new text string
     */
    public void setText(String message) {
        this.message = message;
    }

    /**
     * Moves this text by a given amount.
     * @param dx the amount by which to move in x-direction
     * @param dy the amount by which to move in y-direction
     */
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
    }

    /**
     * Sets the color of this text.
     * @param newColor the new color
     */
    public void setColor(Color newColor) {
        color = newColor;
    }

    /**
     * Sets the font size of this text.
     * @param size the new font size
     */
    public void setFontSize(double size) {
        fontSize = size;
    }

    /**
     * Draws this text on the given graphics context.
     * @param gc the graphics context to draw on
     */
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.setFont(Font.font(fontSize));
        gc.fillText(message, x, y + fontSize); // Add fontSize to y to account for text baseline
    }

    @Override
    public String toString() {
        return "FXText[x=" + x + ",y=" + y + ",text=\"" + message + "\"]";
    }
}
