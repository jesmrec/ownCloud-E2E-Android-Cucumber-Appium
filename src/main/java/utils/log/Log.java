package utils.log;

import android.SharingPage;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    static Logger Log = Logger.getLogger(SharingPage.class.getName());
    static Handler fileHandler = null;

    public static void init(){
        try {
            //consoleHandler = new ConsoleHandler();
            Log.getLevel();
            fileHandler  = new FileHandler("logs.log", 5*1024000, 1, true);
            fileHandler.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";
                @Override
                public synchronized String format(LogRecord logRecord) {
                    return String.format(format,
                        new Date(logRecord.getMillis()),
                        logRecord.getLevel().getLocalizedName(),
                        logRecord.getMessage()
                    );
                }
            });
            Log.setLevel(Level.FINE);
            fileHandler.setLevel(Level.FINE);
            Log.addHandler(fileHandler);
        } catch(IOException e){
            log(Level.SEVERE, "Exception in FileHandler: " + e.getMessage());
        }
    }

    public static void log (Level level, String message){
            Log.log(level, message);
    }
}
