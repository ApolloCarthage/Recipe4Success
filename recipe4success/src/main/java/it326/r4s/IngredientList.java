package it326.r4s;
import java.util.Collection;
import java.util.HashSet;

/**
* Class used to manage, maintain, and access a collection of ingredients
* @author Josh Nepomuceno
* @date 04/06/2022
*/

public class IngredientList extends Entity implements Exportable {
    private Collection<Ingredient> ingredients;

    // constructors
    public IngredientList() {
        super();
        this.ingredients = new HashSet<Ingredient>();
    }

    public IngredientList(Collection<Ingredient> ingredients) {
        super();
        this.ingredients = new HashSet<Ingredient>(ingredients);
    }

    // methods
    
    // returns true if toAdd is already in ingredients
    public boolean addIngredient(Ingredient toAdd) {
        for (Ingredient ingredient : this.ingredients) {
            if (ingredient.getFoodItem().equals(toAdd.getFoodItem())) {
                if (!ingredient.getUnit().equals(toAdd.getUnit()))
                    UnitConverter.convertUnit(ingredient.getUnit(), ingredient.getQuantity(), toAdd.getUnit());
                ingredient.setQuantity(ingredient.getQuantity() + toAdd.getQuantity());
                return true;
            }
        }
        this.ingredients.add(toAdd);
        return false;
    }

    // returns true if any Ingredient in toAdd is already in ingredients (false only if it removes nothing)
    public boolean addIngredients(Collection<Ingredient> toAdd) {
        boolean flag = true;
        for (Ingredient ingredientToAdd : toAdd) {
            if (!this.addIngredient(ingredientToAdd)) flag = false;
        }
        return flag;
    }

    // returns false if toRemove is not in ingredients
    public boolean removeIngredient(Ingredient toRemove) { return ingredients.remove(toRemove); }

    // returns false if toRemove is not in ingredients
    public boolean removeIngredients(Collection<Ingredient> toRemove) { return ingredients.removeAll(toRemove); }

    public void clearIngredients() { ingredients.clear(); }

    /**
     * Checks whether this ingredient list contains all the ingredients of another collection.
     * @param ingredients the collection of ingredients to be compared against.
     * @return True if this ingredient list contains all the ingredients (quantities of this list must be larger) of the collection, false otherwise.
     */
    public boolean containsIngredients(Collection<Ingredient> otherIngredients) {
        if (this.ingredients.containsAll(otherIngredients)) return true;
        else {
            for (Ingredient ingredient : otherIngredients) {
                for (Ingredient otherIngredient : this.ingredients) {
                    if (!ingredient.equals(otherIngredient)) {
                        if (ingredient.getQuantity() > otherIngredient.getQuantity()) return false;
                    }
                }
            }
        }
        return true;
    }

    // getter
    public Collection<Ingredient> getIngredients() { return this.ingredients; }

    @Override
    public boolean equals(IngredientList otherIngredientList) {
        Collection<Ingredient> otherCollection = otherIngredientList.getIngredients();
        return this.containsIngredients(otherCollection) && this.ingredients.size() == otherCollection.size();
    }

    @Override
    public String toString() {
        String result = "List of Ingredients:\n";
        for (Ingredient ingredient : this.ingredients) {
            result += " -\t" + ingredient.toString();
        }
        return result;
    }
}
