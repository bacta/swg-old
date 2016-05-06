package com.ocdsoft.bacta.swg.shared.lot;

import bacta.iff.Iff;
import com.google.common.base.Preconditions;
import com.ocdsoft.bacta.swg.shared.foundation.Tag;
import com.ocdsoft.bacta.swg.shared.math.Rectangle2d;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ocdsoft.bacta.swg.shared.foundation.Tag.*;

/**
 * Created by crush on 4/23/2016.
 */
public class StructureFootprint {
    private static final Logger LOGGER = LoggerFactory.getLogger(StructureFootprint.class);

    private static final int TAG_FOOT = Tag.convertStringToTag("FOOT");
    private static final int TAG_PRNT = Tag.convertStringToTag("PRNT");

    @Getter
    private int width;
    @Getter
    private int height;
    @Getter
    private Rectangle2d boxTestRect = new Rectangle2d();
    @Getter
    private int pivotX;
    @Getter
    private int pivotZ;
    private LotType[] data;
    @Getter
    private float hardReservationTolerance;
    @Getter
    private float structureReservationTolerance;
    @Getter
    private int numberOfReservations;
    @Getter
    private int numberOfHardReservations;
    @Getter
    private int numberOfStructureReservations;

    public StructureFootprint() {
        //Setup defaults. Default is one reservation lot.
        width = 1;
        height = 1;
        data = new LotType[]{LotType.STRUCTURE};
        hardReservationTolerance = 1.0f;
        structureReservationTolerance = 1.0f;
        numberOfReservations = 1;
        numberOfStructureReservations = 1;
    }

    public LotType getLotType(int x, int z) {
        return data[width * z + x];
    }

    public boolean isTopBorder(int x, int z) {
        final boolean valid = getLotType(x, z) == LotType.STRUCTURE;
        final boolean outOfRange = z < 1;

        return valid && (outOfRange || getLotType(x, z - 1) != LotType.STRUCTURE);
    }

    public boolean isBottomBorder(int x, int z) {
        final boolean valid = getLotType(x, z) == LotType.STRUCTURE;
        final boolean outOfRange = z >= height - 1;

        return valid && (outOfRange || getLotType(x, z + 1) != LotType.STRUCTURE);
    }

    public boolean isRightBorder(int x, int z) {
        final boolean valid = getLotType(x, z) == LotType.STRUCTURE;
        final boolean outOfRange = x >= width - 1;

        return valid && (outOfRange || getLotType(x + 1, z) != LotType.STRUCTURE);
    }

    public boolean isLeftBorder(int x, int z) {
        final boolean valid = getLotType(x, z) == LotType.STRUCTURE;
        final boolean outOfRange = x < 1;

        return valid && (outOfRange || getLotType(x - 1, z) != LotType.STRUCTURE);
    }

    public void load(final Iff iff) {
        iff.enterForm(TAG_FOOT);
        {
            final int version = iff.getCurrentName();

            if (version == TAG_0000) {
                load0000(iff);
            } else {
                LOGGER.error("unknown version [%s]", Tag.convertTagToString(version));
            }
        }
    }

    private void load0000(final Iff iff) {
        iff.enterForm(TAG_0000);
        {
            iff.enterChunk(TAG_INFO);
            {
                width = iff.readInt();
                height = iff.readInt();
                pivotX = iff.readInt();
                pivotZ = iff.readInt();
                hardReservationTolerance = iff.readFloat();
                structureReservationTolerance = iff.readFloat();
            }
            iff.exitChunk(TAG_INFO);

            boxTestRect.x0 = 0.0f;
            boxTestRect.y0 = 0.0f;
            boxTestRect.x1 = width;
            boxTestRect.y1 = height;

            if (iff.enterChunk(TAG_DATA, true)) {
                boxTestRect.x0 = iff.readInt();
                boxTestRect.y0 = iff.readInt();
                boxTestRect.x1 = iff.readInt();
                boxTestRect.y1 = iff.readInt();

                iff.exitChunk(TAG_DATA);
            }

            final StringBuilder footprint = new StringBuilder(width * height);

            iff.enterChunk(TAG_PRNT);
            {
                for (int i = 0; i < height; ++i) {
                    final String row = iff.readString();

                    Preconditions.checkState(row.length() == width, "[%s]: width mismatch", iff.getFileName());

                    footprint.append(row);
                }
            }
            iff.exitChunk(TAG_PRNT);

            data = new LotType[width * height];
            numberOfReservations = 0;
            numberOfHardReservations = 0;
            numberOfStructureReservations = 0;

            for (int i = 0, size = width * height; i < size; ++i) {
                final char c = footprint.charAt(i);

                switch (c) {
                    case 'F':
                    case 'f':
                        data[i] = LotType.STRUCTURE;
                        ++numberOfReservations;
                        ++numberOfStructureReservations;
                        break;

                    case 'H':
                    case 'h':
                        data[i] = LotType.HARD;
                        ++numberOfReservations;
                        ++numberOfHardReservations;
                        break;

                    case '.':
                        data[i] = LotType.NOTHING;
                        break;

                    default:
                        data[i] = LotType.HARD;
                        ++numberOfReservations;
                        ++numberOfHardReservations;
                        LOGGER.warn("unknown lot type %c", c);
                        break;
                }
            }
        }
        iff.exitForm(TAG_0000);
    }
}
