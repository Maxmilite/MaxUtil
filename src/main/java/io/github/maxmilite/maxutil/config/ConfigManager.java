package io.github.maxmilite.maxutil.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.client.MaxUtilClient;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class ConfigManager {
    public File FOLDER, config;
    public ConfigManager() {

        this.FOLDER = MaxUtilClient.FOLDER;
        this.config = new File(FOLDER, "config.json");
        init();

    }

    public void init() {
        if (!this.FOLDER.exists()) {
            this.FOLDER.mkdirs();
        }
        if (!this.config.exists()) {
            try {
                this.config.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeConfig();
        } else {
            readConfig();
        }

    }

    public void writeConfig() {
        var list = MaxUtilClient.manager.getModules();
        Map<String, String> objectList = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        Writer writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(config), StandardCharsets.UTF_8);
            for (var i : list) {
                i.insertElement();
                String x = gson.toJson(i.jsonObject);
                objectList.put(i.getName(), x);
            }
            writer.write(gson.toJson(objectList));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readConfig() {
        FileInputStream fp;
        try {
            fp = new FileInputStream(config);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String jsonString;

        try {
            jsonString = IOUtils.toString(fp, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Map<String, String> objectList = new HashMap<>();

        objectList = (Map<String, String>) gson.fromJson(jsonString, Object.class);

        for (var i : MaxUtilClient.manager.getModules()) {
            JsonObject jsonObject = gson.fromJson(objectList.get(i.getName()), JsonObject.class);
            i.jsonObject = jsonObject;
            if (i.jsonObject == null) {
                i.insertElement();
            } else {
                i.readJsonObject();
            }
        }

        writeConfig();

    }

}
