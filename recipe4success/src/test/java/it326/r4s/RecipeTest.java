package it326.r4s;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import it326.r4s.model.*;
import it326.r4s.model.Review.Rating;
import it326.r4s.model.UnitConverter.Unit;

public class RecipeTest {
    
    static Recipe setupRecipe;
    static User theUser;
    static FoodItem.Pool fiPool = FoodItem.Pool.getInstance();
    static Category.Pool cPool = Category.Pool.getInstance();

    @Before
    public void setUp(){
        theUser = new User("Testman");

        setupRecipe = new Recipe.RecipeBuilder("Mac and Cheese")
        .setDescription("A yummy meal!")
        .setServingSize(2)
        .setIngredientList(new IngredientList())
        .setReviews(new ArrayList<Review>())
        .setCategories(new ArrayList<Category>())
        .setInstructions(new ArrayList<String>())
        .build();
    }


    @Test
    public void testRecipeBuilder(){

        assertNotNull(setupRecipe);
    }
    
    @Test
    public void testRemoveCategory(){
        ArrayList<Category> categoryList = new ArrayList<Category>();
        Category glutenFree = cPool.getCategory(Category.Type.RECIPE, "Gluten Free");
        categoryList.add(glutenFree);

        Recipe aRecipe = new Recipe.RecipeBuilder("Recipe with categories").setCategories(categoryList).build();

        aRecipe.removeCategory(glutenFree);

        assertFalse(aRecipe.getCategories().contains(glutenFree));
    }

    //ADD newCategory again
    @Test
    public void testAddCategory(){
        Category newCategory = cPool.getCategory(Category.Type.RECIPE, "CATEGORY");

        setupRecipe.addCategory(newCategory);

        assertTrue(setupRecipe.getCategories().contains(newCategory));
    }

    @Test
    public void testRemoveIngredient(){
        IngredientList ingredientList = new IngredientList();
        Ingredient testIngredient = new Ingredient(fiPool.getFoodItem("Flour"), 1, Unit.CUP);
        ingredientList.addIngredient(testIngredient);
        
        Recipe aRecipe = new Recipe.RecipeBuilder("Recipe with Ingredientlist").setIngredientList(ingredientList).build();

        aRecipe.removeIngredient(testIngredient);

        assertFalse(aRecipe.getIngredientList().getIngredients().contains(testIngredient));
    }

    @Test
    public void testAddIngredient(){
        IngredientList ingredientList = new IngredientList();
        Ingredient testIngredient = new Ingredient(fiPool.getFoodItem("Flour"), 1, Unit.CUP);
        
        Recipe aRecipe = new Recipe.RecipeBuilder("Recipe with Ingredientlist").setIngredientList(ingredientList).build();
        
        aRecipe.addIngredient(testIngredient);

        assertTrue(aRecipe.getIngredientList().getIngredients().contains(testIngredient));
    }

    @Test 
    public void testRemoveReview(){
        Review newReview = new Review(theUser, Rating.FOUR);
        ArrayList<Review> reviewList = new ArrayList<Review>();
        reviewList.add(newReview);

        Recipe aRecipe = new Recipe.RecipeBuilder("Recipe with Reviews").setReviews(reviewList).build();

        aRecipe.removeReview(newReview);

        assertFalse(aRecipe.getReviews().contains(newReview));
    }

    @Test
    public void testAddReview(){
        Review newReview = new Review(theUser, Rating.FOUR);

        setupRecipe.addReview(newReview);

        assertTrue(setupRecipe.getReviews().contains(newReview));
    }

}
