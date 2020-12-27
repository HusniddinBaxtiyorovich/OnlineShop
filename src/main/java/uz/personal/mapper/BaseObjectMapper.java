package uz.personal.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.personal.annotation.CustomField;
import uz.personal.config.handler.CustomAnnotationHandler;
import uz.personal.utils.BaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class BaseObjectMapper {

    private final CustomAnnotationHandler annotationHandler;
    private final BaseUtils utils;

    @Autowired
    public BaseObjectMapper(CustomAnnotationHandler annotationHandler, BaseUtils utils) {
        this.annotationHandler = annotationHandler;
        this.utils = utils;
    }

    @SuppressWarnings("unchecked")
    private <C> C toDto(Object entity, String mapperName, String mapperMethodName) throws Exception {
        Object mapper = BaseUtils.getBean(mapperName);
        Method method = mapper.getClass().getMethod(mapperMethodName, entity.getClass());
        return (C) method.invoke(mapper, entity);
    }

    public <C> C toDto(Object[] resultSet, String mapperMethodName) throws Exception {
        // first step MUST main select object as T ex: select T from table T
        C item;
        List<Object> resultSetList = Arrays.asList(resultSet);
        Object entity = resultSetList.stream().findFirst().orElseThrow(RuntimeException::new);
        item = toDto(entity, generateMapperBeanName(entity), mapperMethodName);

        // other steps
        List<Field> customFields = annotationHandler.getAnnotatedFields(item);

        for (int i = 1; i < resultSetList.size(); i++) {
            Field field = customFields.get(i - 1);
            field.setAccessible(true);
            Object object = resultSetList.get(i);
            if (object != null && object.getClass().getName().equals(field.getType().getName())) {
                try {
                    field.set(item, object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (object != null && object.getClass().getSimpleName().equals(field.getType().getSimpleName().replace("Dto", ""))) {
                String mapperName = field.getAnnotation(CustomField.class).mapperName();
                field.set(item, toDto(object, generateMapperBeanName(object), mapperName));
            }
        }
        List<Field> jsonFields = annotationHandler.getJsonFields(item);
        if (jsonFields != null && jsonFields.size() > 0) {
            fromJson(jsonFields, item, customFields);
        }

        return item;
    }

    private <C> void fromJson(List<Field> jsonFields, C dto, List<Field> customFields) throws Exception {
        for (Field jsonField : jsonFields) {
            fromJsonField(jsonField, dto, customFields);
        }
    }

    private <C> void fromJsonField(Field jsonField, C dto, List<Field> customFields) throws Exception {
        jsonField.setAccessible(true);
        String params = (String) jsonField.get(dto);
        HashMap<String, JsonNode> map = utils.fromStringToHashMap(params);
        Object[] keys = map.keySet().toArray();
        customFields.forEach(field -> {
            for (Object key : keys) {
                if (key.equals(field.getName())) {
                    try {
                        field.setAccessible(true);
                        field.set(dto, utils.nodeToObject(map.get(key), field));
                    } catch (IllegalAccessException | JsonProcessingException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public <C> List<C> toDtoList(List<Object[]> resultList, String mapperMethodName) throws Exception {
        long length = resultList.size();
        List<C> dtoList = new ArrayList<>();
        for (Object[] objects : resultList) {
            dtoList.add(toDto(objects, mapperMethodName));
        }
        return dtoList;
    }

    private String generateMapperBeanName(Object o) {
        String dtoMapperName = o.getClass().getSimpleName();
        dtoMapperName = dtoMapperName.substring(0, 1).toLowerCase() + dtoMapperName.substring(1);
        dtoMapperName += "MapperImpl";
        return dtoMapperName;
    }
}
