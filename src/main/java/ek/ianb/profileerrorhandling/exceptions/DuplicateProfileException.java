package ek.ianb.profileerrorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateProfileException extends RuntimeException {
    public DuplicateProfileException(String message) {
        super(message);
    }
}
