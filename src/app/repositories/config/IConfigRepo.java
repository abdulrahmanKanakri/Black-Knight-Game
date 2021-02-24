package app.repositories.config;

import app.config.Config;

public interface IConfigRepo {
    Config getConfigData();
    void setConfigData(Config config);
}
