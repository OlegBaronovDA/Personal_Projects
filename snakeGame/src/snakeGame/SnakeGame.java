package snakeGame;

import processing.core.*;

public class SnakeGame extends PApplet {

	int shiftPressed = 0;
	int appleCount = -1;
	PVector headLocation = new PVector(200, 440);
	int headXmove = 40;
	int headYmove = 0;
	int[] appleXlocation;
	int[] appleYlocation;
	int appleXrandomiser;
	int appleYrandomiser;
	int[][] bodyArray = new int[10][2];
	int[][] headBodyDistance = { { 1 }, { 1 }, { 1 }, { 1 }, { 1 }, { 1 }, { 1 }, { 1 }, { 1 } };

	public void settings() {
		size(800, 800);
	}

	public void setup() {
		frameRate(5);
		background(0, 155, 0);

		drawCell(20, 60, true);
		appleLocationRange();

	}

	public void draw() {
		infoScreen(shiftPressed);

		// Game Start
		if (shiftPressed % 2 != 0) {

			// Apple Score
			fill(255, 0, 0);
			textSize(30);
			text(appleCount, width * (float) 0.10, height * (float) 0.05);

			drawFruit(appleXrandomiser, appleYrandomiser);

			// Renew the headBodyDistance Array
			for (int i = 0; i < 9; i++) {
				headBodyDistance[i][0] = 1;
			}

			// Body Location: bodyPosAxis (1) = yAxis; bodyPosAxis (0) = xAxis;
			for (int score = appleCount; score >= 0; score--) {
				for (int bodyPosAxis = 0; bodyPosAxis <= 1; bodyPosAxis++) {

					if (bodyPosAxis == 0 && score > 0) {
						bodyArray[score][bodyPosAxis] = bodyArray[score - 1][bodyPosAxis];
					}

					if (bodyPosAxis == 0 && score == 0) {
						bodyArray[score][bodyPosAxis] = (int) (headLocation.x);
					}

					if (bodyPosAxis == 1 && score > 0) {
						bodyArray[score][bodyPosAxis] = bodyArray[score - 1][bodyPosAxis];
					}

					if (bodyPosAxis == 1 && score == 0) {
						bodyArray[score][bodyPosAxis] = (int) (headLocation.y);
					}
				}
			}
			
			
			headLocation.add(headXmove, headYmove);
			snakeHead();

			// Body Creation
			if (appleCount >= 1) {
				snakeBody(bodyArray[0][0], bodyArray[0][1]);
				headBodyDistance[0][0] = (int) dist(bodyArray[0][0], bodyArray[0][1], headLocation.x, headLocation.y);

				if (appleCount >= 2) {
					snakeBody(bodyArray[1][0], bodyArray[1][1]);
					headBodyDistance[1][0] = (int) dist(bodyArray[1][0], bodyArray[1][1], headLocation.x,
							headLocation.y);

					if (appleCount >= 3) {
						snakeBody(bodyArray[2][0], bodyArray[2][1]);
						headBodyDistance[2][0] = (int) dist(bodyArray[2][0], bodyArray[2][1], headLocation.x,
								headLocation.y);

						if (appleCount >= 4) {
							snakeBody(bodyArray[3][0], bodyArray[3][1]);
							headBodyDistance[3][0] = (int) dist(bodyArray[3][0], bodyArray[3][1], headLocation.x,
									headLocation.y);

							if (appleCount >= 5) {
								snakeBody(bodyArray[4][0], bodyArray[4][1]);
								headBodyDistance[4][0] = (int) dist(bodyArray[4][0], bodyArray[4][1], headLocation.x,
										headLocation.y);

								if (appleCount >= 6) {
									snakeBody(bodyArray[5][0], bodyArray[5][1]);
									headBodyDistance[5][0] = (int) dist(bodyArray[5][0], bodyArray[5][1],
											headLocation.x, headLocation.y);

									if (appleCount >= 7) {
										snakeBody(bodyArray[6][0], bodyArray[6][1]);
										headBodyDistance[6][0] = (int) dist(bodyArray[6][0], bodyArray[6][1],
												headLocation.x, headLocation.y);

										if (appleCount >= 8) {
											snakeBody(bodyArray[7][0], bodyArray[7][1]);
											headBodyDistance[7][0] = (int) dist(bodyArray[7][0], bodyArray[7][1],
													headLocation.x, headLocation.y);

											if (appleCount >= 9) {
												snakeBody(bodyArray[8][0], bodyArray[8][1]);
												headBodyDistance[8][0] = (int) dist(bodyArray[8][0], bodyArray[8][1],
														headLocation.x, headLocation.y);
											}
										}
									}
								}
							}
						}
					}
				}
			}

			// Self-Collision
			for (int i = 0; i < 7; i++) {
				if (headBodyDistance[i][0] == 0) {
					shiftPressed += 1;
					background(0, 255, 0);
					drawCell(20, 60, true);
					infoScreen("YOU LOST", true);
				}
			}
		}
	}

	public void keyPressed() {
		// Count SHIFT presses
		if (keyCode == SHIFT && shiftPressed % 2 == 0 || keyCode == SHIFT && shiftPressed == 0) {
			shiftPressed += 1;
		}

		// Movement
		if (shiftPressed % 2 != 0) {
			if (keyCode == UP) {
				if (appleCount == 0) {
					headXmove = 0;
					headYmove = -40;
				}

				if (appleCount >= 1) {
					if (headXmove == 0 && headYmove == 40) {
						headXmove = 0;
						headYmove = 40;
					} else {
						headXmove = 0;
						headYmove = -40;
					}
				}
			}

			if (keyCode == DOWN) {
				if (appleCount == 0) {
					headXmove = 0;
					headYmove = 40;
				}

				if (appleCount >= 1) {
					if (headXmove == 0 && headYmove == -40) {
						headXmove = 0;
						headYmove = -40;
					} else {
						headXmove = 0;
						headYmove = 40;
					}
				}
			}

			if (keyCode == LEFT) {
				if (appleCount == 0) {
					headXmove = -40;
					headYmove = 0;
				}

				if (appleCount >= 1) {
					if (headXmove == 40 && headYmove == 0) {
						headXmove = 40;
						headYmove = 0;
					} else {
						headXmove = -40;
						headYmove = 0;
					}
				}
			}
			if (keyCode == RIGHT) {
				if (appleCount == 0) {
					headXmove = 40;
					headYmove = 0;
				}

				if (appleCount >= 1) {
					if (headXmove == -40 && headYmove == 0) {
						headXmove = -40;
						headYmove = 0;
					} else {
						headXmove = 40;
						headYmove = 0;
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		PApplet.main("snakeGame.SnakeGame");
	}

	// Functions:

	void drawCell(float xAxis, float yAxis, boolean colour) {
		for (float i = yAxis; i <= 760; i += 40.0) {
			for (float j = xAxis; j <= 760; j += 40.0) {
				if (colour == true) {
					fill(0, 110, 0);
					square(j, i, 40);
					colour = false;
				} else {
					fill(0, 200, 0);
					square(j, i, 40);
					colour = true;
				}
			}
		}
	}

	// Apple Location

	void appleLocationRange() {
		appleXlocation = new int[18];
		for (int i = 0; i < appleXlocation.length; i++) {
			appleXlocation[i] = 40 + (i * 40);
		}

		appleYlocation = new int[17];
		for (int i = 0; i < appleYlocation.length; i++) {
			appleYlocation[i] = 80 + (i * 40);
		}
	}

	void infoScreen(int pressed) {
		if (pressed == 0) {
			infoScreen("Snake Game", false);
		} else if (pressed % 2 != 0) {

			// Grid refresh - for movement
			background(0, 0, 255);
			stroke(0);
			drawCell(20, 60, true);
			drawFruit(width * (float)0.05, height * (float)0.04);

			// First apple location creation
			if (appleCount == -1) {
				appleXrandomiser = appleXlocation[(int) (random(0, 18))];
				appleYrandomiser = appleYlocation[(int) (random(0, 17))];
				appleCount += 1;
			}

			// In-game apple location creation
			if (headLocation.x == appleXrandomiser && headLocation.y == appleYrandomiser) {
				appleXrandomiser = appleXlocation[(int) (random(0, 18))];
				appleYrandomiser = appleYlocation[(int) (random(0, 17))];
				appleCount += 1;
			}

			if (appleCount == 10) {

				// Win Game Screen
				shiftPressed += 1; // Remeber - it's shiftPressed because fail due to press
				pressed += 1;

				if (pressed % 2 == 0 && appleCount == 10) {
					background(0, 255, 0);
					drawCell(20, 60, true);
					infoScreen("YOU WON", true);
				}
			} else if (headLocation.x <= 0 || headLocation.x >= 800 || headLocation.y <= 40 || headLocation.y >= 800) {

				// Outbounds Game-Over
				shiftPressed += 1;
				background(0, 0, 255);
				drawCell(20, 60, true);
				infoScreen("YOU LOST", true);
			}
		}
	}

	// Draw Apple
	void drawFruit(float x, float y) {
		fill(255, 0, 0);
		noStroke();
		circle(x, y, 40); // Apple
		stroke(0, 200, 0);
		line(x, y - 10, x, y - 20); // Apple Stick
	}

	void snakeHead() {
		noStroke();
		fill(0);
		circle(headLocation.x, headLocation.y, 40);
		fill(255);
	}

	void snakeBody(float x, float y) {
		fill(0, 0, 255);
		circle(x, y, 40);
	}

	void infoScreen(String gameStatusText, boolean x) {
		fill(0, 0, 255);
		rect(width * (float) 0.2, height * (float) 0.25, width * (float) 0.6, height * (float) 0.5);
		fill(0);

		if (x == false) {
			textSize(25);
			text(gameStatusText, width * (float) 0.4, height * (float) 0.3);
			text("Game Instructions:", width * (float) 0.25, height * (float) 0.4);
			text("Use LEFT, RIGHT, UP, DOWN to move.", width * (float) 0.25, height * (float) 0.45);
			text("Eat 10 apples to win.", width * (float) 0.25, height * (float) 0.55);
		}
		if (x == true) {
			textSize(60);
			text(gameStatusText, width * (float) 0.36, height * (float) 0.4);
			textSize(30);
			text("Your Score:", width * (float) 0.3, height * (float) 0.55);
			textSize(60);
			text(appleCount, width * (float) 0.55, height * (float) 0.55);

			// Game reset - NOT USED
			appleCount = 0;
			headLocation = new PVector(200, 440);
			headXmove = 40;
			headYmove = 0;
		}

		textSize(30);
		text("Press SHIFT to start.", width * (float) 0.35, height * (float) 0.65);
	}
}
