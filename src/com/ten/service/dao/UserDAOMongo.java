package com.ten.service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metric;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ten.service.model.User;

@Repository
public class UserDAOMongo {

    private final MongoTemplate db;

    private static final String COLLECTION_NAME = "users";

    @Autowired
    public UserDAOMongo(MongoTemplate mongo) {
        db = mongo;
    }

    public void create(User user) {
        db.save(user);
    }

    public User get(final String id) {
        return db.findOne(new Query(Criteria.where("id").is(id)), User.class, COLLECTION_NAME);
    }

    public List<User> listAll() {
        return db.findAll(User.class, COLLECTION_NAME);
    }

    public void deleteAll() {
        for (User user : listAll()) {
            db.remove(user, COLLECTION_NAME);
        }
    }

    public GeoResults<User> listNear(double x, double y, double distance, Metric distanceMetric,
            int limit) {
        NearQuery query =
                NearQuery.near(new Point(x, y)).maxDistance(new Distance(distance, distanceMetric))
                        .num(limit);

        return db.geoNear(query, User.class, COLLECTION_NAME);
    }

    public void delete(String id) {
        User user = get(id);
        db.remove(user, COLLECTION_NAME);
    }

    public void updateLocation(String id, double x, double y) {
        User user = get(id);
        user.setLocation(new double[] {x, y});
        user.setLastLoginTime(System.currentTimeMillis());
        db.save(user);
    }
}
