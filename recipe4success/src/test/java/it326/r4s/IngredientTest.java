package it326.r4s;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.experimental.runners.Enclosed;
import org.junit.runners.Parameterized.*;
import org.junit.runner.RunWith;

import it326.r4s.UnitConverter.*;

@RunWith(Enclosed.class)
public class IngredientTest {
    
    //Inner class used to run parameterized tests for testing correct quantity conversion in the method changeUnit() in Ingredient.java
    @RunWith(Parameterized.class)
    public static class testChangeUnitQuanities{

        @Test
        public void quantityConverted(Unit initialUnit, Unit newUnit, double initialQuantity, double expextedNewQuantity){

            Ingredient theIngredient = new Ingredient(new FoodItem(), initialQuantity, initialUnit);

            theIngredient.changeUnit(newUnit);

            double returnedQuantity = theIngredient.getQuantity();
            
            assertEquals(expextedNewQuantity, returnedQuantity, 0.01);

        }

        @Parameters
        public static Collection checkConversionData(){
            return Arrays.asList(new Object[][]{
                {Unit.TEASPOON, Unit.TABLESPOON, 5, 1.666667},
                {Unit.KILOGRAM, Unit.GRAM, 10, 10000},
                {Unit.GALLON, Unit.CUP, -2, -1},
                {Unit.GALLON, Unit.POUND, 10, -1}
            });
        }
    }

    //Inner class used to run parameterized tests for testing correct unit conversion in the method changeUnit() in Ingredient.java
    @RunWith(Parameterized.class)
    public static class testChangeUnitUnits{
        
        @Test
        public void unitConverted(Unit initialUnit, Unit newUnit){

            final double TEST_QUANTITY = 10; //should be irrelevant but is changable here
            Ingredient theIngredient = new Ingredient(new FoodItem(), TEST_QUANTITY, initialUnit);

            theIngredient.changeUnit(newUnit);

            Unit returnedUnit = theIngredient.getUnit();
            
            assertEquals(newUnit, returnedUnit);
        }

        @Parameters
        public static Collection checkConversionData(){
            return Arrays.asList(new Object[][]{
                {Unit.TEASPOON, Unit.TABLESPOON},
                {Unit.KILOGRAM, Unit.GRAM},
                {Unit.GALLON, Unit.CUP},
                {Unit.GALLON, Unit.POUND}
            });
        }
    }

}
