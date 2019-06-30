package core.synchro;

import annotation.Status;
import annotation.Usage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;


@Status(author = "Bastien NISOLE",
        progression = 50,
        version = 2.3)
public class Synchro {

    @Usage(description = "Comparer 2 fichiers")
    public static boolean compareTwoFile(File file1, File file2){
        try {
            return Arrays.equals(Files.readAllBytes(file1.toPath()),Files.readAllBytes(file2.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
