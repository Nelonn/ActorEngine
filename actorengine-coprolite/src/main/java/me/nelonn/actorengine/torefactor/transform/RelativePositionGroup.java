package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.bestvecs.MutVec3d;
import me.nelonn.bestvecs.Vec3d;

import java.util.Collections;
import java.util.List;

public class RelativePositionGroup implements RelativePosition {
    private final List<RelativePosition> values;

    public RelativePositionGroup(List<RelativePosition> values) {
        this.values = Collections.unmodifiableList(values);
    }

    @Override
    public Vec3d apply(VariablesMap rootProperties) {
        MutVec3d result = new MutVec3d(0, 0, 0);
        for (RelativePosition relativePosition : values) {
            Vec3d vec = relativePosition.apply(rootProperties);
            result.add(vec);
        }
        return result;
    }
}
