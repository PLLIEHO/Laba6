package com.company.core;

import com.company.data.HumanBeing;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.company.core.Collection.humanQue;


public class Core {
    private String currentProgram;
    Pattern pSpace = Pattern.compile(" ");
    ArrayList<String> lastCommands = new ArrayList<>();
    private String fileXML;

    public void history(String command){
        lastCommands.add(command);
        if(lastCommands.size()>9){
            lastCommands.remove(0);
        }
    }

    public void searchFile() throws FileNotFoundException {
        System.out.println("Введите название файла: ");
        Scanner in = new Scanner(System.in);
        String filename = in.nextLine();
        File file = new File(filename);
        if(file.canRead()) {
            try {
                Parser.start(filename);
                fileXML = filename;
            } catch (FileNotFoundException e) {
                System.out.println("Имя файла не распознано. Пожалуйста, повторите ввод.");
                this.searchFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Файл заблокирован для чтения.");
            System.exit(1);
        }
    }

    public void script() throws IOException {
        System.out.println("Введите команду: ");
        currentProgram = InputCore.input();
        lastCommands.add(currentProgram);
        String[] values = pSpace.split(currentProgram);
        this.history(values[0]);

        if(values[0].equals(CommandList.help)){
            System.out.println("help : вывести справку по доступным командам \n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении \n" +
            "add {element} : добавить новый элемент в коллекцию \n" +
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному \n" +
            "remove_by_id id : удалить элемент из коллекции по его id \n" +
            "clear : очистить коллекцию \n" +
            "save : сохранить коллекцию в файл \n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме. \n" +
            "exit : завершить программу (без сохранения в файл) \n" +
            "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции. Доступные для сравнения элементы: \n" +
            "coordinates_x, coordinates_y, realhero, hastoothpick, impactspeed, carcool; \n" +
            "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный \n" +
            "history : вывести последние 9 команд (без их аргументов) \n" +
            "max_by_real_hero : вывести любой объект из коллекции, значение поля realHero которого является максимальным \n" +
            "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку \n" +
            "print_descending : вывести элементы коллекции в порядке убывания");
            script();
        }
        else if(values[0].equals(CommandList.info)){
            System.out.println("Тип коллекции: ArrayDeque \n" + "Текущий размер коллекции: " + humanQue.size() + "\n" + "Дата инициализации: " + Collection.getData().toString());
            script();
        }
        else if(values[0].equals(CommandList.show)){
            for(HumanBeing o : humanQue){
                System.out.println(o.toString());
            }
            script();
        }
        else if(values[0].equals(CommandList.add)){
            AddCore.add();
            script();
        }
        else if(values[0].equals(CommandList.execute)) {
            if (values.length > 1) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(values[1]));
                    InputCore.setScriptFlag(true);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        InputCore.getScriptList().add(line);
                        InputCore.incScriptCounter();
                    }
                    script();
                } catch (FileNotFoundException e) {
                    System.out.println("Имя файла введено неверно. Пожалуйста, повторите ввод.");
                    script();
                }
            } else {
                System.out.println("Вы не ввели аргумент.");
                script();
            }
        }
        else if(values[0].equals(CommandList.update)) {
            if (values.length > 1) {
                try {
                    Long ID = Long.parseLong(values[1]);
                    String element = values[2].toLowerCase();
                    AddCore.update(ID, element);
                    script();
                } catch (NumberFormatException e) {
                    System.out.println("ID введён неверно. Пожалуйста, повторите ввод.");
                    script();
                }
            } else {
                System.out.println("Вы не ввели аргумент.");
                script();
            }
        }
        else if(values[0].equals(CommandList.remove_by_id)){
            if(values.length>1) {
                try {
                    Long ID = Long.parseLong(values[1]);
                    for (HumanBeing human : humanQue) {
                        if (human.getId() == ID) {
                            humanQue.remove(human);
                            System.out.println("Объект с заданным ID успешно удалён.");
                        }
                    }
                    script();
                } catch (NumberFormatException e) {
                    System.out.println("ID введён неверно. Пожалуйста, повторите ввод.");
                    script();
                }
            } else {
            System.out.println("Вы не ввели аргумент.");
            script();
        }
        }
        else if(values[0].equals(CommandList.clear)){
            humanQue.clear();
            System.out.println("Коллекция успешно очищена.");
            script();
        }
        else if(values[0].equals(CommandList.save)){
            OutputCore.save(fileXML);
            script();
        }
        else if(values[0].equals(CommandList.exit)){
            System.out.println("До скорой встречи!");
            System.exit(1);
        }
        else if(values[0].equals(CommandList.add_if_max)){
            if(values.length>1) {
                String element = values[1].toLowerCase();
                AddCore.setAddIfMaxFlag(true);
                AddCore.setCore(this);
                AddCore.setAddIfMaxElement(element);
                AddCore.add();
            } else {
                System.out.println("Вы не ввели аргумент.");
            }
            script();
        }
        else if(values[0].equals(CommandList.max_by_real_hero)){
            Collection.maxByRealHero();
            script();
        }
        else if(values[0].equals(CommandList.filter)){
            if(values.length>1) {
                Collection.searchName(values[1]);
            } else {
            System.out.println("Вы не ввели аргумент.");
            }
            script();
        }
        else if(values[0].equals(CommandList.print_descending)){
            Collection.descendingSort();
            script();
        }
    }
}
