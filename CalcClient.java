//Import all the important packages

//Import the package which contains the Client Stub
import WssCalculator.*;

//Import the below two packages to use the Naming Service
import org.omg.CosNaming.*;
//package pour exécuter un application basé sur corba
import org.omg.CORBA.*;
import java.io.*;
import java.util.*;




public class CalcClient

{

    static Calc cimpl;

    public static void main(String args[])

    {

        try

        {

//Déclaration et initialisation des variables
            int i;
            int j;
            float result;
            int x=1;


//Création et initialisation de l'ORB
//init() permet d'affecter les propriétes pendant lerun time

            ORB orb=ORB.init(args,null);

//ORB fournit au client la possibilté de localiser les services souhaité
// Récupérer la référence du service de nommage


            org.omg.CORBA.Object objRef=orb.resolve_initial_references("NameService");


//affecter l'objet à son type

            NamingContextExt ncRef=NamingContextExtHelper.narrow(objRef);


//Une chaine qui à pour référence The naming service pour l'objet Calc

            String name="Calc";

            cimpl=CalcHelper.narrow(ncRef.resolve_str(name));
            System.out.println("Obtained a handle on the server object");


            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));


            while(x==1)

            {
                Scanner s = new Scanner(System.in);
                System.out.println("Veuillez entrer le montant en HT :");
                i = s.nextInt();
                System.out.println("Veuillez entrer le % du TVA :");
                j = s.nextInt();



                result=cimpl.calculate(i,j);
                System.out.println("Le résultat du calcul est "+result);

                System.out.println("Choisir 1 pour continuer ou 0 pour quitter ");
                x = s.nextInt();
            }

//Si le client veut annuler l'opération
            cimpl.shutdown();

        }


        catch(Exception e)

        {

            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);

        }

    }

}