package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        //        Set<String> category = jedis.zrange("category", 0, -1);
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> all = null;
        if (category == null || category.size() == 0) {
            System.out.println("从数据库查询");
            all = categoryDao.findAll();
            for (int i = 0; i < all.size(); i++) {
                jedis.zadd("category", all.get(i).getCid(), all.get(i).getCname());
            }
        } else {
            System.out.println("从redis查询");
            all = new ArrayList<>();
            /*for (String name : category) {
                Category category1 = new Category();
                category1.setCname(name);
                all.add(category1);
            }*/
            for (Tuple tuple : category) {
                Category category1 = new Category();
                category1.setCname(tuple.getElement());
                category1.setCid((int) tuple.getScore());
                all.add(category1);
            }
        }
        return all;
    }
}
/* System.out.println("无redis，直接查数据库");
         List<Category> all = categoryDao.findAll();
        return all;*/
/*
            Jedis jedis =null;
            jedis = JedisUtil.getJedis();
            if (jedis==null){
                System.out.println("无redis，直接查数据库");
                List<Category> all = categoryDao.findAll();
                return all;
            }else{
            Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
            List<Category> all=null;
            if(category==null||category.size()==0){
                System.out.println("从数据库查询");
                all = categoryDao.findAll();
                for (int i = 0; i <all.size() ; i++) {
                    jedis.zadd("category",all.get(i).getCid(),all.get(i).getCname());
                }
            }else{
                System.out.println("从redis查询");
                all = new ArrayList<>();
               *//* for (String name : category) {
                    Category category1=new Category();
                    category1.setCname(name);
                    all.add(category1);
                }*//*
                for (Tuple tuple : category) {
                    Category category1=new Category();
                    category1.setCname(tuple.getElement());
                    category1.setCid((int)tuple.getScore());
                    all.add(category1);
                }
            }
            return all;
            }
    }*/

