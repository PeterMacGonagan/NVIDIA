package misc;


import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Cmd;
import utils.Log;

public class Main
{
    private static boolean bLock = true;
    private static TrayIcon trayIcon = null;

    private static Image imgLock = null;
    private static Image imgUnlock = null;

    public static void main(String[] args)
    {
        /* Use an appropriate Look and Feel */
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        catch (InstantiationException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

        /* Turn off metal's use of bold fonts */
        UIManager.put(
                "swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event-dispatching thread:
        //adding TrayIcon.
        SwingUtilities.invokeLater(
                new Runnable()
                {

                    public void run()
                    {
                        createAndShowGUI();
                    }
                }
        );
    }

    private static void createAndShowGUI()
    {

        //Check the SystemTray support
        if (!SystemTray.isSupported())
        {
            System.out.println("SystemTray is not supported");
            return;
        }

        imgLock = createImage("images/lock.png", "tray icon - lock");
        imgUnlock = createImage("images/unlock.png", "tray icon - unlock");

        final PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(imgLock);
        trayIcon.setImageAutoSize(true);
        bLock = true;
        AppInitDLL_put1();

        final SystemTray tray = SystemTray.getSystemTray();


        MenuItem exitItem = new MenuItem("Exit");

		
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try
        {
            tray.add(trayIcon);
        }
        catch (AWTException e)
        {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addMouseListener(new MouseListener()
        {

            public void mouseClicked(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    bLock = !bLock;

                    if (bLock)
                    {
                        AppInitDLL_put1();
                    }
                    else
                    {
                        AppInitDLL_put0();
                    }
                }
            }

            public void mousePressed(MouseEvent e)
            {
            }

            public void mouseReleased(MouseEvent e)
            {
            }

            public void mouseEntered(MouseEvent e)
            {
            }

            public void mouseExited(MouseEvent e)
            {
            }
        });


		
        exitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AppInitDLL_put1(); //just to be sure

                tray.remove(trayIcon);
                System.exit(0);
            }
        });

    }

    //http://superuser.com/questions/607572/how-do-i-modify-the-data-of-an-existing-registry-key-value-name-from-cmd
    //[HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Windows]
    //"RequireSignedAppInit_DLLs"=dword:00000000
    //
    //[HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Windows NT\CurrentVersion\Windows]
    //"RequireSignedAppInit_DLLs"=dword:00000000
    private static void AppInitDLL_put0()
    {
        String cmd1 = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Windows\" /t REG_DWORD /v RequireSignedAppInit_DLLs /d 00000000 /f";
        String cmd2 = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Windows NT\\CurrentVersion\\Windows\" /t REG_DWORD /v RequireSignedAppInit_DLLs /d 00000000 /f";

        exec(cmd1);
        exec(cmd2);

        trayIcon.setImage(imgUnlock);
    }

    private static void AppInitDLL_put1()
    {
        String cmd1 = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Windows\" /t REG_DWORD /v RequireSignedAppInit_DLLs /d 00000001 /f";
        String cmd2 = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Windows NT\\CurrentVersion\\Windows\" /t REG_DWORD /v RequireSignedAppInit_DLLs /d 00000001 /f";

        exec(cmd1);
        exec(cmd2);

        trayIcon.setImage(imgLock);
    }

    private static void exec(String cmd)
    {
        Cmd.OutputCommand out1 = Cmd.executeCommand(cmd);
        Log.println("Status: " + out1.getExitValue(), Log.Color.YELLOW);

        if (out1.getExitValue() != 0)
        {
            trayIcon.displayMessage("Problem", "CMD: " + cmd + "\nExit value: " + out1.getExitValue() + "\nOutput: " + out1.getOutput(), TrayIcon.MessageType.ERROR);
        }
    }

    //Obtain the image URL
    protected static Image createImage(String path, String description)
    {
        URL imageURL = Main.class
                .getResource(path);

        if (imageURL
            == null)
        {
            System.err.println("Resource not found: " + path);
            return null;
        }

        else
        {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
