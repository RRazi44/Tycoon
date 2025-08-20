package fr.razi.data;

import java.util.UUID;

public record BoxData(
        UUID id,
        UUID ownerId,
        LocationData spawnLocation,
        int size,
        long totalMoney,
        long earnings
)
{}
