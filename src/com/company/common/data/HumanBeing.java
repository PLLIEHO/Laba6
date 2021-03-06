package com.company.common.data;

import java.util.Date;

public class HumanBeing {
    private long id;
    private String name;
    private Coordinates coordinates = new Coordinates();
    private java.util.Date creationDate;
    private Boolean realHero;
    private Boolean hasToothPick;
    private Long impactSpeed;
    private WeaponType weaponType;
    private Mood mood;
    private Car car = new Car();

    public void setId(long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCoordinatesX(float x){
        coordinates.setX(x);
    }
    public void setCoordinatesY(double y){
        coordinates.setY(y);
    }
    public void setCreationDate(Date date){ this.creationDate = date;}
    public void setRealHero(boolean realHero){this.realHero = realHero;}
    public void setHasToothPick(Boolean toothPick){this.hasToothPick = toothPick;}
    public void setImpactSpeed(Long impactSpeed){this.impactSpeed = impactSpeed;}
    public void setWeaponType(WeaponType type){this.weaponType = type;}
    public void setMood(Mood mood){this.mood = mood;}
    public void setCarName(String CarName){car.setName(CarName);}
    public void setCarCool(boolean cool){car.setCool(cool);}

    public long getId(){return id;}
    public String getName(){return name;}
    public float getCoordinatesX(){return coordinates.getX();}
    public double getCoordinatesY(){return coordinates.getY();}
    public Date getCreationDate(){return creationDate;}
    public Boolean getRealHero() {
        return realHero;
    }
    public Boolean getHasToothPick(){
        if(hasToothPick!=null) {
            return hasToothPick;
        } else {
            return null;
        }
    }
    public Long getImpactSpeed(){return impactSpeed;}
    public WeaponType getWeaponType() {
        return weaponType;
    }
    public Mood getMood() {
        return mood;
    }
    public String getCarName(){return car.getName();}
    public boolean getCarCool(){return car.getCool();}

    public String toothPickChecker(){
        String str;
        if(hasToothPick == null){
            str = "?????? ????????????????????";
        } else if(!hasToothPick){
            str = "false";
        } else if(hasToothPick){
            str = "true";
        } else {str = "?????? ????????????????????";}
        return str;
    }

    @Override
    public String toString(){
        String str = "ID: " + id + "\n" + "??????: " + name + "\n" + "????????????????????: " + "\n" + "X: " + coordinates.getX() + "\n" +
                "Y: " + coordinates.getY() + "\n" + "???????? ????????????????: " + creationDate.toString() + "\n" + "???????????? ??????????: " +
                realHero.toString() + "\n" + "?????????????? ????????????????????: " + this.toothPickChecker() + "\n" + "???????????????? ??????????????????????: " +
                impactSpeed + "\n" + "????????????: " + weaponType + "\n" + "????????????????????: " + mood + "\n" + "????????????: " + "\n" + "???????????????? ????????????: " +
                car.getName() + "\n" + "???????????????? ????????????: " + car.getCool() + "\n";
        return str;
    }

}
