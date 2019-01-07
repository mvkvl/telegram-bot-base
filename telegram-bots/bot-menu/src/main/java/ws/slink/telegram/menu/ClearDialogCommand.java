package ws.slink.telegram.menu;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class ClearDialogCommand implements TreeNode {

	TreeNode parent = null;
	String title;
	String callback;

	public ClearDialogCommand(String callback) {
		this.callback = callback;
		this.title = new String(Character.toChars(0x267d)); // 0x21bb
	}

	@Override
	public TreeNode getParent() {
		return this.parent;
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
		if (parent != null) {
			return parent.getAction(chatId, messageId, null);
		} else {
			return null;
		}
	}
}
