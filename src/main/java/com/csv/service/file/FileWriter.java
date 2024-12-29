package com.csv.service.file;

public interface FileWriter<T> {

    void write(T content, String filePath);
}
