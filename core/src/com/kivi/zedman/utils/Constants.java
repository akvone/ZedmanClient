package com.kivi.zedman.utils;

/**
 * Class for global constants.
 *
 */
public class Constants {
    public static final float PPM = 32; //Point Per Meter - converting from Box2d to render coordinates
    public static final float MICRO_SIDE = 4; //Point Per Meter - converting from Box2d to render coordinates
    public static final float MICRO_SIDE_BOX = MICRO_SIDE/PPM; //Point Per Meter - converting from Box2d to render coordinates
    public static float CAMERA_WIDTH = 128f;
    public static float CAMERA_HEIGHT = 72f;

    //Screen Constants
    public static int STANDARD_BATCH_WIDTH = 1280;
    public static int STANDARD_BATCH_HEIGHT = 720;

    //Server Constants
    public static int STANDARD_PACKETS_PER_SECOND = 30;

    public static final short FILTER_PLAYER = 0x0001;
    public static final short FILTER_WALL = 0x0002;
}
