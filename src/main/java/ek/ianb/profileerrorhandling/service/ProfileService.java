package ek.ianb.profileerrorhandling.service;


import ek.ianb.profileerrorhandling.exceptions.DatabaseOperationException;
import ek.ianb.profileerrorhandling.exceptions.DuplicateProfileException;
import ek.ianb.profileerrorhandling.exceptions.ProfileNotFoundException;
import ek.ianb.profileerrorhandling.model.Profile;
import ek.ianb.profileerrorhandling.repository.ProfileRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository repo;

    public ProfileService(ProfileRepository repo) {
        this.repo = repo;
    }

    public List<Profile> list() {
        return repo.findAll();
    }

    public Profile get(int id) {
        Profile p = repo.findById(id);
        if (p == null) throw new ProfileNotFoundException(id);
        return p;
    }

    public Profile create(Profile profile) {
        try {
            return repo.insert(profile);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProfileException(
                    "A profile with this name or email already exists."
            );
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("Failed to create profile", dataAccessException);
        }
    }

    public Profile update(int id, Profile p) {
        Profile existing = get(id);
        existing.setName(p.getName());
        existing.setEmail(p.getEmail());
        try {
            int rows = repo.update(existing);
            if (rows == 0) throw new ProfileNotFoundException(id);
            return existing;
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProfileException(
                    "A profile with this name or email already exists."
            );
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to update profile", e);
        }
    }

    public void delete(int id) {
        try {
            int rows = repo.deleteById(id);
            if (rows == 0) throw new ProfileNotFoundException(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to delete profile", e);
        }
    }
}
