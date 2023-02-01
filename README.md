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

<ul>
<li>
Save level - f5
</li>
<li>
Load level - f9
</li>
<li>
To level edit - f2
</li>
<li>
to level - f1
</li>
<li>
Right mouse to drag camera in level edit.
</li>
<li>
Left mouse click to add to scene.
</li>
<li>
Left mouse+shift to select item.
</li>
<li>
Mouse drag to select multiple items.
</li>
<li>
Delete to delete selected items.
</li>
<li>
C to copy selected items.
</li>
<li>
Q + R to rotate selected items.
</li>
</ul>

I will work further on this project to be able to built platformer and/or arcade games in the future (maybe).
