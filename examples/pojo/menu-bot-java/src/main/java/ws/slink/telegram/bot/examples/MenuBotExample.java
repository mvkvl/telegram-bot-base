package ws.slink.telegram.bot.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ws.slink.telegram.BotBuilder;
import ws.slink.telegram.menu.BotMenu;
import ws.slink.telegram.tools.BotConfig;
import ws.slink.telegram.tools.ParamsParser;

import javax.annotation.PostConstruct;

public class MenuBotExample extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(MenuBotExample.class);

    private String proxyString;
    private String token;
    private String  name;
    private int     chat;

    private BotMenu botMenu;

    public MenuBotExample(DefaultBotOptions opts) {
        super(opts);
        botMenu = new BotMenuSample("test bot");
    }
    public MenuBotExample token(String token) {
        this.token = token;
        return this;
    }
    public MenuBotExample name(String name) {
        this.name = name;
        return this;
    }
    public MenuBotExample chat(int chatId) {
        this.chat = chatId;
        return this;
    }
    public MenuBotExample proxyString(String proxyString) {
        this.proxyString = proxyString;
        return this;
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }
    @Override
    public String getBotToken() {
        return this.token;
    }
    @Override
    public void onUpdateReceived(Update update) {
        long chat_id = 0;
        BotApiMethod baMessage = null;
        SendMessage response = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            chat_id = update.getMessage().getChatId();
            if (chat_id == this.chat) {
                if (message_text.equals("/start")) {
                    SendMessage mes = (SendMessage) botMenu.getRoot().getAction(chat_id, 0, null);
                    mes.enableHtml(true);
                    baMessage = mes;
                }
            }
        } else if (update.hasMessage()) {
            Message message = update.getMessage();
            response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            response.setText(text);
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            chat_id          = update.getCallbackQuery().getMessage().getChatId();
            long message_id  = update.getCallbackQuery().getMessage().getMessageId();
            EditMessageText emt = (EditMessageText)botMenu.getNode(call_data).getAction(chat_id, message_id, null);
            emt.enableHtml(true);
            baMessage = emt;
        }

        if (null != baMessage)
            try {
                execute(baMessage); // Sending our message object to user
//                logger.info("Sent message \"{}\" to {}", response.getText(), response.getChatId());
            } catch (TelegramApiException e) {
//                logger.error("Failed to send message \"{}\" to {} due to error: {}", response.getText(), response.getChatId(), e.getMessage());
                e.printStackTrace();
            }

        if (null != response)
            try {
                execute(baMessage); // Sending our message object to user
                logger.info("Sent message \"{}\" to {}", response.getText(), response.getChatId());
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", response.getText(), response.getChatId(), e.getMessage());
                e.printStackTrace();
            }

    }
    @PostConstruct
    public void start() {
        if (null == proxyString || proxyString.isEmpty())
            logger.info("Starting telegram bot\nusername: {}, token: {}", getBotToken(), getBotToken());
        else
            logger.info("Starting proxy-connected telegram bot\nusername: {}, token: {}", getBotToken(), getBotToken());
    }


    public static void main(String [] args) {
// *** Parse command line arguments (for mandatory parameter "--config")
        ParamsParser pp = new ParamsParser(args);

        // *** Read configuration from config file
        BotConfig bc = new BotConfig(pp.configFilePath);

        // *** Initialize Api Context
        ApiContextInitializer.init();

        // *** Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // *** Create bot
        MenuBotExample bot = new BotBuilder<MenuBotExample>(bc.proxy())
                                             .build(MenuBotExample.class.getName())
                                             .chat(bc.getTelegramChat())
                                             .token(bc.getTelegramToken())
                                             .name(bc.getTelegramName())
                                             .proxyString(bc.proxy());
        // *** Register bot
        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}