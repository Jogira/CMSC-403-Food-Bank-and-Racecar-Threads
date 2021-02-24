//  FoodBank will have a single instance variable named food of type int.
//  FoodBank will define a default constructor which initializes food to zero.
//  FoodBank will have two methods: giveFood and takeFood. Both methods will have a single parameter of type int.
//  giveFood will add the value of the parameter to the food instance variable, takeFood will subtract the value.
//  Jonathan Giraud CMSC 403

public class FoodBank
{
    int food;

    public FoodBank()
    {
        food = 0;
    }

    //Add food and display the current bank balance.
    public void giveFood(int givenFood)
    {
        food = food + givenFood;
        System.out.println("GIVING " + givenFood +" items of food. The balance is now " + food + " items.");
    }

    //Take away food and display the current bank balance.
    public void takeFood(int takenFood)
    {
        food = food - takenFood;
        System.out.println("TAKING " + takenFood +" items of food. The balance is now " + food + " items.");
    }
}