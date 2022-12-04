module com.ndt.productmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.ndt.productmanagement to javafx.fxml;
    exports com.ndt.productmanagement;
    exports com.ndt.pojo;
    exports com.ndt.pojo.statistic;
}