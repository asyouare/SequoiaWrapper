package org.asyou.sequoia.transform.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.asyou.sequoia.common.Format;
import org.asyou.sequoia.type.DateTimeFactory;
import org.bson.types.BSONTimestamp;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by steven on 17/9/15.
 */
public class BSONTimestampTypeAdapter extends ABasicTypeAdapter<BSONTimestamp> {
    @Override
    public BSONTimestamp reading(JsonReader jsonReader) throws IOException {
        BSONTimestamp timestamp = null;
        jsonReader.beginObject();
        jsonReader.nextName();

        String value = jsonReader.nextString();

        String dateStr = value.substring(0, value.lastIndexOf('.'));
        String incStr = value.substring(value.lastIndexOf('.') + 1);
        Date date = null;
        try {
            date = Format.simpleDateTimeFormatS6_Bson().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int seconds = null == date ? 0 : (int)(date.getTime()/1000);
        int inc = Integer.parseInt(incStr);
        timestamp = new BSONTimestamp(seconds, inc);

//        Timestamp ts = Timestamp.valueOf(LocalDateTime.parse(value, Format.dateTimeFormatterS6_Bson));
//        timestamp = new BSONTimestamp((int)(ts.getTime()/1000),0);

        jsonReader.endObject();
        return timestamp;
    }

    @Override
    public void writing(JsonWriter jsonWriter, BSONTimestamp value) throws IOException {
        String micro = String.format("%06d", Integer.valueOf(value.getInc()));
        jsonWriter.beginObject().name("$timestamp").value(DateTimeFactory.format(value.getDate(),"yyyy-MM-dd-HH:mm:ss.") + micro).endObject();
    }
}
