package ws.slink.telegram.menu;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public class BackDialogCommand implements TreeNode {

	TreeNode parent = null;
	String title;
	String callback;

	public BackDialogCommand(String callback) {
		this.callback = callback;
		this.title = new String(Character.toChars(0x21e6)); // 0x21d0
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
		if (parent != null && parent.getParent() != null) {
			return parent.getParent().getAction(chatId, messageId, null);
		} else {
			return null;
		}
	}
}
