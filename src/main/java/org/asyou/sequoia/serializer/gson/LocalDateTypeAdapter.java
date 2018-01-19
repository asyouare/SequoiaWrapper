package org.asyou.sequoia.serializer.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.asyou.sequoia.common.Format;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by steven on 17/10/24.
 */
public class LocalDateTypeAdapter extends ABasicTypeAdapter<LocalDate> {

    @Override
    public LocalDate reading(JsonReader jsonReader) throws IOException {
        LocalDate date = null;
        jsonReader.beginObject();
        jsonReader.nextName();
        String value = jsonReader.nextString();
        date = LocalDate.parse(value, Format.dateFormatter);
        jsonReader.endObject();
        return date;
    }

    @Override
    public void writing(JsonWriter jsonWriter, LocalDate value) throws IOException {
        jsonWriter.beginObject().name("$date").value(value.format(Format.dateFormatter)).endObject();
    }
}
