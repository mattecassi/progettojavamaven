package Tutorial;


import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Compito;
import Models.CompitoSvolto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TutorialCompito {
    public static void main(String[] args) {

        try {
            for (Compito c:  Compito.getCompitiNonSvoltiQuestaSettimana()){
                System.out.println(c.getTipoCompito());
                System.out.println(c);
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }



    }

}
