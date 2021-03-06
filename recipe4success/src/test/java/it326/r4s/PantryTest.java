package it326.r4s;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import it326.r4s.model.FoodItem;
import it326.r4s.model.Ingredient;
import it326.r4s.model.IngredientList;
import it326.r4s.model.Pantry;
import it326.r4s.model.Recipe;
import it326.r4s.model.UnitConverter.Unit;

public class PantryTest {
    
    static Recipe theRecipe;
    static Ingredient ingredient, ingredient2, ingredient3, ingredient4;
    static IngredientList ingredientList;
    static Pantry pantry;
    static FoodItem.Pool fiPool = FoodItem.Pool.getInstance();

    @Before
    public void before(){
        theRecipe = new Recipe.RecipeBuilder("Red Velvet Cupcakes")
        .setDescription("Believe it or not it's actually just red chocolate.")
        .setIngredientList(new IngredientList())
        .build();

        ingredient = new Ingredient(fiPool.getFoodItem("Flour"), 2, Unit.CUP);
        ingredient2 = new Ingredient(fiPool.getFoodItem("Egg"), 6, Unit.NONE);
        ingredient3 = new Ingredient(fiPool.getFoodItem("Sugar"), 4, Unit.OUNCE);
        ingredient4 = new Ingredient(fiPool.getFoodItem("Apple"), 4, Unit.NONE);

        theRecipe.addIngredient(ingredient);
        theRecipe.addIngredient(ingredient2);
        theRecipe.addIngredient(ingredient4);

        ingredientList = new IngredientList();
        ingredientList.addIngredient(ingredient);
        ingredientList.addIngredient(ingredient2);
        ingredientList.addIngredient(ingredient3);
        pantry = new Pantry();
        pantry.setIngredientList(ingredientList);
    }
  
    @Test
    public void testRemoveRecipeIngredients() {
        IngredientList expectIngredientList = new IngredientList();
        expectIngredientList.addIngredient(ingredient3);

        pantry.removeRecipeIngredients(theRecipe);
        Collection<Ingredient> expectIngredients = expectIngredientList.getIngredients();
        Collection<Ingredient> actualIngredients = pantry.getIngredientList().getIngredients();
        assertEquals(expectIngredients, actualIngredients);
    }
}
