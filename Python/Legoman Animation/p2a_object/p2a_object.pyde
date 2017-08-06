

# Animation Example
#Name: Liubov Nikolenko
#I am replicating orange road sign, using instansing
time = 0   # use time to move objects from one frame to the next
tex = None
h = 0
first = True
first1 = True

count2 = 0
translate_array2 = []
rotate_array2 = []
prev = 0
bg = None


def setup():
    global bg
    size (1300, 1300, P3D)
    perspective (60 * PI / 180, 1, 0.1, 1000)  # 60 degree field of view
def draw():
    global time
    global h
    global first
    global prev
    global count2
    global rotate_array2
    global translate_array2

    if (time >= 3.97):
        time = 0
        h = 0
        first = True
        count2 = 0
        prev = 0
        translate_array2 = []
        rotate_array2 = []
        fist1 = True
    else:
        time += 0.01
    
    camera (5*time, 0, 100, 5*time, 0, 0, 0,  1, 0)  # position the virtual camera

    background (67, 211, 240)  # clear screen and set background to white
    noStroke();
    
    # create a directional light source
    ambientLight(50, 50, 50);
    lightSpecular(255, 255, 255)
    #pointLight(255, 255, 255, 0, 40, 36);
    pointLight(255, 255, 255, 20, -40, 40);
    #pointLight(255, 255, 255, -10, -10, 10);
    #pointLight(255, 255, 255, -20, 40, 30);
    directionalLight (255, 255, 255, 0, 10, 35)
    #directionalLight (255, 255, 255, 40 - 25 * time, -5, 10)
    #lightSpecular(255, 255, 255)
    #specular(200, 200, 200)
    
    noStroke()
    specular (180, 180, 180)
    #specular(255, 255, 155)
    shininess (15.0)

    pushMatrix()
    rotateY(-PI/2)
    #rotateX(time)
    fill(255, 255, 0)
    translate(0, 0, 40 -25 * time)
    if (time > 0.5 and (-30 * sin(1.5*h) < 0  or first)):
        first = False
        translate(0, -30 * sin(1.5*h), 0)
        h += 0.01
        
        
    
    scale(0.5, 0.5, 0.5)
    #rotateX(time)
    draw_figure()
    popMatrix()
    #-----------------------------
    pushMatrix()
    translate(0, 10, 35)
    scale(0.3, 0.3, 0.3)
    sign()
    translate(-20, 0, 0)
    sign()
    translate(40, 0, 0)
    sign()
    popMatrix()
    pushMatrix()
    fill(200)
    translate(0, 26, 0)
    box(350, 1, 350)
    noLights()
    fill(255, 255, 255)
    pushMatrix()
    translate(-100, -1, 30)
    for i in range(10):
        translate(25, 0, 0)
        box(20, 0.05, 3)
    #translate(6, -1, 30)
    #box(20, 0.05, 3)
    popMatrix()
    pushMatrix()
    translate(-100, -1, -30)
    for i in range(10):
        translate(25, 0, 0)
        box(20, 0.05, 3)
    #translate(6, -1, 30)
    #box(20, 0.05, 3)
    popMatrix()
    lights()
    popMatrix()
    pushMatrix()
    translate(-100, -8.1, -2)
    for i in range(10):
        translate(27, 0, 0)
        fill(160, 82, 45)
        box(5, 35, 0.05)
        pushMatrix()
        translate(0, -27, 1)
        fill(0, 255, 0)
        ellipse(0, 0, 35, 35)
        popMatrix()
    popMatrix()
    pushMatrix()
    translate(0, 15, -100)
    fill(0, 200, 0)
    box(350, 0.05, 150)
    popMatrix()

    
    
def draw_figure():
    global translate_array2
    global rotate_array2
    global count2
    global prev
    #global translate_array3
    #global rotate_array3
    #global count3
    global prev1
    global first1
    
    #draw body of the man
    pushMatrix()
    translate(2.5, 44, 0)
    rotateY(PI/2)
    scale(1.5, 1.5, 1.5)
    skateboard()
    popMatrix()
    fill (0, 255, 0)
    pushMatrix()
    ##################
    if (time < 3 or sin(time) > 0):
        rotateZ(0.3 * sin(time))
        translate(5 * sin(time),  0, 0)
    ######################
    translate (0, 15, 0)  # move up and down
    scale(-0.7, -0.7, 0.7)
    drawBody()
    popMatrix()
    
    #drawing the lower body
    #leg 1
    drawLeg()
    #leg 2
    pushMatrix()
    translate(0, 0, -12)
    drawLeg()
    popMatrix()
    #middle part
    pushMatrix()
    translate(0, 20, 0)
    scale(4.7, 4.7, 4.7)
    cylinder()
    popMatrix()
    
    #draw shoulders
    fill(255, 0, 255)
    pushMatrix()
    #######################
    if (time < 3 or sin(time) > 0):
        rotateZ(0.3 * sin(time))
        translate(5 * sin(time),  0, 0)
    #####################
    translate(0, -3, 0)
    pushMatrix()
    translate(0, 0, 9.5)
    sphereDetail(60)  # this controls how many polygons are used to make a sphere
    scale(1, -1, -1)
    sphere(4)
    popMatrix()
    pushMatrix()
    translate(0, 0, -9.5)
    sphere(4)
    popMatrix()
    popMatrix()


    #drawing neck and head
    fill(255, 255, 0)
    pushMatrix()
    ###########################
    if (time < 3 or sin(time) > 0):
        rotateZ(0.3 * sin(time))
        translate(5 * sin(time),  0, 0)
    #############################
    translate(0, -9.5, 0)
    drawNeck()
    translate(0, -8, 0)
    drawHead()
    popMatrix()
    #################
    
    #drawing eyes
    noLights() #done intentionally for a "flat" effect
    fill(0, 0, 0)
    pushMatrix()
    ###########################
    if (time < 3 or sin(time) > 0):
        rotateZ(0.3 * sin(time))
        translate(5 * sin(time),  0, 0)
    #############################
    pushMatrix()
    translate(6, -18.5, 2.5)
    rotateY(-PI/2)
    scale(1, 1, 1)
    sphere(1.2)
    #ellipse(0, 0, 1, 1)
    #ellipse(0, 0, 1, 1)
    popMatrix()
    pushMatrix()
    translate(6, -18.5, -2.5)
    rotateY(-PI/2)
    scale(1, 1, 1)
    sphere(1.2)
    #ellipse(0, 0, 1, 1)
    #ellipse(0, 0, 1, 1)
    popMatrix()
    lights()

    noFill()
    stroke(0)
    strokeWeight(3)
    pushMatrix()
    translate(7.32, -17.5, 0)
    if (not(time >=0.5 and (-30 * sin(1.5*h) < 0 or first1))):
        rotateX(PI/6)
        #first1 = False
    rotateY(-PI/2) 
    if (time >=0.5 and (-30 * sin(1.5*h) < 0 or first1)):
        ellipse(0, 3, 4, 2)
        first1 = False
    else:
        arc(0, 0, 7, 7, PI/2, PI/1.2)
    popMatrix()
    popMatrix()
    noStroke()
    #######

    #hands
    pushMatrix()
    if (time < 3 or sin(time) > 0):
        rotateZ(0.3 * sin(time))
        translate(5 * sin(time),  0, 0)
    pushMatrix()
    translate(0, 4, 14)
    #####################

    if (time < 0.9):
        k = 8*sin(time)
        if (k > 7.3 or k < 0.2):
            k = prev
        else:
            prev = k
        translate(0, -6*sin(1.1*time), k)
        rotateX(PI/2 + PI/6.4 + time)
        translate_array2.append((-6*sin(1.1*time), k))
        rotate_array2.append(PI/2 + PI/6.4 + time)
        
    elif (count2 < len(translate_array2)):
        i = len(translate_array2) - count2 - 1
        translate(0, translate_array2[i][0], translate_array2[i][1])
        rotateX(rotate_array2[i])
        count2 += 1
    else:
        rotateX(PI/2 + PI/6.4)
    rotateY(PI)
    ########################
    draw_full_arm1()
    popMatrix()
    translate(0, 4, -14)
    #rotateX(-PI/2 - PI/6.4)
    if (time < 0.9):
        translate(0, translate_array2[- 1][0], -translate_array2[- 1][1])
        rotateX(-rotate_array2[-1])
        
    elif (count2 < len(translate_array2)):
        i = len(translate_array2) - count2 - 1
        translate(0, translate_array2[i][0], -translate_array2[i][1])
        rotateX(-rotate_array2[i])
    else:
        rotateX(-PI/2 - PI/6.4)
    draw_full_arm1()
    popMatrix()
    
def arm1():
    global count
    global translate_array
    global rotate_array
    fill(255, 0, 255)
    pushMatrix()
    rotateX(PI/2 + PI/6.4)
    rotateY(PI)
    scale(3, 3, 8.8)
    cylinder()
    popMatrix()

def draw_full_arm1():
    fill(255, 0, 255)
    pushMatrix()
    #rotateX(PI/2 + PI/6.4)
    #rotateY(PI)
    pushMatrix()
    scale(3, 3, 8.8)
    cylinder()
    popMatrix()
    ##########################
    fill(255, 255, 0)
    pushMatrix()
    translate(0, 0, 9)
    scale(1.8, 1.8, 2)
    cylinder()
    popMatrix()
    pushMatrix()
    translate(0, 0, 14)
    rotateX(-PI/2)
    rotateY(-PI/2)    
    scale(3, 4, 3)
    drawHand()
    popMatrix()
    popMatrix()
    
    
def drawLeg():
    #draw full leg1 of the man
    #thigh 1
    fill(0, 0, 255)
    pushMatrix()
    translate(0, 20, 6)
    pushMatrix()
    scale(5, 5.2, 5.5)
    cylinder()
    popMatrix()
    
    #leg1 (main part)
    pushMatrix()
    translate(0, 12.8, 0)
    scale(1.75, 4, 2.1)
    box(5)
    popMatrix()
    
    #foot 1
    pushMatrix()
    translate(5, 20, 0)
    scale(1.6, 1.09, 2.1)
    box(5)
    popMatrix()
    
    popMatrix()
        

def drawNeck():
    pushMatrix()
    rotateX(PI/2)
    scale(3.55, 3.55, 1.7)
    cylinder() #neck
    popMatrix()

def drawHead():
    pushMatrix()
    rotateX(PI/2)
    scale(7.4, 7.4, 5.5) #7.4
    cylinder()
    popMatrix()
    pushMatrix()
    fill(255, 255, 0)
    translate(0, -5, 0)
    scale(1, 0.25, 1)
    sphere(7.4)
    popMatrix()
    pushMatrix()
    translate(0, 5, 0)
    scale(1, 0.25, 1)
    sphere(7.4)
    popMatrix()
    pushMatrix()
    translate(0, -7, 0)
    rotateX(PI/2)
    scale(4, 4, 1.7)
    cylinder() #top part
    popMatrix()
    
# cylinder with radius = 1, z range in [-1,1]
def cylinder(sides = 64):
    # first endcap
    
    beginShape()
    for i in range(sides):
        theta = i * 2 * PI / sides
        x = cos(theta)
        y = sin(theta)
        vertex ( x,  y, -1)
    endShape(CLOSE)
    # second endcap
    beginShape()
    for i in range(sides):
        theta = i * 2 * PI / sides
        x = cos(theta)
        y = sin(theta)
        vertex ( x,  y, 1)
    endShape(CLOSE)
    # sides
    x1 = 1
    y1 = 0
    for i in range(sides):
        theta = (i + 1) * 2 * PI / sides
        x2 = cos(theta)
        y2 = sin(theta)
        beginShape()
        normal (x1, y1, 0)
        vertex (x1, y1, 1)
        vertex (x1, y1, -1)
        normal (x2, y2, 0)
        vertex (x2, y2, -1)
        vertex (x2, y2, 1)
        endShape(CLOSE)
        x1 = x2
        y1 = y2
        
 

 

  
def cylinder1(sides = 20):
    beginShape(TRIANGLE_STRIP)
    for j in range(1):
        if (j == 0):
            z = 0
        else:
            z = 0
        for i in range(sides):
            theta = i * 2 * PI / sides
            x = cos(theta)
            y = sin(theta)
            if (y > - 0.3): #regulates the "height of the arc" 
                vertex ( x,  y, z)
                vertex ( 0.7 * x,  0.7* y, z)
    endShape(CLOSE)
    
def drawHand():
    bound = 0.65
    i = -bound
    inc = 0.01
    while(i <= bound):
        pushMatrix()
        translate(0, 0, i)
        cylinder1()
        popMatrix()
        i += inc
        
def drawBody():
    bound = 3.5
    i = 0
    inc = 0.05
    count = 0
    fill(0, 0, 255)
    box(14, 4, 34)
    while(i <= bound):
        #fill(0, 255, 0)
        pushMatrix()
        translate(0, 0.5 * count, 0)
        box(15 - i* 1.37, 0.5, 35 - i* 1.37)
        popMatrix() 
        i += inc  
        count += 1
        if (count % 2 == 0):
            fill(0, 255, 0)
        else:
            fill(255, 255, 0)
            
def sign():
    fill(255, 128, 0)
    cone(1, 8, 20)
    pushMatrix()
    translate(0, 21, 0)
    scale(3.8, 0.3, 3.8)
    box(5)
    popMatrix()
    
def skateboard():
    pushMatrix()
    scale(5.5, 0.3, 2)
    box(5)
    popMatrix()
    pushMatrix()
    translate(11, 1, 0)
    rotateX(PI/2)
    scale(1, 1, 0.8)
    cylinder()
    popMatrix()
    pushMatrix()
    translate(-11, 1, 0)
    rotateX(PI/2)
    scale(1, 1, 0.8)
    cylinder()
    popMatrix()
    pushMatrix()
    translate(-11, 2.5, 0)
    scale(0.8, 0.8, 4)
    cylinder()
    popMatrix()
    
    pushMatrix()
    translate(-11, 2.5, 0)
    scale(0.8, 0.8, 5)
    cylinder()
    popMatrix()
    
    pushMatrix()
    translate(11, 2.5, 0)
    scale(0.8, 0.8, 5)
    cylinder()
    popMatrix()
    
    fill(255, 0, 0)
    pushMatrix()
    translate(-11, 2.5, 4)
    rotateZ(time)
    scale(1.7, 1.7, 0.5)
    cylinder()
    popMatrix()
    pushMatrix()
    translate(-11, 2.5, -4)
    rotateZ(time)
    scale(1.7, 1.7, 0.5)
    cylinder()
    popMatrix()
    
    pushMatrix()
    translate(11, 2.5, 4)
    scale(1.7, 1.7, 0.5)
    rotateZ(time)
    cylinder()
    popMatrix()
    pushMatrix()
    translate(11, 2.5, -4)
    scale(1.7, 1.7, 0.5)
    rotateZ(time)
    cylinder()
    popMatrix()
def cone(bottom, top, h, sides = 64):
  pushMatrix();
  
  translate(0,h/2,0);
  

  x = []
  z = []

  x2 = []
  z2 = []

 
  #get the x and z position on a circle for all the sides
  for i in range(sides + 1):
    angle = TWO_PI / (sides) * i
    x.append(sin(angle) * bottom)
    z.append(cos(angle) * bottom)
  
  for i in range(sides + 1):
    angle = TWO_PI / (sides) * i
    x2.append(sin(angle) * top)
    z2.append(cos(angle) * top)
 
  #draw the bottom of the cylinder
  beginShape(TRIANGLE_FAN);
 
  vertex(0,   -h/2,    0)
 
  for i in range(sides + 1):
    vertex(x[i], -h/2, z[i])
 
  endShape()
 
  #draw the center of the cylinder
  beginShape(QUAD_STRIP); 
 
  for i in range(sides + 1):
    vertex(x[i], -h/2, z[i])
    vertex(x2[i], h/2, z2[i])
 
  endShape()
 
  #draw the top of the cylinder
  beginShape(TRIANGLE_FAN)
 
  vertex(0,   h/2,    0)
 
  for i in range(sides + 1):
    vertex(x2[i], h/2, z2[i])
 
  endShape()
  
  popMatrix()
        

    
    

   
    