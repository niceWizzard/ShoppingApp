package org.nice.constants;

import javax.swing.border.EmptyBorder;

public final class Padding {
    public static  EmptyBorder byParts(int horizontal, int vertical) {
        return new EmptyBorder(vertical, horizontal, vertical, horizontal);
    }

    public static EmptyBorder byBox(int padding)  {
        return new EmptyBorder(padding, padding, padding, padding);
    }

}
