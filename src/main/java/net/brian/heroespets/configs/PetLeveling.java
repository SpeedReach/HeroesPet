package net.brian.heroespets.configs;

import java.util.HashMap;
import java.util.Map;

public class PetLeveling {

    private static HashMap<Integer,HashMap<Integer,Integer>> levelMap;

    static {

    }

    public static int getRequiredExp(int tier,int currentLevel){
        HashMap<Integer,Integer> map = levelMap.get(tier);
        if(map != null){
            return map.getOrDefault(currentLevel+1,-1);
        }
        return -1;
    }

    public static int getMaxLevel(int tier){
        return 50;
    }

}
