package plugin;

import javafx.stage.Stage;

public interface LeNuage {
    void lnOpen();
    void lnClose();
    void nuageOpen();
    void nuageClose();
    Stage returnNewStage();
    String returnNuageName();
}

