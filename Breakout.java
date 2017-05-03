/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
		public static final int APPLICATION_WIDTH = 400;
		public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
		private static final int WIDTH = APPLICATION_WIDTH;
		private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
		private static final int PADDLE_WIDTH = 60;
		private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
		private static final int PADDLE_Y_OFFSET = 80;

	/** Number of bricks per row */
		private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
		private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
		private static final int BRICK_SEP = 4;

	/** Width of a brick */
		private static final int BRICK_WIDTH =
		  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
		private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
		private static final int BALL_DIAMETER = 15;
		
	/** Speed of the ball */
		private static final int BALL_SPEED = 1;
		
	/** Pause time for the ball */
		private static final int BALL_PAUSE_TIME = 7;

	/** Offset of the top brick row from the top */
		private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
		private static final int N_OF_TURNS = 3;
		
	/** How long to pause after paddle movement */
		private static final int PADDLE_PAUSE_TIME = 10;
		
	
		
		/* Finds the x value of the top left corner of the first brick so the bricks
		 * can be drawn centered in the application window in method "createRowsOfBricks()"
		 */
		private int locateFirstBrickX() {
			int x = 0;
			int lengthOfBrickRow = (NBRICKS_PER_ROW * BRICK_WIDTH) + (BRICK_SEP * (NBRICKS_PER_ROW - 1));
			x = (APPLICATION_WIDTH - lengthOfBrickRow) / 2;
			return x;
		}

		/* Takes a brick object and it's row, and depending on which
		 * row it's in, colors it a different color
		 */
		private void colorBricks(GRect currentBrick, int rowNumber) {
			if (rowNumber < 2) {
				currentBrick.setColor(Color.RED);
				currentBrick.setFilled(true);
				currentBrick.setFillColor(Color.RED);
			}
			else if (rowNumber < 4) {
				currentBrick.setColor(Color.ORANGE);
				currentBrick.setFilled(true);
				currentBrick.setFillColor(Color.ORANGE);
			}
			else if (rowNumber < 6) {
				currentBrick.setColor(Color.YELLOW);
				currentBrick.setFilled(true);
				currentBrick.setFillColor(Color.YELLOW);
			}
			else if (rowNumber < 8) {
				currentBrick.setColor(Color.GREEN);
				currentBrick.setFilled(true);
				currentBrick.setFillColor(Color.GREEN);
			}
			else if (rowNumber < 10) {
				currentBrick.setColor(Color.CYAN);
				currentBrick.setFilled(true);
				currentBrick.setFillColor(Color.CYAN);
			}
			else if (rowNumber < 12) {
				currentBrick.setColor(Color.BLACK);
				currentBrick.setFilled(true);
				currentBrick.setFillColor(Color.BLACK);
			}
			else {
				currentBrick.setColor(Color.BLUE);
				currentBrick.setFilled(true);
				currentBrick.setFillColor(Color.BLUE);
			}
		}
		
		/* Draws and colors the starting bricks. 
		 * Draws one row at a time  
		 */
		private void createRowsOfBricks() {
			for (int j = 0; j < NBRICK_ROWS; j++) {
				for (int i = 0; i < NBRICKS_PER_ROW; i++) {
					GRect currentBrick = new GRect(locateFirstBrickX() + (BRICK_WIDTH + BRICK_SEP) * i,
							BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP) * j, BRICK_WIDTH, BRICK_HEIGHT);
					
					colorBricks(currentBrick, j);
					add(currentBrick);
				}
			}
		}
		
		/* Gets the x coordinate for the starting position of the paddle */
		private int getStartingXForPaddle() {
			int x = 0;
			x = (APPLICATION_WIDTH - PADDLE_WIDTH) / 2;
			return x;
		}
		
		/* Gets the y coordinate for the starting position of the paddle */
		private int getStartingYForPaddle() {
			int y = 0;
			y = APPLICATION_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
			return y;
		}
		
		/* Draws the starting paddle and places it in the middle of the screen at the correct height */
		private GObject createPaddle() {
			int x = getStartingXForPaddle();
			int y = getStartingYForPaddle();
			GRect paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
			paddle.setColor(Color.BLACK);
			paddle.setFilled(true);
			paddle.setFillColor(Color.BLACK);
			add(paddle);
			return paddle;
		}
		
		/* Returns the x value for the top left corner of the ball, 
		 * which is in the middle of the screen */
		private int getStartingXForBall() {
			int x = 0;
			x = (APPLICATION_WIDTH - BALL_DIAMETER) / 2;
			return x;
		}
		
		/* Returns the y value for the top left corner of the ball, 
		 * which is in the middle of the screen */
		private int getStartingYForBall() {
			int y = 0;
			y = (APPLICATION_HEIGHT / 2);
			return y;
		}
		
		/* Creates a new ball object that is black, and returns it */
		private GObject createBall() {
			int x = getStartingXForBall();
			int y = getStartingYForBall();
			GOval ball = new GOval(x, y, BALL_DIAMETER, BALL_DIAMETER);
			ball.setColor(Color.BLACK);
			ball.setFilled(true);
			ball.setFillColor(Color.BLACK);
			add(ball);
			return ball;
		}
		
		/** Called on mouse drag to reposition the object */
		public void mouseMoved(MouseEvent e) {
			
				// if paddle is on screen then move
				if (paddle.getX() >= 0 && paddle.getX() <= (getWidth() - PADDLE_WIDTH)) {
					paddle.move(e.getX() - (paddle.getX() + (PADDLE_WIDTH / 2)), 0);
				}
				
				// if paddle moves off left side of screen then put it on the far left, but on the screen
				if (paddle.getX() < 0) {
					paddle.setLocation(0, getStartingYForPaddle());
				}
				
				// if paddle moves off right side of screen the put it on the far right, but on the screen
				if (paddle.getX() > (getWidth() - PADDLE_WIDTH)) {
					paddle.setLocation(getWidth() - PADDLE_WIDTH, getStartingYForPaddle());
				}
			pause(PADDLE_PAUSE_TIME);
		}
		
		/* When a new ball spawns, call this method and it will change
		 * the vx (x velocity) to a random number between 1 and BALL_SPEED, or -1 and -BALL_SPEED */
		private void chooseBallXDirectionAndSpeed() {
			vx = rgen.nextDouble(1.0, BALL_SPEED);
			if (rgen.nextBoolean(0.5)) vx = -vx;
		}
		
		/* Gives both the x starting direction and speed and combines that with the
		 * starting vy speed (velocity of y downward, it starts positive) into one method */
		private void chooseStartingBallDirectionAndSpeed() {
			chooseBallXDirectionAndSpeed();
			vy = BALL_SPEED;
		}
		
		/* Moves the ball with velocity vx, vy, then pauses a short time */
		private void moveBall() {
			ball.move(vx, vy);
			pause(BALL_PAUSE_TIME);
		}
		
		/* checks if the ball hits a wall, if it does then angle of incidence = angle of reflection */
		private void checkWallCollision() {
			/* If the ball hits the right wall, reverse x velocity */
			if (ball.getX() >= (getWidth() - BALL_DIAMETER)) {
				vx = -vx;
			}
			/* If the ball hits the left wall, reverse x velocity */
			if (ball.getX() <= 0) {
				vx = -vx;
			}
			/* If the ball hits the ceiling, reverse y velocity */
			if (ball.getY() <= 0) {
				vy = -vy;
			}
		}
		
		/* Checks to see if the ball hits the bottom wall.
		 * If it does, increment failcounter and remove the ball from the screen.
		 * If they have any turns left, create a new ball but set the round started to false
		 * If they have no turns left, end the game */
		private void checkBottomWallCollision() {
			if (ball.getY() >= (getHeight() - BALL_DIAMETER)) {
				failCounter++;
				remove(ball);
				
				if (failCounter < N_OF_TURNS) {
					ball = createBall();
					roundStarted = false;
				}
				else {
					gameOverScreen();
				}
			}
		}
		
		/* Method that's called when you run out of lives */
		private void gameOverScreen() {
			GLabel gameOver = new GLabel("Game over");
			gameOver.setFont("Arial-46");
			gameOver.setLocation(((getWidth() - gameOver.getWidth()) / 2), ((getHeight() - gameOver.getHeight()) / 2));  
			gameOver.setColor(Color.RED);
			add(gameOver);
			pause(5000);
			System.exit(0);
		}
		
		/* Checks if the ball hits a brick, if it does then removes the brick
		 * and the angle of incidence = angle of reflection */
		private void checkBrickCollision() {
			/* finding all of the points around the ball on 4 sides, going clockwise starting at top left */
			double topLeftX = ball.getX();
			double topLeftY = ball.getY();
			
			double topRightX = ball.getX() + (BALL_DIAMETER);
			double topRightY = ball.getY();
			
			double bottomRightX = ball.getX() + (BALL_DIAMETER);
			double bottomRightY = ball.getY() + (BALL_DIAMETER);	
			
			double bottomLeftX = ball.getX();
			double bottomLeftY = ball.getY() + (BALL_DIAMETER);
			
			/* Checks each corner of the ball in turn to see if it collided with an object*/
			GObject collidingObject = getCollidingObject(topLeftX, topLeftY);
			if (collidingObject == null) {
				collidingObject = getCollidingObject(topRightX, topRightY);
				if (collidingObject == null) {
					collidingObject = getCollidingObject(bottomRightX, bottomRightY);
					if (collidingObject == null) {
						collidingObject = getCollidingObject(bottomLeftX, bottomLeftY);
					}
				}
			}
			
			/* If the ball collides with an object, either just reverse y velocity if it collided with the paddle
			 * or reverse the y velocity and remove the object it collided with if it collided with a brick.
			 * Walls do not count as objects */
			if (collidingObject != null) {
				if (collidingObject == paddle) {
					if (bottomLeftY <= (APPLICATION_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT) || bottomRightY <= (APPLICATION_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT)) {
						vy = -vy;
					}
					else {
						vx = -vx;
					}
				}
				else {
					vy = -vy;
					remove(collidingObject);
					numberOfBricksRemaining--;
					if (numberOfBricksRemaining == 0) {
						youWinScreen();
					}
				}
			}
		}
		
		/* checks a point with (x, y) coordinates and if there is an object there, returns that object.
		 * otherwise, returns null */ 
		private GObject getCollidingObject(double x, double y) {
			if (getElementAt(x, y) != null) {
				return getElementAt(x, y);
			}
			else return null;
		}
		
		/* Method that's called when you clear all the bricks */
		private void youWinScreen() {
			GLabel youWin = new GLabel("You win!");
			youWin.setFont("Arial-46");
			youWin.setLocation(((getWidth() - youWin.getWidth()) / 2), ((getHeight() - youWin.getHeight()) / 2));  
			youWin.setColor(Color.BLUE);
			add(youWin);
			pause(5000);
			System.exit(0);
		}
		
		/* Sets up the game */
		private void setUpGame() {
			createRowsOfBricks();
			initializeMouse();
			ball = createBall();
		}
		
		/* Plays the game */
		private void playGame() {
			/* Boolean added to make the if statement below only happen one time, each time they miss the ball
			 * and the round needs to be started again */
			boolean roundReset = false;
			boolean labelVisible = true;
			GLabel clickMessage = null;
			while(true) {
				if (!roundReset && !roundStarted) {
					roundReset = true;
					chooseStartingBallDirectionAndSpeed();
					clickMessage = addClickAnywhere();
					add(clickMessage);
					labelVisible = true;
				}
				
				while(roundStarted) {
					if (labelVisible) {
						remove(clickMessage);
						labelVisible = false;
						roundReset = false;
					}
					moveBall();
					checkBrickCollision();
					checkWallCollision();
					checkBottomWallCollision();
				}
			}
		}
		
		/* If the mouse button is pushed down, the round starts */
		public void mousePressed(MouseEvent e) {
			roundStarted = true;
//			System.out.println("I just clicked");
//			System.out.println("Click!");
		}
		
		/* Returns a GLabel that says click anywhere to launch the ball.
		 * Call this method between rounds while waiting for mouse click */
		private GLabel addClickAnywhere() {
			GLabel clickAnywhere = new GLabel("Click anywhere to launch the ball. You have " + (N_OF_TURNS - failCounter) + "  lives remaining");
			clickAnywhere.setFont("Arial-14");
			clickAnywhere.setLocation((APPLICATION_WIDTH - clickAnywhere.getWidth()) / 2, 200); 
			clickAnywhere.setColor(Color.BLACK);
			return clickAnywhere;
		}
		
		/* Prepares the mouse to control the paddle */
		private void initializeMouse() {
			addMouseListeners();
		}
		
	/** Runs the Breakout program. */
		public void run() {
			this.resize(APPLICATION_WIDTH + (BRICK_WIDTH / 2), APPLICATION_HEIGHT);
			setUpGame();
			playGame();
		}
		
		/* Instance variables */
		private GObject paddle = createPaddle();
		private GObject ball;
		private GLabel clickAnywhere = addClickAnywhere();
		private double vx, vy; /* Velocity of the ball */
		private int failCounter = 0; /* How many times they have miss the ball and let it hit the bottom */
		private boolean roundStarted = false; /* Whether or not the ball is currently moving */
		private int numberOfBricksRemaining = NBRICKS_PER_ROW * NBRICK_ROWS; /* How many bricks are remaining so we know when the game is over */
		
		
		private RandomGenerator rgen = RandomGenerator.getInstance();
	}

/*TODO:
 * MAKE IT SHOW HOW MANY LIVES YOU HAVE LEFT
 * FIX BUG THAT HAPPENS WHEN YOU MOVE PADDLE SIDEWAYS INTO THE BALL
 */