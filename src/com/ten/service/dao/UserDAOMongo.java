package com.ten.service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metric;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ten.service.configuration.DatabaseConfig.DatabaseProperties;
import com.ten.service.model.User;

@Repository
@EnableConfigurationProperties(DatabaseProperties.class)
public class UserDAOMongo {

    private final MongoTemplate db;

    @Autowired
    private DatabaseProperties config;

    @Autowired
    public UserDAOMongo(MongoTemplate mongo) {
        db = mongo;
    }

    public void create(User user) {
        db.insert(user);
    }

    public User get(final String id) {
        return db.findOne(new Query(Criteria.where("id").is(id)), User.class, config.getDbUserCollection());
    }

    public List<User> listAll() {
        return db.findAll(User.class, config.getDbUserCollection());
    }

    public void deleteAll() {
        for (User user : listAll()) {
            db.remove(user, config.getDbUserCollection());
        }
    }

    public GeoResults<User> listNear(String id, double distance, Metric distanceMetric, int limit) {
        User user = get(id);
        NearQuery query =
                NearQuery.near(new Point(locationToPoint(user.getLocation())))
                        .maxDistance(new Distance(distance, distanceMetric)).num(limit);

        return db.geoNear(query, User.class, config.getDbUserCollection());
    }
    
    private static Point locationToPoint(double[] location) {
        return new Point(location[0], location[1]);
    }

    public void delete(String id) {
        User user = get(id);
        db.remove(user, config.getDbUserCollection());
    }

    public User updateLocation(String id, double x, double y) {
        User user = get(id);
        user.setLocation(new double[] {x, y});
        user.setLastLoginTime(System.currentTimeMillis());
        db.save(user);
        return user;
    }
}
