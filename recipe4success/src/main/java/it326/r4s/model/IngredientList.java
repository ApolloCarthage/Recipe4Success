package it326.r4s.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class used to manage, maintain, and access a collection of ingredients
 * 
 * @author Josh Nepomuceno
 * @date 04/06/2022
 */

public class IngredientList extends Entity {

    // *Instance Variable*\\
    private ArrayList<Ingredient> ingredients;

    // *Constructors*\\
    /**
     * Creates a default IngredientList object.
     */
    public IngredientList() {
        this.ingredients = new ArrayList<Ingredient>();
    }

    /**
     * Copy constructor to create a new ingredient list that is a copy of another
     * 
     * @param ingredientList
     */
    public IngredientList(IngredientList ingredientList) {
        this.ingredients = new ArrayList<Ingredient>();
        if (!(ingredientList == null)) {
            for (Ingredient ingredient : ingredientList.getIngredients()) {
                this.ingredients.add(new Ingredient(ingredient));
            }
        }
    }

    /**
     * Creates a IngredientList object with a specified collection of ingredients.
     * 
     * @param ingredients a collection of ingredients.
     */
    public IngredientList(Collection<Ingredient> ingredients) {
        super();
        this.ingredients = new ArrayList<Ingredient>(ingredients);
    }

    // *methods*\\
    /**
     * Gets all ingredients in the IngredientList.
     * 
     * @return a collection of ingredients.
     */
    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    /**
     * Attempts to add an Ingredient to the IngredientList.
     * 
     * @param ingredient an Ingredient to be added to the IngredientList.
     * @return true if ingredient is already in ingredients, false otherwise.
     */
    public boolean addIngredient(Ingredient ingredient) {
        for (Ingredient existingIngredient : this.ingredients) { // check if ingredient already exists
            if (ingredient.getFoodItem().equals(existingIngredient.getFoodItem())) {
                Ingredient copyIngredient = new Ingredient(existingIngredient.getFoodItem(),
                        existingIngredient.getQuantity(), existingIngredient.getUnit());
                if (copyIngredient.getUnit() == ingredient.getUnit()) {
                    copyIngredient.setQuantity(copyIngredient.getQuantity() + ingredient.getQuantity());
                    this.ingredients.add(copyIngredient);
                    this.ingredients.remove(existingIngredient);
                    return true;
                } else if (copyIngredient.changeUnit(ingredient.getUnit())) {
                    copyIngredient.setQuantity(copyIngredient.getQuantity() + ingredient.getQuantity());
                    this.ingredients.add(copyIngredient);
                    this.ingredients.remove(existingIngredient);
                    return true;
                }
            }
        }
        this.ingredients.add(ingredient);
        return false;
    }

    /**
     * Attempts to add an IngredientList of Ingredients to the IngredientList.
     * 
     * @param toAdd an IngredientList of Ingredients to be added to the
     *              IngredientList.
     * @return true if any Ingredient in toAdd is already in ingredients, false
     *         otherwise.
     */
    public boolean addIngredients(IngredientList listToAdd) {
        return addIngredients(listToAdd.getIngredients());
    }

    /**
     * Attempts to add a collection of Ingredients to the IngredientList.
     * 
     * @param toAdd a collection of Ingredients to be added to the IngredientList.
     * @return true if all Ingredients in toAdd are in Ingredients, false otherwise.
     */
    public boolean addIngredients(Collection<Ingredient> toAdd) {
        boolean flag = true;
        for (Ingredient ingredientToAdd : toAdd) {
            if (!this.addIngredient(ingredientToAdd))
                flag = false; // if if ingrient didn't already exist
        }
        return flag;
    }

    /**
     * Attempts to remove an Ingredient from the IngredientList.
     * 
     * @param toRemove an Ingredient to be removed from the IngredientList.
     * @return false if toRemove is not in ingredients, true otherwise.
     */
    public boolean removeIngredient(Ingredient toRemove) {
        return ingredients.remove(toRemove);
    }

    /**
     * Attempts to remove another IngredientList from the IngredientList.
     * 
     * @param toRemove another IngredientList to be removed from the
     *                 IngredientList.
     * @return false if toRemove is not in the IngredientList, true otherwise.
     */
    public boolean removeIngredients(IngredientList toRemove) {
        return ingredients.removeAll(toRemove.getIngredients());
    }

    /**
     * Attempts to remove a collection of Ingredients from the IngredientList.
     * 
     * @param toRemove a collection of Ingredients to be removed from the
     *                 IngredientList.
     * @return true if anything is removed, otherwise false.
     */
    public boolean removeIngredients(Collection<Ingredient> toRemove) {
        boolean changed = ingredients.removeAll(toRemove);
        for (Ingredient existingIngredient : ingredients) {
            for (Ingredient ingredientToRemove : toRemove) {
                if (ingredientToRemove.getFoodItem().equals(existingIngredient.getFoodItem())) {
                    Ingredient copyIngredient = new Ingredient(existingIngredient);
                    if (copyIngredient.getUnit() == ingredientToRemove.getUnit()) {
                        double newQuantity = copyIngredient.getQuantity() - ingredientToRemove.getQuantity();
                        if (newQuantity <= 0.02) {
                            this.ingredients.remove(existingIngredient);
                            changed = true;
                            break;
                        }
                        existingIngredient.setQuantity(newQuantity);
                        changed = true;
                    }
                    else if (copyIngredient.changeUnit(ingredientToRemove.getUnit())) {
                        double newQuantity = copyIngredient.getQuantity() - ingredientToRemove.getQuantity();
                        if (newQuantity <= 0.02) {
                            this.ingredients.remove(existingIngredient);
                            changed = true;
                            break;
                        }
                        existingIngredient.setQuantity(newQuantity);
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    /**
     * Attempts to remove all ingredients from the IngredientList.
     */
    public void clearIngredients() {
        ingredients.clear();
    }

    /**
     * Checks whether this ingredient list contains all the ingredients of another
     * collection.
     * 
     * @param smallCollection the collection of ingredients to be compared against.
     * @return True if this ingredient list contains all the ingredients (quantities
     *         of this list must be larger) of the collection, false otherwise.
     */
    public boolean containsIngredients(Collection<Ingredient> smallCollection) {
        Collection<Ingredient> thisCollection = this.ingredients;
        if (thisCollection.containsAll(smallCollection)) {
            return true;
        }
        for (Ingredient smallIngredient : smallCollection) {
            boolean found = false;
            for (Ingredient thisIngredient : thisCollection) { // for each possible comparison
                if (smallIngredient.getFoodItem().equals(thisIngredient.getFoodItem())) {
                    Ingredient copyIngredient = new Ingredient(smallIngredient);
                    if (smallIngredient.getUnit() == thisIngredient.getUnit()
                            || copyIngredient.changeUnit(thisIngredient.getUnit())) {
                        if (copyIngredient.getQuantity() <= thisIngredient.getQuantity()) {
                            found = true;
                            break;
                        }
                    }
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether this IngredientList contains all the ingredients of another
     * IngredientList
     * 
     * @param ingredientList the IngredientList to be compared against.
     * @return True if this ingredient list contains all the ingredients (quantities
     *         of this list must be larger) of the collection, false otherwise.
     */
    public boolean containsIngredients(IngredientList ingredientList) {
        return containsIngredients(ingredientList.getIngredients());
    }

    /**
     * An override for the .equals method of java.obj.
     * 
     * @param obj an IngredientList object.
     *            private Collection<Ingredient> ingredients;
     * @return true if two IngredientLIst object are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the compared object is of correct type
        if (!(obj instanceof IngredientList) || obj == null) {
            return false;
        }
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }

        IngredientList otherIngredientList = (IngredientList) obj;
        Collection<Ingredient> otherCollection = otherIngredientList.getIngredients();
        return this.containsIngredients(otherCollection) && this.ingredients.size() == otherCollection.size();
    }

    /**
     * An override for the .toString method of java.obj.
     * 
     * @return a string representation of the Ingredient object.
     */
    @Override
    public String toString() {
        String string = "";
        int i = 1;
        for (Ingredient ingredient : ingredients) {
            if (i == 1) {
                string += (i + ") " + ingredient.toString());
            } else {
                string += ("\n" + i + ") " + ingredient.toString());
            }
            System.out.println(i + ") " + ingredient.toString());
            i++;
        }
        return string;
    }

    /**
     * Empties the ingredient list
     */
    public void makeEmpty() {
        ingredients = new ArrayList<Ingredient>();
    }

    /**
     * Re-organizes the ingredient list by placing
     * the "toMove" ingredient after the "moveAfter" ingredient
     * 
     * @param toMove
     * @param moveAfter
     */
    public boolean reorganizeIngredients(Ingredient toMove, Ingredient moveAfter) {
        if (!ingredients.contains(moveAfter)) {
            return false;
        }
        ArrayList<Ingredient> newList = new ArrayList<Ingredient>();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.equals(toMove)) {
                continue;
            }
            newList.add(ingredient);
            if (ingredient.equals(moveAfter)) {
                newList.add(toMove);
            }
        }
        ingredients = newList;
        return true;
    }
}
