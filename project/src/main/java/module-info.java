module com.jpasikainen.tira {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jpasikainen.tira to javafx.fxml;
    exports com.jpasikainen.tira;
}