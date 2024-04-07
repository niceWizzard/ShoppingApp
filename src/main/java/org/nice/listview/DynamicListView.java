package org.nice.listview;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DynamicListView<T> extends JPanel {
    private final Collection<T> items;


    private final Map<String, JComponent> itemViewMap = new HashMap<>();

    private final Function<Item<JComponent>, T> viewBuilder ;
    private final Function<String,T> keyBuilder;
    private final Item<JComponent> defaultView;

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JPanel getRoot() {
        return root;
    }
    private final JScrollPane scrollPane;
    private final JPanel root;


    /**
     * Must be called everytime the provided list changes to reflect the change in the ui as well.
     */
    public void update() {
        var updatedMap = new HashMap<String, T>();
        for(var item : items) {
            var key = keyBuilder.call(item);
            updatedMap.put(key, item);

            var wasAdded = !itemViewMap.containsKey(key);
            if(wasAdded) {
                var returnedItem  = viewBuilder.call(updatedMap.get(key));
                itemViewMap.put(
                        key,
                        returnedItem.item()
                );
                root.add(returnedItem.item(), returnedItem.addOptions().orElse(""));
            }
        }

        var toRemove = new ArrayList<String>();
        for(var itemKey : itemViewMap.keySet()) {
            var wasRemoved =  !updatedMap.containsKey(itemKey);
            if(wasRemoved) {
                toRemove.add(itemKey);
            }
        }

        for(var itemKey : toRemove) {
            var view = itemViewMap.get(itemKey);
            root.remove(view);
            itemViewMap.remove(itemKey);
        }

        if(items.isEmpty()) {
            root.add(defaultView.item(), defaultView.addOptions().orElse(""));
        } else {
            root.remove(defaultView.item());
        }
        root.revalidate();
        root.repaint();

    }


    public DynamicListView(
            Collection<T> items,
            Function<String, T> keyBuilder,
            Function<Item<JComponent>, T> viewBuilder,
            Item<JComponent> defaultView,
            LayoutManager layout
    ) {
        this.defaultView = defaultView;
        this.items = items;
        this.keyBuilder = keyBuilder;
        this.viewBuilder = viewBuilder;

        root = new JPanel();
        this.root.setLayout(layout);
        scrollPane = new JScrollPane(root);
        add(scrollPane);

        init();
    }

    private void init() {
        for(var item : items) {
            var id = keyBuilder.call(item);
            var returnedItem = viewBuilder.call(item);
            var itemView = returnedItem.item();
            itemViewMap.put(id, itemView);
            root.add(itemView, returnedItem.addOptions().orElse(""));
        }
        if(items.isEmpty()) {
            root.add(defaultView.item(), defaultView.addOptions().orElse(""));
        }
    }
}
