module stellino.marco.casajavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens stellino.marco.casajavafx to javafx.fxml;
    exports stellino.marco.casajavafx;
}