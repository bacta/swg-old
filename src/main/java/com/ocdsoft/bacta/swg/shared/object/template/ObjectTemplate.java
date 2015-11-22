package com.ocdsoft.bacta.swg.shared.object.template;

import bacta.iff.Iff;
import com.ocdsoft.bacta.swg.foundation.DataResource;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by crush on 3/4/14.
 */
public abstract class ObjectTemplate extends DataResource {
    protected final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public static final int ID_DERV = Iff.createChunkId("DERV"); //Derived from
    public static final int ID_XXXX = Iff.createChunkId("XXXX"); //Parameter
    public static final int ID_PCNT = Iff.createChunkId("PCNT"); //Parameter count
    public static final int ID_CCLT = Iff.createChunkId("CCLT");
    public static final int ID_CPO = Iff.createChunkId("CPO ");
    public static final int ID_CSFO = Iff.createChunkId("CFSO");
    public static final int ID_OUTL = Iff.createChunkId("OUTL");
    public static final int ID_RCCT = Iff.createChunkId("RCCT");
    public static final int ID_SBMK = Iff.createChunkId("SBMK");
    public static final int ID_SBOT = Iff.createChunkId("SBOT");
    public static final int ID_SCNC = Iff.createChunkId("SCNC");
    public static final int ID_SCOT = Iff.createChunkId("SCOT");
    public static final int ID_SCOU = Iff.createChunkId("SCOU");
    public static final int ID_SDSC = Iff.createChunkId("SDSC");
    public static final int ID_SFOT = Iff.createChunkId("SFOT");
    public static final int ID_SGLD = Iff.createChunkId("SGLD");
    public static final int ID_SGRP = Iff.createChunkId("SGRP");
    public static final int ID_SHOT = Iff.createChunkId("SHOT");
    public static final int ID_SIOT = Iff.createChunkId("SIOT");
    public static final int ID_SITN = Iff.createChunkId("SITN");
    public static final int ID_SJED = Iff.createChunkId("SJED");
    public static final int ID_SMLE = Iff.createChunkId("SMLE");
    public static final int ID_SMSC = Iff.createChunkId("SMSC");
    public static final int ID_SMSD = Iff.createChunkId("SMSD");
    public static final int ID_SMSO = Iff.createChunkId("SMSO");
    public static final int ID_SPLY = Iff.createChunkId("SPLY");
    public static final int ID_SSHP = Iff.createChunkId("SSHP");
    public static final int ID_STAT = Iff.createChunkId("STAT");
    public static final int ID_STER = Iff.createChunkId("STER");
    public static final int ID_STOK = Iff.createChunkId("STOK");
    public static final int ID_STOT = Iff.createChunkId("STOT");
    public static final int ID_SUNI = Iff.createChunkId("SUNI");
    public static final int ID_SVOT = Iff.createChunkId("SVOT");
    public static final int ID_SWAY = Iff.createChunkId("SWAY");
    public static final int ID_SWOT = Iff.createChunkId("SWOT");

    public static final int ID_CVMM = Iff.createChunkId("CVMM");
    public static final int ID_CSCV = Iff.createChunkId("CSCV");
    public static final int ID_RICV = Iff.createChunkId("RICV");
    public static final int ID_PCCV = Iff.createChunkId("PCCV");

    public static final int ID_SISS = Iff.createChunkId("SISS");
    public static final int ID_DSSA = Iff.createChunkId("DSSA");

    //Server types...
    public static final int ID_SRCO = Iff.createChunkId("SRCO");

    @Getter @Setter
    protected ObjectTemplate baseData; //This is a reference to the derived from template.

    public abstract int getId();

    public ObjectTemplate(final String templateName) {
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
