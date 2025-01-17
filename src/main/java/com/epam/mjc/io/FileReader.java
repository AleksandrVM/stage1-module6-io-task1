package com.epam.mjc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileReader {

    public static final String NEW_LINE = "/n";
    public static final String DELIMITER = ":";

    public enum KeyProfile {NAME, AGE, EMAIL, PHONE}

    public Profile getDataFromFile(File file) {

        if (file.length() == 0)
            throw new FileFormatException(file.getAbsolutePath() + " is empty.");

        try (BufferedReader fileReader = new BufferedReader(new java.io.FileReader(file))) {
            StringBuilder dataProfile = new StringBuilder();
            String line = null;
            while ((line = fileReader.readLine()) != null) {
                dataProfile.append(line).append(NEW_LINE);
            }
            return parse(dataProfile.toString());
        } catch (FileNotFoundException e) {
            throw new FileNotExist(e);
        } catch (IOException e) {
            throw new FileReadingException(e);
        }
    }

    private Profile parse(String dataProfile) {
        Profile profile = new Profile();
        String[] lines = dataProfile.split(NEW_LINE);
        for (String line : lines) {
            String[] keyValue = line.split(DELIMITER, 2);
            String value = keyValue[1].trim();

            switch (getKeyProfile(keyValue[0])) {
                case NAME:
                    profile.setName(value);
                    break;
                case AGE: {
                    try {
                        profile.setAge(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        throw new FileFormatException("Age value is not correct. Age: " + value, e);
                    }
                    break;
                }
                case EMAIL:
                    profile.setEmail(value);
                    break;
                case PHONE:
                    try {
                        profile.setPhone(Long.valueOf(value));
                    } catch (NumberFormatException e) {
                        throw new FileFormatException("Phone value is not correct. Phone:" + value, e);
                    }
                    break;
            }
        }
        return profile;
    }

    private KeyProfile getKeyProfile(String key) {
        try {
            return KeyProfile.valueOf(key.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new FileFormatException("Unknown key: " + key, e);
        }
    }
}
