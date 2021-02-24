//  FoodConsumer is identical to FoodProducer except that the random number generated in run will be removed from the FoodBank object.
//  Jonathan Giraud CMSC 403

import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class FoodConsumer extends Thread
{
    ReentrantLock lock = new ReentrantLock();
    FoodBank foodBank;
    Random random = new Random();

    public FoodConsumer(FoodBank foodBank)
    {
        this.foodBank = foodBank;
    }

    @Override
    public void run()
    {
        int randomFoodAmount;

        //Loop infinitely.
        while (true)
        {
            //Generate a number from 1-100.
            randomFoodAmount = random.nextInt(100) + 1;

            //Lock the thread when taking food from the food bank.
            lock.lock();
            //Check if there's enough food in the bank before trying to take from it.
            if (foodBank.food >= randomFoodAmount)
            {
                foodBank.takeFood(randomFoodAmount);
            }

            //If there's not enough food, say so and skip the attempt at trying to rob the food bank.
            else
            {
                System.out.println("Waiting to get food! There is less than " + randomFoodAmount + " food items in the bank.");
            }
            //Unlock the thread when the food has been finished being subtracted from the bank.
            lock.unlock();

            try
            {
                //Sleep the program for 100 milliseconds.
                Thread.sleep(100);
            }

            //Catch statement because Java won't let me run this without one.
            catch (InterruptedException e)
            {
                System.out.println("ERROR.");
            }
        }
    }
}