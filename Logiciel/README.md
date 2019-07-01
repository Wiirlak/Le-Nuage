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
    String color = "#fffff";

    void lnOpen();
    void lnClose();
    void nuageOpen();
    void nuageClose();
}

```

###Ajout de la classe *Runnable*
Ajoutez une classe **Runnable**, 
elle fera office de point de lancement de votre plugin dans l'application LeNuage.
Elle aura  une méthode **main()**.

*Exemple* :
```java
public class Runnable {
  public Runnable() {
  }

  public void main(){
      //Do your work here
  }
}
```

##Utilisation des points d'entrée & variables
Pour utiliser votre plugin a tel ou tel moment de l'execution de l'application ou bien changer des fonctions de l'application, 
il vous faudra utiliser les méthodes définies dans *LeNuage*

###Glossaire
####Points d'entrée
#####ln...
>Points de l'application
- Open : Démarrage de l'application
- Close : Sortie de l'application

#####nuage...
>Points des nuages
- Open : Ouverture d'un nuage
- Close : Sortie d'un nuage

####Variables
Ces variables sont liés à l'environnement de l'application et peuvent être utilisés.
- color : Change la couleur du background

### Etendez de l'Inteface
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
