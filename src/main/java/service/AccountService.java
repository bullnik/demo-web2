package service;

import model.UserProfile;
import service.dbService.DBException;
import service.dbService.DBService;
import service.dbService.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final DBService dbService = new DBService();
    private static final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    public AccountService() {

    }

    public void registerNewUser(UserProfile userProfile) {
        if (userProfile.getLogin().equals("")
                || userProfile.getEmail().equals("")
                || userProfile.getPass().equals("")) {
            throw new RuntimeException("User data not valid");
        }

        if (dbService.checkUserExists(userProfile.getLogin())) {
            throw new RuntimeException("User with same login already registered");
        }

        dbService.addUser(userProfile);
    }

    public UserProfile getUserByLoginAndPassword(String login, String password) {
        if (!dbService.checkUserExists(login)) {
            throw new RuntimeException("User profile not found");
        }
        UserProfile userProfile = dbService.getUser(login);
        if (!password.equals(userProfile.getPass())) {
            throw new RuntimeException("Password not correct");
        }
        return userProfile;
    }

    public UserProfile getUserBySessionId(String sessionId) {
        UserProfile userProfile = sessionIdToProfile.get(sessionId);
        if (userProfile == null) {
            throw new RuntimeException("User profile not found");
        }
        return userProfile;
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        if (sessionIdToProfile.containsKey(sessionId)) {
            throw new RuntimeException("Session on this profile already exists");
        }
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        if (!sessionIdToProfile.containsKey(sessionId)) {
            throw new RuntimeException("Session not found");
        }
        sessionIdToProfile.remove(sessionId);
    }
}
