package com.tobpartyfocus;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.CommandExecuted;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class TobPartyFocusPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private TobPartyFocusConfig config;

	private int tobPartyWidgetID = 1835020; //Teekiz<br>-
	private boolean isInParty = false;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	//check if player is a party - done
	//check to see if the player is within a certain boundry
	//hide players and overhead text

	//party size?
	//key set?
	public List<String> getPartyMembers()
	{
		String playersNamesInParty = client.getWidget(tobPartyWidgetID).getText();
		playersNamesInParty = playersNamesInParty.replace("-", "");
		return List.of(playersNamesInParty.split("<br>"));
	}
	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged)
	{
		if (varbitChanged.getVarbitId() == Varbits.THEATRE_OF_BLOOD)
		{
			isInParty = varbitChanged.getValue() == 1;
		}
	}

	//overhead text could be getting all text and setting it to ""
	@Subscribe
	public void onChatMessage(ChatMessage chatMessage)
	{
		if (!chatMessage.getName().equalsIgnoreCase("partyMember"))
		{
			//filter
		}
	}

	@Subscribe
	public void onCommandExecuted(CommandExecuted commandExecuted)
	{
		//::tobtest
		if (commandExecuted.getCommand().equalsIgnoreCase("tobtest"))
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", getPartyMembers().get(0), null);
		}
	}


	@Provides
	TobPartyFocusConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TobPartyFocusConfig.class);
	}
}
