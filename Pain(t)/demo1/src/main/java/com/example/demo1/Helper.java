package com.example.demo1;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.TimerTask;

public class Helper extends TimerTask{
    HelloApplication hi = new HelloApplication();
    @Override
    public void run() {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(hi.returnImage(), null), "jpg", hi.returnFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
