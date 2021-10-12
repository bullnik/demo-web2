package service.dbService;

import model.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {

    private final Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UserProfile getUserProfile(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        UsersDataSet usersDataSet = (UsersDataSet) criteria
                .add(Restrictions.eq("login", login))
                .uniqueResult();

        if (usersDataSet == null) {
            throw new RuntimeException("User with this login not found");
        }
        return new UserProfile(usersDataSet.getLogin(),
                usersDataSet.getPassword(),
                usersDataSet.getEmail());
    }

    public void insertUser(UserProfile userProfile) throws HibernateException {
        session.save(new UsersDataSet(userProfile));
    }

    public boolean checkUserExists(String login) {
        try {
            getUserProfile(login);
        } catch (Exception e) {
            if (!e.getMessage().equals("User with this login not found")) {
                throw new RuntimeException(e.getMessage());
            }
            return false;
        }
        return true;
    }
}