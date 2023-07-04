package com.github.martinfrank.poolapp;

import com.github.martinfrank.poolapp.data.Activator;
import com.github.martinfrank.poolapp.data.Oxygen;
import com.github.martinfrank.poolapp.data.PhChange;
import com.github.martinfrank.poolapp.data.PoolData;

import java.util.List;

public interface RestDataReceiver {
    void updatePoolData(List<PoolData> poolDataList);

    void updatePhChange(PhChange phChange);
    void updateOxygen(Oxygen oxygen);
    void updateActivator(Activator activator);

    void showToast(String message);
}
