/*
 * ===========================================================================
 * Standards Java Game Library Source Code
 * Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff
 * Standards is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Standards Source Code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.
 *
 * Standards is the long-overdue update to the everlasting Standards 2.0 library
 * Andrew Matzureff and I created two years ago. I am including it in this project
 * to simplify the rendering and logic pipeline, but with a focus on the MVC
 * paradigm.
 *
 * We connect to the Apache FastMath API for some of our trigonometric functions,
 * and we use John Carmack's fast inverse square root function. Lastly, for
 * StandardAudio, we use the javax.sound (Trail's Sound) Oracle API.
 * ===========================================================================
 */
package com.joshuacrotts.uncg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

/**
 * This class will be similar to the Math class in terms of operations. Used for
 * arithmetic, but primarily as of now (06-07-2019), it's good for:
 *
 * - Random Numbers - Determining Mouse Location (if the mouse coordinates are
 * over a rectangle (area) - Added the ability to load in specific fonts at a
 * specific size. Pass in the String and the size, and it will be returned. -
 * Clamping a value to a specific range - Loading an image, fast math from Quake
 * III (sqrt and inverse sqrt())
 */
public abstract class StdOps {

  /**
   * Returns a random integer between min and max.
   *
   * @param min
   * @param max
   * @return random integer
   */
  public static int randomInt(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException(" Max must be smaller than min ");
    }
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }

  /**
   * Generates a number between [min, minUpperBound) U (maxLowerBound, max)
   *
   * For instance to generate a number between -10 and 10, but no lower than -5
   * or 5, do randBounds( -10, -5, 5, 10). Precision doesn't really matter;
   *
   * @param min
   * @param minUpperBound
   * @param maxLowerBound
   * @param max
   *
   * In the end, min leq x leq minUpperBound OR maxLowerBound leq x leq max;
   * @return
   */
  public static double randomDoubleBounds(double min, double minUpperBound, double maxLowerBound, double max) {
    double n;

    do {
      n = StdOps.randomDouble(min, max);

    } while ((n < min || n > minUpperBound) && (n < maxLowerBound || n > max));

    return n;
  }

  /**
   * Returns a random double between min and max.
   *
   * @param min
   * @param max
   * @return
   */
  public static double randomDouble(double min, double max) {
    if (min >= max) {
      throw new IllegalArgumentException(" Max must be smaller than min ");
    }

    return ThreadLocalRandom.current().nextDouble(min, max + 1);
  }

  /**
   * Returns true if the mouse coordinates are within a specified
   * bounds/rectangle, false otherwise,
   *
   * @param mx - mouse x coordinate
   * @param my - mouse y coordinate
   * @param x - x position of rectangle
   * @param y - y position of rectangle
   * @param width - width of rectangle
   * @param height - height of rectangle
   * @return
   */
  public static boolean isMouseOver(int mx, int my, int x, int y, int width, int height) {
    return ((mx > x) && (mx < x + width)) && ((my > y) && (my < y + height));
  }

  /**
   * Clamps num between min and max.
   *
   * @param num
   * @param min
   * @param max
   * @return clamped number, or if unaltered, the number itself.
   */
  public static int clampInt(int num, int min, int max) {
    if (num < min) {
      return min;
    } else if (num > max) {
      return max;
    }
    return num;
  }

  /**
   *
   * @param c
   * @param offset
   * @return
   */
  public static Color changeColorBrightness(Color c, int offset) {
    int r, g, b;

    r = StdOps.clampInt(c.getRed() + offset, 0, 0xff);
    g = StdOps.clampInt(c.getGreen() + offset, 0, 0xff);
    b = StdOps.clampInt(c.getBlue() + offset, 0, 0xff);

    return new Color(r, g, b);
  }

  /**
   *
   * @param path
   * @param size
   * @return
   */
  public static Font initFont(String path, float size) {
    Font f = null;

    try {
      f = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(path)));
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
      return null;
    }
    return f;
  }

  /**
   *
   * @param path
   * @return
   */
  public static BufferedImage loadImage(String path) {
    BufferedImage sprite = null;
    try {
      sprite = ImageIO.read(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sprite;
  }

  /**
   * Carmack's fast inverse sqrt function
   *
   * @param x
   * @return
   */
  public static double fastInvSqrt(double x) {
    double xhalf = 0.5d * x;
    long i = Double.doubleToLongBits(x);
    i = 0x5fe6ec85e7de30daL - (i >> 1);
    x = Double.longBitsToDouble(i);
    x *= (1.5d - xhalf * x * x);
    return x;
  }

  /**
   * Adds a collection of MouseListener objects to a Swing component. This is 
   * primarily for batch-adding.
   * 
   * @param c Component swing component to add MMLs to.
   * @param args MouseListener arguments. Any arbitrary number.
   */
  public static void addMouseListeners(Component c, MouseListener... args) {
    for (MouseListener ml : args) {
      c.addMouseListener(ml);
    }
  }

  /**
   * Adds a collection of MouseMotionListener objects to a Swing component. This is 
   * primarily for batch-adding.
   * 
   * @param c Component swing component to add MMLs to.
   * @param args MouseMotionListener arguments. Any arbitrary number.
   */
  public static void addMouseMotionListeners(Component c, MouseMotionListener... args) {
    for (MouseMotionListener ml : args) {
      c.addMouseMotionListener(ml);
    }
  }
}
