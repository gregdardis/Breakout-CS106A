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
		private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
		private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
		private static final int N_OF_TURNS = 3;
		
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
		private int getXForPaddle() {
			int x = 0;
			x = (APPLICATION_WIDTH - PADDLE_WIDTH) / 2;
			return x;
		}
		
		/* Gets the y coordinate for the starting position of the paddle */
		private int getYForPaddle() {
			int y = 0;
			y = APPLICATION_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
			return y;
		}
		
		/* Draws the starting paddle and places it in the middle of the screen at the correct height */
		private GObject createPaddle() {
			int x = getXForPaddle();
			int y = getYForPaddle();
			GRect paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
			paddle.setColor(Color.BLACK);
			paddle.setFilled(true);
			paddle.setFillColor(Color.BLACK);
			add(paddle);
			return paddle;
		}
		
		/** Called on mouse drag to reposition the object */
		public void mouseMoved(MouseEvent e) {
			if (paddle != null) {
//				y might be PADDLE_HEIGHT?
				paddle.move(e.getX() - lastX, getYForPaddle() + (PADDLE_HEIGHT / 2));
				lastX = e.getX();
//				lastY = e.getY();
//				y used to be same as x but for y
			}
		}
		
		private void setUpGame() {
			createRowsOfBricks();
			createPaddle();
			initializeMouse();
		}
		
//		public void mouseDragged(MouseEvent mouse, GObject paddle) {
//			lastX = mouse.getX();
//			paddle = getElementAt(lastX, getYForPaddle());
//		}
		
//		private void playGame() {
//			addMouseListeners();
//		}
		
		/* Prepares the mouse to control the paddle, paddle is set to middle of the paddle's location */
		private void initializeMouse() {
			addMouseListeners();
			startX = getXForPaddle() + (PADDLE_WIDTH / 2);
			startY = getYForPaddle() + (PADDLE_HEIGHT / 2);
			paddle = getElementAt(startX, startY);
		}
		
	/** Runs the Breakout program. */
		public void run() {
			this.resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
			setUpGame();
//			playGame();
		}
		
		/* Instance variables */
		private double lastX; /* The last mouse X position */
		private GObject paddle = createPaddle();
		private double startX; /* The starting x position for the mouse */
		private double startY; /* The starting y position for the mouse */
	}

//	 TODO: GET MOUSE MOVING THE PADDLE
/*Notes from lectures:
 * To see if the ball hit a brick, call getElementAt(location of brick? or ball or something?
 * and if it doesn't return null it means there is a brick there. It will return a GRect object
 * which you will then remove.
 * 
 *  Use waitForClick() to pause until they click the mouse to get the game start again
 *  after they lose a life */