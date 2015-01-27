package utils;

/**
 *
 * @author Rodrigue ROLAND
 */
public class Log
{
    public enum Color
    {
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        PURPLE,
        CYAN,
        WHITE
    }

    public static String LINE = "--------------------------------------------------------------------------------------------";
    private static final Color m_DefautColor = Color.YELLOW;

    static public void clear()
    {
        print(ColorCodes.RESET);
    }

    static public void print(String msg)
    {
        print(msg, m_DefautColor);
    }

    static public void println(String msg)
    {
        println(msg, m_DefautColor);
    }

    static public void parseandprint(String msg)
    {
        System.out.print(ColorCodes.ParseColors(msg));
    }

    static public void parseandprintln(String msg)
    {
        System.out.println(ColorCodes.ParseColors(msg));
    }

    static public void print(String msg, Color color)
    {
        System.out.print(link(color) + msg);
    }

    static public void println(String msg, Color color)
    {
        System.out.println(link(color) + " " + msg);
    }

    static public void changeColor(Color color)
    {
        System.out.print(link(color));
    }

    static private String link(Color color)
    {
        switch (color)
        {
            case BLACK:
                return ColorCodes.BLACK;
            case RED:
                return ColorCodes.RED;
            case GREEN:
                return ColorCodes.GREEN;
            case YELLOW:
                return ColorCodes.YELLOW;
            case BLUE:
                return ColorCodes.BLUE;
            case PURPLE:
                return ColorCodes.PURPLE;
            case CYAN:
                return ColorCodes.CYAN;

        }

        return ColorCodes.WHITE;
    }
}
