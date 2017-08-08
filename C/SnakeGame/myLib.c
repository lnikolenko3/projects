#include "myLib.h"
#include <stdlib.h>
#include "applered.h"


unsigned short *videoBuffer = (unsigned short *)0x6000000;
int length = INIT_LENGTH;
apple myApple;
void setPixel(int r, int c, unsigned short color)
{
	videoBuffer[OFFSET(r, c, 240)] = color;
}

void drawRect(int row, int col, int height, int width, volatile unsigned short color)
{
	for(int r=0; r<height; r++)
	{
		for(int r=0; r<height; r++) {
			DMA[DMA_CHANNEL_3].src = &color;
			DMA[DMA_CHANNEL_3].dst = videoBuffer + 240 * (row + r) + col;
			DMA[DMA_CHANNEL_3].cnt = width | DMA_SOURCE_FIXED |
			DMA_DESTINATION_INCREMENT | DMA_ON;
		}
	}
}
void drawrow(int row, volatile unsigned short color) {
	DMA[DMA_CHANNEL_3].src = &color;
	DMA[DMA_CHANNEL_3].dst = videoBuffer + 240 * row;
	DMA[DMA_CHANNEL_3].cnt = 240 | DMA_SOURCE_FIXED |
	DMA_DESTINATION_INCREMENT | DMA_ON;

}
void drawImage3(int x, int y, int width, int height, const unsigned short* image) {
	for (int i = 0; i < height; i++) {
		DMA[DMA_CHANNEL_3].src = image + width * i;
		DMA[DMA_CHANNEL_3].dst = videoBuffer + 240 * (y+i) + x;
		DMA[DMA_CHANNEL_3].cnt = width | DMA_SOURCE_INCREMENT |
		DMA_DESTINATION_INCREMENT | DMA_ON;
	}

}
void drawSnake(int row, int col, volatile unsigned short color, volatile block *pb) {
	//block arr[length];
	for (int i = 0; i < length; i++) {
		pb[i].row = row;
		pb[i].col = col;
		pb[i].orientation = 0;
		//pb[i].length = length;
		drawRect (row, col, DIM, DIM, color);
		col = col + DIM + GAP;
	}
		//mySnake.first_col = mySnake.first_col + 8 + 2;
}
//}
void forward(volatile block* pb, volatile unsigned short color) {
	volatile unsigned short black = BLACK;
	//block* pb = *ppb;
	int len = length;
	drawRect (pb[0].row, pb[0].col, DIM, DIM, black);
	//pb = pb + 1;
	block new;
	new.row = pb[len - 1].row;
	new.col = pb[len - 1].col + DIM + GAP;
	new.orientation = 0;
	//new.length = len;
	drawRect(pb[len - 1].row, pb[len - 1].col + DIM + GAP, DIM, DIM, color);
	for (int i = 1; i < len; i++) {
		pb[i-1] = pb[i];
	}

	//pb[len - 1] = new;
	// DMA[DMA_CHANNEL_3].src = &(pb[1]);
	// DMA[DMA_CHANNEL_3].dst = &(pb[0]);
	// DMA[DMA_CHANNEL_3].cnt = (len - 1) | DMA_SOURCE_INCREMENT |
	// DMA_DESTINATION_INCREMENT | DMA_ON;
	pb[len - 1] = new;
	//length = length + 1;
	//pb[0] = pb[1];
	//pb[1] = new;
	//pb[0].length
	//block temp = pb[0];
	drawrow(BORDER_ROW, RED);

	//drawRect((pb)[len].row, (pb)[len].col + 8 + 2, 6, 8, color);
	//volatile unsigned short black = BLACK;
	//drawRect(mySnake.last_row)
}
void left(volatile block* pb, volatile unsigned short color) {
	volatile unsigned short black = BLACK;
	//block* pb = *ppb;
	int len = length;
	drawRect (pb[0].row, pb[0].col, DIM, DIM, black);
	//pb = pb + 1;
	block new;
	new.row = pb[len - 1].row;
	new.col = pb[len - 1].col - DIM - GAP;
	new.orientation = 0;
	//new.length = len;
	drawRect(pb[len - 1].row, pb[len - 1].col - DIM - GAP, DIM, DIM, color);
	for (int i = 1; i < len; i++) {
		pb[i-1] = pb[i];
	}
	//DMA[DMA_CHANNEL_3].src = &(pb[1]);
	//DMA[DMA_CHANNEL_3].dst = &(pb[0]);
	//DMA[DMA_CHANNEL_3].cnt = (len - 1) | DMA_SOURCE_INCREMENT |
	//DMA_DESTINATION_INCREMENT | DMA_ON;
	pb[len - 1] = new;
	drawrow(BORDER_ROW, RED);
}
void down(volatile block* pb, volatile unsigned short color) {
	volatile unsigned short black = BLACK;
	int len = length;
	drawRect (pb[0].row, pb[0].col, DIM, DIM, black);
	//pb = pb + 1;
	block new;
	new.row = pb[len - 1].row + DIM + GAP;
	new.col = pb[len - 1].col;
	new.orientation = 1;
	//new.length = len;
	drawRect(pb[len - 1].row + DIM + GAP, pb[len - 1].col, DIM, DIM, color);
	for (int i = 1; i < len; i++) {
		pb[i-1] = pb[i];
	}
	pb[len - 1] = new;
	drawrow(BORDER_ROW, RED);
}
void up(volatile block* pb, volatile unsigned short color) {
	volatile unsigned short black = BLACK;
	//block* pb = *ppb;
	int len = length;
	drawRect (pb[0].row, pb[0].col, DIM, DIM, black);
	//pb = pb + 1;
	block new;
	new.row = pb[len - 1].row - DIM - GAP;
	new.col = pb[len - 1].col;
	new.orientation = 1;
	//new.length = len;
	drawRect(pb[len - 1].row - DIM - GAP, pb[len - 1].col, DIM, DIM, color);
	for (int i = 1; i < len; i++) {
		pb[i-1] = pb[i];
	}
	pb[len - 1] = new;
	drawrow(BORDER_ROW, RED);
}
void forwardOne(volatile block* pb, volatile unsigned short color) {
	//volatile unsigned short black = BLACK;
	//block* pb = *ppb;
	int len = length;
	//drawRect (pb[len - 1].row, pb[len - 1].col + DIM + GAP, DIM + 2, DIM + 2, BLACK);
	drawRect (myApple.row, myApple.col, DIM, DIM, BLACK);
	drawRect(pb[len - 1].row, pb[len - 1].col, DIM, DIM, GREEN);
	//pb = pb + 1;
	block new;
	new.row = pb[len - 1].row;
	new.col = pb[len - 1].col + DIM + GAP;
	new.orientation = 0;
	//new.length = len;
	drawRect(pb[len - 1].row, pb[len - 1].col + DIM + GAP, DIM, DIM, color);
	//for (int i = 1; i < len; i++) {
		//pb[i-1] = pb[i];
	//}

	pb[len] = new;
	length = length + 1;
	myApple.isDrawn = 0;
	drawrow(BORDER_ROW, RED);
}
void upOne(volatile block* pb, volatile unsigned short color) {
	//volatile unsigned short black = BLACK;
	//block* pb = *ppb;
	int len = length;
	drawRect(myApple.row, myApple.col, DIM, DIM, BLACK);
	drawRect(pb[len - 1].row, pb[len - 1].col, DIM, DIM, GREEN);
	//drawRect (pb[len - 1].row - DIM - GAP, pb[len - 1].col, DIM + GAP, DIM + GAP, BLACK);
	//pb = pb + 1;
	block new;
	new.row = pb[len - 1].row - DIM - GAP;
	new.col = pb[len - 1].col;
	new.orientation = 1;
	//new.length = len;
	drawRect(pb[len - 1].row - DIM - GAP, pb[len - 1].col, DIM, DIM, color);
	//for (int i = 1; i < len; i++) {
	//	pb[i-1] = pb[i];
	//}
	pb[len] = new;
	length = length + 1;
	myApple.isDrawn = 0;
	drawrow(BORDER_ROW, RED);
}
void downOne(volatile block* pb, volatile unsigned short color) {
	//volatile unsigned short black = BLACK;
	int len = length;
	drawRect(myApple.row, myApple.col, DIM, DIM, BLACK);
	drawRect(pb[len - 1].row, pb[len - 1].col, DIM, DIM, GREEN);
	//drawRect (pb[len - 1].row + DIM + GAP, pb[len - 1].col, DIM + GAP, DIM + GAP, BLACK);

	//pb = pb + 1;
	block new;
	new.row = pb[len - 1].row + DIM + GAP;
	new.col = pb[len - 1].col;
	new.orientation = 1;
	//new.length = len;
	drawRect(pb[len - 1].row + DIM + GAP, pb[len - 1].col, DIM, DIM, color);
	//for (int i = 1; i < len; i++) {
	//	pb[i-1] = pb[i];
	//}
	pb[len] = new;
	length = length + 1;
	myApple.isDrawn = 0;
	drawrow(BORDER_ROW, RED);
}
void leftOne(volatile block* pb, volatile unsigned short color) {
	//volatile unsigned short black = BLACK;
	//block* pb = *ppb;
	int len = length;
	drawRect(myApple.row, myApple.col, DIM, DIM, BLACK);
	drawRect(pb[len - 1].row, pb[len - 1].col, DIM, DIM, GREEN);
	//drawRect (pb[len - 1].row, pb[len - 1].col - DIM - GAP, DIM, DIM, BLACK);
	//pb = pb + 1;

	block new;
	new.row = pb[len - 1].row;
	new.col = pb[len - 1].col - DIM - GAP;
	new.orientation = 0;
	//new.length = len;
	drawRect(pb[len - 1].row, pb[len - 1].col - DIM - GAP, DIM, DIM, color);
	//for (int i = 1; i < len; i++) {
	//	pb[i-1] = pb[i];
	//}
	//DMA[DMA_CHANNEL_3].src = &(pb[1]);
	//DMA[DMA_CHANNEL_3].dst = &(pb[0]);
	//DMA[DMA_CHANNEL_3].cnt = (len - 1) | DMA_SOURCE_INCREMENT |
	//DMA_DESTINATION_INCREMENT | DMA_ON;
	myApple.isDrawn = 0;
	pb[len] = new;
	length = length + 1;
	drawrow(BORDER_ROW, RED);
}
void generateApple(volatile block* pb) {

	//int m = rand()%5;
	myApple.isDrawn = 1;
	if (myApple.isDrawn) {
		//int m = rand()%5;
		int m = 1;
		if (m) {
		  int col = 2 + rand()%228;
		  int row = 15 + rand()%135;
		  int p = 0;
		  while (!p) {
			 int good = 1;
			 for (int i = 0; i < length && good; i++) {
				 int rdel = abs(row - pb[i].row);
				 int cdel = abs(col - pb[i].col);
				 if ((rdel <= 10) && (cdel <= 10)) {
					 //drawRect(70, 70, 8, 8, BLUE);
					 good = 0;
				 }

		  	}
			if (!good) {
				col = 2 + rand()%228;
	  		  	row = 15 + rand()%135;
			} else {
				p = 1;
			}

		  }
		  myApple.row = row;
		  myApple.col = col;
		  drawImage3(myApple.col, myApple.row, APPLERED_WIDTH, APPLERED_HEIGHT, applered_data);

		}
	}
}
int ateItself(volatile block *pb) {
	int x = 0; // 0 - did not eat
	volatile block head = pb[length - 1];
	for (int i = 0; i < length-1 && !x; i++) {
		if ((pb[i].row == head.row) && (pb[i].col == head.col)) {
			x = 1;
			return x;
		}
	}
	return x;
}
void delay(int n)
{
	volatile int x = 0;
	for(int i=0; i<n*10000; i++)
	{
		x++;
	}
}

void waitForVblank()
{
	while(SCANLINECOUNTER > 160);
	while(SCANLINECOUNTER < 160);
}
