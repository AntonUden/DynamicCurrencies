package net.zeeraa.dynamiccurrencies;

import org.bukkit.command.CommandSender;

import net.zeeraa.zcommandlib.command.ZCommand;

public class TestCMD extends ZCommand {

	public TestCMD() {
		super("test");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage("yes");
		return false;
	}

}
