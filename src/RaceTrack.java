// Create a class RaceTrack which uses JavaFX (subclasses the Application class) and overrides its start method to do the required drawing. Each car must be advanced by a separate Thread.
// On each thread iteration, each car should advance a random 0-10 pixels forward. Once advanced, a thread should sleep for 50 miliseconds.
// The threads will execute until a car reaches the end of the track. Once this occurs, an Alert should be spawned alerting the user of the winner and all cars stop. The primaryStage will be 500px by 200px and is not be resizable.
// Jonathan Giraud CMSC 403.
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Random;


public class RaceTrack extends Application
{
    public static void main(String[] args)
    {
        //Launch the program.
        launch();
    }

    //Variables for the car images.
    private ImageView catCar1;
    private ImageView catCar2;
    private ImageView catCar3;
    private Thread catCarThread1;
    private Thread catCarThread2;
    private Thread catCarThread3;
    //Tracks if a race is still going on.
    private boolean running = false;

    @Override
    public void start(Stage primaryStage)
    {
        //Initialize the primary stage with a title, and restricting it from being resized.
        //Then add rectangles to represent the racetracks. I colored them for fun. You never even gave us an image of a car to use for this project, so I had fun.
        primaryStage.setTitle("Richmond Racing Thing");
        primaryStage.setResizable(false);
        Pane windowPane = new Pane();
        Rectangle track1 = new Rectangle(50, 50, 400, 20);
        track1.setFill(Color.valueOf("FC9C6B"));
        Rectangle track2 = new Rectangle(50, 100, 400, 20);
        track2.setFill(Color.valueOf("53C2C3"));
        Rectangle track3 = new Rectangle(50, 150, 400, 20);
        track3.setFill(Color.valueOf("00FF3E"));

        //Set images for the cars and set their starting positions.
        Image racecar1 = new Image("file:src/totallycar.png");
        catCar1 = new ImageView(racecar1);
        catCar1.relocate(0, 10);
        Image racecar2 = racecar1;
        catCar2 = new ImageView(racecar2);
        catCar2.relocate(0, 60);
        Image racecar3 = racecar1;
        catCar3 = new ImageView(racecar3);
        catCar3.relocate(0, 120);

        //Make the buttons and set their positions.
        Button start = new Button("Start");
        start.relocate(0, 0);
        Button pause = new Button("Pause");
        pause.relocate(230, 0);
        Button reset = new Button("Reset");
        reset.relocate(455, 0);

        //Display everything in the window pane.
        windowPane.getChildren().addAll(track1, track2, track3, catCar1, catCar2, catCar3, start, pause, reset);

        //Event listener to react to button presses for the start, pause, and reset buttons.
        //The start button won't do anything if the program is already running. If a race isn't currently ongoing, then the race is started.
        start.setOnAction((ActionEvent event) ->
        {
            if(running)
            {
                return;
            }
            running = true;
            start();
        });

        //The pause button will do nothing if there are currently no running threads. If they are, it just sets the race to a halt by setting 'running' to false.
        pause.setOnAction((ActionEvent event) ->
        {
            if(!running && catCarThread1 != null && catCarThread2 != null && catCarThread3 != null)
            {
                return;
            }
            running = false;
        });

        //Reset puts all cars back to the start of the track and stops the race so they don't instantly start running when reset is pressed.
        reset.setOnAction((ActionEvent event) ->
        {
            running = false;
            catCar1.setX(0);
            catCar2.setX(0);
            catCar3.setX(0);
        });

        //Just felt like changing the background's color. It then sets the window's background with that color and sets the window's dimensions and sets the display to visible.
        BackgroundFill backgroudFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroudFill);
        windowPane.setBackground(background);
        Scene scene = new Scene(windowPane, 500, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //Method to display who wins using alert dialogue boxes.
    private void alert(int winner)
    {
        Alert winnerAlert = new Alert(AlertType.INFORMATION);
        winnerAlert.setTitle("The race is over!");
        winnerAlert.setContentText("We didn't get a car image given to use for this project.");
        winnerAlert.setHeaderText("'CAR' " + winner + " WINS.");
        winnerAlert.show();
    }

    //This function will start race. All three tasks do the same exact thing.
    //First, infinitely loop and check if the race is still ongoing.
    //The race is considered to be ongoing IF not a single thread/catCar has reached the finish line,
    //which is represented by checking if the X position is past 400.
    //The cars start at X position 0, but when they reach 400, the running state of the program will be set to false,
    //the race will halt, and whoever reached the end first will be alerted as the winner.
    //
    //How far each car moves is determined by a randomly generated number under or equal to 10.
    //After a move is made, sleep the car for 50 milliseconds.
    //This applies to all three tasks for each thread, so I'm not rewriting it for each section, sorry.
    private void start()
    {
        Random randomMovementAmount = new Random();

        Task move1 = new Task<Void>()
        {
            @Override
            public Void call()
            {
                while(true && running)
                {

                    if(catCar1.getX() >= 400)
                    {
                        running = false;
                        Platform.runLater(() -> alert(1));
                        break;
                    }

                    Platform.runLater(() -> catCar1.setX(catCar1.getX() + randomMovementAmount.nextInt(11)));

                    try
                    {
                        Thread.sleep(50);
                    }

                    catch(InterruptedException e)
                    {
                        System.out.println("ERROR.");
                    }
                }
                return null;
            }
        };

        Task move2 = new Task<Void>()
        {
            @Override
            public Void call()
            {
                while(true && running)
                {

                    if(catCar2.getX() >= 400)
                    {
                        running = false;
                        Platform.runLater(() -> alert(2));
                        break;
                    }

                    Platform.runLater(() -> catCar2.setX(catCar2.getX() + randomMovementAmount.nextInt(11)));

                    try
                    {
                        Thread.sleep(50);
                    }

                    catch(InterruptedException e)
                    {
                        System.out.println("ERROR.");
                    }
                }
                return null;
            }
        };

        Task move3 = new Task<Void>()
        {
            @Override
            public Void call()
            {
                while(true && running)
                {

                    if(catCar3.getX() >= 400)
                    {
                        running = false;
                        Platform.runLater(() -> alert(3));
                        break;
                    }

                    Platform.runLater(() -> catCar3.setX(catCar3.getX() + randomMovementAmount.nextInt(11)));

                    try
                    {
                        Thread.sleep(50);
                    }

                    catch(InterruptedException e)
                    {
                        System.out.println("ERROR.");
                    }
                }
                return null;
            }
        };

        //Set what each thread must do and then start all of the threads.
        catCarThread1 = new Thread(move1);
        catCarThread2 = new Thread(move2);
        catCarThread3 = new Thread(move3);
        catCarThread1.start();
        catCarThread2.start();
        catCarThread3.start();
    }
}