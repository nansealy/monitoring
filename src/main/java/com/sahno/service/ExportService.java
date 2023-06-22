package com.sahno.service;

import lombok.SneakyThrows;

public interface ExportService {
    @SneakyThrows
    byte[] export(long id);
}
