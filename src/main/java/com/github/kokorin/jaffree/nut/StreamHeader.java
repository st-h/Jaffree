package com.github.kokorin.jaffree.nut;

import java.util.Collections;
import java.util.Set;

public class StreamHeader {

    public final long streamId;
    public final StreamHeader.Type streamType;
    public final byte[] fourcc;
    public final long timeBaseId;
    public final long msbPtsShift;
    public final long maxPtsDistance;
    public final long decodeDelay;
    public final Set<StreamHeader.Flag> flags;
    public final Video video;
    public final Audio audio;

    public StreamHeader(long streamId, Type streamType, byte[] fourcc, long timeBaseId, long msbPtsShift,
                        long maxPtsDistance, long decodeDelay, Set<Flag> flags, Video video, Audio audio) {
        this.streamId = streamId;
        this.streamType = streamType;
        this.fourcc = fourcc;
        this.timeBaseId = timeBaseId;
        this.msbPtsShift = msbPtsShift;
        this.maxPtsDistance = maxPtsDistance;
        this.decodeDelay = decodeDelay;
        this.flags = flags;
        this.video = video;
        this.audio = audio;
    }

    public static class Video {
        public final long width;
        public final long height;
        public final long sampleWidth;
        public final long sampleHeight;
        public final ColourspaceType type;

        public Video(long width, long height, long sampleWidth, long sampleHeight, ColourspaceType type) {
            this.width = width;
            this.height = height;
            this.sampleWidth = sampleWidth;
            this.sampleHeight = sampleHeight;
            this.type = type;
        }
    }

    public static class Audio {
        public final long samplerateNumerator;
        public final long samplerateDenomominator;
        public final long channelCount;

        public Audio(long samplerateNumerator, long samplerateDenomominator, long channelCount) {
            this.samplerateNumerator = samplerateNumerator;
            this.samplerateDenomominator = samplerateDenomominator;
            this.channelCount = channelCount;
        }
    }

    /**
     * AKA Stream Class
     */
    public enum Type {
        VIDEO(0),
        AUDIO(1),
        SUBTITLES(2),
        USER_DATA(4);

        private long code;

        Type(long code) {
            this.code = code;
        }

        public static Type fromCode(long code) {
            for (Type type : values()) {
                if (type.code == code) {
                    return type;
                }
            }

            return null;
        }
    }

    public enum Flag {
        /**
         * indicates that the fps is fixed
         */
        FIXED_FPS(1);

        private final long code;

        Flag(long code) {
            this.code = code;
        }

        public static Set<Flag> fromBitCode(long value) {
            if (value == FIXED_FPS.code) {
                return Collections.singleton(FIXED_FPS);
            }

            return Collections.emptySet();
        }
    }

    public enum ColourspaceType {
        /*
     0    unknown
     1    ITU Rec 624 / ITU Rec 601 Y range: 16..235 Cb/Cr range: 16..240
     2    ITU Rec 709               Y range: 16..235 Cb/Cr range: 16..240
    17    ITU Rec 624 / ITU Rec 601 Y range:  0..255 Cb/Cr range:  0..255
    18    ITU Rec 709               Y range:  0..255 Cb/Cr range:  0..255
         */
        UNKNOWN(0),
        ITU_624(1),
        ITU_709(2),
        ITU_624_255(17),
        ITU_709_255(18);

        private final long code;

        ColourspaceType(long code) {
            this.code = code;
        }

        public static ColourspaceType fromCode(long code) {
            for (ColourspaceType type : values()) {
                if (code == type.code) {
                    return type;
                }
            }

            return null;
        }
    }
}
