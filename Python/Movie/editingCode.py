#Liubov Nikolenko
#903059157
#lnikolenko3@gatech.edu
#section A01
#"We with James Hyde
#worked on the homework assignment alone, using
#only this semester's course materials
#This is the code that contains special effects.
#In the movie we are using such effects as:
#seeing red, split-screen (video)
#and fading to bkack
from Myro import *
init()
setPicSize("small")
def seizure():
    for t in timer(3):
        forward(1,.2)
        backward(1,.2)

# Seeing Red, Worth 15 points
def seeingRed():
    pictureList0=[]
    p=takePicture()
    for pix in getPixels(p):
        setRed(pix,240)
    pictureList0.append(p)
    savePicture(p,"seeingRed.jpg")
    return pictureList0
    #savePicture(p,"seeingRed.jpg")
    #turnRight(1,.5)


def splitScreenVideo(frames):
    frames=int(frames)
    pictureList1=[]
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
            pictureList1.append(pic1)
            index=index+1
        savePicture(pictureList1,"splitScreenVideo.gif")
        return pictureList1
        #savePicture(pictureList,"splitScreenVideo.gif")


def fade(frames):
    frames=int(frames)
    dark=255/(frames-1)
    pictureList2=[]
    index=1
    if frames<2:
        print("more than one frame needed")
        return None
    else:
        while index<=frames:
            p=takePicture()
            turnLeft(.3, .25)
            for pix in getPixels(p):
                r=getRed(pix)
                g=getGreen(pix)
                b=getBlue(pix)
                setRed(pix,r-(dark*(index-1)))
                setGreen(pix,g-(dark*(index-1)))
                setBlue(pix,b-(dark*(index-1)))
            pictureList2.append(p)
            index=index+1
        p=takePicture()
        for pix in getPixels(p):
            setRGB(pix, (0,0,0))
        pictureList2.append(p)
        savePicture(pictureList2,"fade.gif")
        return pictureList2
        #savePicture(pictureList,"fade.gif")



seeingRed()
p111=takePicture()
savePicture(p111, "aaa.gif")
