deed-planner
============

Deed planner/house calculator for Wurm Online - public repository.

Program official forum thread: http://forum.wurmonline.com/index.php?/topic/79352-deedplanner-3d-house-and-deed-planner/

<b>This is Java 7 project - it will does not work with older versions of JDK!</b>

<b>To be able to run and edit this program, please follow this steps:</b><br>
1. You can use any IDE, but GUI is made in NetBeans, so you need NetBeans if you want to make any changes in it.<br>
2. Download and attach two libraries: latest version of LWJGL ( http://www.lwjgl.org/ ) and TWL PNGDecoder ( http://twl.l33tlabs.org/dist/PNGDecoder.jar )<br>
3. download latest official release of DeedPlanner from program thread ( http://forum.wurmonline.com/index.php?/topic/79352-deedplanner-3d-house-and-deed-planner/ ) and place folder "Data" and file "version.txt" in program working directory.<br>
4. Set VM option in project properties: -Djava.library.path="absolute_path_to_your_system_LWJGL_natives"

Code is not documented, but volunteers can document it, though the code should be the best documentation for itself. ;)

<b>Project goals:</b><br>
1. Creation of the most advanced, professional-looking deed planner for Wurm Online<br>
2. Access to the widest possible audience - as little platform-dependend code as possible<br>
3. The program usage should be as simple as possible<br>
4. Cooperation with other Wurm-related programs and (possibly in the future) the game itself.

The program main class is located in Form/HouseCalc.java.

The program have modular structure with core in Mapper/Mapper.java.
