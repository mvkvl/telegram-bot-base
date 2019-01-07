package ws.slink.telegram.menu;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractBotMenu implements BotMenu {

	private Map<String, TreeNode> nodes = new HashMap<>();
	private String botTitle;
	
	/**
	 * builds bot's dialog hierarchy
	 * should be redefined in child classes
	 * @param botTitle
	 */
	abstract protected void build();
	
	public AbstractBotMenu(String botTitle) {
		this.botTitle = botTitle;
		nodes.put("root", new DialogItem(botTitle, "root"));
		_build();
	}
	
	/**
	 * @param callback
	 * @return dialog Node
	 */
	public TreeNode getNode(String callback) {
		return nodes.get(callback);
	}

	/**
	 * @return Root Node
	 */
    public TreeNode getRoot() {
		return nodes.get("root");
	}
    
	private void _build() {
		new DialogItem(botTitle, "root");
		build();
		fillNodes(getRoot());
	}
	private void fillNodes(TreeNode node) {
		this.nodes.put(node.getCallback(), node);
		if (node.getChildren() != null)
			for (TreeNode c: node.getChildren())
				fillNodes(c);
	}
}
