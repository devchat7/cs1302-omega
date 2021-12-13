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
 * PONG game against an AI. Move your paddle with the mouse and get the ball past
 * your opponent to score a point. First to three points wins.
 */
public class OmegaApp extends Application {

    //initializing variables
    private static int width = 800;
    private static int height = 600;
    private static int paddleWidth = 10;
    private static int paddleHeight = 80;
    private static int diameter = 15;
    private static int points = 3;

    double ballSpeedX = 1;
    double ballSpeedY = 1;
    int startXUser = 0;
    int startXOpp = width - paddleWidth;
    double startYUser = height / 2;
    int startYOpp = height / 2;
    int scoreUser = 0;
    int scoreOpp = 0;
    boolean gameStart;
    int ballPosX = width / 2;
    int ballPosY = height / 2;
    String winner;

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
        canvas.setOnMouseClicked(e -> mouseClick());
        //setup Stage
        stage.setTitle("OmegaApp!");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
        tl.play();
    } // start

    /**
     * Method that sets gameStart to true when mouse is clicked.
     * In the event that someone has won, sets the click to reset the score.
     */
    private void mouseClick() {
        gameStart = true;
        if (scoreUser == points || scoreOpp == points) {
            resetScore();
            System.out.println("Running");
        } //if
    } //mouseClick

    /**
     * Creats the aspects for the stage. Inputs the graphics content from the canvas to display.
     * @param gc the GraphicsContext implementation that will be used in a timeline at start.
     */
    private void run(GraphicsContext gc) {
        setContents(gc);
        if (gameStart) {
            ballPosX += ballSpeedX;
            ballPosY += ballSpeedY;
            if (ballPosX < width) {
                startYOpp = ballPosY - paddleHeight / 2;
            } else {
                if (ballPosY > startYOpp + paddleHeight / 2) {
                    startYOpp = startYOpp += 1;
                } else {
                    startYOpp = startYOpp - 1;
                } //else
            } //if
            gc.fillOval(ballPosX, ballPosY, diameter, diameter);
        } else {
            winnerScreen(gc);
        } //else
        if (((ballPosX + diameter > startXOpp) && ballPosY >= startYOpp &&
            ballPosY <= startYOpp + paddleHeight) || ((ballPosX < startXUser
            + paddleWidth) && ballPosY >= startYUser && ballPosY <= startYUser + paddleHeight)) {
            ballSpeedY += new Random().nextInt(3) * Math.signum(ballSpeedY);
            ballSpeedY *= -1;
            ballSpeedX += new Random().nextInt(3) * Math.signum(ballSpeedX);
            ballSpeedX *= -1;
            miss();
            System.out.println(ballSpeedX);
            System.out.println(ballSpeedY);
        } //if
        gc.fillText(scoreUser + "             " + scoreOpp, width / 2, 100);
        gc.fillRect(startXOpp, startYOpp, paddleWidth, paddleHeight);
        gc.fillRect(startXUser, startYUser, paddleWidth, paddleHeight);
        score();
        border();
    } //run

    /**
     * Sets the GraphicsContext background screen.
     * @param gc is the overall screen that creates
     * the frae for the game.
     */
    private void setContents(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.GREEN);
        gc.setFont(Font.font(35));
    } //setContents

    /**
     * Creates the bottom and the top border so that ball can
     * bounce off.
     */
    private void border() {
        if (ballPosY > height || ballPosY < 0) {
            ballSpeedY *= -1;
        } //if
    } //border

    /**
     * Calculates the score and puts on display.
     */
    private void score() {
        if (ballPosX > startXOpp + paddleWidth) {
            scoreUser++;
            gameStart = false;
        } //if
        if (ballPosX < startXUser - paddleWidth) {
            scoreOpp++;
            gameStart = false;
        } //if
    } //score

    /**
     * Sets the score back to 0 to 0.
     */
    private void resetScore() {
        scoreUser = 0;
        scoreOpp = 0;
    } //resetScore

    /**
     * Uses score to display the winner screen.
     * @param gc the screen that prints the winner and that the
     * game has ended.
     */
    private void winnerScreen(GraphicsContext gc) {
        if (scoreUser == points || scoreOpp == points) {
            if (scoreUser > scoreOpp) {
                winner = "YOU";
            } else {
                winner = "CPU";
            } //else
            gc.setStroke(Color.RED);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("GAME OVER! " + winner + " WON!"
                + " CLICK TO REPLAY!", width / 2, height / 2);
            ballPosX = width / 2;
            ballPosY = height / 2;
            gameStart();
        } else {
            gc.setStroke(Color.YELLOW);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("PONG! Click to Launch the Ball!", width / 2, height / 2);
            ballPosX = width / 2;
            ballPosY = height / 2;
            gameStart();
        } //else
    } //winnerScreen

    /**
     * At the start of the game this determines which direction the ball should launch.
     * Takes a 50/50 chance for the X and Y axis, so the game can start in 4 different ways.
     */
    private void gameStart() {
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
    } //gameStart

    /**
     * Random occasion for the AI to miss.
     * The AI can only miss when the speed surpasses 12.
     * Makes the game very difficult to win!
     */
    private void miss() {
        int rand = new Random().nextInt(3);
        int num = 0;
        if ((ballSpeedX >= 12 || ballSpeedX <= -12) && rand == num) {
            System.out.println(rand);
            startYOpp += 1000;
        } //if
    } //miss
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
