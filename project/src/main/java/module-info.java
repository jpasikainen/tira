module com.jpasikainen.tira {
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.jpasikainen.tira to javafx.fxml;
    exports com.jpasikainen.tira;
    exports com.jpasikainen.tira.logic;
    opens com.jpasikainen.tira.logic to javafx.fxml;
    exports com.jpasikainen.tira.gui;
    opens com.jpasikainen.tira.gui to javafx.fxml;
    exports com.jpasikainen.tira.solver;
    opens com.jpasikainen.tira.solver to javafx.fxml;
}