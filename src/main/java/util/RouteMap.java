package util;

import java.util.ArrayList;
import java.util.List;

/*
* 路线图
* */
public class RouteMap {

    private List<Route> routeList=new ArrayList<>();

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }
}
