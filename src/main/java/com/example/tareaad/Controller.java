package com.example.tareaad;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private TextField userName;

    @FXML
    private Button logIn;

    @FXML
    private Button next;
    @FXML
    private PasswordField password;

    @FXML
    private Label logInError;

    @FXML
    public static Connection conexion = null;

    @FXML
    private static List<String> listaTablas = new ArrayList<>();

    @FXML
    private TextField fieldTable;

    @FXML
    private Label errorTable;

    @FXML
    public void openWindow() throws IOException {
        if (!userName.getText().isEmpty() && !password.getText().isEmpty()) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("tables.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Stage old = (Stage) logIn.getScene().getWindow();
            old.close();
        }
    }

    @FXML
    protected void onLogInClick() {
        String url = "jdbc:mysql://localhost/northwind";
        url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
            conexion = DriverManager.getConnection(url, userName.getText(), password.getText());
            openWindow();
        } catch (SQLException ex) {
            logInError.setText("Se ha producido un error");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    protected void getMetaData() {
        try {
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet resul = null;
            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url2 = dbmd.getURL();
            String user = dbmd.getUserName();
            String[] types = {"TABLE"};

            resul = dbmd.getTables("northwind", null, null, types);
            while (resul.next()) {
                String catalogo = resul.getString("TABLE_CAT");// CATALOG
                String esquema = resul.getString("TABLE_SCHEM"); // SCHEMA
                String tabla = resul.getString("TABLE_NAME"); // NAME
                String tipo = resul.getString("TABLE_TYPE"); // TYPE
                listaTablas.add(tabla);
            }
            resul.close();

        } catch (SQLException ex) {
            System.out.println("Algun error se ha producido");
        }
    }


    @FXML
    public void openWindow2() throws IOException {
        if (!userName.getText().isEmpty() && !password.getText().isEmpty()) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("tableContent.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Stage old = (Stage) next.getScene().getWindow();
            old.close();
        }
    }

    @FXML
    protected void onNextClick() {
        /*
        Comprobar si la tabla introducida se encuentra
         en la base de datos(Usar lista del metodo getMetadata())

         */
        //Rellenar con el mapa
        try {
            openWindow2();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}