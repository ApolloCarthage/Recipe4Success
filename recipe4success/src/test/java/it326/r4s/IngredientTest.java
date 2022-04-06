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
    
    /*Inner class used to run parameterized tests for that the method changeUnit() in Ingredient.java
    correctly returns either false or true based on the validity of the quantity entered*/
    @RunWith(Parameterized.class)
    public static class testChangeUnitReturnValidity{
        
        private double initialQuantity;
        private boolean expectedValidity; //whether that quantity should return true or false

        //Initializes this class with different specified parameters each time
        public testChangeUnitReturnValidity(double initialQuantity, Boolean expectedValidity){
            this.initialQuantity = initialQuantity;
            this.expectedValidity = expectedValidity;
        }

        //The sets of parameters with which testChangeUnitQuantities is initialized. Each set is ran once when the test class is run
        @Parameters
        public static Collection checkConversionData(){
            return Arrays.asList(new Object[][]{
                {5, true},
                {10, true},
                {-2, false},
            });
        }

        //Actual test for Ingredient.changeUnit() which checks for correct quantity conversion
        @Test
        public void quantityConverted(){

            final Unit INIT_UNIT = Unit.TEASPOON; //this should be irrelevant but is changable here just in case
            Ingredient theIngredient = new Ingredient(new FoodItem(), initialQuantity, INIT_UNIT);

            boolean returnedValidity = theIngredient.changeUnit(INIT_UNIT);
            
            assertEquals(expectedValidity, returnedValidity);

        }

    }

    //Inner class used to run parameterized tests for testing correct unit conversion in the method changeUnit() in Ingredient.java
    @RunWith(Parameterized.class)
    public static class testChangeUnitUnits{
        
        private Unit initialUnit;
        private Unit newUnit;

        //Initializes this class with different specified parameters each time
        public testChangeUnitUnits(Unit initialUnit, Unit newUnit){
            this.initialUnit = initialUnit;
            this.newUnit = newUnit;
        }

        //The sets of parameters with which testChangeUnitQuantities is initialized. Each set is ran once when the test class is run
        @Parameters
        public static Collection checkConversionData(){
            return Arrays.asList(new Object[][]{
                {Unit.TEASPOON, Unit.TABLESPOON},
                {Unit.KILOGRAM, Unit.GRAM},
                {Unit.GALLON, Unit.CUP},
                {Unit.GALLON, Unit.POUND}
            });
        }

        //Actual test for Ingredient.changeUnit() which checks for correct quantity conversion
        @Test
        public void unitConverted(){

            final double TEST_QUANTITY = 10; //should be irrelevant but is changable here
            Ingredient theIngredient = new Ingredient(new FoodItem(), TEST_QUANTITY, initialUnit);

            theIngredient.changeUnit(newUnit);

            Unit returnedUnit = theIngredient.getUnit();
            
            assertEquals(newUnit, returnedUnit);
        }

    }

}