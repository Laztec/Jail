package net.simpvp.Jail;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.net.InetAddress;

import net.md_5.bungee.api.ChatColor;

public class BlockedCountries implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();

		if (player.isWhitelisted()) {
			return;
		}

		String country = GeoIP.getCountry(event.getAddress());

		if (Config.is_country_blocked(country)) {
			String msg = String.format("Blocking %s from country %s", player.getName(), country);
			Jail.instance.getLogger().info(msg);

			String reason = ChatColor.RED + "We're sorry, this server is unavailable in your country.";
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, reason);
		}

		return;
	}
}

