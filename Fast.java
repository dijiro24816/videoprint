import java.io.*;

class Fast {
    // [フィールドの定義] > [1]
    static int firstFrame = 1; // firstFrame > 0
    static int lastFrame = 149;
    static int ppmHeaderSize = 14;
    static byte[] ppmRGBBytes = new byte[86400];

    // [フィールドの定義] > [2]
    static PrintWriter pw = new PrintWriter(System.out);

    // [メソッドの定義] > [1]
    static void hideCursor() {
        pw.write("\u001B[?25l");
    }
    static void showCursor() {
        pw.write("\u001B[?25h");
    }
    static void moveTopLeft() {
        pw.write("\u001B[1;1H");
    }

    // [メソッドの定義] > [2]
    static String frameFileName(int frame) {
        StringBuilder sb = new StringBuilder();
        sb.append("videoFrame/");
        sb.append(frame);
        sb.append(".ppm");
        return sb.toString();
    }

    // [メソッドの定義] > [3]
    static void printPixel(int r, int g, int b) {
        StringBuilder sb = new StringBuilder();
        sb.append("\u001B[48;2;");
        sb.append(r);
        sb.append(";");
        sb.append(g);
        sb.append(";");
        sb.append(b);
        sb.append("m \u001B[0m");
        pw.write(sb.toString());
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
            pw.flush();
        }
        showCursor();
        pw.flush();
    }
}