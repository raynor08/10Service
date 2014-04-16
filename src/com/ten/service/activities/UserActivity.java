package com.ten.service.activities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.ten.service.dao.UserDAOMongo;
import com.ten.service.model.User;
import com.ten.service.utils.IdUtils;

@RestController
public class UserActivity {
    @Autowired
    private UserDAOMongo userDAO;

    // TODO: maybe expose these through API as parameter
    private final static double DITANCE = 10;
    private final static Metrics DISTANCE_METRICS = Metrics.MILES;
    private final static double END_OF_THE_WORLD = 10000;
    private final static int WE_ARE_THE_SPARTANS = 300;

    private final static Function<GeoResult<User>, User> GEO_TO_USER =
            new Function<GeoResult<User>, User>() {
                public User apply(GeoResult<User> geo) {
                    return geo.getContent();
                }
            };

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestParam(value = "socialId", required = true) String socialId,
            @RequestParam(value = "x", required = true) double x, @RequestParam(value = "y",
                    required = true) double y) {
        long currentTime = System.currentTimeMillis();
        User user =
                new User(String.valueOf(IdUtils.generateUserId(socialId)), socialId, currentTime,
                        currentTime, new double[] {x, y});
        userDAO.create(user);
        return user;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "id") String id) {
        return userDAO.get(id);
    }

    @RequestMapping(value = "/user/{id}/ten", method = RequestMethod.GET)
    public List<User> findNearbyUsers(@PathVariable(value = "id") String id, @RequestParam(
            value = "limit", required = false, defaultValue = "30") int limit) {
        GeoResults<User> results = userDAO.listNear(id, DITANCE, DISTANCE_METRICS, limit);
        return Lists.transform(results.getContent(), GEO_TO_USER);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public User login(@RequestParam(value = "socialId", required = true) String socialId,
            @RequestParam(value = "x", required = true) double x, @RequestParam(value = "y",
                    required = true) double y) {
        final String id = String.valueOf(IdUtils.generateUserId(socialId));
        User user = getUser(id);
        if (user == null) {
            user = createUser(socialId, x, y);
        } else {
            user = updateLocation(id, x, y);
        }

        return user;
    }

    @RequestMapping(value = "/user/{id}/eleven", method = RequestMethod.GET)
    public User findFarthestUser(@PathVariable(value = "id") String id) {
        GeoResults<User> results =
                userDAO.listNear(id, END_OF_THE_WORLD, DISTANCE_METRICS, WE_ARE_THE_SPARTANS);
        List<User> users = Lists.transform(results.getContent(), GEO_TO_USER);

        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(users.size() - 1);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> listAllUsers() {
        return userDAO.listAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public void deleteAll() {
        userDAO.deleteAll();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "id") String id) {
        userDAO.delete(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public User updateLocation(@PathVariable(value = "id") String id, @RequestParam(value = "x",
            required = true) double x, @RequestParam(value = "y", required = true) double y) {
        return userDAO.updateLocation(id, x, y);
    }
}
