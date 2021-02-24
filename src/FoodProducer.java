//	FoodProducer will have a single instance variable named bank of type FoodBank.
//	FoodProducer will have a parameterized constructor with a single parameter of type FoodBank.
//	The parameterized constructor will initialize the value of bank to the single parameter.
//	FoodProducer will extend the Thread class and override Thread’s run method.
//	FoodProducer’s run method will loop infinitely. On each loop iteration run will generate a random number from 1-100 and add that much food to the bank instance variable.
//	After adding food, the thread will sleep for 100 milliseconds.
//  Jonathan Giraud CMSC 403

import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class FoodProducer extends Thread
{
    private ReentrantLock lock = new ReentrantLock();
    private FoodBank foodBank;
    private Random random = new Random();

    public FoodProducer(FoodBank foodBank)
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
            //Lock the thread when adding food to the food bank.
            lock.lock();
            foodBank.giveFood(randomFoodAmount);
            //Unlock the thread when the food has been finished being subtracted from the bank.
            lock.unlock();
            try
            {
                //Sleep the program for 100 milliseconds.
                Thread.sleep(100);
            }

            //Catch statement because Java won't let me run this without one.
            catch(InterruptedException e)
            {
                System.out.println("ERROR.");
            }
        }
    }
}