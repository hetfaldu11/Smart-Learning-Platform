package com.fm.smartlearningplatform.exceptionhandler.handler;


import com.fm.smartlearningplatform.exceptionhandler.dto.ErrorResponse;
import com.fm.smartlearningplatform.exceptionhandler.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // ────────────────────── MethodArgument Not ValidException ────────────────────────────────────────────────


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse response = buildError(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_FAILED",
                "Validation failed.",
                request,
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    // ────────────────────── HttpMessageNotReadableException ────────────────────────────────────────────────


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = buildError(
                HttpStatus.BAD_REQUEST,
                "MALFORMED_JSON",
                "Request body is missing or malformed.",
                request
        );

        return ResponseEntity.badRequest().body(response);
    }

    // ────────────────────── ResourceNotFoundException ────────────────────────────────────────────────


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = buildError(
                HttpStatus.NOT_FOUND,
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                request
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // ────────────────────── DuplicateResourceException ────────────────────────────────────────────────


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateResourceException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = buildError(
                HttpStatus.CONFLICT,
                "DUPLICATE_RESOURCE",
                ex.getMessage(),
                request
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    // ────────────────────── EmailNotVerifiedException ────────────────────────────────────────────────


    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotVerified(
            EmailNotVerifiedException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.badRequest().body(
                buildError(
                        HttpStatus.BAD_REQUEST,
                        "EMAIL_NOT_VERIFIED",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── PhoneNotVerifiedException ────────────────────────────────────────────────


    @ExceptionHandler(PhoneNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> handlePhoneNotVerified(
            PhoneNotVerifiedException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.badRequest().body(
                buildError(
                        HttpStatus.BAD_REQUEST,
                        "PHONE_NOT_VERIFIED",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── InvalidOtpException ────────────────────────────────────────────────


    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOtp(
            InvalidOtpException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.badRequest().body(
                buildError(
                        HttpStatus.BAD_REQUEST,
                        "INVALID_OTP",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── OtpExpiryException────────────────────────────────────────────────


    @ExceptionHandler(OtpExpiryException.class)
    public ResponseEntity<ErrorResponse> handleOtpExpired(
            OtpExpiryException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.badRequest().body(
                buildError(
                        HttpStatus.BAD_REQUEST,
                        "OTP_EXPIRED",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── InvalidPasswordException────────────────────────────────────────────────


    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(
            InvalidPasswordException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                buildError(
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_PASSWORD",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── OtpWaitException────────────────────────────────────────────────

    @ExceptionHandler(OtpWaitException.class)
    public ResponseEntity<ErrorResponse> handleOtpWait(
            OtpWaitException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.badRequest().body(
                buildError(
                        HttpStatus.BAD_REQUEST,
                        "OTP_WAIT",
                        ex.getMessage(),
                        request
                )
        );
    }

// ────────────────────── SessionExpiredException────────────────────────────────────────────────

    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<ErrorResponse> handleSessionExpired(
            SessionExpiredException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                buildError(
                        HttpStatus.UNAUTHORIZED,
                        "SESSION_EXPIRED",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── SessionRevokedException────────────────────────────────────────────────


    @ExceptionHandler(SessionRevokedException.class)
    public ResponseEntity<ErrorResponse> handleSessionRevoked(
            SessionRevokedException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                buildError(
                        HttpStatus.UNAUTHORIZED,
                        "SESSION_REVOKED",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── UnauthorizedException────────────────────────────────────────────────


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                buildError(
                        HttpStatus.UNAUTHORIZED,
                        "UNAUTHORIZED",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── RateLimitExceededException────────────────────────────────────────────────


    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRateLimit(
            RateLimitExceededException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(
                buildError(
                        HttpStatus.TOO_MANY_REQUESTS,
                        "RATE_LIMIT_EXCEEDED",
                        ex.getMessage(),
                        request
                )
        );
    }

    // ────────────────────── Exception────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            HttpServletRequest request
    ) {

        ErrorResponse response = buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred.",
                request
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // ────────────────────── Helper ────────────────────────────────────────────────

    private ErrorResponse buildError(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request
    ) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .code(code)
                .message(message)
                .path(request.getRequestURI())
                .build();
    }

    private ErrorResponse buildError(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request,
            Map<String, String> errors
    ) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .code(code)
                .message(message)
                .path(request.getRequestURI())
                .errors(errors)
                .build();
    }
}