package com.company.core;

import com.company.data.HumanBeing;
import com.company.data.Mood;
import com.company.data.WeaponType;
import com.company.exceptions.FileErrorException;
import com.company.exceptions.NoNameException;

import javax.sound.midi.SysexMessage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.core.Collection.humanQue;

public class Parser {
    private static Pattern XMLinfo = Pattern.compile("<\\?([\\s\\S]+?)\\?>");
    private static Pattern basic = Pattern.compile("<([\\s\\S]+?)>");
    private static Pattern value = Pattern.compile(">([\\s\\S]*?)<");
    private static boolean infoFlag = true;
    private static boolean coordFlag = false;
    private static String info;
    private static int count = 0;
    private static int carFlag = 0;
    private static boolean idFlag = true;
    private static Map<String, Boolean> checklist = new HashMap<>();
    private static boolean sygnal;
    private static boolean deleteFlag = false;

    public static void start(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        if(lineCounter(fileName)>20) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("<HeroList>")) {
                    execute(line);
                }
            }
        } else {
            System.out.println("Файл пуст или содержит недостаточно информации.");
        }
        }

       public static int lineCounter(String filename) throws IOException {
           BufferedReader reader = new BufferedReader(new FileReader(filename));
           String lin;
           int counter = 0;
           while ((lin = reader.readLine()) != null) {
                counter += 1;
           }
           reader.close();
           return counter;
       }

    public static void execute(String line){
        if(infoFlag) {
            info = line;
            System.out.println(info);
            infoFlag = false;
        }
        else{
            Matcher matcherA = basic.matcher(line);
            Matcher matcherB = value.matcher(line);
            count += 1;
            if(matcherA.find()) {
                String tag = matcherA.group().replace("<", "").replace(">", "");
                if (idFlag&&!tag.equals("\\HeroList")) {
                    if (check()) {
                        HumanBeing human = new HumanBeing();
                        //long id = Long.parseLong(tag);
                        //human.setId(id);
                        Collection.addHuman(human);
                        sygnal = false;
                        idFlag = false;
                    }
                }
                    else if(sygnal&&!tag.equals("\\HeroList")&&!check()) {
                        humanQue.removeLast();
                        throw new FileErrorException("В файле ошибка. Проверьте целостность данных и количество аргументов.");
                    }
                 else {
                    switch (tag) {
                        case "ID":
                            if (matcherB.find()) {
                                String idString = matcherB.group().replace("<", "").replace(">", "");
                                try {
                                    long id = Long.parseLong(idString);
                                    checklist.put("ID", true);
                                    Collection.getHuman().setId(id);
                                } catch (NumberFormatException e){
                                    System.out.println("ID не читается!");
                                    deleteFlag = true;
                                }
                            }
                        case "Name":
                            if (matcherB.find()) {
                                String name = matcherB.group().replace("<", "").replace(">", "");
                                if (carFlag == 0) {
                                    if(checklist.get("name")==null) {
                                        if (!name.equals("") && name != null) {
                                            Collection.getHuman().setName(name);
                                            checklist.put("name", true);
                                        } else {
                                            throw new NoNameException("Имя не должно быть пустым!");
                                        }
                                    }
                                } else if (carFlag == 1&&checklist.get("carname")==null) {
                                    Collection.getHuman().setCarName(name);
                                    checklist.put("carname", true);
                                }
                            }
                            break;
                        case "Coordinates":
                            if(checklist.get("coordinates") ==null) {
                                coordFlag = true;
                                checklist.put("coordinates", true);
                                break;
                            }
                            break;
                        case "x":
                            if(checklist.get("coordinates")) {
                                if (matcherB.find()) {
                                    String x = matcherB.group().replace("<", "").replace(">", "");
                                    float FloatX = Float.parseFloat(x);
                                    if (FloatX > -316) {
                                        Collection.getHuman().setCoordinatesX(FloatX);
                                        checklist.put("x", true);
                                    }
                                }
                                break;
                            }
                            break;
                        case "y":
                            if(checklist.get("coordinates")) {
                                if (matcherB.find()) {
                                    String y = matcherB.group().replace("<", "").replace(">", "");
                                    double DoubleY = Double.parseDouble(y);
                                    Collection.getHuman().setCoordinatesY(DoubleY);
                                    checklist.put("y", true);
                                }
                                break;
                            }
                            break;
                        case "\\Coordinates":
                            coordFlag = false;
                            break;
                        case "CreationDate":
                            if(checklist.get("creationdate")==null) {
                                if (matcherB.find()) {
                                    String date = matcherB.group().replace("<", "").replace(">", "");
                                    Date data = new Date(Long.parseLong(date));
                                    //Date data = new Date(Long.valueOf(String date).longValue());
                                    Collection.getHuman().setCreationDate(data);
                                    checklist.put("creationdate", true);
                                }
                                break;
                            }
                            break;
                        case "RealHero":
                            if(checklist.get("realhero")==null) {
                                if (matcherB.find()) {
                                    String hero = matcherB.group().replace("<", "").replace(">", "");
                                    if (hero.equals("true")) {
                                        Collection.getHuman().setRealHero(true);
                                        checklist.put("realhero", true);
                                    } else if (hero.equals("false")) {
                                        Collection.getHuman().setRealHero(false);
                                        checklist.put("realhero", true);
                                    }
                                }
                                break;
                            }
                            break;
                        case "HasToothpick":
                            if(checklist.get("hastoothpick")==null) {
                                if (matcherB.find()) {
                                    String toothPick = matcherB.group().replace("<", "").replace(">", "");
                                    if (toothPick.equals("true")) {
                                        Collection.getHuman().setHasToothPick(true);
                                        checklist.put("hastoothpick", true);
                                    } else if (toothPick.equals("false")) {
                                        Collection.getHuman().setHasToothPick(false);
                                        checklist.put("hastoothpick", true);
                                    }
                                }
                                break;
                            }
                            break;
                        case "ImpactSpeed":
                            if(checklist.get("impactspeed")==null) {
                                if (matcherB.find()) {
                                    String speed = matcherB.group().replace("<", "").replace(">", "");
                                    if (!speed.equals("")) {
                                        Long impactSpeed = Long.parseLong(speed);
                                        Collection.getHuman().setImpactSpeed(impactSpeed);
                                        checklist.put("impactspeed", true);
                                    } else {
                                        Collection.getHuman().setImpactSpeed(null);
                                        checklist.put("impactspeed", true);
                                    }
                                }
                                break;
                            }
                            break;
                        case "WeaponType":
                            if(checklist.get("weapontype")==null) {
                                if (matcherB.find()) {
                                    String type = matcherB.group().replace("<", "").replace(">", "");
                                    switch (type) {
                                        case "AXE":
                                            Collection.getHuman().setWeaponType(WeaponType.AXE);
                                            checklist.put("weapontype", true);
                                            break;
                                        case "PISTOL":
                                            Collection.getHuman().setWeaponType(WeaponType.PISTOL);
                                            checklist.put("weapontype", true);
                                            break;
                                        case "SHOTGUN":
                                            Collection.getHuman().setWeaponType(WeaponType.SHOTGUN);
                                            checklist.put("weapontype", true);
                                            break;
                                        case "RIFLE":
                                            Collection.getHuman().setWeaponType(WeaponType.RIFLE);
                                            checklist.put("weapontype", true);
                                            break;
                                    }
                                }
                                break;
                            }
                            break;
                        case "Mood":
                            if(checklist.get("mood")==null) {
                                if (matcherB.find()) {
                                    String mood = matcherB.group().replace("<", "").replace(">", "");
                                    switch (mood) {
                                        case "SADNESS":
                                            Collection.getHuman().setMood(Mood.SADNESS);
                                            checklist.put("mood", true);
                                            break;
                                        case "GLOOM":
                                            Collection.getHuman().setMood(Mood.GLOOM);
                                            checklist.put("mood", true);
                                            break;
                                        case "APATHY":
                                            Collection.getHuman().setMood(Mood.APATHY);
                                            checklist.put("mood", true);
                                            break;
                                        case "CALM":
                                            Collection.getHuman().setMood(Mood.CALM);
                                            checklist.put("mood", true);
                                            break;
                                        case "RAGE":
                                            Collection.getHuman().setMood(Mood.RAGE);
                                            checklist.put("mood", true);
                                            break;
                                    }
                                }
                                break;
                            }
                            break;
                        case "Car":
                            if(checklist.get("car")==null) {
                                carFlag = carFlag + 1;
                                checklist.put("car", true);
                                break;
                            }
                            break;
                        case "Cool":
                            if(checklist.get("carcool")==null) {
                                if (matcherB.find()) {
                                    String cool = matcherB.group().replace("<", "").replace(">", "");
                                    if (cool.equals("true")) {
                                        Collection.getHuman().setCarCool(true);
                                        checklist.put("carcool", true);
                                    } else if (cool.equals("false")) {
                                        Collection.getHuman().setCarCool(false);
                                        checklist.put("carcool", true);
                                    }

                                }
                                break;
                            }
                            break;
                        case "\\Car":
                            carFlag = carFlag - 1;
                            break;
                        case "\\HumanBeing":
                            sygnal = true;
                            idFlag = true;
                            break;
                        default:
                                //sygnal = true;
                                //idFlag = true;
                            break;
                    }
                    // }
                }
            }
            }
        }
    public static String getInfo(){
        return info;
    }
    public static boolean check(){
        if(checklist.size()==14){
            System.out.println(checklist.size());
            System.out.println(checklist.toString());
            checklist.clear();
            System.out.println(checklist.toString());
            return (true);
        } else if(checklist.size()==0){
            System.out.println(checklist.size());
            return(true);
        }
        else if(checklist.size()<13){
            System.out.println(1);
            checklist.clear();
            return (false);
        }
        return (false);
    }
}

