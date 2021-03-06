package it326.r4s;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import it326.r4s.model.FoodItem;
import it326.r4s.model.Ingredient;
import it326.r4s.model.IngredientList;
import it326.r4s.model.UnitConverter.Unit;

public class IngredientListTest {
    private static IngredientList theIngredientList;
    private static Ingredient ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6;
    private static FoodItem fItem;
    private static FoodItem.Pool pool = FoodItem.Pool.getInstance();
    private static ArrayList<Ingredient> ingredientArray, expectedList, actualList;

    @Before
    public void setUp() {
        fItem = pool.getFoodItem("Apple");
        ingredient1 = new Ingredient(fItem, 2, Unit.NONE);
        fItem = pool.getFoodItem("Banana");
        ingredient2 = new Ingredient(fItem, 3, Unit.GRAM);
        fItem = pool.getFoodItem("Cherry");
        ingredient3 = new Ingredient(fItem, 4, Unit.OUNCE);
        ingredient4 = new Ingredient(ingredient1);
        ingredient4.setQuantity(ingredient4.getQuantity() * 2);
        ingredient5 = new Ingredient(ingredient2);
        ingredient5.setQuantity(ingredient5.getQuantity() * 2);
        ingredient6 = new Ingredient(ingredient3);
        ingredient6.setQuantity(ingredient6.getQuantity() * 2);

        theIngredientList = new IngredientList();
        theIngredientList.addIngredient(ingredient1);
        theIngredientList.addIngredient(ingredient2);

        expectedList = new ArrayList<Ingredient>();
        actualList = new ArrayList<Ingredient>();
        ingredientArray = new ArrayList<Ingredient>();
    }

    @Test
    public void testConstructor() {
        Collection<Ingredient> theIngredients = new ArrayList<Ingredient>();
        theIngredients.add(ingredient1);
        theIngredients.add(ingredient2);
        theIngredients.add(ingredient3);
        theIngredientList = new IngredientList(theIngredients);
        assertNotNull(theIngredientList);
        assertEquals(theIngredients.size(), theIngredientList.getIngredients().size());

        IngredientList otherIngredientList = new IngredientList();
        otherIngredientList.addIngredient(ingredient2);
        otherIngredientList.addIngredient(ingredient3);
        theIngredientList = new IngredientList(otherIngredientList);
        assertNotNull(theIngredientList);
        assertEquals(2, theIngredientList.getIngredients().size());
    }
    
    @Test
    public void testAddIngredient() {
        assertEquals(true, theIngredientList.addIngredient(ingredient1));
        assertEquals(false, theIngredientList.addIngredient(ingredient3));
        actualList = theIngredientList.getIngredients();
        expectedList.add(ingredient2);
        expectedList.add(ingredient4);
        expectedList.add(ingredient3);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testRemoveIngredient() {
        assertEquals(true, theIngredientList.removeIngredient(ingredient1));
        assertEquals(false, theIngredientList.removeIngredient(ingredient3));
        actualList = theIngredientList.getIngredients();
        expectedList.add(ingredient2);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testRemoveIngredients() {
        ingredientArray.add(ingredient3);
        assertFalse(theIngredientList.removeIngredients(ingredientArray));
        ingredientArray.add(ingredient1);
        ingredientArray.add(ingredient2);
        assertTrue(theIngredientList.removeIngredients(ingredientArray));
        ingredientArray.clear();
        assertFalse(theIngredientList.removeIngredients(ingredientArray));
        theIngredientList.clearIngredients();
        theIngredientList.addIngredient(ingredient1);
        ingredientArray.add(ingredient2);
        ingredientArray.add(ingredient3);
        assertFalse(theIngredientList.removeIngredients(ingredientArray));
        expectedList.add(ingredient1);
        actualList = theIngredientList.getIngredients();
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testClearIngredients() {
        theIngredientList.addIngredient(ingredient1);
        theIngredientList.addIngredient(ingredient2);
        theIngredientList.addIngredient(ingredient3);
        theIngredientList.addIngredient(ingredient4);
        theIngredientList.addIngredient(ingredient5);
        theIngredientList.addIngredient(ingredient6);

        theIngredientList.clearIngredients();
        Collection<Ingredient> emptyList = new ArrayList<>();
        assertEquals(emptyList, theIngredientList.getIngredients());
    }

    @Test
    public void testContainsIngredients() {
        ingredientArray.add(ingredient4);
        ingredientArray.add(ingredient2);
        assertFalse(theIngredientList.containsIngredients(ingredientArray));
        theIngredientList.addIngredient(ingredient1);
        assertTrue(theIngredientList.containsIngredients(ingredientArray));
    }

    @Test
    public void testReorganizeIngredients() {
        theIngredientList.addIngredient(ingredient3);
        // move 1 after 3
        theIngredientList.reorganizeIngredients(ingredient1, ingredient3);
        Collection<Ingredient> expectedList = new ArrayList<>();
        expectedList.add(ingredient2);
        expectedList.add(ingredient3);
        expectedList.add(ingredient1);
        actualList = theIngredientList.getIngredients();
        assertEquals(expectedList, actualList);
    }

    // * White Box Tests for addIngredients - Zach Plattner *//
    /**
     * test basis path 1 (43 --> 44 --> 48)
     */
    @Test
    public void testAddIngredients1() {
        assertEquals(true, theIngredientList.addIngredients(ingredientArray));
        actualList = theIngredientList.getIngredients();
        expectedList.add(ingredient1);
        expectedList.add(ingredient2);
        assertEquals(expectedList, actualList);
    }

    /**
     * test basis path 2 (43 --> 44 --> 45 --> 48)
     */
    @Test
    public void testAddIngredients2() {
        ingredientArray.add(ingredient1);
        assertEquals(true, theIngredientList.addIngredients(ingredientArray));
        actualList = theIngredientList.getIngredients();
        expectedList.add(ingredient2);
        expectedList.add(ingredient4);
        assertEquals(expectedList, actualList);
    }

    /**
     * test basis path 3 (43 --> 44 --> 45 --> 46 --> 48)
     */
    @Test
    public void testAddIngredients3() {
        ingredientArray.add(ingredient3);
        assertEquals(false, theIngredientList.addIngredients(ingredientArray));
        actualList = theIngredientList.getIngredients();
        expectedList.add(ingredient1);
        expectedList.add(ingredient2);
        expectedList.add(ingredient3);
        assertEquals(expectedList, actualList);
    }

    /**
     * test basis path 4 (43 --> 44 --> 45 --> 44 --> 45 --> 48)
     */
    @Test
    public void testAddIngredients4() {
        ingredientArray.add(ingredient1);
        ingredientArray.add(ingredient2);
        assertEquals(true, theIngredientList.addIngredients(ingredientArray));
        actualList = theIngredientList.getIngredients();
        expectedList.add(ingredient4);
        expectedList.add(ingredient5);
        assertEquals(expectedList, actualList);
    }

    /**
     * test basis path 5 (43 --> 44 --> 45 --> 46 --> 44 --> 45 --> 48)
     */
    @Test
    public void testAddIngredients5() {
        ingredientArray.add(ingredient3);
        ingredientArray.add(ingredient2);
        assertEquals(false, theIngredientList.addIngredients(ingredientArray));
        actualList = theIngredientList.getIngredients();
        expectedList.add(ingredient1);
        expectedList.add(ingredient3);
        expectedList.add(ingredient5);
        assertEquals(expectedList, actualList);
    }

}
