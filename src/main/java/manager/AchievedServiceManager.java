package manager;

import model.AchievedService;
import model.Image;
import model.Service;
import model.User;

import javax.naming.NamingException;
import javax.transaction.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AchievedServiceManager extends DaoManager {

    public AchievedServiceManager() {
        super();
    }


    public static boolean newAchievedService(String email, Long serviceID,
                                             String date, String picture, List<String> to_emails) throws HeuristicRollbackException, SystemException, HeuristicMixedException, NamingException, RollbackException, NotSupportedException {

        Service service = serviceDao.find(serviceID);
        User user = getUser(email);

        if (user == null || service == null || date == null || serviceIsAlreadyAchieved(serviceID)) {
            return false;
        }

        //users who benefit from the service
        List<User> to = new ArrayList<User>();
        for (String user_email : to_emails) {
            User to_user = getUser(user_email);
            if (to_user != null) to.add(to_user);
        }

        Image image = downloadImg(picture);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.FRANCE);
        Date achieved_date = null;
        try {
            achieved_date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new java.sql.Timestamp(achieved_date.getTime());

        AchievedService achievedService = new AchievedService(service, user, to,
                timestamp, image, false);
        achievedServiceDao.create(achievedService);

        return true;
    }
}
