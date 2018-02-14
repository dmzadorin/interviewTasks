package ru.dmzadorin.interview.tasks.quoteService.model;

/**
 * Created by Dmitry Zadorin on 15.02.2018
 */
public class ErrorResponse {
    private final String errorMsg;

    public ErrorResponse(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
