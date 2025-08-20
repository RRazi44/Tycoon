package fr.razi.data;

public record LocationData (
        double x,
        double y,
        double z,
        String worldName,
        float yaw,
        float pitch
){}
