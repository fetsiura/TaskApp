package com.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String [][] tasks;


    public static void main(String[] args) {
        System.out.println("Hello =)" + ConsoleColors.PURPLE);
        System.out.println("What should I call you?");
        Scanner scanner = new Scanner(System.in);
        String hy = scanner.nextLine();
        System.out.println("Hey "+ hy + " =)");
        System.out.println("I am a program that will help you not forget about your important things");
        tasks = loadDataToTab(FILE_NAME);
        printOptions(OPTIONS);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            switch (input) {
                case "exit":
                    saveTabToFile(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.RED + "See you later, "+hy);
                    System.exit(0);
                    break;
                case "add":
                    addTask();
                    break;
                case "remove":
                    remoteTask(tasks, getTheNumber());
                    System.out.println("Point was successfully deleted");
                    break;
                case "list":
                    printTab(tasks);
                    break;
                default:
                    System.out.println("Select a correct option");
            }
            printOptions(OPTIONS);
        }

    }

    public static void printOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Please select an options:" + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static String[][] loadDataToTab(String fileName/*, String separator8*/) {
        Path path = Paths.get(fileName);
        if(!Files.exists(path)) {
            System.out.println("File not exist");
            System.exit(0);
        }
        String [][] tab = null;
        try {
            List<String> strings = null;
            strings = Files.readAllLines(path);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i =0; i < strings.size(); i++){
                String[] split = strings.get(i).split(",");
                for (int j=0; j<split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void printTab (String[][] tab) {
        for (int i =0; i<tab.length; i++) {
            System.out.print(i + " : ");
            for (int j=0; j<tab[i].length; j++) {
                System.out.println(tab[i][j]+ " ");
            }
            System.out.println();
        }
    }
    private static void addTask() {
        Scanner sc= new Scanner(System.in);
        System.out.println("Please add task descriptions");
        String desc = sc.nextLine();
        System.out.println("Please add task due data");
        String dueData = sc.nextLine();
        System.out.println("Is your task is important: Yes/No");
        String taskStatus = sc.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length +1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = desc;
        tasks[tasks.length-1][1] = dueData;
        tasks[tasks.length-1][2] = taskStatus;
    }
    public static boolean isNumberGreaterEqualZero (String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >=0;
        } return false;
    }
    public static int getTheNumber () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remote");
        String number = scanner.nextLine();
        while (!isNumberGreaterEqualZero(number)) {
            System.out.println("Incorrect number");
            scanner.nextLine();
            number = number;
        }
        return Integer.parseInt(number);
    }

    private static void remoteTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }

        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element is not exist");
        }
    }
    public static void saveTabToFile (String fileName, String[][] tab) {
        Path path = Paths.get(fileName);
        String[] lines = new String[tasks.length];
        for (int i=0; i<tab.length; i++) {
            lines[i] = String.join(",",tab[i]);
        } try {
            Files.write(path, Arrays.asList(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
