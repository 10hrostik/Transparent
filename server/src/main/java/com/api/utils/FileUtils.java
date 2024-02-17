package com.api.utils;

import java.io.File;

public class FileUtils {
  public static boolean createFolderIfNotExists(String uri) {
    File file = new File(uri);
    if (!file.exists()) {
      return file.mkdirs();
    }
    return true;
  }
}
