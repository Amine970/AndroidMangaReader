package com.example.mangareader;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static String[] toStringArray(String string) {
        return string == null ? null : string.split(",");
    }

    @TypeConverter
    public static String stringArrayToString(String[] string) {
        String rep = "";
        if(string == null)
            return null;
        for(int i = 0; i < string.length; i++) {
            rep += string[i];
            if(i != string.length -1)
                rep += ",";
        }
        return rep;
    }
}
