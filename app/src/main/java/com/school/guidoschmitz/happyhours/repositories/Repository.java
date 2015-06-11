package com.school.guidoschmitz.happyhours.repositories;

/**
 * Created by Liam on 10/06/2015.
 */
public class Repository {
    protected static LocationRepositoryInterface repository;
    protected static LocationApiRepository api;
    protected static LocationCacheRepository cache;

    public static void setConnectivity(boolean hasConnection) {
        repository = hasConnection ? api : cache;
    }
}
