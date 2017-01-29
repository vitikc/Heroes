package me.vitikc.heroes;

import javax.annotation.Nullable;

/**
 * Created by Vitikc on 09/Jan/17.
 */
public enum  HeroTypes {
    BARBARIAN("Barbarian"),LEGION_COMMANDER("Legion Commander"), YURNERO("Yurnero");
    private String name;
    HeroTypes(String name){
        this.name = name;
    }
    public static HeroTypes getType(String name){
        for (HeroTypes type : HeroTypes.values()){
            if (type.getName().equalsIgnoreCase(name)){
                return type;
            }
        }
        return null;
    }
    public String getName(){
        return this.name;
    }
}

