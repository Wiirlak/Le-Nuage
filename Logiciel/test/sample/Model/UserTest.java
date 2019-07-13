package sample.Model;

import org.junit.jupiter.api.Test;
import core.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getNom() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getNom(),"dupont");
    }

    @Test
    void setNom() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        user1.setNom("DupOnt");
        assertEquals(user1.getNom(),"DupOnt");
    }

    @Test
    void getPrenom() {
        User user1 = new User("dupont","martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getPrenom(),("martin"));
    }

    @Test
    void setPrenom() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        user1.setPrenom("MarTIn");
        assertEquals(user1.getPrenom(),"MarTIn");
    }

    @Test
    void getEmail() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getEmail(),"are@gmail.fr");
    }

    @Test
    void setEmail() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        user1.setEmail("aRe@gmail.fr");
        assertEquals(user1.getEmail(),"aRe@gmail.fr");
    }

    @Test
    void getDate() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getDate(),"12-02-1998");
    }

    @Test
    void setDate() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        user1.setDate("16-02-1998");
        assertEquals(user1.getDate(),"16-02-1998");
    }

    @Test
    void getMdp() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getMdp(),"lolilol");
    }

    @Test
    void setMdp() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        user1.setMdp("cheh");
        assertEquals(user1.getMdp(),"cheh");
    }

    @Test
    void getMdpc() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getMdpc(),"lolilol");
    }

    @Test
    void setMdpc() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        user1.setMdpc("cheh");
        assertEquals(user1.getMdpc(),"cheh");
    }

    @Test
    void getCheck() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.getCheck(),true);
    }

    @Test
    void setCheck() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        user1.setCheck(false);
        assertEquals(user1.getCheck(),false);
    }

    @Test
    void isValidEmail() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.isValidEmail(),true);
        user1.setEmail("  ");
        assertEquals(user1.isValidEmail(),false);
    }

    @Test
    void pwdEqual() {
        User user1 = new User("dupont","Martin","are@gmail.fr","12-02-1998","lolilol","lolilol",true);
        assertEquals(user1.pwdEqual(),true);
        user1.setMdpc("ddsdfqf");
        assertEquals(user1.pwdEqual(),false);
    }
}