package ek.ianb.profileerrorhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(int id) {
        super("Profile not found: id=" + id);
    }
}
