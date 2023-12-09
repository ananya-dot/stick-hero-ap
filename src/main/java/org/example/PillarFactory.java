package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class PillarFactory {

    private static final Map<String, Rectangle> pillarCache = new HashMap<>();

    public static Rectangle getPillar(String color, double width, double height) {
        String key = color + "_" + width + "_" + height;

        if (pillarCache.containsKey(key)) {
            return pillarCache.get(key);
        } else {
            Rectangle newPillar = createPillar(color, width, height);
            pillarCache.put(key, newPillar);
            return newPillar;
        }
    }

    private static Rectangle createPillar(String color, double width, double height) {
        Rectangle pillar = new Rectangle(width, height);
        pillar.setFill(Color.web(color));
        return pillar;
    }
}
