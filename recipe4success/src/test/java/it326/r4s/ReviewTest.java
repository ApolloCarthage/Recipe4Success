package it326.r4s;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import it326.r4s.model.Review;
import it326.r4s.model.Review.Rating;
import it326.r4s.model.User;

@RunWith(Enclosed.class)
public class ReviewTest {
    
    @RunWith(Parameterized.class)
    public static class testGetRatingValueWrapper{

        private int expectedValue;
        private Rating ratingEnum; //whether that quantity should return true or false

        //Initializes this class with different specified parameters each time
        public testGetRatingValueWrapper(int expectedValue, Rating ratingEnum){
            this.expectedValue = expectedValue;
            this.ratingEnum = ratingEnum;
        }

        //The sets of parameters with which testChangeUnitQuantities is initialized. Each set is ran once when the test class is run
        @Parameters
        public static Collection<Object[]> getRatingValueData(){
            return Arrays.asList(new Object[][]{
                {1, Rating.ONE},
                {2, Rating.TWO},
                {3, Rating.THREE},
                {4, Rating.FOUR},
                {5, Rating.FIVE}
            });
        }

        @Test
        public void testGetRatingValue(){
            User theUser = new User("Testman");
            
            Review newReview = new Review(theUser, ratingEnum);

            int actualValue = newReview.getRatingValue();        
            assertEquals(expectedValue, actualValue);
        }
    }
}
