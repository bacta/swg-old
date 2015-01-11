package com.ocdsoft.bacta.swg.shared.tre;

import lombok.Getter;

/**
 * Created by crush on 3/19/14.
 */
public class SearchPath extends SearchNode {
    @Getter
    private final String filePath;

    public SearchPath(String filePath, int searchPriority) {
        super(searchPriority);

        this.filePath = filePath;
    }

    @Override
    public byte[] open(String filePath) {
        return null;
    }

    @Override
    public boolean exists(String filePath) {
        return false;
    }
}
