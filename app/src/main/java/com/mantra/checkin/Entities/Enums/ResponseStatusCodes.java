package com.mantra.checkin.Entities.Enums;

/**
 * Created by nravishankar on 9/23/2016.
 */
public enum ResponseStatusCodes {
    Success,
    Error;

    public static ResponseStatusCodes fromInteger(int x) {
        switch(x) {
            case 0:
                return Success;
            case 1:
                return Error;
        }
        return null;
    }
}
