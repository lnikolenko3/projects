#Liubov Nikolenko
#903059157
#lnikolenko3@gatech.edu
#section A01
#We (I and James Hyde)
#worked on the homework assignment alone,
#using only this semester's course materials

#Filming part (from the third person perspective)
from Myro import *
import editingCode
aList=[]
for t in timer(60):
    p=takePicture()
    aList.append(p)
savePicture(aList, "b.gif")

#Performance for a video,
#made from a third person's view
for item in aList:
    show(item, "win1")
    wait(0.2)