package it326.r4s.view;

import it326.r4s.view.utilities.InputAccess;

public class FoodItemView {
    
    //*Constructor*\\
    /**
     * Constructor for R4S's FoodItemView
     */
    public FoodItemView(){}

    /**
     * Facilitates the process of the user editing the name
     * @return a String representing the new name
     */
    public String editName(){
        InputAccess ia = new InputAccess();
        String name = "";

        do{
            System.out.println("\nWhat is the name of the new ingredient?");
            name = ia.getInputLine();
        } while (name.equals(""));

        return name;
    }
}
