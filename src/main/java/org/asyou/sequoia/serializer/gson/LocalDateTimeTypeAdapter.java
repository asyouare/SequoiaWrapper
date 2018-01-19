package org.asyou.sequoia.serializer.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.asyou.sequoia.common.Format;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by steven on 17/10/24.
 */
public class LocalDateTimeTypeAdapter extends ABasicTypeAdapter<LocalDateTime>  {
    @Override
    public LocalDateTime reading(JsonReader jsonReader) throws IOException {
        LocalDateTime dateTime = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString().replace(":",".");
        String dateStr = value.substring(0, value.lastIndexOf('.'));
        String incStr = value.substring(value.lastIndexOf('.') + 1);
        String micro = String.format("%06d", Integer.valueOf(incStr));
        String dvalue = dateStr + "." + micro;
        dateTime = LocalDateTime.parse(dvalue, Format.dateTimeFormatterS6_Bson);
        jsonReader.endObject();
        return dateTime;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDateTime value) throws IOException {
        jsonWriter.beginObject().name("$timestamp").value(value.format(Format.dateTimeFormatterS6_Bson)).endObject();
    }
}
