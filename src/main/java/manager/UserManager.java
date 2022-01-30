package manager;

import model.*;

import javax.naming.NamingException;
import javax.transaction.*;

public class UserManager extends DaoManager {

    public UserManager() {
        super();
    }

    /**
     * Creates an new user
     *
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     * @return
     */
    public static boolean createUser(String email, String password, String firstname, String lastname) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {
        if (getUser(email) == null) {
            if (!(email.equals("") && password.equals("") && firstname.equals("") && lastname.equals(""))) {
                User user = new User(email, password, firstname, lastname);
                userDao.create(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if login is successful
     *
     * @param email
     * @param password
     * @return
     */
    public static User login(String email, String password) {
        User u = getUser(email);
        if (u != null && u.getPassword().equals(password))
            return u;
        return null;
    }

    /**
     * Edit user firstname or lastname
     *
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     * @return
     */
    public static boolean editUser(String email, String password, String firstname, String lastname) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        User user = getUser(email);
        if (user.getPassword().equals(password)) {
            if (user != null && firstname != null && lastname != null && firstname != "" && lastname != "") {
                user.setFirstname(firstname);
                user.setLastname(lastname);
                userDao.edit(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove user from colocation
     *
     * @param email
     * @param name
     * @return
     */
    public static boolean quitColocation(String email, String name) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        User user = getUser(email);
        Colocation colocation = getColocation(name);
        if (user != null && colocation != null && !isAdmin(user, colocation)) {
            colocation.getMembers().remove(user);
            colocationDao.edit(colocation);
            return true;
        }
        return false;
    }

    /**
     * User vote for a service
     *
     * @param email
     * @param serviceID
     * @param vote
     * @return
     */
    public static boolean vote(String email, Long serviceID, int vote) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        User user = getUser(email);
        Service service = serviceDao.find(serviceID);

        if (user == null || service == null) {
            return false;
        }

        Colocation colocation = service.getColocation();
        if (userIsIntoColoc(user, colocation) && !service.getUserWhoVoted().contains(user)) {
            if (vote == 1) service.upVote();
            else service.downVote();
            service.getUserWhoVoted().add(user);
            serviceDao.edit(service);

            int votes_number = service.getUserWhoVoted().size();
            int members_number = service.getColocation().getMembers().size();
            if (votes_number == members_number) { //All colocation members have voted
                //Service is removed if the majority votes against
                if (!service.isAccepted()) serviceDao.remove(service);
            }

            return true;
        }
        return false;
    }

    public static boolean valid(String email, Long achievedServiceID, boolean validated) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        User validator = getUser(email);
        AchievedService achievedService = achievedServiceDao.find(achievedServiceID);
        User from = achievedService.getFrom();
        Colocation colocation = achievedService.getService().getColocation();

        if (validator == null || achievedService == null || from == null) {
            return false;
        }

        Score userScore = getUserScoreIntoColocation(colocation,from);

        // validator != user who achieved the service
        // and is into the list of users who benefit of the service
        if (!validator.equals(from) && achievedService.getTo().contains(validator)) {
            if (validated) {
                int cost = achievedService.getService().getCost();
                achievedService.setValidated(true);
                userScore.addToScore(cost);
                achievedServiceDao.edit(achievedService);
                userDao.edit(from);
                scoreDao.edit(userScore);
                for (User user : achievedService.getTo()) {
                    Score score = getUserScoreIntoColocation(colocation,user);
                    score.decreaseScore(cost);
                }
                //If service is achieved, we remove the service from table
                serviceDao.remove(achievedService.getService());
            } else {
                achievedService.setValidated(false);
                achievedServiceDao.edit(achievedService);
            }

            return true;
        }

        return false;
    }

    public static boolean editProfilePicture(String email, String picture) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, NotSupportedException, RollbackException {
        User user = getUser(email);

        if (user == null || picture == null) {
            return false;
        }

        Image image = downloadImg(picture);
        if(image != null){
            user.setImage(image);
            userDao.edit(user);
        } else {
            return false;
        }

        return true;

    }
}
