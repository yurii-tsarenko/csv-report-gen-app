package com.csv.service;

import java.io.InputStream;

public interface FileReader<T> {

    T read(InputStream stream);
}
