package secret.santa;

import java.util.ArrayList;

import lombok.Synchronized;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;

public class ConfigCache
{
    private Parameters params = new Parameters();
    private final FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
    
    public ConfigCache(String fileName) {
        
        System.out.println("CONFIG=[" + fileName + "]");
        
        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(fileName)
                        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
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
    
}
