package adapter.type;

import org.asyou.sequoia.common.Format;
import org.asyou.sequoia.type.DateTimeFactory;
import org.bson.types.BSONTimestamp;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by steven on 17/10/23.
 */
public class TestDateTime {
    @Test
    public void Test_Parse(){
        String str = "2017-10-23 11.48.15.008000";
        LocalDateTime dt = LocalDateTime.of(2017,10,23,11,48,15,8000);
        System.out.println(dt.toString());
        Pattern p = Pattern.compile("/^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d).[0-5]\\d.[0-5]\\d$/");
        Matcher m = p.matcher(dt.toString());
        if (m.find())
            System.out.println(m.group());
    }

    @Test
    public void Test_Timestamp(){
        String str = "2017-10-23-11.48.15.080000";
        LocalDateTime dateTime = LocalDateTime.parse(str, Format.dateTimeFormatterS6_Bson);
        System.out.println(dateTime.toLocalTime());
        System.out.println(Timestamp.valueOf(dateTime).getTime() / 1000);
        System.out.println(dateTime.toLocalTime().getNano() / 1000);
        System.out.println(String.format("%06d",dateTime.toLocalTime().getNano() / 1000));

        System.out.println();
        BSONTimestamp timestamp = new BSONTimestamp((int)(DateTimeFactory.getTime(dateTime)/1000),0);
        System.out.println(timestamp);

        Format.simpleDateTimeFormatS6_Bson().format(LocalDateTime.now());
    }

    public BSONTimestamp transform() {
        String str = LocalDateTime.now().format(Format.dateTimeFormatterS6_Bson);
        String dateStr = str.substring(0, str.lastIndexOf('.'));
        String incStr = str.substring(str.lastIndexOf('.') + 1);
        Date date = null;
        System.out.println(str);
        try {
            date = Format.simpleDateTimeFormatSecond_Bson().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int seconds = (int)(date.getTime()/1000);
        int inc = Integer.parseInt(incStr);
        BSONTimestamp timestamp = new BSONTimestamp(seconds, inc);
        return timestamp;
    }
}
