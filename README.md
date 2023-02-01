# W2DGE-Game-Engine-Game-Rolling-Stone-
A Java based small game engine with level editing. 

Build in Januray 2023.

A small Java based Game Engine called W2DGE.
Contains a level editor as well. 
Built a small game called "Rolling Stone", where stone is trying to survive all obstacles (loosely based on Geometry Dash).

Tile width and height are 42x42. This could lead to some raster exceptions if the user wants to use bigger images to render on the game objects.

Parallax background and ground still needs work. Does not work if the player tries to go back. 
Collision manager works really nice, but sometimes the player will fall on an angle on a block and dies without reason (Better bounds calculations needed).
Box and triangle bounds for now, but circle bounds will get added as well.

The engine will create one Screen (JFrame) and the user will add "Scenes" to create different kind of "Screens" were the user could edit the level or load a level.

Save level - f5
Load level - f9
To level edit - f2
to level - f1

Right mouse to drag camera in level edit.
Left mouse click to add to scene.
Left mouse+shift to select item.
Mouse drag to select multiple items.
Delete to delete selected items.
C to copy selected items.
Q + R to rotate selected items.

I will work further on this project to be able to built platformer and/or arcade games in the future (maybe).
