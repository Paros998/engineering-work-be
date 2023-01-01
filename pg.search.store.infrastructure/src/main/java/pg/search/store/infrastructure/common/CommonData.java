package pg.search.store.infrastructure.common;

import lombok.experimental.UtilityClass;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class CommonData {
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static LocalDate parseFrom(final String from) {
        try {
            return LocalDate.parse(from, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (final DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Date: %s does not match the accepted format :%s",
                    from, CommonData.DATE_FORMAT));
        }
    }
}
