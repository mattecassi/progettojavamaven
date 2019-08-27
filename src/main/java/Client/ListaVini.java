package Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListaVini {
    ObservableList<Vino> list = FXCollections.observableArrayList();

    public ObservableList<Vino> getList() {
        return list;
    }

    public void addVino(Vino vino){
        list.add(vino);
    }
}
