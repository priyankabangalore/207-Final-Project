/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;

public class ValidCmd extends Command {
    /**
     * Is this a valid command?
     */
    public ValidCmd() {
        super("empty");
    }

    /**
     * Is the string a valid string?
     * @param s the string being checked
     */
    public void valid(String s){
        if(s.equals("42069")){
            System.out.println(
              "▄▄▄▄▄▄ ▄ .▄▪  .▄▄ ·   ▪  .▄▄ ·    ▄▄▄·  ▐ ▄   ▄▄▄ . ▄▄▄· .▄▄ · ▄▄▄▄▄▄▄▄ .▄▄▄    ▄▄▄ . ▄▄ •  ▄▄ • \n" +
              "▀•██ ▀██▪▐███ ▐█ ▀.   ██ ▐█ ▀.   ▐█ ▀█ •█▌▐█  ▀▄.▀·▐█ ▀█ ▐█ ▀. •██  ▀▄.▀·▀▄ █·  ▀▄.▀·▐█ ▀ ▪▐█ ▀ ▪\n" +
              "  ▐█.▪██▀▀█▐█·▄▀▀▀█▄  ▐█·▄▀▀▀█▄  ▄█▀▀█ ▐█▐▐▌  ▐▀▀▪▄▄█▀▀█ ▄▀▀▀█▄ ▐█.▪▐▀▀▪▄▐▀▀▄   ▐▀▀▪▄▄█ ▀█▄▄█ ▀█▄\n" +
              "  ▐█▌·██▌▐▀▐█▌▐█▄▪▐█  ▐█▌▐█▄▪▐█  ▐█▪ ▐▌██▐█▌  ▐█▄▄▌▐█▪ ▐▌▐█▄▪▐█ ▐█▌·▐█▄▄▌▐█•█▌  ▐█▄▄▌▐█▄▪▐█▐█▄▪▐█\n" +
              "  ▀▀▀ ▀▀▀ ·▀▀▀ ▀▀▀▀   ▀▀▀ ▀▀▀▀    ▀  ▀ ▀▀ █▪   ▀▀▀  ▀  ▀  ▀▀▀▀  ▀▀▀  ▀▀▀ .▀  ▀   ▀▀▀ ·▀▀▀▀ ·▀▀▀▀ \n");
        }
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return null;
    }
}
