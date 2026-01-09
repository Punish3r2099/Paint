package com.example.demo1;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.IOException;

public class HelperImpl extends Helper {
    @Override
    public void run() {
    HelloApplication f = new HelloApplication();
        try {
        ImageIO.write(SwingFXUtils.fromFXImage(f.returnImage(), null), "jpg", f.returnFile());
        } catch (IOException e) {
        throw new RuntimeException(e);
        }
    }
}
