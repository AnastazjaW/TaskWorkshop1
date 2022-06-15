package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasksTab;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "tasks.csv";
        tasksTab = loadDataToTab(fileName);
        showMenuOptions();
        while (scanner.hasNextLine()) {
            String option = scanner.nextLine();
            switch (option) {
                case "add":
                    addNewTask(scanner);
                    break;
                case "remove":
                    tasksTab = ArrayUtils.remove(tasksTab, takeNumberOfTaskToRemove(scanner));
                    break;
                case "list":
                    showListOfTasks(tasksTab);
                    break;
                case "exit":
                    updateFile(fileName);
                    System.out.println(ConsoleColors.RED + "Bye-bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            showMenuOptions();
        }


    }


    public static String[][] loadDataToTab(String filename) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        String[][] tasksTab = null;
        try {
            int numOfLines = Files.readAllLines(path).size();
            int columns = 3;
            tasksTab = new String[numOfLines][columns];
            int iterator = 0;
            for (String line : Files.readAllLines(path)) {
                String[] dataLine = line.split(", ");
                System.arraycopy(dataLine, 0, tasksTab[iterator], 0, tasksTab[iterator].length);
                iterator++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasksTab;
    }

    public static void showMenuOptions() {
        String[] menuOptions = {"add", "remove", "list", "exit"};
        System.out.println(ConsoleColors.BLUE + ("Please select an option: "));
        for (String option : menuOptions) {
            System.out.println(ConsoleColors.WHITE + option);
        }
    }
    
    public static void addNewTask(Scanner scan) {
        tasksTab = Arrays.copyOf(tasksTab, tasksTab.length + 1);
        tasksTab[tasksTab.length - 1] = new String[3];
        System.out.println("Please add task description: ");
        tasksTab[tasksTab.length - 1][0] = scan.nextLine();
        System.out.println("Please add ask due date in format YYYY-MM-DD: ");
        tasksTab[tasksTab.length - 1][1] = scan.nextLine();
        System.out.println("Is your task important - input true or false: ");
        tasksTab[tasksTab.length - 1][2] = scan.nextLine();
    }

    public static void showListOfTasks(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(String.valueOf(i) + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static int takeNumberOfTaskToRemove(Scanner scan) {
        int taskToRemove = 0;
        System.out.println("Please select a task number to remove. Number should be in range " +
                "[0 ;" + String.valueOf(tasksTab.length - 1) + "]");
        String usersInput = scan.nextLine();
        boolean ifParsable = NumberUtils.isParsable(usersInput);
        while (!ifParsable) {
            System.out.println("Wrong input!");
            System.out.println("Please select a task number to remove. Number should be in range " +
                    "[0 ;" + String.valueOf(tasksTab.length - 1) + "]");
            usersInput = scan.nextLine();
            ifParsable = NumberUtils.isParsable(usersInput);
            taskToRemove = Integer.parseInt(usersInput);
            if ((taskToRemove < 0) || (taskToRemove > tasksTab.length)) {
                ifParsable = false;
            }
        }
        return taskToRemove;
    }


    public static void updateFile(String filename) {
        Path path = Paths.get(filename);
        StringBuilder stringBuilder = new StringBuilder();
        if (!Files.exists(path)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        List<String> tasksList = new ArrayList<>();
        for (String[] task : tasksTab) {
            for (String s : task) {
                stringBuilder.append(s).append(", ");
            }
            String line = stringBuilder.toString();
            tasksList.add(line);
            stringBuilder.setLength(0);
        }
        try {
            Files.write(path, tasksList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



