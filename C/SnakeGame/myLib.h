typedef unsigned short u16;

#define REG_DISPCTL *(unsigned short *)0x4000000
#define MODE3 3
#define BG2_ENABLE (1<<10)

#define SCANLINECOUNTER *(volatile unsigned short *)0x4000006

extern unsigned short *videoBuffer;
extern int length;

#define COLOR(r, g, b)  ((r) | (g)<<5 | (b)<<10)
#define RED COLOR(31,0,0)
#define GREEN COLOR(0,31,0)
#define BLUE COLOR(0,0,31)
#define YELLOW COLOR(31,31,0)
#define CYAN COLOR(0, 31, 31)
#define MAGENTA COLOR(31,0,31)
#define WHITE COLOR(31,31,31)
#define BLACK 0
#define GRAY COLOR(31,31,31)
#define OFFSET(r, c, rowlen) ((r)*(rowlen) + (c))
//objects
typedef struct
{
	int row;
	int col;
	int rdel;
	int cdel;
	int size;
	u16 color;
} MOVOBJ;
typedef struct {
	int row;
	int col;
	int isDrawn;
} apple;
extern apple myApple;
enum GBAState {
	START,
	START_NODRAW,
	GAME,
	STATE_FORWARD,
	STATE_DOWN,
	STATE_UP,
	STATE_LEFT,
	GAMEOVER,
	GAMEOVER_NODRAW
};

// Buttons

#define BUTTON_A		(1<<0)
#define BUTTON_B		(1<<1)
#define BUTTON_SELECT	(1<<2)
#define BUTTON_START	(1<<3)
#define BUTTON_RIGHT	(1<<4)
#define BUTTON_LEFT		(1<<5)
#define BUTTON_UP		(1<<6)
#define BUTTON_DOWN		(1<<7)
#define BUTTON_R		(1<<8)
#define BUTTON_L		(1<<9)

#define KEY_DOWN_NOW(key)  (~(BUTTONS) & key)

#define BUTTONS *(volatile unsigned int *)0x4000130

/* DMA */

#define REG_DMA0SAD         *(const volatile u32*)0x40000B0 // source address
#define REG_DMA0DAD         *(volatile u32*)0x40000B4       // destination address
#define REG_DMA0CNT         *(volatile u32*)0x40000B8       // control register

// DMA channel 1 register definitions
#define REG_DMA1SAD         *(const volatile u32*)0x40000BC // source address
#define REG_DMA1DAD         *(volatile u32*)0x40000C0       // destination address
#define REG_DMA1CNT         *(volatile u32*)0x40000C4       // control register

// DMA channel 2 register definitions
#define REG_DMA2SAD         *(const volatile u32*)0x40000C8 // source address
#define REG_DMA2DAD         *(volatile u32*)0x40000CC       // destination address
#define REG_DMA2CNT         *(volatile u32*)0x40000D0       // control register

// DMA channel 3 register definitions
#define REG_DMA3SAD         *(const volatile u32*)0x40000D4 // source address
#define REG_DMA3DAD         *(volatile u32*)0x40000D8       // destination address
#define REG_DMA3CNT         *(volatile u32*)0x40000DC       // control register

typedef struct
{
	const volatile void *src;
	const volatile void *dst;
	unsigned int cnt;
} DMA_CONTROLLER;
typedef struct {
	int row;
	int col;
	int orientation;
} block;
//typedef struct
//{
	//unsigned short length;
	//block arr[length];

	//volatile unsigned short snake_color;
	//int first_row;
	//int first_col;
	//int last_row;
	//int last_col;
//} snake;

#define DMA ((volatile DMA_CONTROLLER *) 0x040000B0)

// Defines
#define DMA_CHANNEL_0 0
#define DMA_CHANNEL_1 1
#define DMA_CHANNEL_2 2
#define DMA_CHANNEL_3 3

#define DMA_DESTINATION_INCREMENT (0 << 21)
#define DMA_DESTINATION_DECREMENT (1 << 21)
#define DMA_DESTINATION_FIXED (2 << 21)
#define DMA_DESTINATION_RESET (3 << 21)

#define DMA_SOURCE_INCREMENT (0 << 23)
#define DMA_SOURCE_DECREMENT (1 << 23)
#define DMA_SOURCE_FIXED (2 << 23)

#define DMA_REPEAT (1 << 25)

#define DMA_16 (0 << 26)
#define DMA_32 (1 << 26)

#define DMA_NOW (0 << 28)
#define DMA_AT_VBLANK (1 << 28)
#define DMA_AT_HBLANK (2 << 28)
#define DMA_AT_REFRESH (3 << 28)

#define DMA_IRQ (1 << 30)
#define DMA_ON (1 << 31)
//constants;
#define INIT_LENGTH 4
#define DIM 8
#define GAP 2
#define BORDER_ROW 10
#define MAX_LENGTH 200

// Prototypes
void generateApple(volatile block* pb);
int ateItself(volatile block *pb);
void upOne(volatile block* pb, volatile unsigned short color);
void downOne(volatile block* pb, volatile unsigned short color);
void forwardOne(volatile block* pb, volatile unsigned short color);
void leftOne(volatile block* pb, volatile unsigned short color);
void drawImage3(int r, int c, int width, int height, const unsigned short* image);
void down(volatile block* pb, volatile unsigned short color);
void up(volatile block* pb, volatile unsigned short color);
void left(volatile block* pb, volatile unsigned short color);
void drawrow(int row, volatile unsigned short color);
void drawSnake(int row, int col, volatile unsigned short color, volatile block* pb);
void setPixel(int r, int c, unsigned short color);
void forward(volatile block* pb, volatile unsigned short color);
void drawRect(int row, int col, int height, int width, unsigned short color);
void delay(int n);
void waitForVblank();
