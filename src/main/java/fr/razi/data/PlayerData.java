package fr.razi.data;

import java.util.UUID;

public record PlayerData(
        UUID id,
        UUID boxId,
        String rank,
        long balance,
        long earning,
        int level
) {

}
