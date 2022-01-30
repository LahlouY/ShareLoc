package manager;

import dao.AbstractDao;
import model.*;

import javax.naming.NamingException;
import javax.transaction.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DaoManager {

    //Dao
    protected static final AbstractDao<User> userDao = new AbstractDao(User.class);
    protected static final AbstractDao<Colocation> colocationDao = new AbstractDao(Colocation.class);
    protected static final AbstractDao<Service> serviceDao = new AbstractDao(Service.class);
    protected static final AbstractDao<AchievedService> achievedServiceDao = new AbstractDao(AchievedService.class);
    protected static final AbstractDao<Image> imageDao = new AbstractDao(Image.class);
    protected static final AbstractDao<Message> messageDao = new AbstractDao(Message.class);
    protected static final AbstractDao<Score> scoreDao = new AbstractDao(Score.class);

    //Table names
    protected static final String TABLE_EMAIL = "email";
    protected static final String TABLE_NAME = "name";

    //Constants
    protected static final int DEFAULT_SCORE = 100;

    public DaoManager() {
    }

    /* Common methods between managers */

    /**
     * Return an user instance by email
     *
     * @param email
     * @return
     */
    public static User getUser(String email) {
        if (email == null) return null;
        User u = userDao.findByTable(TABLE_EMAIL, email);
        return u;
    }

    /**
     * Return a colocation instance by name
     *
     * @param name
     * @return
     */
    public static Colocation getColocation(String name) {
        if (name == null) return null;
        Colocation colocation = colocationDao.findByTable(TABLE_NAME, name);
        return colocation;
    }

    /**
     * Return True if user is the colocation administrator
     *
     * @param user
     * @param colocation
     * @return
     */
    public static boolean isAdmin(User user, Colocation colocation) {
        return user.getEmail().equals(colocation.getAdmin().getEmail());
    }

    /**
     * Return true if user is into the colocation
     *
     * @param user
     * @param colocation
     * @return
     */
    public static boolean userIsIntoColoc(User user, Colocation colocation) {
        int cpt = 0;
        for (User u : colocation.getMembers()) {
            if (u.getEmail().equals(user.getEmail())) {
                cpt++;
            }
        }
        return cpt == 1;
    }

    /**
     * Return true if service is already achieved
     *
     * @param serviceID
     * @return
     */
    public static boolean serviceIsAlreadyAchieved(Long serviceID) {
        int cpt = 0;
        for (AchievedService achievedService : achievedServiceDao.findAll()) {
            if (achievedService.getService().getId() == serviceID) {
                cpt++;
            }
        }
        return cpt == 1;
    }

    /**
     * Store into the DB a new Image entity
     *
     * @param imgPath
     * @return
     */
    public static Image downloadImg(String imgPath) {
        File img = new File(imgPath);
        try {
            byte[] bytes = Files.readAllBytes(img.toPath());
            Image image = new Image(bytes);
            imageDao.create(image);
            return image;

        } catch (IOException | HeuristicRollbackException | SystemException | HeuristicMixedException | RollbackException | NotSupportedException | NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return the user Score instance into a colocation
     *
     * @param colocation
     * @param user
     * @return
     */
    public static Score getUserScoreIntoColocation(Colocation colocation, User user) {
        if (userIsIntoColoc(user, colocation)) {
            for (Score score : user.getScores()) {
                if (score.getColocation().getId() == colocation.getId()) {
                    return score;
                }
            }
        }
        return null;
    }

}
