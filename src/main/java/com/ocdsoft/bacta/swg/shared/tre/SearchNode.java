package com.ocdsoft.bacta.swg.shared.tre;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/19/14.
 */
public abstract class SearchNode implements Comparable<SearchNode> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    private final int searchPriority;

    public SearchNode(int searchPriority) {
        this.searchPriority = searchPriority;
    }

    @Override
    public int compareTo(SearchNode o) {
        return Integer.compare(searchPriority, o.searchPriority) * -1; //Descending order.
    }

    public abstract byte[] open(String filePath);

    public abstract boolean exists(String filePath);
}
