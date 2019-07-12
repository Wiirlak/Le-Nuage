package plugin;

import javafx.stage.Stage;

public interface LeNuage {
    void lnOpen();
    void lnClose();
    void nuageOpen();
    void nuageClose();
    Stage returnNewStage();
    String returnNuageName();
    void upload();
    void download();
    void leave();
    void deleteNuage();
    void joinNuage();
    void leaveNuage();
}

