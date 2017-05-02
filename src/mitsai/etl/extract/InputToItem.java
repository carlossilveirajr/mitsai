package mitsai.etl.extract;

import mitsai.etl.commun.Item;

/**
 * Created by junior on 8/31/16.
 */
public interface InputToItem {

    Item convert(Object object);
}
