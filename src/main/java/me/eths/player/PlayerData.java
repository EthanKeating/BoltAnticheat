package me.eths.player;

import lombok.Getter;
import me.eths.player.processors.*;
import org.bukkit.entity.Player;

@Getter
public final class PlayerData {

    private final Player player;
    private final PositionProcessor positionProcessor = new PositionProcessor(this);
    private final RotationProcessor rotationProcessor = new RotationProcessor(this);
    private final CheckProcessor checkProcessor = new CheckProcessor(this);
    private final ActionProcessor actionProcessor = new ActionProcessor(this);
    private final TransactionProcessor transactionProcessor = new TransactionProcessor(this);

    public PlayerData(Player player) {
        this.player = player;
    }



}
