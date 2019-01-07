package ws.slink.telegram.menu;

@FunctionalInterface
public interface Command {
	String call(long chatId, long messageId);
}
