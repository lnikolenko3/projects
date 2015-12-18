#Liubov Nikolenko
#903059157
#lnikolenko3@gatech.edu
#section A01
#We (I and James Hyde)
#worked on the homework assignment alone,
#using only this semester's course materials.
#This is another code for the acting robot
#In the movie robot was controlled manually,but
#it turned out that we have to make it possible
#for other people to reproduce the movie, so we
#came up with an acting code 2 that replays all the
#movements done by a robot in a movie using manual
#control.
from Myro import *
init()
turnLeft(1,0.1)
turnRight(1,0.1)

forward(1,3)
for t in timer (4):
    forward(1,0.1)
    backward(1,0.1)