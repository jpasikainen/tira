module com.jpasikainen.tira {
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.jpasikainen.tira to javafx.fxml;
    exports com.jpasikainen.tira;
    exports com.jpasikainen.tira.logic;
    opens com.jpasikainen.tira.logic to javafx.fxml;
    exports com.jpasikainen.tira.gui;
    opens com.jpasikainen.tira.gui to javafx.fxml;
    exports com.jpasikainen.tira.util;
    opens com.jpasikainen.tira.util to javafx.fxml;
}