package mz.co.commandline.grocery.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer;
import java.util.Set;
import java.util.UUID;

public class BluetoothPrinter {

    private static final byte[] BOLD_TEXT = new byte[]{0x1B, 0x45, 0x01};
    private static final byte[] NORMAL_TEXT = new byte[]{0x1B, 0x45, 0x00};
    private static final byte[] TEXT_ALIGN_CENTER = new byte[]{0x1B, 0x61, 0x01};
    private static final byte[] RESET_PRINTER = new byte[]{0x1B, 0x40};

    private static final int MAX_LENGTH = 32;
    private static final String NEW_LINE = "\n";
    private static final int MAX_HEIGHT = 200;
    private static final String DEVICE_NAME = "P58C";


    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;

    private OutputStream outputStream;
    private InputStream inputStream;

    private byte[] readBuffer;
    private int readBufferPosition;
    private volatile boolean stopWorker;

    public void findDevice() {
        try {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    if (DEVICE_NAME.equals(device.getName())) {
                        bluetoothDevice = device;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();
            beginListenForData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printImage(Bitmap bitmap) {
        try {
            bitmap = resizeBitmapForP58C(bitmap, MAX_HEIGHT);
            byte[] bytes = bitmapToBytes(bitmap, false);
            outputStream.write(TEXT_ALIGN_CENTER);
            outputStream.write(bytes);
            outputStream.write(RESET_PRINTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printText(String text) {

        if (text == null) {
            throw new RuntimeException("The text cannot be null");
        }

        text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        text = StringUtils.abbreviate(text, MAX_LENGTH);

        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(text).append(NEW_LINE);
            outputStream.write(buffer.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printBoldText(String text) {
        try {
            outputStream.write(BOLD_TEXT);
            printText(text);
            outputStream.write(NORMAL_TEXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printBoldText(String left, String right) {
        try {
            outputStream.write(BOLD_TEXT);
            printText(left, right);
            outputStream.write(NORMAL_TEXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printText(String left, String right) {
        right = StringUtils.leftPad(right, MAX_LENGTH - left.length());
        StringBuffer buffer = new StringBuffer();
        buffer.append(left).append(right);
        printText(buffer.toString());
    }

    private void beginListenForData() {
        try {
            final Handler handler = new Handler();
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            new Thread(() -> {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = inputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(
                                            readBuffer, 0,
                                            encodedBytes, 0,
                                            encodedBytes.length
                                    );

                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(() -> Log.e("SENT_DATA", data));
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            stopWorker = true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lineDivision(char c) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < MAX_LENGTH; index++) {
            builder.append(c);
        }
        printText(builder.toString());
    }

    public void printNewLine() {
        printText("");
    }

    public void printBoldCenterText(String text) {
        text = StringUtils.center(text, MAX_LENGTH);
        printBoldText(text);
    }


    public static byte[] bitmapToBytes(Bitmap bitmap, boolean gradient) {
        int
                bitmapWidth = bitmap.getWidth(),
                bitmapHeight = bitmap.getHeight(),
                bytesByLine = (int) Math.ceil(((float) bitmapWidth) / 8f);

        byte[] imageBytes = initGSv0Command(bytesByLine, bitmapHeight);

        int i = 8,
                greyscaleCoefficientInit = 0,
                gradientStep = 6;

        double
                colorLevelStep = 765.0 / (15 * gradientStep + gradientStep - 1);

        for (int posY = 0; posY < bitmapHeight; posY++) {
            int greyscaleCoefficient = greyscaleCoefficientInit,
                    greyscaleLine = posY % gradientStep;
            for (int j = 0; j < bitmapWidth; j += 8) {
                int b = 0;
                for (int k = 0; k < 8; k++) {
                    int posX = j + k;
                    if (posX < bitmapWidth) {
                        int color = bitmap.getPixel(posX, posY),
                                red = (color >> 16) & 255,
                                green = (color >> 8) & 255,
                                blue = color & 255;

                        if (
                                (gradient && (red + green + blue) < ((greyscaleCoefficient * gradientStep + greyscaleLine) * colorLevelStep)) ||
                                        (!gradient && (red < 160 || green < 160 || blue < 160))
                        ) {
                            b |= 1 << (7 - k);
                        }

                        greyscaleCoefficient += 5;
                        if (greyscaleCoefficient > 15) {
                            greyscaleCoefficient -= 16;
                        }
                    }
                }
                imageBytes[i++] = (byte) b;
            }

            greyscaleCoefficientInit += 2;
            if (greyscaleCoefficientInit > 15) {
                greyscaleCoefficientInit = 0;
            }
        }

        return imageBytes;
    }

    public static byte[] initGSv0Command(int bytesByLine, int bitmapHeight) {
        int xH = bytesByLine / 256,
                xL = bytesByLine - (xH * 256),
                yH = bitmapHeight / 256,
                yL = bitmapHeight - (yH * 256);

        byte[] imageBytes = new byte[8 + bytesByLine * bitmapHeight];
        imageBytes[0] = 0x1D;
        imageBytes[1] = 0x76;
        imageBytes[2] = 0x30;
        imageBytes[3] = 0x00;
        imageBytes[4] = (byte) xL;
        imageBytes[5] = (byte) xH;
        imageBytes[6] = (byte) yL;
        imageBytes[7] = (byte) yH;
        return imageBytes;
    }

    public Bitmap resizeBitmapForP58C(Bitmap originalBitmap, int height) {
        int width = 384; // Maximum print width for P58C printer
        float originalWidth = originalBitmap.getWidth();
        float originalHeight = originalBitmap.getHeight();

        float scaleWidth = width / originalWidth;
        float scaleHeight = height / originalHeight;

        // Use the minimum scale value to ensure the entire image fits within the specified height
        float scale = Math.min(scaleWidth, scaleHeight);

        int newWidth = Math.round(originalWidth * scale);
        int newHeight = Math.round(originalHeight * scale);

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        return resizedBitmap;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }
}
