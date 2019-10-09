package util;

import java.util.Calendar;

public class DateUtil {

    private String name;

    public static int getDate(){
        Calendar calendar = Calendar.getInstance();
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        weekDay = weekDay - 1;
        if(weekDay == 0){
            weekDay = 7;
        }
        return weekDay;
    }
}
