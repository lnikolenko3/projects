#include "myLib.h"
#include <stdlib.h>
#include "snake_title.h"
#include "gameover.h"
#include "text.h"
#include "applered.h"
#include <stdio.h>

#define NUMOBJS 7



int main()
{
	//extern *length = INIT_LENGTH;
	//length = length + 1;
	REG_DISPCTL = MODE3 | BG2_ENABLE;
	enum GBAState state = START_NODRAW;
	//int row = 50;
	//int col = 60;
	int lives = 3;
	int score = 0;
	volatile unsigned short bgcolor = BLACK;
	unsigned short snake_color = GREEN;
	//block p
	//block p;
	//p.row = 0;
	//p.col = 0;
	//p.orientation = 0;
	//static apple myApple;
	//volatile int func = 0;
	//p.length = INIT_LENGTH;
	//volatile block *pb = &p;
	block pb[MAX_LENGTH];
	int was_released = 1; //the button was released
	int was_released_select = 1;
	//volatile int is_title_drawn = 1;
	myApple.row = 15;
	myApple.col = 60;
	int erased = 0;
	volatile unsigned short border = RED;
	//drawImage3(50, 100, APPLE_WIDTH, APPLE_HEIGHT, apple_data);
	//while(1);
	DMA[3].src = snake_title_data;
	DMA[3].dst = videoBuffer;
	DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_INCREMENT; //size 38400
	drawString(5, 120, "Press A to continue", BLACK);
	char str[80];
	char str1[80];
	//while(1);
	//char message[] = "Hello, World!";
	//drawString(0, 0, message, RED);
	//char message[] = "LIVES: 3";
	//char message1[] = "SCORE: 0";
	int gameover = 0;
	while(1)  // Game Loop
	{
		//waitForVblank();
		//delay(4);
		if (length > MAX_LENGTH && !gameover) {
			state = GAMEOVER;
		}
		if (KEY_DOWN_NOW(BUTTON_SELECT) && was_released) {
			if (!state == START_NODRAW) {
			state = START;
			was_released_select = 0;
			erased = 1;
			}

		}
		switch (state)
		{
			case START:
				gameover = 0;
				lives = 3;
				score = 0;
				waitForVblank();
				DMA[3].src = snake_title_data;
				DMA[3].dst = videoBuffer;
				DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_INCREMENT;
				drawString(5, 120, "Press A to continue", BLACK);
				erased = 0;
				state = START_NODRAW;
				length = INIT_LENGTH;
				break;
			case START_NODRAW:
				state = START_NODRAW;
				if (KEY_DOWN_NOW(BUTTON_A) && was_released) {
				state = GAME;
				was_released = 0;
				erased = 1;
				}
				break;
			case GAME:
				if (erased) {
					erased = 0;
					state = STATE_FORWARD;

					//lives = 3;
  					sprintf(str, "LIVES: %d", lives);
					sprintf(str1, "SCORE: %d", score);
					//char message[] = "LIVES: 3";
					//char message1[] = "SCORE: 0";
					DMA[3].src = &bgcolor;
					DMA[3].dst = videoBuffer;
					DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_FIXED;
					drawString(0, 0, str, WHITE);
					drawString(0, 60, str1, WHITE);
					waitForVblank();
					drawImage3(myApple.col, myApple.row, APPLERED_WIDTH, APPLERED_HEIGHT, applered_data);
					//drawImage3(50, 100, APPLERED_WIDTH, APPLERED_HEIGHT, applered_data);
					drawrow(BORDER_ROW, border);
					drawSnake(50, 50, snake_color, pb);
					drawRect(myApple.row, myApple.col, DIM, DIM, BLACK);
					generateApple(pb);
					//char message1[] = "SCORE: 0";
					//drawString(0, 60, message1, WHITE);

					//forward(pb, snake_color);
					//func = 0;
				}

				if (KEY_DOWN_NOW(BUTTON_SELECT) && was_released) {
					state = START;
					was_released_select = 0;
					erased = 1;
					break;
				}
				//delay(4);
				break;
			case STATE_FORWARD:
				//forward(pb, snake_color);
				if (KEY_DOWN_NOW(BUTTON_DOWN)) {
					state = STATE_DOWN;
					break;
				}
				if (KEY_DOWN_NOW(BUTTON_UP)) {
					state = STATE_UP;
					break;
				}
				//if ((pb[length-1].row == 100) &&(pb[length-1].col == 50)) {
				//	forwardOne(pb, snake_color);
				//}
				if (KEY_DOWN_NOW(BUTTON_SELECT) && was_released) {
					state = START;
					was_released_select = 0;
					erased = 1;
					break;
				}
				if (ateItself(pb)) {
					lives = lives - 1;
					if (lives) {
						length = INIT_LENGTH;
						erased = 1;
						state = GAME;
						break;
					}
					//score = 0;
					state = GAMEOVER;
					break;
				}
				//check for collosions with horizontal walls
				volatile block* pb_head = pb + length - 1;

				//int r = pb_head->row;
				int c = pb_head->col;
				if (c + DIM + GAP >= 240) {
					lives = lives - 1;
					if (lives) {
						length = INIT_LENGTH;
						erased = 1;
						state = GAME;
						break;
					}
					//score = 0;
					state = GAMEOVER;
				} else {
					waitForVblank();
					if ((abs(pb[length-1].row - myApple.row)<=9) && (abs(pb[length-1].col-myApple.col)<=9)) {
						forwardOne(pb, snake_color);
						drawString(0, 60, str1, BLACK);
						score = score + 5;
						sprintf(str1, "SCORE: %d", score);
						drawString(0, 60, str1, WHITE);
						//myApple.row = 0;
						//myApple.col = 0;
						generateApple(pb);
						//drawRect(50, 50, 8, 8, CYAN);
						//drawRect(100, 50, 8, 8, BLACK);
						//drawRect(pb[length-1].row, pb[length-1].col,8, 8, GREEN);
					} else {
						forward(pb, snake_color);
					}
					//drawrow(BORDER_ROW, border);
					//drawrow(BORDER_ROW, border);
					//forward(pb, snake_color);
					delay(4);
				}


				break;
			case STATE_DOWN:
				//down(pb, snake_color);
				if (KEY_DOWN_NOW(BUTTON_RIGHT)) {
					state = STATE_FORWARD;
					break;
				}
				if (KEY_DOWN_NOW(BUTTON_LEFT)) {
					state = STATE_LEFT;
					break;
				}
				if (KEY_DOWN_NOW(BUTTON_SELECT) && was_released) {
					state = START;
					was_released_select = 0;
					erased = 1;
					break;
				}
				if (ateItself(pb)) {
					lives = lives - 1;
					if (lives) {
						length = INIT_LENGTH;
						erased = 1;
						state = GAME;
						break;
					}
					//score = 0;
					state = GAMEOVER;
					break;
				}
				//check for collosions with horizontal walls
				//volatile block* pb_head = pb + length - 1;
				//int r = pb_head->row;
				int r = pb[length-1].row;
				if (r + DIM + GAP >= 159) {
					lives = lives - 1;
					if (lives) {
						length = INIT_LENGTH;
						erased = 1;
						state = GAME;
						break;
					}
					//score = 0;
					state = GAMEOVER;
				} else {
					waitForVblank();
					if ((abs(pb[length-1].row - myApple.row)<=9) && (abs(pb[length-1].col-myApple.col)<=9)) {
						downOne(pb, snake_color);
						//myApple.row = 0;
						//myApple.col = 0;
						drawString(0, 60, str1, BLACK);
						score = score + 5;
						sprintf(str1, "SCORE: %d", score);
						drawString(0, 60, str1, WHITE);
						generateApple(pb);
						//drawRect(50, 50, 8, 8, RED);
						//drawRect(50, 50, 8, 8, GREEN);
					} else {
						down(pb, snake_color);

					}
					//down(pb, snake_color);
					//drawrow(BORDER_ROW, border);
					delay(4);
				}

				break;
			case STATE_LEFT:
				//left(pb, snake_color);
				if (KEY_DOWN_NOW(BUTTON_UP)) {
					state = STATE_UP;
					break;
				}
				if (KEY_DOWN_NOW(BUTTON_DOWN)) {
					state = STATE_DOWN;
					break;
				}
				if (KEY_DOWN_NOW(BUTTON_SELECT) && was_released) {
					state = START;
					was_released_select = 0;
					erased = 1;
					break;
				}
				//waitForVblank();
				//left(pb, snake_color);
				//delay(3);
				//int c = pb[length-1].col;
				//pb[length-1].col - DIM - GAP <= 0
				if (ateItself(pb)) {
					lives = lives - 1;
					if (lives) {
						length = INIT_LENGTH;
						erased = 1;
						state = GAME;
						break;
					}
					//score = 0;
					state = GAMEOVER;
					break;
				}
				if (pb[length-1].col - DIM <= 0) {
					lives = lives - 1;
					if (lives) {
						length = INIT_LENGTH;
						erased = 1;
						state = GAME;
						break;
					}
					//score = 0;
					state = GAMEOVER;
				} else {
					waitForVblank();
					if ((abs(pb[length-1].row - myApple.row)<=9) && (abs(pb[length-1].col-myApple.col)<=9)) {
						leftOne(pb, snake_color);
						drawString(0, 60, str1, BLACK);
						score = score + 5;
						sprintf(str1, "SCORE: %d", score);
						drawString(0, 60, str1, WHITE);
						//myApple.row = 0;
						//myApple.col = 0;
						generateApple(pb);
						//drawRect(50, 50, 8, 8, GREEN);
					} else {
						left(pb, snake_color);
					}
					//drawrow(BORDER_ROW, border);

					//drawrow(BORDER_ROW, border);
					//left(pb, snake_color);
					delay(4);
				}
				break;
				case STATE_UP:
					//up(pb, snake_color);
					if (KEY_DOWN_NOW(BUTTON_RIGHT)) {
						state = STATE_FORWARD;
						break;
					}
					if (KEY_DOWN_NOW(BUTTON_LEFT)) {
						state = STATE_LEFT;
						break;
					}
					if (KEY_DOWN_NOW(BUTTON_SELECT) && was_released) {
						state = START;
						was_released_select = 0;
						erased = 1;
						break;
					}
					//waitForVblank();
					//up(pb, snake_color);
					//delay(4);
					//int r = pb[length-1].row;
					if (ateItself(pb)) {
						lives = lives - 1;
						if (lives) {
							length = INIT_LENGTH;
							erased = 1;
							state = GAME;
							break;
						}
						//score = 0;
						state = GAMEOVER;
						break;
					}
					if (pb[length-1].row - DIM <= BORDER_ROW) {
						lives = lives - 1;
						if (lives) {
							length = INIT_LENGTH;
							erased = 1;
							state = GAME;
							break;
						}
						//score = 0;
						state = GAMEOVER;
					} else {
						waitForVblank();
						if ((abs(pb[length-1].row - myApple.row)<=9) && (abs(pb[length-1].col-myApple.col)<=9)) {
							upOne(pb, snake_color);
							drawString(0, 60, str1, BLACK);
							score = score + 5;
							sprintf(str1, "SCORE: %d", score);
							drawString(0, 60, str1, WHITE);
							//myApple.row = 0;
							//myApple.col = 0;
							generateApple(pb);
							//drawRect(50, 50, 8, 8, MAGENTA);
						} else {
							up(pb, snake_color);
						}
						//up(pb, snake_color);
						//drawrow(BORDER_ROW, border);
						delay(4);
					}
					break;
				case GAMEOVER:
					gameover = 1;
					waitForVblank();
					if (length > MAX_LENGTH) {
						DMA[3].src = &bgcolor;
						DMA[3].dst = videoBuffer;
						DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_FIXED
						| DMA_DESTINATION_INCREMENT;
						drawString(60, 100, "You won!", RED);

					} else {
						DMA[3].src = gameover_data;
						DMA[3].dst = videoBuffer;
						DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_INCREMENT;
						drawString(0, 60, str1, BLACK);
						//score = 0;
						sprintf(str1, "SCORE: %d", score);
						drawString(0, 0, str1, WHITE);
						drawString(10, 0, "Press Select to restart", WHITE);

					}

					score = 0;
					lives = 3;
					//drawString(0, 0, "SCORE:", WHITE);
					state = GAMEOVER_NODRAW;
					break;
				case GAMEOVER_NODRAW:
					//state = START;
					if (KEY_DOWN_NOW(BUTTON_SELECT) && was_released_select) {
						state = START;
						was_released_select = 0;
						erased = 1;
				}
				break;



		}
			if (KEY_DOWN_NOW(BUTTON_A)) {
				was_released = 0;
			} else {
				was_released = 1;
			}
			if (KEY_DOWN_NOW(BUTTON_SELECT)) {
				was_released_select = 0;
			} else {
				was_released_select = 1;
			}



	}
}
