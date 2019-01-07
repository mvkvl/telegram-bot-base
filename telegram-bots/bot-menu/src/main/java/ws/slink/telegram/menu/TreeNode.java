package ws.slink.telegram.menu;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface TreeNode {
	
	default void add(TreeNode node) {};
	default void remove(TreeNode node) {};
	default void remove(int i) {};

	TreeNode getParent();
	void setParent(TreeNode parent);

	default TreeNode getChild(int i) {return null;}
	default List<TreeNode> getChildren() {return null;};
	
	String getTitle();
	String getCallback();
	
	BotApiMethod getAction(long chatId, long messageId, String messageText);
}
