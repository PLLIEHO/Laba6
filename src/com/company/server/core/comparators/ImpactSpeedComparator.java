package com.company.server.core.comparators;

import com.company.common.data.HumanBeing;

import java.util.Comparator;

public class ImpactSpeedComparator implements Comparator<HumanBeing> {
    @Override
    public int compare(HumanBeing o1, HumanBeing o2) {
        if(o1.getImpactSpeed() - o2.getImpactSpeed() > 0){
            return 1;
        } else return -1;
    }
}
