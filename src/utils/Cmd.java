package utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Rodrigue ROLAND
 */
public class Cmd
{
    public static class OutputCommand
    {
        private String output = "";
        private int exitValue = -1;

        public OutputCommand()
        {
        }

        /**
         * @return the exitValue
         */
        public int getExitValue()
        {
            return exitValue;
        }

        /**
         * @param exitValue the exitValue to set
         */
        public void setExitValue(int exitValue)
        {
            this.exitValue = exitValue;
        }

        /**
         * @return the output
         */
        public String getOutput()
        {
            return output;
        }

        /**
         * @param output the output to set
         */
        public void setOutput(String output)
        {
            this.output = output;
        }
    }

    public static OutputCommand executeCommand(String command)
    {
        Log.println("CMD: " + command, Log.Color.PURPLE);

        OutputCommand outputCommand = new OutputCommand();
        StringBuilder output = new StringBuilder();

        Process p;

        try
        {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                output.append(line + "\n");
            }

            outputCommand.setExitValue(p.waitFor());
        }
        catch (IOException | InterruptedException e)
        {
            Log.println("Exception: " + e.toString());
        }

        outputCommand.setOutput(output.toString());

        return outputCommand;
    }
}
