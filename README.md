# NVidia-Optimus-Bug 

On laptop with double graphic cards, by example, INTEL HD4600 and NVidia GTX 870M GPU, some softwares dont' use the NVidia GPU and prefer to use the intel card.
 
This little software permits to force the use of the NVidia card.

## How to install?
Look in the **dist** folder.

Put **NVidia-Optimus-Bug.jar** in, by example, *C:\APP\utilities\NVidia-Optimus-Bug.jar* (if you change the path you need to edit the .bat file)

Put **Nvidia-optimus-bug.bat** in your startup folder (Windows menu -> All programs -> Startup)

Restart or **execute** the .bat

## How to use it?

Now, you have the icon of a black locker in your tray. 

When you click on it, the color of the locker changes(black or red).

- **BLACK** = ONLY LOAD SIGNED DLL
- **RED** = LOAD UNSIGNED DLL

It's solve the "optimus bug driver". When you want that a software uses your NVidia card, put the locker on red otherwise on black. 

## Remark
Don't forget to go under NVidia config panel to set the software which needs to use your NVidia card.

Sometimes, the software will crash, it's like that, I can't do anything. In this case, you need to use your intel card. 