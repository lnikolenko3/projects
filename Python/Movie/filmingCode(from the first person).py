#Liubov Nikolenko
#903059157
#lnikolenko3@gatech.edu
#section A01
#We (I and James Hyde)
#worked on the homework assignment alone,
#using only this semester's course materials
#This code makes a film from the
#"first person" perspective
from Myro import*
import editingCode

l2=editingCode.splitScreenVideo(25)
l3=editingCode.fade(25)
p1=takePicture()
savePicture(p1, "b4seeingRed.gif")
p2=editingCode.seeingRed()
aList=l2+l3
aList.append(p1)
aList=aList+p2
savePicture(aList, "a.gif")
#This is a performance for a "first person" video
for item in aList:
    show(item, "win1")
    wait(0.2)