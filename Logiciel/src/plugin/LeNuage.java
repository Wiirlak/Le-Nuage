package plugin;

import javafx.stage.Stage;

public interface LeNuage {
    void lnOpen();
    void lnClose();
    void nuageOpen();
    void nuageClose();
    void nuageDelete();
    void nuageLeave();
    void otherUpload();
    void otherDownload();
    void otherLeave();
    Stage returnNewStage();
    String returnNuageName();
}

