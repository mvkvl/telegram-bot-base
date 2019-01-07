package ws.slink.telegram.menu;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class DialogCommand implements TreeNode {

	TreeNode parent = null;
	String    title;
	String callback;
	Command command;

	public DialogCommand(String title, String callback, Command command) {
		this.title = title;
		this.callback = callback;
		this.command = command;
	}
	
	@Override
	public TreeNode getParent() {
		return parent;
	}
	@Override
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	@Override
	public String getTitle() {
		return title;
	}
	@Override
	public String getCallback() {
		return callback;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BotApiMethod getAction(long chatId, long messageId, String messageText) {
		String callResult = this.command.call(chatId, messageId); 
		return this.getParent().getAction(chatId, messageId, callResult);
	}
}
