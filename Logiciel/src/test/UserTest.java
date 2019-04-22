package test;

import org.junit.jupiter.api.Test;
import sample.Model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getNom() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getNom(),"Dupont");
    }

    @Test
    void setNom() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);

        user1.setNom("DupOnt");
        assertEquals(user1.getNom(),"Dupont");
    }

    @Test
    void getPrenom() {
        User user1 = new User("dupont","martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);

        assertEquals(user1.getPrenom(),("Martin"));
    }

    @Test
    void setPrenom() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);

        user1.setNom("MarTIn");
        //assertEquals(user1.getPrenom(),"Martin");
    }

    @Test
    void getEmail() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void getDate() {
    }

    @Test
    void setDate() {
    }

    @Test
    void getMdp() {
    }

    @Test
    void setMdp() {
    }

    @Test
    void getMdpc() {
    }

    @Test
    void setMdpc() {
    }

    @Test
    void getCheck() {
    }

    @Test
    void setCheck() {
    }

    @Test
    void isValidEmail() {
    }

    @Test
    void pwdEqual() {
    }
}