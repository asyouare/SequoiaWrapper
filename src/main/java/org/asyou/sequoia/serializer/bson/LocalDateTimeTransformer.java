package org.asyou.sequoia.serializer.bson;

import org.asyou.sequoia.common.Format;
import org.bson.Transformer;
import org.bson.types.BSONTimestamp;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by steven on 17/10/24.
 */
public class LocalDateTimeTransformer implements Transformer {
    @Override
    public Object transform(Object o) {
        if (o instanceof BSONTimestamp) {
            BSONTimestamp timestamp = (BSONTimestamp) o;
            String dateStr = LocalDateTime.ofInstant(timestamp.getDate().toInstant(), Format.DEFAULT_ZONE_ID)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss.")) + timestamp.getInc();
            return LocalDateTime.parse(dateStr, Format.dateTimeFormatterS6_Bson);
        }else if (o instanceof LocalDateTime){
            String str = ((LocalDateTime)o).format(Format.dateTimeFormatterS6_Bson);
            String dateStr = str.substring(0, str.lastIndexOf('.'));
            String incStr = str.substring(str.lastIndexOf('.') + 1);
            Date date = null;
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
        return null;
    }
}
