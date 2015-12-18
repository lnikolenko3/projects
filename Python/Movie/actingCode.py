#Liubov Nikolenko
#903059157
#lnikolenko3@gatech.edu
#section A01
#We (I and James Hyde)
#worked on the homework assignment alone,
#using only this semester's course materials.
#This a code for an "acting robot". The robot is
#controlled manually.
from Myro import *
from Graphics import *
init()
win=Window("Enter Robot Commands",500,500)
def f():
    f=open("myMovements.txt","a")
    forward(1,.1)
    y=getLight("left")
    x=getLight("right")
    z=getObstacle("right")
    SensorValue=(y/(x+z))
    f.write("forward  .1  {}".format(SensorValue))
    f.write("\n")
    f.close()
def b():
    f=open("myMovements.txt","a")
    backward(1,.1)
    y=getLight("left")
    x=getLight("right")
    z=getObstacle("right")
    SensorValue=(y/(x+z))
    f.write("backward .1  {}".format(SensorValue))
    f.write("\n")
    f.close()
def l():
    f=open("myMovements.txt","a")
    turnLeft(1,.1)
    y=getLight("left")
    x=getLight("right")
    z=getObstacle("right")
    SensorValue=(y/(x+z))
    f.write("left     .1  {}".format(SensorValue))
    f.write("\n")
    f.close()
def r():
    f=open("myMovements.txt","a")
    turnRight(1,.1)
    y=getLight("left")
    x=getLight("right")
    z=getObstacle("right")
    SensorValue=(y/(x+z))
    f.write("right    .1  {}".format(SensorValue))
    f.write("\n")
    f.close()
def s():
    f=open("myMovements.txt","a")
    beep(.1,1500)
    f.write("beep     .1")
    f.write("\n")
    f.close()
def keyControl(win,event):
    if event.key=="Up":
        f()
    if event.key=="Down":
        b()
    if event.key=="Right":
        r()
    if event.key=="Left":
        l()
    if event.key=="b":
        s()
onKeyPress(keyControl)
