package inputs;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundHandler {
    public static boolean muted = false;
    public static Clip clip;

    public static void RunMusic(String path) {


        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path)); // mở tệp and lấy dữ liệu
            clip = AudioSystem.getClip(); // hệ thống âm thanh
            clip.open(inputStream);
            if(!muted) clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }
}
