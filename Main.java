import java.io.*;

class Main {
    // [フィールドの定義] > [1]
    static int firstFrame = 1; // firstFrame > 0
    static int lastFrame = 149;
    static int ppmHeaderSize = 14;
    static byte[] ppmRGBBytes = new byte[86400];

    // [メソッドの定義] > [1]
    static void hideCursor() {
        System.out.print("\u001B[?25l");
    }
    static void showCursor() {
        System.out.print("\u001B[?25h");
    }
    static void moveTopLeft() {
        System.out.print("\u001B[1;1H");
    }

    // [メソッドの定義] > [2]
    static String frameFileName(int frame) {
        return "videoFrame/" + frame + ".ppm";
    }

    // [メソッドの定義] > [3]
    static void printPixel(int r, int g, int b) {
        System.out.print("\u001B[48;2;" + r + ";" + g + ";" + b + "m \u001B[0m");
    }

    public static void main(String[] args) throws IOException {
        // [処理] > [1]
        hideCursor();
        for (int frame = firstFrame; frame <= lastFrame; frame++) {
            moveTopLeft();

            // [フレームごとの処理] > [1]
            FileInputStream file = new FileInputStream(frameFileName(frame));
            file.skip(ppmHeaderSize);
            file.read(ppmRGBBytes);

            // [フレームごとの処理] > [2]
            for (int i = 0; i < ppmRGBBytes.length; i += 3) {
                int r = Byte.toUnsignedInt(ppmRGBBytes[i]);
                int g = Byte.toUnsignedInt(ppmRGBBytes[i + 1]);
                int b = Byte.toUnsignedInt(ppmRGBBytes[i + 2]);
                printPixel(r, g, b);
            }
        }
        showCursor();
    }
}