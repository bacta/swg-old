package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.iff.chunk.ChunkBuffer;
import com.ocdsoft.bacta.swg.shared.object.crc.PersistentCrcString;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/4/14.
 */
public abstract class ObjectTemplate extends DataResource<ObjectTemplate> {
    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public static final int ID_DERV = ChunkBuffer.createChunkId("DERV"); //Derived from
    public static final int ID_XXXX = ChunkBuffer.createChunkId("XXXX"); //Parameter
    public static final int ID_PCNT = ChunkBuffer.createChunkId("PCNT"); //Parameter count
    public static final int ID_CCLT = ChunkBuffer.createChunkId("CCLT");
    public static final int ID_CPO  = ChunkBuffer.createChunkId("CPO ");
    public static final int ID_CSFO = ChunkBuffer.createChunkId("CFSO");
    public static final int ID_OUTL = ChunkBuffer.createChunkId("OUTL");
    public static final int ID_RCCT = ChunkBuffer.createChunkId("RCCT");
    public static final int ID_SBMK = ChunkBuffer.createChunkId("SBMK");
    public static final int ID_SBOT = ChunkBuffer.createChunkId("SBOT");
    public static final int ID_SCNC = ChunkBuffer.createChunkId("SCNC");
    public static final int ID_SCOT = ChunkBuffer.createChunkId("SCOT");
    public static final int ID_SCOU = ChunkBuffer.createChunkId("SCOU");
    public static final int ID_SDSC = ChunkBuffer.createChunkId("SDSC");
    public static final int ID_SFOT = ChunkBuffer.createChunkId("SFOT");
    public static final int ID_SGLD = ChunkBuffer.createChunkId("SGLD");
    public static final int ID_SGRP = ChunkBuffer.createChunkId("SGRP");
    public static final int ID_SHOT = ChunkBuffer.createChunkId("SHOT");
    public static final int ID_SIOT = ChunkBuffer.createChunkId("SIOT");
    public static final int ID_SITN = ChunkBuffer.createChunkId("SITN");
    public static final int ID_SJED = ChunkBuffer.createChunkId("SJED");
    public static final int ID_SMLE = ChunkBuffer.createChunkId("SMLE");
    public static final int ID_SMSC = ChunkBuffer.createChunkId("SMSC");
    public static final int ID_SMSD = ChunkBuffer.createChunkId("SMSD");
    public static final int ID_SMSO = ChunkBuffer.createChunkId("SMSO");
    public static final int ID_SPLY = ChunkBuffer.createChunkId("SPLY");
    public static final int ID_SSHP = ChunkBuffer.createChunkId("SSHP");
    public static final int ID_STAT = ChunkBuffer.createChunkId("STAT");
    public static final int ID_STER = ChunkBuffer.createChunkId("STER");
    public static final int ID_STOK = ChunkBuffer.createChunkId("STOK");
    public static final int ID_STOT = ChunkBuffer.createChunkId("STOT");
    public static final int ID_SUNI = ChunkBuffer.createChunkId("SUNI");
    public static final int ID_SVOT = ChunkBuffer.createChunkId("SVOT");
    public static final int ID_SWAY = ChunkBuffer.createChunkId("SWAY");
    public static final int ID_SWOT = ChunkBuffer.createChunkId("SWOT");

    public static final int ID_CVMM = ChunkBuffer.createChunkId("CVMM");
    public static final int ID_CSCV = ChunkBuffer.createChunkId("CSCV");
    public static final int ID_RICV = ChunkBuffer.createChunkId("RICV");
    public static final int ID_PCCV = ChunkBuffer.createChunkId("PCCV");

    public static final int ID_SISS = ChunkBuffer.createChunkId("SISS");
    public static final int ID_DSSA = ChunkBuffer.createChunkId("DSSA");

    //Server types...
    public static final int ID_SRCO = ChunkBuffer.createChunkId("SRCO");

    @Getter @Setter
    protected ObjectTemplate baseData; //This is a reference to the derived from template.

    public abstract int getId();

    public ObjectTemplate(final String templateName) {
        super(new PersistentCrcString(templateName));
    }

    public ObjectTemplate(final PersistentCrcString templateName) {
        super(templateName);
    }

    public boolean derivesFrom(final String str) {
        //Check the current template.
        if (getName().equals(str))
            return true;

        //Check the current template's base.
        if (this.baseData != null) {
            if (this.baseData.getName().equals(str)) {
                return true;
            } else {
                return this.baseData.derivesFrom(str);
            }
        }

        return false;
    }
}
