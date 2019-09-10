
import Models.Vino;
import Utils.Utility;

public class Main {

    public static void main(String[] args){


        try {


            System.out.println("POST");
            for (String s: Utility.loadDataForCmb("vino","nome","",Vino.class)){
                System.out.println(s);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Errore " + e.getMessage());
        }
    }

}
