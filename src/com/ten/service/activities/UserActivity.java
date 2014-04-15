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

    private final static Function<GeoResult<User>, User> GEO_TO_USER =
            new Function<GeoResult<User>, User>() {
                public User apply(GeoResult<User> geo) {
                    return geo.getContent();
                }
            };

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestParam(value = "socialId", required = true) String socialId,
            @RequestParam(value = "userName", required = true) String userName, @RequestParam(
                    value = "x", required = true) double x, @RequestParam(value = "y",
                    required = true) double y) {
        long currentTime = System.currentTimeMillis();
        User user =
                new User(String.valueOf(IdUtils.generateUserId(socialId)), userName, currentTime,
                        currentTime, x, y);
        userDAO.create(user);
        return user;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "id") String id) {
        return userDAO.get(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> findUsers(@RequestParam(value = "x", required = true) double x,
            @RequestParam(value = "y", required = true) double y, @RequestParam(value = "limit",
                    required = false, defaultValue = "10") int limit) {
        GeoResults<User> results = userDAO.listNear(x, y, DITANCE, DISTANCE_METRICS, limit);
        List<GeoResult<User>> lstContents = results.getContent();
        return Lists.transform(lstContents, GEO_TO_USER);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "id") String id) {
        userDAO.delete(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public void updateLocation(@PathVariable(value = "id") String id, @RequestParam(value = "x",
            required = true) double x, @RequestParam(value = "y", required = true) double y) {
        userDAO.updateLocation(id, x, y);
    }
}
