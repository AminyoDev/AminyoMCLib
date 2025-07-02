package dev.aminyo.aminyomclib.core.utils.database;

public class Query {
    private String query;

    public Query(String query){
        this.query = query;
    }

    public String getQuery(){
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}