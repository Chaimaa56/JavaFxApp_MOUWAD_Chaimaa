module com.example.scientist_manage {
    requires javafx.controls;
    requires javafx.fxml;

    requires gson;

    requires java.sql;
    requires org.apache.poi.ooxml;


    opens com.emsi.scientist_manage to javafx.fxml;
    opens com.emsi.scientist_manage.entities to javafx.base,gson;
    opens com.emsi.scientist_manage.dao to javafx.base;
    opens com.emsi.scientist_manage.service to javafx.base;
    opens com.emsi.scientist_manage.dao.impl to javafx.base;
    opens com.emsi.scientist_manage.view to javafx.fxml, javafx.controls, javafx.base;


    exports com.emsi.scientist_manage;
    exports com.emsi.scientist_manage.entities;
    exports com.emsi.scientist_manage.dao;
    exports com.emsi.scientist_manage.service;
    exports com.emsi.scientist_manage.dao.impl;
    exports com.emsi.scientist_manage.view;
}