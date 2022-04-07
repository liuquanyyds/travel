package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    public Favorite FindFavoriteByridAndUid(int rid,int uid);
    public int findCountByRid(int rid);

    void addFavorite(int parseInt, int uid);
}
