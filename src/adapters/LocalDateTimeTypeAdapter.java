package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
        private static final  DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME; //ofPattern("yyyy-MM-dd:HH.mm");

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            jsonWriter.value(localDate.format(dtf));
        }

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        String body = jsonReader.nextString();
        return LocalDateTime.parse(body, dtf);
    }

}
