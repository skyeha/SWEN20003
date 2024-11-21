import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2023
 *
 * Please enter your name below
 * @author
 */
public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";

    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
    private final static String LV0_INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final static String LV1_INSTRUCTION =
            "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE\nEAT THE PELLET TO ATTACK";
    private final String GAME_FONT =  "res/FSO8BITR.TTF";
    private final static int MESSAGE_SIZE = 64;
    private final static int INSTRUCTION_SIZE = 24;
    private final static int SCORE_SIZE = 20;


    // Gate-keeping value to check if each level is started and if win the game
    private static boolean level0Started = false;
    private static boolean level0Complete = false;
    private static boolean level1Started = false;
    private static boolean level1Complete = false;

    //--------------------- Initialise game characters, environment and HUD
    // Characters
    private Pacman pacman;
    private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

    // Environment
    private ArrayList<Wall> walls = new ArrayList<Wall>();
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private ArrayList<Pellet> pellets = new ArrayList<Pellet>();
    private ArrayList<Cherry> cherries = new ArrayList<Cherry>();

    // HUD
    private Score score = new Score(new Point(25,25), GAME_FONT, SCORE_SIZE);
    private ArrayList<Heart> hearts = new ArrayList<Heart>();
    private int currentLives;

    // Game mode
    private static int frame = 0;
    private int frenzyFrame = 0;
    private boolean frenzy = false;


    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV("res/level0.csv");
        generateHeart();
    }

    // Initialise player HP
    private void generateHeart() {
        double heartX = 900;
        double heartY = 10;
        for (int i = 0; i < 3; i++) {
            hearts.add(new Heart(new Point(heartX, heartY)));
            heartX += 30;
        }
        currentLives = 3;
    }


    /**
     * Method used to read file and create objects (you can change/move
     * this method as you wish).
     */
    private void readCSV(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String text;
            int ghostCount = 0;
            int wallCount = 0;
            int dotCount = 0;

            while ((text = br.readLine()) != null) {
                String[] cells = text.split(",");
                double x = Double.parseDouble(cells[1]);
                double y = Double.parseDouble(cells[2]);

                double heartX = 870;
                double heartY = 10;

                switch (cells[0].toLowerCase()) {
                    case "player":
                        pacman = new Pacman(new Point(x, y));
                        break;
                    case "ghost":
                        ghosts.add(new Ghost(new Point(x,y)));
                        break;
                    case "ghostred":
                        ghosts.add(new Ghost(new Point(x,y),
                                cells[0].toLowerCase(),
                                new String[] {"res/ghostRed.png", "res/ghostFrenzy.png"},
                                1, 0.5));
                        break;
                    case "ghostblue":
                        ghosts.add(new Ghost(new Point(x,y),
                                cells[0].toLowerCase(),
                                new String[] {"res/ghostBlue.png", "res/ghostFrenzy.png"},
                                2, 1.5));
                        break;
                    case "ghostgreen":
                        ghosts.add(new Ghost(new Point(x,y),
                                cells[0].toLowerCase(),
                                new String[] {"res/ghostGreen.png", "res/ghostFrenzy.png"},
                                4, 3.5));
                        break;
                    case "ghostpink":
                        ghosts.add(new Ghost(new Point(x,y),
                                cells[0].toLowerCase(),
                                new String[] {"res/ghostPink.png", "res/ghostFrenzy.png"},
                                3, 2.5));
                        break;
                    case "wall":
                        walls.add(new Wall(new Point(x, y)));
                        break;
                    case "dot":
                        dots.add(new Dot(new Point(x, y)));
                        break;
                    case "pellet":
                        pellets.add(new Pellet(new Point(x, y)));
                        break;
                    case "cherry":
                        cherries.add(new Cherry(new Point(x,y)));
                        break;
                    default:
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

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        // Hidden button to skip level 0
        if (input.wasPressed(Keys.W)) {
            level0Complete = true;
        }

        if (input.wasPressed(Keys.SPACE) && !level0Started) {
            level0Started = true;
        }

        // Render start screen while level 0 not started
        if (!level0Started) {
            level0StartScreenRender();
            return;
        }

        // Rendering level 0 to play
        if (!level0Complete) {
            level0(input);
            return;
        }

        // Small pause after moving forward to level 1
        if (frame <= 300) {
            levelCompleteRender();
            frame++;
            return;
        }

        // Start level 1 once level 0 is finished
        if (input.wasPressed(Keys.SPACE) && !level1Started) {
            level1Started = true;
            reset();
            generateHeart();
            readCSV("res/level1.csv");
        }

        if (!level1Started) {
            level1StartScreenRender();
            return;
        }

        if  (!level1Complete) {
            level1(input);
        } else {
            winScreenRender();
        }
    }

    private void level0(Input input) {

        if (checkLooseCond()) {
            looseScreenRender();
            return;
        }

        if (checkLv0WinCond()) {
            level0Complete = true;
            return;
        }

        // Rendering the level
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        environmentRender();
        enemyRenderLv0();
        HUDRender();

        // Collision with enemy check
        if (enemyCollision(frenzy)) {
            pacman.reset();
            return;
        }

        // Collision with dots check
        bountyCollision();

        // Move our pacman!
        pacman.move(input, walls);
    }

    private void level1(Input input) {
        if (checkLooseCond()) {
            looseScreenRender();
            return;
        }

        if (checkLv1WinCond()) {
            level1Complete = true;
            return;
        }

        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        HUDRender();
        environmentRender();
        enemyRenderLv1();


        bountyCollision();

        // Keep check of the amount of time given for frenzy mode
        // and make sure everything goes back to normal after frenzy mode
        if (frenzy && frenzyFrame <= 1000) {
            frenzyMode(input, walls);
            frenzyFrame++;
            return;
        } else {
            frenzy = false;
            frenzyFrame = 0;
            frenzyModifier(frenzy);
            visibilityReset();
        }


        // Check for enemy collision
        if (enemyCollision(frenzy)) {
            pacman.reset();
            return;
        }
        pacman.move(input, walls);
    }

    // This function governs the changes in the game during frenzy mode
    private void frenzyMode(Input input, ArrayList<Wall> walls) {
        frenzyModifier(frenzy);
        enemyCollision(frenzy);
        pacman.move(input, walls);
    }

    // This function set the state of each character to frenzy state
    private void frenzyModifier(boolean frenzy) {
        pacman.isFrenzied = frenzy;
        for (Ghost ghost: ghosts) {
            ghost.isFrenzied = frenzy;
        }
    }

    // This function render our environment (doodad)
    private void environmentRender() {
        for (Wall wall: walls) {
            wall.draw();
        }
        for (Dot dot: dots) {
            dot.draw();
        }
        for (Pellet pellet: pellets) {
            pellet.draw();
        }
        for (Cherry cherry: cherries) {
            cherry.draw();
        }
    }

    // This function renders the enemy for level 0
    private void enemyRenderLv0() {
        for (Ghost ghost: ghosts) {
            ghost.move();
        }
    }

    // This function renders the enemy for level 1
    private void enemyRenderLv1() {
        for (Ghost ghost: ghosts) {
            ghost.move(walls);
        }
    }

    // This function renders the HUD
    private void HUDRender() {
        score.draw();

        for (Heart heart: hearts) {
            heart.draw();
        }
    }

    // This function checks for pacman collision with enemy both during normal and frenzy mode
    private boolean enemyCollision(boolean frenzy) {
        for (Ghost ghost: ghosts) {
            if (pacman.boundedRect.intersects(ghost.boundedRect)) {
                if (frenzy) {
                    if (!ghost.getVisibility()) {break;}
                    score.scoreAdd(30);
                    ghost.setVisibility(false);
                    ghost.reset();
                    break;
                }
                hearts.remove(currentLives-1);
                currentLives--;
                pacman.reset();
                ghost.reset();
                return true;
            }
        }
        return false;
    }

    // This function checks for collision with dots, cherries, and pellet
    private void bountyCollision() {
        for (Dot dot: dots) {
            if (pacman.boundedRect.intersects(dot.boundedRect)) {
                score.scoreAdd(10);
                dots.remove(dot);
                break;
            }
        }
        for (Cherry cherry: cherries) {
            if (pacman.boundedRect.intersects(cherry.boundedRect)) {
                score.scoreAdd(20);
                cherries.remove(cherry);
                break;
            }
        }
        for (Pellet pellet: pellets) {
            if (pacman.boundedRect.intersects(pellet.boundedRect)) {
                frenzy = true;
                pellets.remove(pellet);
                break;
            }
        }

    }

    // This function checks if the player has lost
    private boolean checkLooseCond() {
        return currentLives == 0;
    }

    // This function checks if player has won level 0
    private boolean checkLv0WinCond() {
        return score.winLevel0();
    }

    // This function checks if player has won level 1
    private boolean checkLv1WinCond() {
        return score.winLevel1();
    }

    // This function renders the starting screen when the game is opened
    private void level0StartScreenRender() {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        new Font(GAME_FONT, MESSAGE_SIZE).drawString(GAME_TITLE, 260, 250);
        new Font(GAME_FONT, INSTRUCTION_SIZE).drawString(LV0_INSTRUCTION, 320, 440);
    }

    // This function renders the starting screen when the level 0 is completed
    private void level1StartScreenRender() {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        new Font(GAME_FONT, 40).drawString(LV1_INSTRUCTION, 200, 350);
    }

    // This function renders the loosing screen when player lost
    private void looseScreenRender() {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        new Font(GAME_FONT, MESSAGE_SIZE).drawString("GAME OVER!",
                WINDOW_WIDTH /3.5
                , WINDOW_HEIGHT / 1.9);
    }

    // This function renders when player completed level 1
    private void winScreenRender() {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        new Font(GAME_FONT, MESSAGE_SIZE).drawString("WELL DONE!",
                WINDOW_WIDTH /3.5
                , WINDOW_HEIGHT / 1.9);
    }

    // This function renders the screen where player completed level 0
    private void levelCompleteRender() {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        new Font(GAME_FONT, MESSAGE_SIZE).drawString("LEVEL COMPLETE!",
                WINDOW_WIDTH / 6.0
                , WINDOW_HEIGHT / 1.9);
    }

    // This function reset the visibility of the ghost that are eaten during frenzy mode
    private void visibilityReset() {
        for (Ghost ghost: ghosts) {
            ghost.setVisibility(true);
        }
    }

    // This function clears the instantiated game elements
    private void reset() {
        walls.clear();
        hearts.clear();
        ghosts.clear();
        dots.clear();
        score.reset();
    }

}

