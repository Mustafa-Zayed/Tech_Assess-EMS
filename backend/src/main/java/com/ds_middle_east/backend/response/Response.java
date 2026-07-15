package com.ds_middle_east.backend.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Generic response class for holding standard API response data
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON serialization
public class Response<T> {
    private int statusCode;
    private String message;
    private T data;
}