package me.eths.player;

import javafx.geometry.Pos;
import lombok.Getter;
import me.eths.player.processors.CheckProcessor;
import me.eths.player.processors.PositionProcessor;
import me.eths.player.processors.RotationProcessor;
import me.eths.player.processors.TransactionProcessor;
import org.bukkit.entity.Player;

@Getter
public final class PlayerData {

    private final Player player;
    private final PositionProcessor positionProcessor = new PositionProcessor(this);
    private final RotationProcessor rotationProcessor = new RotationProcessor(this);
    private final CheckProcessor checkProcessor = new CheckProcessor(this);
    private final TransactionProcessor transactionProcessor = new TransactionProcessor(this);

    public PlayerData(Player player) {
        this.player = player;
    }



}
