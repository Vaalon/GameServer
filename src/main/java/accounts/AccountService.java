package accounts;

import dbService.dataSets.UsersDataSet;
import dbService.executor.DBException;
import dbService.executor.DBService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by talosar on 1/18/16.
 */
public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private final DBService dbService;

    public AccountService(DBService dbService) {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        this.dbService = dbService;
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
//        loginToProfile.put(userProfile.getLogin(), userProfile);
        dbService.addUser(userProfile.getLogin(), userProfile.getPass());
    }

    public UserProfile getUserByLogin(String login) throws DBException {
//        return loginToProfile.get(login);
        UsersDataSet usersDataSet = dbService.getUserByLogin(login);
        return usersDataSet == null ? null :
                new UserProfile(usersDataSet.getLogin(), usersDataSet.getPassword(), "test@email.com");
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
