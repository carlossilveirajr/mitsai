package mitsai.miner.dataLoader.commun;

import mitsai.miner.dataLoader.DataConverter;

import java.net.UnknownHostException;

/**
 * Created by junior on 10/26/15.
 */
public abstract class LoadFactory<S> {
    protected final DataConverter<S> converter;
    protected String query = "";

    protected LoadFactory(DataConverter<S> converter) {
        this.converter = converter;
    }

    public abstract DatabaseInMemory fieldDatabase() throws UnknownHostException;

    protected void setQuery(String query) {
        this.query = query;
    }
}
