package com.example.zooseeker_jj_zaaz_team_52;

import androidx.annotation.NonNull;

import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.nio.Attribute;

/**
 * IdentifiedWeightedEdge Class: Single Responsibility - Exactly like DefaultWeightedEdge,
 * but associates an id field with each edge that is used to look up information about the edge
 */
public class IdentifiedWeightedEdge extends DefaultWeightedEdge {
    private String id = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + getSource() + " :" + id + ": " + getTarget() + ")";
    }

    public static void attributeConsumer(Pair<IdentifiedWeightedEdge, String> pair, Attribute attr) {
        IdentifiedWeightedEdge edge = pair.getFirst();
        String attrName = pair.getSecond();
        String attrValue = attr.getValue();

        if (attrName.equals("id")) {
            edge.setId(attrValue);
        }
    }
}
