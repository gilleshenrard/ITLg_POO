/****************************************************************************************************/
/*  Class ScoresDBDataView                                                                          */
/*  Provides methods to display SQLite DB values in a JFX TableView, hosted in a modal window       */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 21/06/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views.JFX;

import com.gilleshenrard.Awele.Controllers.GameController;
import com.gilleshenrard.Awele.Models.DBFields;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ScoresDBDataView {
    private GameController m_controller;
    private Stage m_modal;
    private StackPane m_pane;
    private TableView<DBScores> m_table;
    private ObservableList<DBScores> m_data;

    /**
     * Create a new DB dataView stage
     * @param controller Game controller to use
     */
    ScoresDBDataView(GameController controller) {
        this.m_controller = controller;

        //create the data collection which will host all the data
        this.m_data = FXCollections.observableArrayList();

        //Create the TableView in which the data will be nested
        this.m_table = new TableView<>();
        this.m_table.setEditable(false);
        this.initialiseTable();
        this.m_table.setItems(this.m_data);

        //Create a StackPane to host the TableView
        this.m_pane = new StackPane();
        this.m_pane.getChildren().add(this.m_table);

        //Create the stage which will show the whole scene
        this.m_modal = new Stage();
        this.m_modal.setTitle("Scores");
        this.m_modal.initModality(Modality.APPLICATION_MODAL);
        this.m_modal.setScene(new Scene(this.m_pane, 450, 450));
    }

    /**
     * Initialise all the TableView columns
     */
    private void initialiseTable() {
        TableColumn timeCol = new TableColumn("DateTime");
        timeCol.setCellValueFactory(new PropertyValueFactory<DBScores, String>("time"));

        TableColumn clockCol = new TableColumn("Timing");
        clockCol.setCellValueFactory(new PropertyValueFactory<DBScores, String>("clock"));

        TableColumn winCol = new TableColumn("Winner");
        winCol.setCellValueFactory(new PropertyValueFactory<DBScores, String>("winner"));

        TableColumn pl1Col = new TableColumn("Player 1");
        pl1Col.setCellValueFactory(new PropertyValueFactory<DBScores, String>("player1"));

        TableColumn pl2Col = new TableColumn("Player 2");
        pl2Col.setCellValueFactory(new PropertyValueFactory<DBScores, String>("player2"));

        this.m_table.getColumns().addAll(timeCol, clockCol, winCol, pl1Col, pl2Col);
    }

    /**
     * Show the final stage as a modal window, with updated values
     */
    public void show() {
        //clear the rows held in the ObservableList
        this.m_data.clear();

        //fill it back with each row and display all rows in the console
        while (this.m_controller.selectNext()) {
            DBScores row = new DBScores(this.m_controller.getField(DBFields.TIME),
                                        this.m_controller.getField(DBFields.CLOCK),
                                        this.m_controller.getField(DBFields.WINNER),
                                        this.m_controller.getField(DBFields.PLAYER1),
                                        this.m_controller.getField(DBFields.PLAYER2));

            this.m_data.add(row);
            System.out.println(row);
        }

        //display the final stage
        this.m_modal.showAndWait();
    }
}