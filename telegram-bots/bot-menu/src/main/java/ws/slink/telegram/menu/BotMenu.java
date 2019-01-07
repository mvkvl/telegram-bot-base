package ws.slink.telegram.menu;

public interface BotMenu {
	TreeNode getNode(String callbackStr);
	TreeNode getRoot();
}
