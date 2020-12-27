package uz.personal.dto;

import com.google.gson.Gson;

public abstract class GenericCrudDto implements CrudDto {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
