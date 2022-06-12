package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TaskManager {

    public static void main(String[] args) {
        String[][] temp = tasks();
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                System.out.println(temp[i][j]);
            }
        }
    }

    static String[][] tasks()  {
        Path path = Paths.get("tasks.csv");
        String[][] tasksTab = null;
        try {
            int numOfLines = Files.readAllLines(path).size();
            int columns = 3;
            tasksTab = new String[numOfLines][columns];
            int iterator = 0;
            for (String line : Files.readAllLines(path)) {
                String[] dataLine = line.split(", ");
                for (int i = 0; i < tasksTab[iterator].length; i++) {
                    tasksTab[iterator][i] = dataLine[i];
                }
                iterator ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasksTab;
    }

    public static void showingMenuOptions() {
        String[] menuOptions = {"add", "remove", "list", "exit"};
        System.out.println(ConsoleColors.BLUE + ("Please select an option: "));
        for (String option: menuOptions) {
            System.out.println(ConsoleColors.WHITE + option);
        }
    }


}
