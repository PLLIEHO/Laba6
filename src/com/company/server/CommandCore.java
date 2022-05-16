package com.company.server;

import com.company.common.CommandList;
import com.company.common.Pack;
import com.company.common.Request;
import com.company.server.core.AddCore;
import com.company.server.core.Collection;
import com.company.server.core.UpdateCore;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.regex.Pattern;

public class CommandCore {
    private Request request;
    private String answer;
    private Collection collection;
    private final Pattern enter = Pattern.compile("\n");
    private DatagramChannel channel;

    public CommandCore(Object object, Collection collection, DatagramChannel channel){
        this.request = (Request) object;
        this.collection = collection;
        this.channel = channel;
    }
    public Object commandSearch() throws IOException {
        CommandList command = request.getCommand();
        Pack arg = request.getArgument();
        AnswerSender sender = new AnswerSender();
        switch (command){
            case HELP:
                answer = "help : вывести справку по доступным командам \n" +
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
                        "print_descending : вывести элементы коллекции в порядке убывания";
                System.out.println("Абоба");
                sender.send(channel, answer);
                break;
            case INFO:
                answer = "Тип коллекции: ArrayDeque \n" + "Текущий размер коллекции: " + collection.getCollection().size() + "\n" + "Дата инициализации: " + collection.getData().toString();

            case ADD:
                String[] values = enter.split(arg.getArgA());
                AddCore add = new AddCore(collection, this, values);
                add.add();
                break;
            case UPDATE:
                UpdateCore update = new UpdateCore(collection, this, arg.getArgA(), arg.getArgB());
                update.update();
                break;
            case SHOW:
                answer  = collection.getCollection().toString();


            case CLEAR:
                collection.getCollection().clear();
                answer = "Очищение успешно.";


            case ADD_IF_MAX:
                String[] valuesMax = enter.split(arg.getArgA());
                AddCore addIfMax = new AddCore(collection, this, valuesMax);
                break;
        } return answer;
    }
}
