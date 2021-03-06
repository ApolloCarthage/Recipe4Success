package it326.r4s.controller;


import java.util.ArrayList;

import it326.r4s.model.Meal;
import it326.r4s.model.MealPlan;
import it326.r4s.model.Recipe;
import it326.r4s.model.User;
import it326.r4s.view.MealPlanView;

/**
 * Controller for R4S MealPlan
 * @author Nate Rardin (njrardi@ilstu.edu) and Zach Plattner (zmplatt@ilstu.edu)
 * @date 4/26/22
 */
public class MealPlanController  {

    //*Instance Variables\\
    private MealPlan mealPlan;
    private MealPlanView mealPlanView;

    private UserController authorController; //controller for the author's user object
    private PorterController<MealPlan> mealPlanPorter;

    //*Constructor*\\
    /**
     * Constructor for R4S's MealPlan
     * @param mealPlan - the controller's MealPlan
     */
    public MealPlanController(MealPlan mealPlan, User author) {
        this.mealPlan = mealPlan;
        this.mealPlanView = new MealPlanView(this);
        this.authorController = new UserController(author);
        mealPlanPorter = PorterController.of(MealPlan.class, authorController);
    }

    //*Methods*\\
    /**
     * Getter for the controller's MealPlan
     * @return the MealPlan object
     */
    public MealPlan getMealPlan() {
        return this.mealPlan;
    }

    /**
     * Getter for the controller's MealPlanView
     * @return the MealPlanView object
     */
    public MealPlanView getMealPlanView() {
        return mealPlanView;
    }

    /**
     * Getter for the UserController which controls the MealPlanController's author
     * @return the UseController object
     */
    public UserController getAuthorController(){
        return authorController;
    }

    /**
     * Gets an ArrayList of RecipeControllers associated with all recipes in the mealplan
     * @return the ArrayList of RecipeControllers
     */
    public ArrayList<RecipeController> getRecipeControllers(){
        ArrayList<RecipeController> recipeControllers = new ArrayList<RecipeController>();
        for(Recipe recipe: mealPlan.getRecipes()){
            recipeControllers.add(new RecipeController(recipe, authorController.getUser()));
        }
        return recipeControllers;
    }

    /**
     * Launches the mealPlanView to get a user option selection
     * and then takes the appropriate action
     */
    public void openMealPlan(){
        mealPlanView.displayMealPlan();
        int option;
        while(true){
            option = mealPlanView.getMenuOptionSelection();

            switch (option) {
                case 1:
                    addRecipeToMealPlan();
                    break;
                case 2:
                    removeRecipeFromMealPlan();
                    break;
                case 3:
                    //select recipe
                    openARecipe();
                    break;
                case 4:
                    adjustMealPlanServingSize();
                    break;
                case 5:
                    addToGroceryList();
                    break;
                case 6:
                    exportMealPlan();
                    return;
                case 7:
                    if(mealPlanView.deletionConfirmation()){
                        deleteMealPlan();
                        mealPlanView.displayMealPlan();
                        return;
                    }
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid input, please try again\n");
            }
            mealPlanView.displayMealPlan();
        }
    }


    public void addRecipeToMealPlan() {
        System.out.println("Which recipe would you like to add?");
        try{
            RecipeController selectedRecipeController = authorController.getRecipeBookController().selectRecipeController(getRecipeControllers());
            mealPlan.addMeal(new Meal(selectedRecipeController.getRecipe()));

            System.out.println("Recipe added successfully");
        } catch (RuntimeException re) {
            System.out.println("Oops, something went wrong adding the recipe.");
            return;
        }
    }

    public void removeRecipeFromMealPlan() {
        System.out.println("Which recipe would you like to remove?");
        
        RecipeController selectedRecipeController = authorController.getRecipeBookController().selectRecipeController(getRecipeControllers());

        mealPlan.removeMeal(selectedRecipeController.getRecipe());

        System.out.println("Recipe removed successfully");
    }

    private void openARecipe() {
        System.out.println("Which recipe would you like to open?");

        RecipeController selectedRecipeController = authorController.getRecipeBookController().selectRecipeController(getRecipeControllers());
        selectedRecipeController.openRecipe();
        System.out.println();
    }

    public void adjustMealPlanServingSize() {
        int servingSize = mealPlanView.getNewServingSize();
        if(mealPlanView.adjustMPServingSizeConfirmation(servingSize)){
            mealPlan.setMealServingSize(servingSize);
        }
    }

    public void exportMealPlan() {
        mealPlanPorter.exportTo(mealPlan);
    }

    public void addToGroceryList() {
        if(mealPlanView.addToGroceryListConfirmation()){
            authorController.getUser().addMealPlanToGroceryList(mealPlan);
            System.out.println("Ingredients added successfully");
        }
    }

    public void deleteMealPlan() {
            authorController.getUser().getMealPlanner().removeMealPlan(mealPlan);
    }
}
