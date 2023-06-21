package com.github.martinfrank.poolapp;

import java.util.List;

public interface RestDataReceiver {
    void updatePoolData(List<PoolData> poolDataList);

    void updatePhChange(PhChange phChange);
    void updateOxygen(Oxygen oxygen);
    void updateActivator(Activator activator);

    void showToast(String message);
}
