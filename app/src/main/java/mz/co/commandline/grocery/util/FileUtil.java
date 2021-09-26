package mz.co.commandline.grocery.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    private final int BYTES_SIZE = 4096;

    private Context context;

    public FileUtil(Context context) {
        this.context = context;
    }

    public File save(InputStream inputStream, String fileName) {

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator + fileName);

        try (OutputStream outputStream = new FileOutputStream(file)) {
            try {
                byte[] fileReader = new byte[BYTES_SIZE];

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}
