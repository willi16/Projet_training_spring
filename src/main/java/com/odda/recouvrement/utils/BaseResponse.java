package com.odda.recouvrement.utils;


import lombok.*;
import java.util.Objects;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
@Getter
@Setter
public class BaseResponse<T> {
    private int statusCode;
    private String description;
    private T data;



    //code Techinque
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseResponse<?> that = (BaseResponse<?>) o;
        return statusCode == that.statusCode && Objects.equals(description, that.description) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, description, data);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "statusCode=" + statusCode +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }

}
