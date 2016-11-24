package secret.santa;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import lombok.Data;
import lombok.Synchronized;
import lombok.experimental.Accessors;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConfigCache
{
    private static final Gson GSON = new Gson();
    private static final Type TYPE_USER_CONFIG_MAP = new TypeToken<FluentMap<String, UserConfig>>(){}.getType();
    
    private Parameters params = new Parameters();
    private final FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
    
    public ConfigCache(String fileName) {
        
        System.out.println("CONFIG=[" + fileName + "]");
        
        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(fileName)
                        .setListDelimiterHandler(new DefaultListDelimiterHandler('|')));
    }
    
    public Object get(String key) throws ConfigurationException {
        return getConfig().containsKey(key) ? getConfig().getProperty(key) : "";
    }
    
    @Synchronized
    public Configuration getConfig() throws ConfigurationException {
        return builder.getConfiguration();
    }

    public String setValue(String key, String value) throws ConfigurationException {
        return setConfiguration(key, value).getString(key);
    }
    
    @Synchronized
    public Configuration setConfiguration(String key, Object value) throws ConfigurationException {
        builder.getConfiguration().setProperty(key, value);
        builder.save();
        return builder.getConfiguration();
    }
    
    public String toString() {
        ArrayList<String> configList = new ArrayList<String>();
        try {
            Configuration config = getConfig();
            config.getKeys().forEachRemaining(k -> configList.add("KEY=[" + k + "], VALUE=[" + config.getProperty(k) + "]"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CONFIG=[" + StringUtils.join(configList, ", ") + "]";
    }
    
    public FluentMap<String, UserConfig> getAllUsers() throws ConfigurationException {
        String config = getConfig().getString("user.config");
        FluentMap<String, UserConfig> userConfig = GSON.fromJson(getConfig().getString("user.config"), TYPE_USER_CONFIG_MAP);
        return userConfig != null ? userConfig : FluentMap.<String, UserConfig>with();
    }
    
    public UserConfig getUser(String username) throws ConfigurationException {
        return getAllUsers().get(username); 
    }
    
    public ConfigCache setAllUsers(FluentMap<String, UserConfig> userConfig) throws ConfigurationException {
        setConfiguration("user.config", GSON.toJson(userConfig, TYPE_USER_CONFIG_MAP));
        return this;
    }
    
    public ConfigCache setUser(UserConfig userConfig) throws ConfigurationException {
        setAllUsers(getAllUsers().keyValue(userConfig.username(), userConfig));
        return this;
    }
    
    @Data(staticConstructor = "with")
    @Accessors(fluent = true)
    public static class UserConfig {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private String email;
        private int vote = 0;
        private String data = "";
        
    }
}
