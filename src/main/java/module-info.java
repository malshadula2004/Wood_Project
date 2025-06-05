module org.example.ggg {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    requires javax.mail.api;
    requires itextpdf;
    requires mysql.connector.j;
    requires org.postgresql.jdbc;
    requires static lombok;


    exports org.example.ggg;
    opens org.example.ggg.controller to javafx.fxml;
    opens org.example.ggg.dto to javafx.base;
    opens org.example.ggg.model to javafx.base;
    opens org.example.ggg.TM to javafx.base;
}
