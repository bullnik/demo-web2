package service;

import model.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private static final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    public AccountService() {
        try {
            UserProfile userProfile = new UserProfile("Public", "1", "1@mail.ru");
            registerNewUser(userProfile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void registerNewUser(UserProfile userProfile) {
        if (loginToProfile.containsKey(userProfile.getLogin())) {
            throw new RuntimeException("User with same login already registered");
        }

        if (userProfile.getLogin().equals("")
                || userProfile.getEmail().equals("")
                || userProfile.getPass().equals("")) {

            throw new RuntimeException("User data not valid");
        }

        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLoginAndPassword(String login, String password) {
        UserProfile userProfile = loginToProfile.get(login);
        if (userProfile == null) {
            throw new RuntimeException("User profile not found");
        }
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
