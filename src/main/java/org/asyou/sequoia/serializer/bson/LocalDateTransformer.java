package org.asyou.sequoia.serializer.bson;

import org.asyou.sequoia.common.Format;
import org.bson.Transformer;
import org.bson.types.BSONTimestamp;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by steven on 17/10/24.
 */
public class LocalDateTransformer implements Transformer {
    @Override
    public Object transform(Object o) {
        if (o instanceof Date) {
            Date date = (Date) o;
            String dateStr = date.toString();
            return LocalDate.parse(dateStr, Format.dateFormatter);
        }else if (o instanceof LocalDate){
            String dateStr = ((LocalDate)o).format(Format.dateFormatter);
            Date date = null;
            try {
                date = Format.simpleDateFormat().parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }
}
