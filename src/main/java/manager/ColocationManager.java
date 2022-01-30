package manager;

import model.Colocation;
import model.Score;
import model.User;

import javax.naming.NamingException;
import javax.transaction.*;

public class ColocationManager extends DaoManager {

    public ColocationManager() {
        super();
    }

    /**
     * Creates a new colocation
     *
     * @param name
     * @param admin_email
     * @return
     */
    public static boolean createColocation(String name, String admin_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        User admin = getUser(admin_email);
        if (admin != null && getColocation(name) == null) {
            if (!(name.equals(""))) {
                Colocation colocation = new Colocation(name, admin);
                colocationDao.create(colocation);
                Score score = new Score(colocation, DEFAULT_SCORE);
                admin.getScores().add(score);
                userDao.edit(admin);
                return true;
            }
        }
        return false;
    }

    /**
     * Invite an user into a colocation if user exist
     *
     * @param name
     * @param admin_email
     * @param email
     * @return
     */
    public static boolean inviteUserIntoColocation(String name, String admin_email, String email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        Colocation colocation = getColocation(name);
        User invited = getUser(email);
        User admin = getUser(admin_email);
        Score score = new Score(colocation, DEFAULT_SCORE);

        if (admin == null || invited == null || colocation == null) {
            return false;
        }

        if (isAdmin(admin, colocation) && !userIsIntoColoc(invited, colocation)) {
            colocation.addMember(invited);
            colocationDao.edit(colocation);
            invited.getScores().add(score);
            userDao.edit(invited);
            return true;
        }

        return false;
    }

    /**
     * If user has admin rights, remove the colocation
     *
     * @param name
     * @param email
     * @return
     */
    public static boolean removeColocation(String name, String email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        Colocation colocation = getColocation(name);
        if (colocation != null && email.equals(colocation.getAdmin().getEmail())) {
            colocationDao.remove(colocation);
            return true;
        }
        return false;
    }

    /**
     * IF user has admin rights, edit the colocation name
     *
     * @param name
     * @param admin_email
     * @param newName
     * @return
     */
    public static boolean editColocationName(String name, String admin_email, String newName) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        Colocation colocation = getColocation(name);
        User admin = getUser(admin_email);
        if (colocation != null && admin != null && admin.getEmail().equals(colocation.getAdmin().getEmail())) {
            colocation.setName(newName);
            colocationDao.edit(colocation);
            return true;
        }
        return false;
    }

    /**
     * If user has admin rights, remove a member from the colocation
     *
     * @param name
     * @param admin_email
     * @param member_email
     * @return
     */
    public static boolean removeMemberFromColoc(String name, String admin_email, String member_email) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        User admin = getUser(admin_email);
        User toDelete = getUser(member_email);
        Colocation colocation = getColocation(name);

        if (colocation != null && admin != null && toDelete != null) {
            if (isAdmin(admin, colocation) && userIsIntoColoc(toDelete, colocation)) {
                colocation.getMembers().remove(toDelete);
                colocationDao.edit(colocation);
                return true;
            }
        }
        return false;
    }

    /**
     * Return a string message according to the user with the highest score in the colocation
     *
     * @param email
     * @param name
     * @return
     */
    public static String getBestUser(String email, String name) {
        User user = getUser(email);
        Colocation colocation = getColocation(name);

        if (user == null || colocation == null || !userIsIntoColoc(user, colocation)) {
            return null;
        }

        if (colocation.getMembers().size() > 1) {
            User best = colocation.getMembers().get(0);
            int score = getUserScoreIntoColocation(colocation, best).getScore();
            for (User u : colocation.getMembers()) {
                if (getUserScoreIntoColocation(colocation, best).getScore()
                        < getUserScoreIntoColocation(colocation, u).getScore()) {
                    best = u;
                    score = getUserScoreIntoColocation(colocation, best).getScore();
                }
            }
            return best.toString() + ", score = " + score;
        } else {
            return "colocation size <= 1";
        }
    }
}
