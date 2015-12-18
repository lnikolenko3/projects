# Liubov Nikolenko
# CS 1301 Section A01
# GT EMAIL: hyde@gatech.edu
# GT ID: 901778353
# I worked on this HW Assignment only with my partner.

# Robot Setup
from Myro import *
init()
setPicSize("small")

# Seeing Red
def seeingRed():
    p=takePicture()
    for pix in getPixels(p):
        setRed(pix,255)
    savePicture(p,"seeingRed.jpg")
    turnRight(1,.5)

# Negative Picture
def negative():
    p=takePicture()
    for pix in getPixels(p):
        r=getRed(pix)
        g=getGreen(pix)
        b=getBlue(pix)
        R=255-r
        G=255-g
        B=255-b
        setRed(pix,R)
        setGreen(pix,G)
        setBlue(pix,B)
    savePicture(p,"negative.jpg")
    turnRight(1,.5)

# Emboss Picture
def emboss():
    p=takePicture()
    h=getHeight(p)
    w=getWidth(p)
    for x in range(w):
        for y in range(h):
            pix1=getPixel(p,x,y)
            pix2=getPixel(p,x+2,y+2)
            r=getRed(pix1)-getRed(pix2)
            g=getGreen(pix1)-getGreen(pix2)
            b=getBlue(pix1)-getBlue(pix2)
            setRed(pix1,r)
            setGreen(pix1,g)
            setBlue(pix1,b)
    savePicture(p,"emboss.jpg")
    turnRight(1,.5)

# Split Screen Video
def splitScreenVideo(frames):
    frames=int(frames)
    pictureList=[]
    index=1
    if frames<2:
        print("more than one frame needed")
        return None
    else:
        while index<=frames:
            pic1=takePicture()
            turnLeft(.5,.25)
            pic2=takePicture()
            w=getWidth(pic1)/2
            for pix2 in getPixels(pic2):
                x=getX(pix2)
                y=getY(pix2)
                if x>w:
                   setPixel(pic1,x,y,pix2)
            pictureList.append(pic1)
            index=index+1
        savePicture(pictureList,"splitScreenVideo.gif")

# Overlay
def overlay():
    turnRight(1,.5)
    p=takePicture()
    for pix in getPixels(p):
        x=getX(pix)
        y=getY(pix)
        if x>=100 and x<=125:
            if y>=100 and y<=250:
                setRed(pix,255)
                setBlue(pix,0)
                setGreen(pix,0)
        if y>=100 and y<=125:
            if x<=225:
                setRed(pix,255)
                setBlue(pix,0)
                setGreen(pix,0)
    savePicture(p,"overlay.jpg")

# Fade
def fade(frames):
    frames=int(frames)
    dark=255/(frames-1)
    pictureList=[]
    index=1
    if frames<2:
        print("more than one frame needed")
        return None
    else:
        while index<=frames:
            p=takePicture()
            for pix in getPixels(p):
                r=getRed(pix)
                g=getGreen(pix)
                b=getBlue(pix)
                setRed(pix,r-(dark*(index-1)))
                setGreen(pix,g-(dark*(index-1)))
                setBlue(pix,b-(dark*(index-1)))
            pictureList.append(p)
            index=index+1
        savePicture(pictureList,"fade.gif")

# Robot Routine
seeingRed()
negative()
emboss()
overlay()
splitScreenVideo(6)
fade(20)



























