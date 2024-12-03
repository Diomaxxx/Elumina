package com.example.elumina.Graphql.Service;

public interface GraphQLCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
