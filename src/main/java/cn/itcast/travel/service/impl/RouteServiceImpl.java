package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
        RouteDao routeDao=new RouteDaoImpl();
        RouteImgDao routeImgDao=new RouteImgDaoImpl();
        SellerDao sellerDao=new SellerDaoImpl();
        FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname) {
  /* private int totalCount;//总记录数
     private int totalPage;//总页数
     private int currentPage;//当前页码
     private int pageSize;//每页显示的条数
     private List<T> list;//每页显示的数据集合*/
        int totalCount = routeDao.findTotalCount(cid,rname);
        int totalPage=totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize) +1;
        int start=(currentPage - 1)* pageSize;
        List<Route> byPage = routeDao.findByPage(cid,rname, start, pageSize);
        PageBean<Route> pageBean=new PageBean<>();
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPagesize(pageSize);
        pageBean.setTotalCount(totalCount);
        pageBean.setList(byPage);
        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        Route route = routeDao.findOne(Integer.parseInt(rid));
        List<RouteImg> routeimg = routeImgDao.findRouteimg(route.getRid());
        Seller seller = sellerDao.findSeller(route.getSid());
        route.setSeller(seller);
        route.setRouteImgList(routeimg);
        int count = favoriteDao.findCountByRid(Integer.parseInt(rid));
        route.setCount(count);
        return route;
    }
}
