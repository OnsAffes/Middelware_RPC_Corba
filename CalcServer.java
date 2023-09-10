import WssCalculator.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;//
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import java.io.*;
import java.util.*;


//Class du serveur qui hérite les fonctionnalité de corba

class Calcserverimpl extends CalcPOA

{

    private ORB orb;

    public void setORB(ORB orb_val)

    {

        orb = orb_val;

    }


    //déclaration et implémentation de la méthode
    public float calculate(int a,int b)

    {   float c = (float) b / 100 ;
        return  ((float) a + (float) a * c);

    }


    //
    public void shutdown()
    {
        orb.shutdown(false);
    }

}//end of the servant class


public class CalcServer

{

    public static void main(String args[])

    {

        try

        {

//Création et initialisation de l'objet ORB



            ORB orb=ORB.init(args,null);




//orb récupére la référence du Root POA
//activation du POA Manager



            POA rootpoa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();


// Créer un servant (instance de classe d'implémentation) et l'enregistrer avec l'ORB

            Calcserverimpl simpl=new Calcserverimpl();
            simpl.setORB(orb);

// Récupérer une référence du servant



            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(simpl);
            Calc href=CalcHelper.narrow(ref);

// Récupérer la référence du service de nommage


            org.omg.CORBA.Object objRef=orb.resolve_initial_references("NameService");


//affecter objRef a son type


            NamingContextExt ncRef=NamingContextExtHelper.narrow(objRef);


// Créer un nom pour le service et ajouter le service


            String name = "Calc":
            NameComponent path[]=ncRef.to_name(name);

//Bind l'objet servant a Calc


            ncRef.rebind(path,href);
// Démarrer le service et attendre les requêtes des clients
            System.out.println("**************Serveur**************");
            System.out.println("Le serveur et en attente des clients....");



//the server attends les nouveaux clients


            orb.run();

        }

        catch (Exception e)

        {

            System.err.println("ERROR: " + e);//Afficher l'erreur

            e.printStackTrace(System.out);

        }


//Si le client se déconnecte
        System.out.println("Quitter");

    }
}