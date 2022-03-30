package kurs.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kurs.Main;
import kurs.VirtualMachineLauncher;

import java.util.ArrayList;
import java.util.List;


public class Controller {
    @FXML
    private void initialize()
    {
        queueTable.getColumns().setAll(initQueueTable());
        doneTable.getColumns().setAll(initQueueTable());
    }
    @FXML
    TableView<Process> queueTable;
    @FXML
    TableView<Process> doneTable;

    ObservableList<Process> queueTableList = FXCollections.observableArrayList();
    ObservableList<Process> doneTableList = FXCollections.observableArrayList();

    private ArrayList<TableColumn<Process, String>> initQueueTable()
    {
        TableColumn<Process, String> idColumn= new TableColumn<>("ID");
        TableColumn<Process, String> nameColumn= new TableColumn<>("Name");
        TableColumn<Process, String> priorColumn= new TableColumn<>("Priority");
        TableColumn<Process, String> statusColumn= new TableColumn<>("Status");
        TableColumn<Process, String> tickColumn = new TableColumn<>("ticksExecuted");
        TableColumn<Process, String> ticksNeededColumn= new TableColumn<>("ticksNeeded");
        TableColumn<Process, String> memColumn= new TableColumn<>("Memory");
        TableColumn<Process, String> startTickColumn= new TableColumn<>("startTick");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        tickColumn.setCellValueFactory(new PropertyValueFactory<>("ticksExecuted"));
        memColumn.setCellValueFactory(new PropertyValueFactory<>("memorySize"));
        startTickColumn.setCellValueFactory(new PropertyValueFactory<>("startTick"));
        ticksNeededColumn.setCellValueFactory(new PropertyValueFactory<>("ticksNeeded"));

        ArrayList<TableColumn<Process, String>> _tmp = new ArrayList<>();
        _tmp.add(idColumn);
        _tmp.add(nameColumn);
        _tmp.add(priorColumn);
        _tmp.add(statusColumn);
        _tmp.add(tickColumn);
        _tmp.add(memColumn);
        _tmp.add(startTickColumn);
        _tmp.add(ticksNeededColumn);
        return _tmp;
    }

    public void updateTable(List<Process> activeProcesses, List<Process> terminatedProcesses)
    {
        queueTableList.setAll(activeProcesses);
        queueTable.setItems(queueTableList);
        queueTable.refresh();

        doneTableList.setAll(terminatedProcesses);
        doneTable.setItems(doneTableList);
        doneTable.refresh();
    }
    @FXML
    Button runBTN;
    @FXML
    Button stopBTN;

    @FXML
    protected void runBTN_Click()
    {
        Main.emuThread = new Thread(new VirtualMachineLauncher());
        Main.emuThread.start();

        runBTN.setDisable(true);
        stopBTN.setDisable(false);
    }

    @FXML
    protected void stopBTN_Click()
    {
        if (Main.emuThread != null && Main.emuThread.isAlive()) {
            Main.emuThread.interrupt();
            Main.emuThread = null;
        }

        queueTable.setItems(null);
        doneTable.setItems(null);

        runBTN.setDisable(false);
    }

}
