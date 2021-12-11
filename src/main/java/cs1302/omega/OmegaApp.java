package cs1302.omega;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class OmegaApp extends Application {

    //initializing variables
    private static int width = 800;
    private static int height = 600;
    private static int paddleWidth = 10;
    private static int paddleHeight = 100;
    private static int diameter = 15;

    int ballSpeedX = 1;
    int ballSpeedY = 1;
    int startXUser = 0;
    int startXOpp = width - paddleWidth;
    int startYUser = height / 2;
    int startYPosOpp = height / 2;
    int scoreUser = 0;
    int scoreOpp = 0;
    boolean gameStart;
    int ballXPos = width / 2;
    int ballYPos = height / 2;

/**
 * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
 * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
 */
    public OmegaApp() {}

    /**
     * ENTER JAVADOC COMMENT.
     * @param stage the Stage.
     */
    public void start(Stage stage) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        //setup Stage
        stage.setTitle("OmegaApp!");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
    } // start


} // OmegaApp






/*
// demonstrate how to load local asset using "file:resources/"
Image bannerImage = new Image("file:resources/readme-banner.png");
ImageView banner = new ImageView(bannerImage);
banner.setPreserveRatio(true);
banner.setFitWidth(640);

// some labels to display information
Label notice = new Label("Modify the starter code to suit your needs.");
Label instructions
= new Label("Move left/right with arrow keys; click rectangle to teleport.");

// demo game provided with the starter code
DemoGame game = new DemoGame(640, 240);

// setup scene
VBox root = new VBox(banner, notice, instructions, game);
Scene scene = new Scene(root);

// setup stage
stage.setTitle("OmegaApp!");
stage.setScene(scene);
stage.setOnCloseRequest(event -> Platform.exit());
stage.sizeToScene();
stage.show();

// play the game
game.play();
*/
