import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Observable;

public class Controller {

    @FXML
    protected TextArea inputArea;

    @FXML
    protected TableView table;

    public void GoPressed()
    {
        table.getItems().clear();
        table.getColumns().clear();
        String inputText = inputArea.getText();
        try {
            //select * from employees limit 10
            Statement stmt = Main.conn.createStatement();
            ResultSet rs = stmt.executeQuery(inputText);
            System.out.println(rs.getMetaData().getColumnName(1));
            ResultSetMetaData meta = rs.getMetaData();
            int numColumn = meta.getColumnCount();

            for(int i = 0; i < numColumn; ++i)
            {
                final int index = i;
                TableColumn column = new TableColumn(meta.getColumnLabel(i + 1));
                column.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, SimpleStringProperty>) param ->
                       new SimpleStringProperty(param.getValue().get(index).toString()));
                table.getColumns().add(column);
            }

            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            while(rs!=null && rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 0; i < numColumn; ++i)
                {
                    row.add(rs.getString(i + 1));
                }
                data.add(row);
            }
            table.getItems().addAll(data);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
