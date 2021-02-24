//	FoodBankPatrons will have a main method in which a FoodBank, FoodProducer, and FoodConsumer object are created.
//	The FoodProducer and FoodConsumer must share the same FoodBank object. Once created, the main method starts these threads.
//  Jonathan Giraud CMSC 403.

public class FoodBankPatrons
{
    public static void main(String[] args)
    {
        FoodBank foodBank = new FoodBank();

        //Create the producer\consumer objects.
        FoodProducer foodProducer = new FoodProducer(foodBank);
        FoodConsumer foodConsumer = new FoodConsumer(foodBank);

        //Start the threads.
        foodProducer.start();
        foodConsumer.start();
    }
}