package org.asyou.sequoia.serializer.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.asyou.sequoia.common.Assertions;
import org.asyou.sequoia.common.Format;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by steven on 17/7/18.
 */
public class DateTypeAdapter extends ABasicTypeAdapter<Date> {
    @Override
    public Date reading(final JsonReader jsonReader) throws IOException {
        Date date = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        try {
            date = Format.simpleDateFormat().parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        jsonReader.endObject();
        return date;
    }

    @Override
    public void writing(final JsonWriter jsonWriter, final Date value) throws IOException {
        String dateStr = Assertions.mustBeLength(Format.simpleDateFormat().format(value),Format.TEMPLATE_DATE.length());
        jsonWriter.beginObject().name("$date").value(dateStr).endObject();
    }
}
