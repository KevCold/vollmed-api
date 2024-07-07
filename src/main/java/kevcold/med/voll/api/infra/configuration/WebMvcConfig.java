package kevcold.med.voll.api.infra.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
    }

    public class LocalDateTimeFormatter implements org.springframework.format.Formatter<LocalDateTime> {

        private final DateTimeFormatter formatter;

        public LocalDateTimeFormatter() {
            this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        }

        @Override
        public LocalDateTime parse(String text, Locale locale) throws ParseException {
            return LocalDateTime.parse(text, formatter);
        }

        @Override
        public String print(LocalDateTime object, Locale locale) {
            return formatter.format(object);
        }
    }
}