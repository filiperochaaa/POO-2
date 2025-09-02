package br.com.biblioteca.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("BibliotecaLogger");
            try {
                Path logsDir = Path.of("logs");
                if (!Files.exists(logsDir)) {
                    Files.createDirectories(logsDir);
                }
                FileHandler fileHandler = new FileHandler("logs/app.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(true);
            } catch (IOException e) {
                System.err.println("Falha ao iniciar logger: " + e.getMessage()); //O código trata exceções de entrada, banco de dados, configuração e validação e mantém os logs.
            }
        }
        return logger;
    }

    public static void info(String msg) {
        getLogger().log(Level.INFO, msg);
    }

    public static void error(String msg, Throwable t) {
        getLogger().log(Level.SEVERE, msg, t);
    }
}
