package com.ocdsoft.bacta.swg.shared.object.template;

import com.ocdsoft.bacta.swg.shared.object.template.param.IntegerParam;
import com.ocdsoft.bacta.swg.shared.object.template.param.StringParam;
import lombok.Getter;

/**
 * Created by crush on 3/4/14.
 */
public class SharedWeaponObjectTemplate extends SharedTangibleObjectTemplate {
    public SharedWeaponObjectTemplate(String templateName) {
        super(templateName);
    }

    @Override
    public int getId() { return ID_SWOT; }

    private enum AttackType {
        Melee,
        Ranged,
        Thrown,
        Ammo
    }

    @Getter
    private StringParam weaponEffect = new StringParam();
    @Getter
    private IntegerParam weaponEffectIndex = new IntegerParam();
    @Getter
    private IntegerParam attackType = new IntegerParam();
}
