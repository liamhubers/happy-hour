package com.school.guidoschmitz.happyhours.repositories;

/**
 * Created by Liam on 10/06/2015.
 */
public class Repository {
    public static RepositoryInterface repository;
    public static RepositoryInterface api;
    public static CacheRepository cache;

    public static void setConnectivity(boolean hasConnection) {
        repository = hasConnection ? api : cache;
    }
}
