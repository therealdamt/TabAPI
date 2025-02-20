package io.github.nosequel.tab.shared.thread;

import io.github.nosequel.tab.shared.TabHandler;
import io.github.nosequel.tab.shared.entry.TabElement;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabThread extends Thread {

    private final TabHandler handler;

    /**
     * Constructor to make a new TabThread
     *
     * @param handler the handler to register it to
     */
    public TabThread(TabHandler handler) {
        super("TabAPI - Thread");
        this.handler = handler;
    }

    @Override
    public void run() {
        while (true) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                final TabElement tabElement = this.handler.getHandler().getElement(player);

                this.handler.getAdapter()
                        .setupProfiles(player)
                        .showRealPlayers(player).addFakePlayers(player)
                        .hideRealPlayers(player).handleElement(player, tabElement)
                        .sendHeaderFooter(player, tabElement.getHeader(), tabElement.getFooter());
            }

            try {
                sleep(handler.getTicks()*50);
            } catch (InterruptedException e) {
                this.run();
                e.printStackTrace();
            }
        }
    }
}