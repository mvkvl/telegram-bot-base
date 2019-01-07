package ws.slink.telegram.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class BotConfig {

	public static class Config {
		Map <String, String> redis;
		Map <String, String> proxy;
		Map <String, String> telegram;
		public Config() {}
		public Map<String, String> getProxy() {
	        return proxy;
	    }
	    public void setProxy(Map<String, String> proxy) {
	        this.proxy = proxy;
	    }
		public Map<String, String> getRedis() {
	        return redis;
	    }
	    public void setRedis(Map<String, String> redis) {
	        this.redis = redis;
	    }
		public Map<String, String> getTelegram() {
	        return telegram;
	    }
	    public void setTelegram(Map<String, String> telegram) {
	        this.telegram = telegram;
	    }
	}

	private Config config;

	public BotConfig(String fileName) {
		this.load(fileName);
		if  (  getTelegramToken() == null
		   	|| getTelegramChat()  ==    0
//			|| getRedisHost()     == null
//			|| getRedisPort()     ==    0
			)
			throw new IllegalArgumentException("not all parameters set");
	}

	private BotConfig load(String fileName) {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
        	config = mapper.readValue(new File(fileName), Config.class);
        	if (null == config.redis)
        		config.redis = new HashMap<>();
        	if (null == config.proxy)
        		config.proxy = new HashMap<>();
        	if (null == config.telegram)
        		config.telegram = new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
        }		
        return this;
	}
	
	public String getTelegramName() {
		return this.config.telegram.get("name");
	}
	public String getTelegramToken() {
		return this.config.telegram.get("token");
	}
	public int getTelegramChat() {
		return Integer.parseInt(this.config.telegram.get("chat"));
	}
	public String getRedisHost() {
		return this.config.redis.get("host");
	}
	public int getRedisPort() {
		try {
			return Integer.parseInt(this.config.redis.get("port"));
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	public String getProxyHost() {
		return this.config.proxy.get("host");
	}
	public int getProxyPort() {
		try {
			return Integer.parseInt(this.config.proxy.get("port"));
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	public String proxy() {
		if (getProxyHost() != null && getProxyPort() != 0)
			return String.format("%s:%d", getProxyHost(), getProxyPort());
		else 
			return null;
	}
	
	public String toString() {
		String s = "";
		s += String.format("telegram:\n   name: %s\n   token: %s\n   chat: %d\n", getTelegramName(), getTelegramToken(), getTelegramChat());
		s += String.format("redis:\n   host: %s\n   port: %d\n", getRedisHost(), getRedisPort());
		s += String.format("proxy:\n   host: %s\n   port: %d\n   url: %s", getProxyHost(), getProxyPort(), proxy());
		return s; 
	}
	
	public static void main(String[] args) {
		String fileName = "./bot.conf";
		System.out.println(new BotConfig(fileName).toString());
	}
}
