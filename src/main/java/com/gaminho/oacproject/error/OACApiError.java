package com.gaminho.oacproject.error;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class OACApiError {

    private String errorMsg;
    private HttpStatus status;
    private String errorDetail;

    public OACApiError() {
    }

    public OACApiError(String errorMsg, HttpStatus status, String errorDetail) {
        this.errorMsg = errorMsg;
        this.status = status;
        this.errorDetail = errorDetail;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OACApiError that = (OACApiError) o;
        return Objects.equals(errorMsg, that.errorMsg) &&
                status == that.status &&
                Objects.equals(errorDetail, that.errorDetail);
    }
}
