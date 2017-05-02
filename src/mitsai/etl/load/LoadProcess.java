package mitsai.etl.load;

import mitsai.etl.commun.Item;
import mitsai.etl.load.database.Target;

import java.util.Collections;
import java.util.List;

/**
 * Created by junior on 9/1/16.
 */
class LoadProcess {
    private final Target target;
    private List<Item> items = Collections.emptyList();

    LoadProcess(Target target) {
        this.target = target;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void run() {
        for (Item item : items)
            for (int i = 0; i < item.size(); ++i)
                target.insert(item.hashFor(i));

        target.close();
    }

}
