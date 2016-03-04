package org.ovirt.engine.core.common.vdscommands;

import java.util.Collections;
import java.util.List;

import org.ovirt.engine.core.common.businessentities.network.NetworkAttachment;
import org.ovirt.engine.core.common.businessentities.network.VdsNetworkInterface;

public class UserConfiguredNetworkData {
    private final List<NetworkAttachment> networkAttachments;
    private final List<VdsNetworkInterface> nics;

    public UserConfiguredNetworkData() {
        this(Collections.<NetworkAttachment>emptyList(), Collections.<VdsNetworkInterface>emptyList());
    }

    public UserConfiguredNetworkData(List<NetworkAttachment> networkAttachments, List<VdsNetworkInterface> nics) {
        this.networkAttachments = networkAttachments;
        this.nics = nics;
    }

    public List<NetworkAttachment> getNetworkAttachments() {
        return networkAttachments;
    }

    public List<VdsNetworkInterface> getNics() {
        return nics;
    }
}
