package it326.r4s;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class RecipeTest {
    
    static Recipe testingRecipe;

    @Before
    public static void setUp(){
        testingRecipe = new Recipe.RecipeBuilder("Mac and Cheese").setDescription("A yummy meal!").build();

    }


    @Test
    public void testRecipeBuilder(){

        assertNotNull(testingRecipe);
    }
}
