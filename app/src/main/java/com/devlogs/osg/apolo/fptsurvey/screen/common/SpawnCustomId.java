package com.devlogs.osg.apolo.fptsurvey.screen.common;

import com.devlogs.osg.apolo.fptsurvey.R;

public class SpawnCustomId {

    public static int getId (int order) throws Exception {
        switch (order) {
            case 1: {return R.id.customid1;}
            case 2: {return R.id.customid2;}
            case 3: {return R.id.customid3;}
            case 4: {return R.id.customid4;}
            case 5: {return R.id.customid5;}
            case 6: {return R.id.customid6;}
            case 7: {return R.id.customid7;}
            case 8: {return R.id.customid8;}
            case 9: {return R.id.customid9;}
            case 10: {return R.id.customid10;}
            default: throw new Exception("Your order is not supported yet (only 1-10)");
        }
    }
}
