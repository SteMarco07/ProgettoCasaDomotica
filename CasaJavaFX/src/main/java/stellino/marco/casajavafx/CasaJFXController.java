package stellino.marco.casajavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

// Assicurati che il package Eccezioni sia correttamente definito e accessibile
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
    @FXML private TextField powerField;
    @FXML private TextField brightnessField;



    // Campi della gestione delle stanze
    @FXML private HBox HboxRadioRoom;
    @FXML private Button BtnAddRoom, BtnRemuveRoom;
    @FXML private RadioButton RdbAddRoom, RdbRemuveRoom;
    @FXML private HBox buttonContainer;


    private static final int GRID_START = 50;
    private static final int GRID_STEP = 50;
    private static final int GRID_TICK_SIZE = 5;

    private boolean isAddingOutlet = false;
    private Presa tempPresa;

    private String currentLightName;
    private String currentRoomName;

    @FXML
    public void initialize() {
        sistema = new SistemaDomotico();

        Image img = new Image(getClass().getResourceAsStream("casa.jpg"));
        houseImage.setImage(img);

        // Configura il listener per la selezione della lista delle stanze
        roomList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateRoomSelection(newVal);
            } else {
                // Se nessuna stanza è selezionata, pulisci le liste e nascondi i controlli
                selectedRoomText.setText("Nessuna Stanza Selezionata");
                outletList.setItems(FXCollections.emptyObservableList());
                outletComboBox.setItems(FXCollections.emptyObservableList());
                lightList.setItems(FXCollections.emptyObservableList());
                updateLightControls(null, null);
            }
        });        // Configura il listener per la selezione della lista delle lampadine
        // Questo listener viene attivato ogni volta che la selezione nella lista cambia
        lightList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            String selectedRoom = roomList.getSelectionModel().getSelectedItem();
            if (newVal != null && selectedRoom != null) {
                try {
                    // Estrai il nome della lampadina rimuovendo il suffisso (ON)/(OFF)
                    String lightName = newVal.contains("(OFF)") ? newVal.substring(0, newVal.length()-6) : newVal.substring(0, newVal.length()-5);

                    System.out.println("Lightname: '" + newVal + "' Nome pulito: '" + lightName + "'");
                    // Verifica che la lampadina esista prima di aggiornare i controlli
                    sistema.getStanza(selectedRoom).getLampadina(lightName);
                    updateLightControls(selectedRoom, lightName);
                } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
                    showError("Errore", "Impossibile trovare la lampadina: " + e.getMessage());
                    updateLightControls(null, null);
                }
            } else {
                // Se non c'è selezione o non c'è stanza selezionata, nascondi i controlli
                updateLightControls(null, null);
            }
        });

        // Configura i controlli della lampadina
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
        // Inizialmente, nessun controllo lampadina è visibile updateLightControls(null, null);
        updateRoomsList(); // Popola la lista delle stanze all'avvio
        houseCanvas.setOnMouseClicked(this::handleCanvasClick);

        ToggleGroup group = new ToggleGroup();
        RdbAddRoom.setToggleGroup(group);
        RdbRemuveRoom.setToggleGroup(group);
        RdbAddRoom.setSelected(true);
        RdbAddRoom.setSelected(true);

        // Inizializza con il pulsante Aggiungi
        buttonContainer.getChildren().clear();
        buttonContainer.getChildren().add(BtnAddRoom);

        group.selectedToggleProperty().addListener((o, old, newValue) -> {
            buttonContainer.getChildren().clear();
            if (newValue == RdbAddRoom) {
                buttonContainer.getChildren().add(BtnAddRoom);
            } else if (newValue == RdbRemuveRoom) {
                buttonContainer.getChildren().add(BtnRemuveRoom);
            }
        });
    }


    @FXML
    protected void onTurnAllOnClick() {
        try {
            sistema.accendiTutte();
            updateTotalPower();
            updateLightsList(); // Aggiorna la UI dopo aver acceso tutte le lampadine
        } catch (Exception e) {
            showError("Errore nell'accensione", "Non è stato possibile accendere tutte le lampadine: " + e.getMessage());
        }
    }

    @FXML
    protected void onTurnAllOffClick() {
        try {
            sistema.spegniTutte();
            updateTotalPower();
            updateLightsList(); // Aggiorna la UI dopo aver spento tutte le lampadine
        } catch (Exception e) {
            showError("Errore nello spegnimento", "Non è stato possibile spegnere tutte le lampadine: " + e.getMessage());
        }
    }

    @FXML
    protected void onStartOutletPositioning() {
        isAddingOutlet = true;
        tempPresa = null;
        showInfo("Posizionamento Presa", "Clicca sulla mappa per posizionare la presa");
    }

    private void handleCanvasClick(MouseEvent event) {
        if (!isAddingOutlet) return;

        int x = (int) event.getX();
        int y = (int) event.getY();

        outletXField.setText(String.valueOf(x));
        outletYField.setText(String.valueOf(y));

        // Mostra un'anteprima visiva
        GraphicsContext gc = houseCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, houseCanvas.getWidth(), houseCanvas.getHeight());
        updateHouseLayout();

        gc.setFill(Color.RED);
        gc.fillOval(x - 5, y - 5, 10, 10);

        isAddingOutlet = false;
    }

    @FXML
    protected void onConfirmOutletPosition() {
        if (outletXField.getText().isEmpty() || outletYField.getText().isEmpty()) {
            showError("Errore", "Posizione non valida");
            return;
        }
        onAddOutletClick();  // Chiama la funzione esistente
    }

    @FXML
    protected void onAddRoomClick() {
        String roomName = roomNameField.getText().trim();
        if (roomName.isEmpty()) {
            showError("Nome Stanza Non Valido", "Per favore, inserisci un nome per la stanza.");
            return;
        }

        try {
            sistema.aggiungiStanza(new Stanza(roomName));
            updateRoomsList();
            roomNameField.clear();
            updateHouseLayout();
        } catch (StanzaEsistente e) {
            showError("Stanza Già Esistente", "Una stanza con questo nome esiste già.");
        }
    }
    
    @FXML
    protected void onAddOutletClick() {
        String selectedRoom = roomList.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            showError("Nessuna Stanza Selezionata", "Per favore, seleziona prima una stanza.");
            return;
        }

        String outletName = outletNameField.getText().trim();
        if (outletName.isEmpty()) {
            showError("Nome Presa Non Valido", "Per favore, inserisci un nome per la presa.");
            return;
        }

        // Ottieni e valida le coordinate
        String xStr = outletXField.getText().trim();
        String yStr = outletYField.getText().trim();
        if (xStr.isEmpty() || yStr.isEmpty()) {
            showError("Posizione Non Valida", "Per favore, inserisci le coordinate X e Y.");
            return;
        }

        try {
            int x = Integer.parseInt(xStr);
            int y = Integer.parseInt(yStr);

            // Crea la presa con la posizione
            Presa presa = new Presa(outletName, x, y);
            sistema.aggiungiPresa(selectedRoom, presa);

            // Pulisci gli input
            outletNameField.clear();
            outletXField.clear();
            outletYField.clear();

            updateOutletsList(selectedRoom);
            updateHouseLayout();
        } catch (NumberFormatException e) {
            showError("Posizione Non Valida", "Le coordinate X e Y devono essere numeri.");
        } catch (StanzaNonTrovata | PresaEsistente e) {
            showError("Errore Aggiunta Presa", e.getMessage());
        }
    }

    @FXML
    protected void onAddLightClick() {
        String selectedRoom = roomList.getSelectionModel().getSelectedItem();
        String selectedOutlet = outletComboBox.getValue();
        String lightName = lightNameField.getText().trim();
        int power = !powerField.getText().trim().isEmpty() ? Integer.parseInt(powerField.getText().trim()) : 60;


        if (selectedRoom == null || selectedOutlet == null) {
            showError("Selezione Richiesta", "Per favore, seleziona sia una stanza che una presa.");
            return;
        }

        if (lightName.isEmpty()) {
            showError("Nome Lampadina Non Valido", "Per favore, inserisci un nome per la lampadina.");
            return;
        }



        try {
            Lampadina light = new Lampadina(lightName, power); // Esempio: lampadina da 60W
            sistema.aggiungiLampadina(selectedRoom, selectedOutlet, light);
            updateLightsList(); // Aggiorna la lista delle lampadine
            lightNameField.clear();
            powerField.clear();
            updateHouseLayout();
        } catch (StanzaNonTrovata | PresaNonTrovata | PresaOccupata | LampadinaEsistente e) {
            showError("Errore Aggiunta Lampadina", e.getMessage());
        }
    }

    private void updateRoomSelection(String roomName) {
        currentRoomName = roomName; // Aggiorna la stanza corrente selezionata
        selectedRoomText.setText("Stanza: " + roomName);
        updateOutletsList(roomName);
        updateLightsList(); // Aggiorna la lista delle lampadine quando cambia la stanza
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
            showError("Errore", "Impossibile trovare la stanza: " + roomName);
            outletList.setItems(FXCollections.emptyObservableList());
            outletComboBox.setItems(FXCollections.emptyObservableList());
        }
    }

    /**
     * Aggiorna la lista delle lampadine, preservando la selezione corrente.
     */
    private void updateLightsList() {
        String selectedRoom = roomList.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) {
            // Se nessuna stanza è selezionata, svuota la lista delle lampadine e nascondi i controlli
            lightList.setItems(FXCollections.emptyObservableList());
            updateLightControls(null, null);
            return;
        }

        // Salva il nome della lampadina attualmente selezionata (se presente)
        String previouslySelectedLightName = null;
        String currentSelectedItemInList = lightList.getSelectionModel().getSelectedItem();
        if (currentSelectedItemInList != null) {
            // Estrai solo il nome della lampadina (es. "Lampadina1" da "Lampadina1 (ON)")
            previouslySelectedLightName = currentSelectedItemInList.split(" ")[0];
        }

        try {
            Stanza stanza = sistema.getStanza(selectedRoom);
            ObservableList<String> lights = FXCollections.observableArrayList();
            for (Lampadina lamp : stanza.getLampadine()) {
                lights.add(lamp.getNome() + (lamp.isAcceso() ? " (ON)" : " (OFF)"));
            }
            lightList.setItems(lights); // Aggiorna gli elementi della ListView

            // Tenta di riselezionare la lampadina che era precedentemente selezionata
            if (previouslySelectedLightName != null) {
                for (String item : lights) {
                    // Controlla se l'elemento nella nuova lista inizia con il nome della lampadina salvato
                    if (item.startsWith(previouslySelectedLightName + " ")) {
                        lightList.getSelectionModel().select(item);
                        // Il listener di lightList.selectedItemProperty() si attiverà automaticamente
                        // per aggiornare i controlli, quindi non è necessario chiamare updateLightControls qui.
                        break;
                    }
                }
            } else {
                // Se non c'era nessuna selezione precedente o l'elemento non è più presente,
                // assicurati che i controlli siano nascosti.
                updateLightControls(null, null);
            }

        } catch (StanzaNonTrovata e) {
            showError("Errore", "Impossibile trovare la stanza: " + selectedRoom);
            lightList.setItems(FXCollections.emptyObservableList()); // Svuota la lista in caso di errore
            updateLightControls(null, null);
        }
    }

    /**
     * Aggiorna i controlli della lampadina (slider, color picker, toggle) in base alla lampadina selezionata.
     * Nasconde i controlli se roomName o lightName sono null.
     */
    private void updateLightControls(String roomName, String lightName) {
        this.currentLightName = lightName;
        this.currentRoomName = roomName;

        if (roomName == null || lightName == null) {
            lightControlsBox.setVisible(false);
            lightControlsBox.setManaged(false); // Non occupa spazio nel layout
            return;
        }

        try {
            Stanza stanza = sistema.getStanza(roomName);
            Lampadina lamp = stanza.getLampadina(lightName);

            // Aggiorna i controlli con lo stato attuale della lampadina
            lightToggle.setSelected(lamp.isAcceso());
            brightnessSlider.setValue(lamp.getLum());

            // Usa la stringa RGB per ottenere il colore della lampadina e impostarlo nel ColorPicker
            String lampColor = lamp.getColoreRGB();
            // Gestisce il caso in cui il colore non sia un formato web valido, impostando il bianco come fallback
            try {
                lightColorPicker.setValue(Color.web(lampColor));
            } catch (IllegalArgumentException e) {
                System.err.println("Formato colore RGB non valido per la lampadina " + lightName + ": " + lampColor + ". Impostazione su bianco.");
                lightColorPicker.setValue(Color.WHITE);
            }

            lightControlsBox.setVisible(true);
            lightControlsBox.setManaged(true); // Occupa spazio nel layout
        } catch (StanzaNonTrovata e) {
            showError("Errore", "Impossibile trovare la stanza: " + e.getMessage());
            lightControlsBox.setVisible(false);
            lightControlsBox.setManaged(false);
        } catch (LampadinaNonTrovata e) {
            showError("Errore", "Impossibile trovare la lampadina: " + e.getMessage());
            lightControlsBox.setVisible(false);
            lightControlsBox.setManaged(false);
        }
    }

    private void updateSelectedLightBrightness() {
        if (currentRoomName == null || currentLightName == null) return;

        try {
            sistema.modificaLumLampadina(currentRoomName, currentLightName, (int)brightnessSlider.getValue());
            updateTotalPower();
            updateHouseLayout();
        } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
            showError("Errore", "Impossibile aggiornare la luminosità della lampadina: " + e.getMessage());
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
            updateLightsList(); // Aggiorna la lista per riflettere lo stato ON/OFF
            updateHouseLayout();
        } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
            showError("Errore", "Impossibile cambiare lo stato della lampadina: " + e.getMessage());
        }
    }

    private void updateSelectedLightColor() {
        if (currentRoomName == null || currentLightName == null) return;

        try {
            Stanza stanza = sistema.getStanza(currentRoomName);
            Lampadina lamp = stanza.getLampadina(currentLightName);

            // Aggiorna il colore della lampadina con il colore selezionato dal ColorPicker
            // lightColorPicker.getValue().toString() restituisce una stringa nel formato 0xRRGGBBAA
            lamp.setColoreRGB(lightColorPicker.getValue().toString());

            // Aggiorna la rappresentazione visiva della casa
            updateHouseLayout();
        } catch (StanzaNonTrovata | LampadinaNonTrovata e) {
            showError("Errore", "Impossibile aggiornare il colore della lampadina: " + e.getMessage());
        }
    }

    private void updateTotalPower() {
        float totalPower = sistema.getPotenzaSistema();
        totalPowerText.setText(String.format("Potenza Totale: %.1fW", totalPower));
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

        // Disegna gli assi
        gc.strokeLine(GRID_START, GRID_START, width, GRID_START);  // Asse X
        gc.strokeLine(GRID_START, GRID_START, GRID_START, height); // Asse Y

        // Disegna la griglia e i segni di spunta
        gc.setStroke(javafx.scene.paint.Color.rgb(200, 200, 200));
        for (int i = GRID_START + GRID_STEP; i < width; i += GRID_STEP) {
            // Linea verticale della griglia
            gc.strokeLine(i, GRID_START, i, height);
            // Segno di spunta asse X
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeLine(i, GRID_START, i, GRID_START + GRID_TICK_SIZE);
            gc.strokeText(String.valueOf(i), i - 10, GRID_START + 20);
            gc.setStroke(javafx.scene.paint.Color.rgb(200, 200, 200));
        }

        for (int i = GRID_START + GRID_STEP; i < height; i += GRID_STEP) {
            // Linea orizzontale della griglia
            gc.strokeLine(GRID_START, i, width, i);
            // Segno di spunta asse Y
            gc.setStroke(javafx.scene.paint.Color.BLACK);
            gc.strokeLine(GRID_START, i, GRID_START + GRID_TICK_SIZE, i);
            gc.strokeText(String.valueOf(i), GRID_START - 30, i + 5);
            gc.setStroke(javafx.scene.paint.Color.rgb(200, 200, 200));
        }
    }

    private void updateHouseLayout() {
        GraphicsContext gc = houseCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, houseCanvas.getWidth(), houseCanvas.getHeight());

        // Disegna la griglia
        drawGrid(gc);

        // Disegna le stanze e il loro contenuto
        for (Stanza stanza : sistema.getStanze()) {
            for (Presa presa : stanza.getPrese()) {
                // Delega il disegno alla Presa
                presa.disegna(gc);

                // Delega il disegno alla Lampadina se presente
                Lampadina lamp = presa.getLampadina();
                if (lamp != null) {
                    // Passa le coordinate della presa alla lampadina per il disegno
                    lamp.disegna(gc, presa.getX(), presa.getY());
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
            showInfo("Salvataggio Completato", "Il sistema è stato salvato correttamente.");
            System.exit(0);
        } catch (IOException e) {
            showError("Errore Durante il Salvataggio", "Non è stato possibile salvare il sistema: " + e.getMessage());
        }
    }

    @FXML
    protected void onSaveCSVClick() {
        try {
            sistema.salvaInFileCSV(getFilePath("SistemaDomotico.csv"));
            showInfo("Salvataggio Completato", "Il sistema è stato salvato correttamente in formato CSV.");
        } catch (IOException e) {
            showError("Errore Durante il Salvataggio", "Non è stato possibile salvare il sistema in CSV: " + e.getMessage());
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
