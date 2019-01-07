package ws.slink.telegram.bot.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ws.slink.telegram.BotBuilder;
import ws.slink.telegram.tools.BotConfig;
import ws.slink.telegram.tools.ParamsParser;

import javax.annotation.PostConstruct;

public class EchoBotExample extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(EchoBotExample.class);

    private String proxyString;
    private String token;
    private String  name;
    private int     chat;

    public EchoBotExample(DefaultBotOptions opts) {
        super(opts);
    }
    public EchoBotExample token(String token) {
        this.token = token;
        return this;
    }
    public EchoBotExample name(String name) {
        this.name = name;
        return this;
    }
    public EchoBotExample chat(int chatId) {
        this.chat = chatId;
        return this;
    }
    public EchoBotExample proxyString(String proxyString) {
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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            response.setText(text);
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
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
        EchoBotExample bot = new BotBuilder<EchoBotExample>(bc.proxy())
                                             .build(EchoBotExample.class.getName())
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