package sample.GUI.util;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * @author Kuba
 * @date 3/16/2021 12:18 PM
 */
public class Animation {
    public static void shakeNodeAnimation(Node node) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(50), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(10f);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        translateTransition.playFromStart();
    }
}
