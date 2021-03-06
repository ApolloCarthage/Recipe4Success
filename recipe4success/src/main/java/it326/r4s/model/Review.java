package it326.r4s.model;

/**
 * A review which confers a rating and a reviewer
 * 
 * @author Nate Rardin (njrardi@ilstu.edu)
 * @date 4/13/22
 */
public class Review extends Entity {

    // * Instance Variables *\\
    private User reviewer;
    private Rating rating;

    // *Enumeration*\\
    /**
     * Enumeration which enforces the rating levels allowed in a review
     */
    public static enum Rating {

        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

        private int value;

        Rating(int value) {
            this.value = value;
        }
    }

    // * Constructor *\\
    /**
     * Constructor for the Review class: requires a rating and the User object
     * associated with the reviewer.
     * 
     * @param reviewer - User object representing the reviewer.
     * @param rating   - Rating enumeration representing the reviewer's rating.
     */
    public Review(User reviewer, Rating rating) {
        this.reviewer = reviewer;
        this.rating = rating;
    }

    // * Methods *\\
    /**
     * Accessor for the review's rating value.
     * 
     * @return an int representing the value of the rating.
     */
    public int getRatingValue() {
        return this.rating.value;
    }

    /**
     * Accessor for the review's reviewer.
     * 
     * @return the User associated with the reviewer.
     */
    public User getReviewer() {
        return this.reviewer;
    }

    /**
     * Mutator for the reviewer.
     * 
     * @param reviewer the User object of reviewer.
     */
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * Accessor for the review's Rating enumeration.
     * 
     * @return the rating enumeration.
     */
    public Rating getRating() {
        return this.rating;
    }

    /**
     * Mutator for the review's Rating enumeration.
     * 
     * @param rating - the rating enumeration of the review.
     */
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    /**
     * An override for the .equals method of java.obj.
     * 
     * @param obj a Review object.
     *            private User reviewer;
     *            private Rating rating;
     * 
     * @return a bool indicating if the two ingredients are equal.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the compared object is of correct type
        if (!(obj instanceof Review) || obj == null) {
            return false;
        }
        // If the object is compared with itself then return true 
        if (obj == this) {
            return true;
        }

        Review otherReview = (Review) obj;
        return this.reviewer.equals(otherReview.getReviewer()) && this.rating == otherReview.getRating();
    }

    /**
     * An override for the .toString method of java.obj.
     * 
     * @return a string representation of the Review object.
     */
    @Override
    public String toString() {
        return this.reviewer.toString() + ": " + this.rating + "/5";
    }
}
