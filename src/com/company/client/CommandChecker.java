package com.company.client;

import com.company.common.*;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.regex.Pattern;

public class CommandChecker {

    private Pattern pSpace = Pattern.compile(" ");
    Request request;
    private Sender sender = new Sender();
    private InetAddress address;
    private DatagramSocket socket;
    public CommandChecker(InetAddress address, DatagramSocket socket){
        this.address = address;
        this.socket = socket;
    }


    public void check() throws IOException {
        Serializer serializer = new Serializer();
        while (true) {
            System.out.println("Введите команду: ");
            String[] values = pSpace.split(InputCore.input());
            String command = values[0].toLowerCase();
            switch (command) {
                case "help":
                    request = new Request(CommandList.HELP, null);
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "info":
                    request = new Request(CommandList.INFO, null);
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "show":
                    request = new Request(CommandList.SHOW, null);
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "add":
                    String arg = "";
                    System.out.println("Введите имя: ");
                    arg = InputCore.input();
                    System.out.println("Введите координату X: ");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("Введите координату Y: ");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("Этот персонаж - герой? (да/нет)");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("У него есть зубочистка? (да/нет)");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("Введите скорость воздействия:");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("Выберите тип оружия: (AXE, PISTOL, SHOTGUN, RIFLE)");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("Выберите настроение: (SADNESS, GLOOM, APATHY, CALM, RAGE)");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("Введите название машины: ");
                    arg = arg + "\n" + InputCore.input();
                    System.out.println("Машина крутая? (да/нет)");
                    arg = arg + "\n" + InputCore.input();
                    request = new Request(CommandList.ADD, new Pack(arg, null));
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "update":
                    String element = values[2];
                    String updArg;
                    switch (element) {
                        case ElementList.NAME:
                            System.out.println("Введите новое имя: ");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.COORDSX:
                            System.out.println("Введите новые координаты (X): ");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.COORDSY:
                            System.out.println("Введите новые координаты (Y): ");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.REALHERO:
                            System.out.println("Этот человек герой?: (да/нет)");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.HASTOOTHPICK:
                            System.out.println("У человека есть зубочистка?: (да/нет)");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.IMPACTSPEED:
                            System.out.println("Введите новую скорость воздействия: ");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.WEAPONTYPE:
                            System.out.println("Введите новый тип оружия: (AXE, PISTOL, SHOTGUN, RIFLE)");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.MOOD:
                            System.out.println("Введите новое настроение: (SADNESS, GLOOM, APATHY, CALM, RAGE)");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.CARNAME:
                            System.out.println("Введите новое название машины:");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        case ElementList.CARCOOL:
                            System.out.println("Машина-то крутая? (да/нет)");
                            updArg = element + "\n" + InputCore.input();
                            break;
                        default:
                            System.out.println("Введен неверный элемент, повторите ввод.");
                            this.check();
                            updArg = "";
                    }
                    request = new Request(CommandList.UPDATE, new Pack(values[1], updArg));
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "remove_by_id":
                    request = new Request(CommandList.REMOVE_BY_ID, new Pack(values[1], null));
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "execute_script":
                    request = new Request(CommandList.EXECUTE, new Pack(values[1], null));
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "add_if_max":
                    request = new Request(CommandList.ADD_IF_MAX, new Pack(values[1], null));
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "max_by_real_hero":
                    request = new Request(CommandList.MAX_BY_REAL_HERO, null);
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "history":
                    request = new Request(CommandList.HISTORY, null);
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "filter_contains_name":
                    request = new Request(CommandList.FILTER, new Pack(values[1], null));
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "print_descending":
                    request = new Request(CommandList.PRINT_DESCENDING, null);
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                case "exit":
                    request = new Request(CommandList.EXIT, null);
                    sender.send(serializer.serialize(this.request).toByteArray(), address, socket);
                    break;
                default:
                    System.out.println("Команда не распознана. Повторите ввод.");
                    this.check();
                    break;
            }
        }
    }

    public Request getRequest(){
        return request;
    }



}
