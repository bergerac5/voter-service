package com.voting.voter.dtos;

public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;
    private int status;

    public ApiResponse(Boolean success, String message, T data, int status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public static <T> ApiResponse<T> success(String message, T data, int status) {
        return new ApiResponse<>(true, message, data, status);
    }

    public static <T> ApiResponse<T> error(String message, int status) {
        return new ApiResponse<>(false, message, null, status);
    }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
