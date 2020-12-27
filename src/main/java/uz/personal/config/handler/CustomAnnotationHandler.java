package uz.personal.config.handler;

import org.springframework.stereotype.Component;
import uz.personal.annotation.CustomField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAnnotationHandler {

    public List<Field> getAnnotatedFields(Object o) {
        List<Field> customFields = new ArrayList<>();
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(CustomField.class)) {
                customFields.add(field);
            }
        }
        return customFields;
    }

    public List<Field> getJsonFields(Object o) {
        List<Field> customFields = getAnnotatedFields(o);
        List<Field> jsonFields = new ArrayList<>();
        for (Field field : customFields) {
            if (field.getAnnotation(CustomField.class).json()) {
                jsonFields.add(field);
            }
        }
        return jsonFields;
    }
}
