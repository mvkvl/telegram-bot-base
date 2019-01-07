package examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ws.slink.telegram.menu.*;

@Component
public class BotMenuSample extends AbstractBotMenu {

    private static final Logger logger = LoggerFactory.getLogger(BotMenuSample.class);

    public BotMenuSample(String botTitle) {
        super(botTitle);
        logger.trace(" > created BotMenuExample instance");
    }

    private TreeNode build_menu_1() {
        TreeNode menu = new DialogItem(" menu 1 ", "main_menu_1");

        TreeNode sm1 = new DialogItem(" item 1.1 ", "sub_menu_1.1");
        sm1.add(new DialogCommand(" call 1.1.1 ",
                "command_1.1.1",
                (chat, message) -> String.format("command 1.1.1 result ")));
        sm1.add(new DialogCommand(" call 1.1.2 ",
                "command_1.1.2",
                (chat, message) -> String.format("command 1.1.2 result ")));
        sm1.add(new DialogCommand(" call 1.1.3 ",
                "command_1.1.3",
                (chat, message) -> String.format("command 1.1.3 result ")));
        sm1.add(new ClearDialogCommand("sub_menu_1.1_reset"));
        sm1.add(new BackDialogCommand("sub_menu_1.1_back"));
        menu.add(sm1);

        TreeNode sm2 = new DialogItem(" item 1.2 ", "sub_menu_1.2");

        TreeNode sm121 = new DialogItem(" item 1.2.1 ", "sub_menu_1.2.1");

        sm2.add(sm121);

        sm121.add(new DialogCommand(" call 1.2.1.1 ",
                "command_1.2.1.1",
                (chat, message) -> String.format("command 1.2.1.1 result ")));
        sm121.add(new DialogCommand(" call 1.2.1.2 ",
                "command_1.2.1.2",
                (chat, message) -> String.format("command 1.2.1.2 result ")));
        sm121.add(new DialogCommand(" call 1.2.1.3 ",
                "command_1.2.1.3",
                (chat, message) -> String.format("command 1.2.1.3 result ")));
        sm121.add(new ClearDialogCommand("sub_menu_1.2.1_reset"));
        sm121.add(new BackDialogCommand("sub_menu_1.2.1_back"));

        sm2.add(new DialogCommand(" call 1.2.2 ",
                "command_1.2.2",
                (chat, message) -> String.format("command 1.2.2 result ")));
        sm2.add(new DialogCommand(" call 1.2.3 ",
                "command_1.2.3",
                (chat, message) -> String.format("command 1.2.3 result ")));
        sm2.add(new ClearDialogCommand("sub_menu_1.2_reset"));
        sm2.add(new BackDialogCommand("sub_menu_1.2_back"));
        menu.add(sm2);

        TreeNode sm3 = new DialogItem(" item 1.3 ", "sub_menu_1.3");
        sm3.add(new DialogCommand(" call 1.3.1 ",
                "command_1.3.1",
                (chat, message) -> String.format("command 1.3.1 result ")));
        sm3.add(new DialogCommand(" call 1.3.2 ",
                "command_1.3.2",
                (chat, message) -> String.format("command 1.3.2 result ")));
        sm3.add(new DialogCommand(" call 1.3.3 ",
                "command_1.3.3",
                (chat, message) -> String.format("command 1.3.3 result ")));
        sm3.add(new ClearDialogCommand("sub_menu_1.3_reset"));
        sm3.add(new BackDialogCommand("sub_menu_1.3_back"));
        menu.add(sm3);

        menu.add(new ClearDialogCommand("main_menu_1_reset"));
        menu.add(new BackDialogCommand("main_menu_1_back"));

        return menu;
    }


    private TreeNode build_menu_2() {
        TreeNode menu = new DialogItem(" menu 2 ", "main_menu_2");

        TreeNode sm1 = new DialogItem(" item 2.1 ", "sub_menu_2.1");
        sm1.add(new DialogCommand(" Command 2.1.1 ",
                "command_2.1.1",
                (chat, message) -> String.format("command 2.1.1 result ")));
        sm1.add(new DialogCommand(" call2.1.2 ",
                "command_2.1.2",
                (chat, message) -> String.format("command 2.1.2 result ")));
        sm1.add(new DialogCommand(" call2.1.3 ",
                "command_2.1.3",
                (chat, message) -> String.format("command 2.1.3 result ")));
        sm1.add(new ClearDialogCommand("sub_menu_2.1_reset"));
        sm1.add(new BackDialogCommand("sub_menu_2.1_back"));
        menu.add(sm1);

        TreeNode sm2 = new DialogItem(" item 2.2 ", "sub_menu_2.2");
        sm2.add(new DialogCommand(" call 2.2.1 ",
                "command_2.2.1",
                (chat, message) -> String.format("command 2.2.1 result ")));
        sm2.add(new DialogCommand(" call 2.2.2 ",
                "command_2.2.2",
                (chat, message) -> String.format("command 2.2.2 result ")));
        sm2.add(new DialogCommand(" call 2.2.3 ",
                "command_2.2.3",
                (chat, message) -> String.format("command 2.2.3 result ")));
        sm2.add(new ClearDialogCommand("sub_menu_2.2_reset"));
        sm2.add(new BackDialogCommand("sub_menu_2.2_back"));
        menu.add(sm2);

        TreeNode sm3 = new DialogItem(" item 2.3 ", "sub_menu_2.3");
        sm3.add(new DialogCommand(" call 2.3.1 ",
                "command_2.3.1",
                (chat, message) -> String.format("command 2.3.1 result ")));
        sm3.add(new DialogCommand(" call 2.3.2 ",
                "command_2.3.2",
                (chat, message) -> String.format("command 2.3.2 result ")));
        sm3.add(new DialogCommand(" call 2.3.3 ",
                "command_2.3.3",
                (chat, message) -> String.format("command 2.3.3 result ")));
        sm3.add(new ClearDialogCommand("sub_menu_2.3_reset"));
        sm3.add(new BackDialogCommand("sub_menu_2.3_back"));
        menu.add(sm3);

        menu.add(new ClearDialogCommand("main_menu_2_reset"));
        menu.add(new BackDialogCommand("main_menu_2_back"));

        return menu;
    }

    @Override
    protected void build() {
        getRoot().add(build_menu_1());
        getRoot().add(build_menu_2());
    }
}
