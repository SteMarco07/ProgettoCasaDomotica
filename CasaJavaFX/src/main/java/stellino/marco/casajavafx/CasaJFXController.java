package stellino.marco.casajavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.io.File;



import Eccezioni.*;

public class CasaJFXController {
    private SistemaDomotico sistema;

    @FXML private Text totalPowerText;
    @FXML private TextField roomNameField;
    @FXML private ListView<String> roomList;
    @FXML private Text selectedRoomText;
    @FXML private TextField outletNameField;
    @FXML private ListView<String> outletList;
    @FXML private TextField lightNameField;
    @FXML private ComboBox<String> outletComboBox;
    @FXML private ListView<String> lightList;
    @FXML private Slider brightnessSlider;
    @FXML private ColorPicker lightColorPicker;
    @FXML private ToggleButton lightToggle;
    @FXML private Canvas houseCanvas;
    @FXML private ImageView houseImage;
    @FXML private TextField outletXField;
    @FXML private TextField outletYField;
    @FXML private VBox lightControlsBox;

    private static final int GRID_START = 50;
    private static final int GRID_STEP = 50;
    private static final int GRID_TICK_SIZE = 5;

    private String currentLightName;
    private String currentRoomName;

    @FXML
    public void initialize() {
        sistema = new SistemaDomotico();
        
        // Load house background image
        Image img = new Image(getClass().getResourceAsStream("casa.jpg"));
        houseImage.setImage(img);
        
        // Configure room list selection listener
        roomList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateRoomSelection(newVal);
            }
        });

        // Configure light list selection listener
        lightList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            String selectedRoom = roomList.getSelectionModel().getSelectedItem();
            if (newVal != null) {
                String lightName = newVal.split(" ")[0]; // Remove (ON)/(OFF) suffix
                updateLightControls(selectedRoom, lightName);
            } else {
                updateLightControls(null, null);
            }
        });

        // Configure light controls
        lightColorPicker.setValue(javafx.scene.paint.Color.WHITE);
        lightColorPicker.setOnAction(e -> updateSelectedLightColor());
        
        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateSelectedLightBrightness();
        });

        lightToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            updateSelectedLightPower(newVal);
        });

        updateTotalPower();
        updateHouseLayout();
        updateLightControls(null, null);
    }

    @FXML
    protected void onTurnAllOnClick() {
        try {
            sistema.accendiTutte();
            updateTotalPower();
            updateLightsList(); // Refresh the UI
        } catch (Exception e) {
            showError("Error turning on all lights", e.getMessage());
        }
    }

    @FXML
    protected void onTurnAllOffClick() {
        try {
            sistema.spegniTutte();
            updateTotalPower();
            updateLightsList(); // Refresh the UI
        } catch (Exception e) {
            showError("Error turning off all lights", e.getMessage());
        }
    }

    @FXML
    protected void onAddRoomClick() {
        String roomName = roomNameField.getText().trim();
        if (roomName.isEmpty()) {
            showError("Invalid Room Name", "Please enter a room name");
            return;
        }

        try {
            sistema.aggiungiStanza(new Stanza(roomName));
            updateRoomsList();
            roomNameField.clear();
            updateHouseLayout();
        } catch (StanzaEsistente e) {
            showError("Room Already Exists", "A room with this name already exists");
        }
    }

    @FXML
    protected void onDeleteRoomClick() {
        String selectedRoom = roomList.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            showError("No Room Selected", "Please select a room to delete");
            return;
        }

        try {
            // Remove room logic here
            updateRoomsList();
            selectedRoomText.setText("No Room Selected");
            updateTotalPower();
            updateHouseLayout();
        } catch (Exception e) {
            showError("Error Deleting Room", e.getMessage());
        }
    }

    @FXML
    protected void onAddOutletClick() {
        String selectedRoom = roomList.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            showError("No Room Selected", "Please select a room first");
            return;
        }

        String outletName = outletNameField.getText().trim();
        if (outletName.isEmpty()) {
            showError("Invalid Outlet Name", "Please enter an outlet name");
            return;
        }

        // Get and validate coordinates
        String xStr = outletXField.getText().trim();
        String yStr = outletYField.getText().trim();
        if (xStr.isEmpty() || yStr.isEmpty()) {
            showError("Invalid Position", "Please enter X and Y coordinates");
            return;
        }

        try {
            int x = Integer.parseInt(xStr);
            int y = Integer.parseInt(yStr);
            
            // Create presa with position
            Presa presa = new Presa(outletName, x, y);
            sistema.aggiungiPresa(selectedRoom, presa);
            
            // Clear inputs
            outletNameField.clear();
            outletXField.clear();
            outletYField.clear();
            
            updateOutletsList(selectedRoom);
            updateHouseLayout();
        } catch (NumberFormatException e) {
            showError("Invalid Position", "X and Y must be numbers");
        } catch (StanzaNonTrovata | PresaEsistente e) {
            showError("Error Adding Outlet", e.getMessage());
        }
    }

    @FXML
    protected void onAddLightClick() {
        String selectedRoom = roomList.getSelectionModel().getSelectedItem();
        String selectedOutlet = outletComboBox.getValue();
        String lightName = lightNameField.getText().trim();

        if (selectedRoom == null || selectedOutlet == null) {
            showError("Selection Required", "Please select both a room and an outlet");
            return;
        }

        if (lightName.isEmpty()) {
            showError("Invalid Light Name", "Please enter a light name");
            return;
        }

        try {
            Lampadina light = new Lampadina(lightName, 60); // Example: 60W bulb
            sistema.aggiungiLampadina(selectedRoom, selectedOutlet, light);
            updateLightsList();
            lightNameField.clear();
            updateHouseLayout();
        } catch (StanzaNonTrovata | PresaNonTrovata | PresaOccupata | LampadinaEsistente e) {
            showError("Error Adding Light", e.getMessage());
        }
    }

    private void updateRoomSelection(String roomName) {
        selectedRoomText.setText("Room: " + roomName);
        updateOutletsList(roomName);
        updateLightsList();
        updateHouseLayout();
    }

    private void updateRoomsList() {
        ObservableList<String> rooms = FXCollections.observableArrayList();
        for (Stanza stanza : sistema.getStanze()) {
            rooms.add(stanza.getNome());
        }
        roomList.setItems(rooms);
    }

    private void updateOutletsList(String roomName) {
        try {
            Stanza stanza = sistema.getStanza(roomName);
            ObservableList<String> outlets = FXCollections.observableArrayList();
            for (Presa presa : stanza.getPrese()) {
                outlets.add(presa.getNome());
            }
            outletList.setItems(outlets);
            outletComboBox.setItems(outlets);
        } catch (StanzaNonTrovata e) {
            showError("Error", "Could not find room: " + roomName);
        }
    }

    private void updateLightsList() {
        String selectedRoom = roomList.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            updateLightControls(null, null);
            return;
        }

        try {
            Stanza stanza = sistema.getStanza(selectedRoom);
            ObservableList<String> lights = FXCollections.observableArrayList();
            for (Lampadina lamp : stanza.getLampadine()) {
                lights.add(lamp.getNome() + (lamp.isAcceso() ? " (ON)" : " (OFF)"));
            }
            lightList.setItems(lights);

            // Update light controls based on selection
            String selectedLight = lightList.getSelectionModel().getSelectedItem();
            if (selectedLight != null) {
                updateLightControls(selectedRoom, selectedLight.split(" ")[0]);
            } else {
                updateLightControls(null, null);
            }
        } catch (StanzaNonTrovata e) {
            showError("Error", "Could not find room: " + selectedRoom);
            updateLightControls(null, null);
        }
    }

    private void updateLightControls(String roomName, String lightName) {
        currentLightName = lightName;
        currentRoomName = roomName;
        
        if (roomName == null || lightName == null) {
            lightControlsBox.setVisible(false);
            lightControlsBox.setManaged(false);
            return;
        }

        try {
            Stanza stanza = sistema.getStanza(roomName);
            Lampadina lamp = stanza.getLampadina(lightName);
            
            // Update controls with current light state
            lightToggle.setSelected(lamp.isAcceso());
            brightnessSlider.setValue(lamp.getLum());
            
            Color lampColor = lamp.getColore();
            lightColorPicker.setValue(lampColor);
            
            lightControlsBox.setVisible(true);
            lightControlsBox.setManaged(true);
        } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
            lightControlsBox.setVisible(false);
            lightControlsBox.setManaged(false);
            showError("Error", "Could not find light: " + e.getMessage());
        }
    }

    private void updateSelectedLightBrightness() {
        if (currentRoomName == null || currentLightName == null) return;

        try {
            sistema.modificaLumLampadina(currentRoomName, currentLightName, (int)brightnessSlider.getValue());
            updateTotalPower();
            updateHouseLayout();
        } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
            showError("Error", "Could not update light brightness: " + e.getMessage());
        }
    }

    private void updateSelectedLightPower(boolean on) {
        if (currentRoomName == null || currentLightName == null) return;

        try {
            if (on) {
                sistema.accendiLampadina(currentRoomName, currentLightName);
            } else {
                sistema.spegniLampadina(currentRoomName, currentLightName);
            }
            updateTotalPower();
            updateLightsList();
            updateHouseLayout();
        } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
            showError("Error", "Could not toggle light: " + e.getMessage());
        }
    }

    private void updateSelectedLightColor() {
        if (currentRoomName == null || currentLightName == null) return;

        try {
            Stanza stanza = sistema.getStanza(currentRoomName);
            Lampadina lamp = stanza.getLampadina(currentLightName);
            
            // Update the light's color with the selected color from the color picker
            lamp.setColore(lightColorPicker.getValue());
            
            // Update the visual representation of the light
            updateHouseLayout();
        } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
            showError("Error", "Could not update light color: " + e.getMessage());
        }
    }

    private void updateTotalPower() {
        float totalPower = sistema.getPotenzaSistema();
        totalPowerText.setText(String.format("Total Power: %.1fW", totalPower));
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void drawGrid(GraphicsContext gc) {
        double width = houseCanvas.getWidth();
        double height = houseCanvas.getHeight();
        
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        gc.setLineWidth(1);
        
        // Draw axes
        gc.strokeLine(GRID_START, GRID_START, width, GRID_START);  // X axis
        gc.strokeLine(GRID_START, GRID_START, GRID_START, height); // Y axis
        
        // Draw grid and ticks
        gc.setStroke(javafx.scene.paint.Color.rgb(200, 200, 200));
        for (int i = GRID_START + GRID_STEP; i < width; i += GRID_STEP) {
            // Vertical grid line
            gc.strokeLine(i, GRID_START, i, height);
            // X axis tick
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeLine(i, GRID_START, i, GRID_START + GRID_TICK_SIZE);
            gc.strokeText(String.valueOf(i), i - 10, GRID_START + 20);
            gc.setStroke(javafx.scene.paint.Color.rgb(200, 200, 200));
        }
        
        for (int i = GRID_START + GRID_STEP; i < height; i += GRID_STEP) {
            // Horizontal grid line
            gc.strokeLine(GRID_START, i, width, i);
            // Y axis tick
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeLine(GRID_START, i, GRID_START + GRID_TICK_SIZE, i);
            gc.strokeText(String.valueOf(i), GRID_START - 30, i + 5);
            gc.setStroke(javafx.scene.paint.Color.rgb(200, 200, 200));
        }
    }

    private void updateHouseLayout() {
        GraphicsContext gc = houseCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, houseCanvas.getWidth(), houseCanvas.getHeight());
        
        // Draw grid
        drawGrid(gc);
        
        // Draw rooms and their contents
        for (Stanza stanza : sistema.getStanze()) {
            for (Presa presa : stanza.getPrese()) {
                // Draw outlet as a small square
                gc.setFill(javafx.scene.paint.Color.GRAY);
                gc.fillRect(presa.getX(), presa.getY(), 10, 10);
                
                // Draw light if present
                Lampadina lamp = presa.getLampadina();
                if (lamp != null) {
                    int outletX = presa.getX();
                    int outletY = presa.getY();
                    double bulbSize = 20;  // Dimensione del bulbo
                    double baseWidth = 10;  // Larghezza della base
                    double baseHeight = 5;  // Altezza della base
                    
                    // Draw base (rectangle)
                    gc.setFill(javafx.scene.paint.Color.GRAY);
                    gc.fillRect(outletX - baseWidth/2, outletY - baseHeight, baseWidth, baseHeight);
                    
                    // Draw bulb border (ellipse)
                    gc.setStroke(javafx.scene.paint.Color.BLACK);
                    gc.strokeOval(outletX - bulbSize/2, outletY - bulbSize - baseHeight, bulbSize, bulbSize);
                    
                    // Draw bulb (ellipse with color)
                    if (lamp.isAcceso()) {
                        javafx.scene.paint.Color color = lightColorPicker.getValue();
                        double brightness = lamp.getLum() / 100.0;
                        javafx.scene.paint.Color fxColor = new javafx.scene.paint.Color(
                            color.getRed(),
                            color.getGreen(),
                            color.getBlue(),
                            brightness
                        );
                        gc.setFill(fxColor);
                    } else {
                        gc.setFill(javafx.scene.paint.Color.WHITE);
                    }
                    gc.fillOval(outletX - bulbSize/2, outletY - bulbSize - baseHeight, bulbSize, bulbSize);
                    
                    // Draw light name
                    gc.setFill(javafx.scene.paint.Color.BLACK);
                    gc.fillText(lamp.getNome(), outletX - bulbSize/2, outletY - bulbSize - baseHeight - 5);
                }
            }
        }
    }

    private String getFilePath(String fileName) {
        // Crea la cartella "file" se non esiste
        File fileDir = new File("file");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return "file" + File.separator + fileName;
    }

    @FXML
    protected void onSaveAndExitClick() {
        try {
            sistema.serializza(getFilePath("SistemaDomotico.bin"));
            showInfo("Salvataggio completato", "Il sistema è stato salvato correttamente");
            System.exit(0);
        } catch (IOException e) {
            showError("Errore durante il salvataggio", "Non è stato possibile salvare il sistema: " + e.getMessage());
        }
    }

    @FXML
    protected void onSaveCSVClick() {
        try {
            sistema.salvaInFileCSV(getFilePath("SistemaDomotico.csv"));
            showInfo("Salvataggio completato", "Il sistema è stato salvato correttamente in formato CSV");
        } catch (IOException e) {
            showError("Errore durante il salvataggio", "Non è stato possibile salvare il sistema in CSV: " + e.getMessage());
        }
    }

    private void showInfo(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}