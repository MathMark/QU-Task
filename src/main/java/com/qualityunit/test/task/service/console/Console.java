package com.qualityunit.test.task.service.console;

import java.io.*;

public class Console implements Closeable {

    private final BufferedWriter writer;

    public Console(String fileName) {
        try {
            this.writer = createWriter(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void writeLine(T message) {
        try {
            writer.write(message.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedWriter createWriter(String fileName) throws IOException {
        return new BufferedWriter(new FileWriter(fileName));
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
