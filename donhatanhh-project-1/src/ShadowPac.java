import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2023
 *
 * Please enter your name below
 * @author
 */
public class ShadowPac extends AbstractGame  {
    //---------------------Window configuration----------------------------------------//
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;

    //---------------------Game title, messages, and format---------------------------------//
    private final static String GAME_TITLE = "SHADOW PAC";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String GAME_INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final String GAME_FONT =  "res/FSO8BITR.TTF";
    private final static int MESSAGE_SIZE = 64;

    private final static int INSTRUCTION_SIZE = 24;

    private final static int SCORE_SIZE = 20;

    //---------------Initialise max instances of each entities---------------------------------------//
    private final static int NUM_GHOST = 4;
    private final static int NUM_WALLS = 145;
    private final static int NUM_DOTS = 121;

    //-------------------------------------Game Logic----------------------------------------------//
    // Gate-keeping value to see if the game is started
    private static boolean gameStarted = false;

    // Create required entities for the game
    private Pacman pacman;
    private Ghost[] ghosts = new Ghost[NUM_GHOST];
    private Wall[] walls = new Wall[NUM_WALLS];
    private Dot[] dots = new Dot[NUM_DOTS];


    // Initialise player's HP and score
    private Heart heart = new Heart();

    private static int currentHeart = 3;
    private static int score = 0;

    // Win condition
    private static final int WINNING_SCORE = 1210;

    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    //-----------Read input file------------------//
    // This code was inspired from SWEN20003 Lecture Week 4
    private void readCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("res/level0.csv"))) {
            String text;
            int ghostCount = 0;
            int wallCount = 0;
            int dotCount = 0;

            while ((text = br.readLine()) != null) {
                String[] cells = text.split(",");
                double x = Double.parseDouble(cells[1]);
                double y = Double.parseDouble(cells[2]);

                switch (cells[0].toLowerCase()) {
                    case "player":
                        pacman = new Pacman(x, y);
                        break;
                    case "ghost":
                        ghosts[ghostCount++] = new Ghost(x, y);
                        break;
                    case "wall":
                        walls[wallCount++] = new Wall(x, y);
                        break;
                    case "dot":
                        dots[dotCount++] = new Dot(x, y);
                        break;
                    default:
                        System.out.println("Invalid entry!");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        boolean wallHit = false;

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        // Check to see if the player has started the game
        if (input.wasPressed(Keys.ENTER) && !gameStarted) {
            gameStarted = true;
            readCSV();
        }

        // Always check if player met the win condition
        if (score ==  WINNING_SCORE) {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            new Font(GAME_FONT, MESSAGE_SIZE).drawString("WELL DONE",
                    WINDOW_WIDTH /3.5
                    , WINDOW_HEIGHT / 1.9);
            return;
        }

        // Always check if player met the loose condition
        if (currentHeart == 0) {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            new Font(GAME_FONT, MESSAGE_SIZE).drawString("GAME OVER!",
                    WINDOW_WIDTH /3.5
                    , WINDOW_HEIGHT / 1.9);
            return;
        }

        // Render starting screen
        if (!gameStarted) {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            new Font(GAME_FONT, MESSAGE_SIZE).drawString(GAME_TITLE, 260, 250);
            new Font(GAME_FONT, INSTRUCTION_SIZE).drawString(GAME_INSTRUCTION, 320, 440);
        } else {
            // Render gaming screen
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            heart.draw();
            new Font(GAME_FONT, SCORE_SIZE).drawString("SCORE " + score, 25, 25);

            // Render obstacles, ghosts and point dot
            for (Wall wall: walls) { wall.draw(); }
            for (Ghost ghost: ghosts) { ghost.draw(); }
            for (Dot dot: dots) { dot.draw(); }


            Rectangle pacmanRect = pacman.getBound();

            // Checking for dot collision
            for (Dot dot : dots) {
                if (pacmanRect.intersects(new Rectangle(dot.getBound())) && dot.getVisibility()) {
                    dot.setVisibility(false);
                    score += 10;
                    break;
                }
            }

            // Checking for ghost collision
            for (Ghost ghost : ghosts) {
                if (pacmanRect.intersects(new Rectangle(ghost.getBound()))) {
                    pacman.reset();
                    heart.setVisible(currentHeart - 1, false);
                    currentHeart--;
                    return;
                }
            }

            // Checking for wall collision
            for (Wall wall : walls) {
                if (pacman.willHitWall(wall, input)) {
                    wallHit = true;
                    break;
                }
            }

            // Render and move our pacman!
           pacman.move(input, wallHit);
        }
    }
}
