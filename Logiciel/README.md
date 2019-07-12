# Utilisation des plugins
>*Mise à jour le 02/07/2019*

Les plugins sont une part essentiel à notre projet annuel.

## Création d'un plugin
Il vous faudra pour ceci:
- Un IDE
- Un projet Java

### Ajout de l'Interface *LeNuage*
Au sein de votre projet créez une classe nommée LeNuage.java.
Elle contiendra le  contenu suivant :
```java
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
//    Stage returnNewStage(); facultatif
//    String returnNuageName(); facultatif

}

```

### Ajout de la classe *Runnable*
Ajoutez une classe **Runnable**, 
elle fera office de point de lancement de votre plugin dans l'application LeNuage.
Elle aura  une méthode **main()**.

*Exemple* :
```java
public class Runnable {
  public Runnable() {
      //Do your work here
  }
}
```

## Utilisation des points d'entrée & variables
Pour utiliser votre plugin a tel ou tel moment de l'execution de l'application ou bien changer des fonctions de l'application, 
il vous faudra utiliser les méthodes définies dans *LeNuage*

### Glossaire
#### Points d'entrée
##### ln...
>Points de l'application
- Open : Démarrage de l'application
- Close : Sortie de l'application

##### nuage...
>Points des nuages
- Open : Ouverture d'un nuage
- Close : Sortie d'un nuage
- Delete : Suppression d'un nuage
- Leave : Partir d'un nuage

#####other...
>Points des téléchargement
- Upload :  Fin d'un chargement
- Download :  Fin d'un téléchargement
- Leave :  Quitter l'application

#### Variables
Ces variables sont liées à l'environnement de l'application et peuvent être utilisés.
- returnNewStage : retourner une instance javaFx
- returnNuageName : retourner le nouveau nom de la fenetre

### Implémentez de l'Inteface
````java
public class Runnable implements LeNuage{
    //Code..
}
````
A ce moment la,  il faudra forcément définir un corps pour chaque méthode, même celles non utilisés.
````java
public class Runnable implements LeNuage{
    @Override
    public void lnOpen() {

    }

    @Override
    public void lnClose() {

    }
    
    //...
}
````

### Utilisez la méthode
A partir de là, il vous suffit d'appelez vos fonctions dans les points d'entrée désirées.


# Utilisation du cli
Pour lancer **LeNuage** en cli, il vous faudra utiliser l'argument *-dev*
> java -jar LeNuage.jar -dev -l login pwd -n MonNuage/dev -d project.jar

## Arguments disponibles
````
*  -cmd,--cmd            Activate the cmd mode
*  -d,--download <arg>   download the named file
*  -g,--getNuage <arg>   get the list of all your Nuages
*  -l,--login <arg>      Login id
*  -n,--navigate <arg>   move in your nuage
*  -p,--password <arg>   Login password
*  -u,--u <arg>          upload file to the current repertory
````
