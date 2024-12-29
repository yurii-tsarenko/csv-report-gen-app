package com.csv.service.file;

import java.io.InputStream;

public interface FileReader<T> {

    T read(InputStream stream);
}
