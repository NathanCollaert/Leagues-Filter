package com.famousfeet;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Leagues Filter"
)
public class LeaguesFilterPlugin extends Plugin
{
	@Inject
	private Client client;

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent event)
	{
		int[] intStack = client.getIntStack();
		int intStackSize = client.getIntStackSize();
		String[] stringStack = client.getStringStack();
		int stringStackSize = client.getStringStackSize();
		if (intStack.length < intStackSize - 3 || stringStack.length < stringStackSize - 1)
		{
			return;
		}

		final int messageType = intStack[intStackSize - 2];
		ChatMessageType chatMessageType = ChatMessageType.of(messageType);
		if (chatMessageType != ChatMessageType.CLAN_MESSAGE)
		{
			return;
		}

		String message = stringStack[stringStackSize - 1].trim();
		log.debug("Broadcast message: " + message);

		if(message.contains("<img=22>")){
			log.debug("Leagues broadcast detected..");
			intStack[intStackSize - 3] = 0;
		}
	}
}
