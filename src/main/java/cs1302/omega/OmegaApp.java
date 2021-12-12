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
    private static int paddleWidth = 15;
    private static int paddleHeight = 100;
    private static int diameter = 15;

    int ballSpeedX = 1;
    int ballSpeedY = 1;
    int startXUser = 0;
    int startXOpp = width - paddleWidth;
    double startYUser = height / 2;
    int startYOpp = height / 2;
    int scoreUser = 0;
    int scoreOpp = 0;
    boolean gameStart;
    int ballPosX = width / 2;
    int ballPosY = height / 2;

/**
 * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
 * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
 */
    public OmegaApp() {}

    /**
     * Start method that runs the overall program.
     * @param stage the Stage.
     */
    public void start(Stage stage) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        canvas.setOnMouseMoved(e -> startYUser  = e.getY());
		canvas.setOnMouseClicked(e ->  gameStart = true);
        //setup Stage
        stage.setTitle("OmegaApp!");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
        tl.play();
    } // start

    /**
     * Creats the aspects for the stage. Inputs the graphics content from the canvas to display.
     * @param gc the GraphicsContext implementation that will be used in a timeline at start.
     */
    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font(25));
        if (gameStart) {
            ballPosX += ballSpeedX;
            ballPosY += ballSpeedY;
            if (ballPosX < width - (width / 4)) {
                startYOpp = ballPosY - paddleHeight / 2;
            } else {
                if (ballPosY > startYOpp + paddleHeight / 2) {
                    startYOpp = startYOpp += 1;
                } else {
                    startYOpp = startYOpp - 1;
                } //else
            }//if

            gc.fillOval(ballPosX, ballPosY, diameter, diameter);
        } else {
            gc.setStroke(Color.RED);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("PONG! Click to Launch the Ball!", width / 2, height / 2);
			ballPosX = width / 2;
			ballPosY = height / 2;
            if (new Random().nextInt(2) == 0) {
                ballSpeedX = 1;
            } else {
                ballSpeedX = -1;
            } //if
            if (new Random().nextInt(2) == 0) {
                ballSpeedY = 1;
            } else {
                ballSpeedY = -1;
            } //if
        } //else

        score();
        border();

    } //run

    private void border() {
        if (ballPosY > height || ballPosY < 0) {
            ballSpeedY *= -1;
        } //if
    } //border

    private void score() {
        if (ballPosX > startXOpp + paddleWidth) {
            scoreUser++;
            gameStart = false;
        } //if
        if (ballPosX < startXUser - paddleWidth) {
            scoreOpp++;
            gameStart = false;
        } //if
    }
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
