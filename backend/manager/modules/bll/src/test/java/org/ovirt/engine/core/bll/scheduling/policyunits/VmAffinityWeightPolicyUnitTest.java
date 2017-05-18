package org.ovirt.engine.core.bll.scheduling.policyunits;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.ovirt.engine.core.common.businessentities.VDS;
import org.ovirt.engine.core.common.businessentities.VM;
import org.ovirt.engine.core.common.scheduling.EntityAffinityRule;
import org.ovirt.engine.core.common.utils.Pair;
import org.ovirt.engine.core.compat.Guid;

@RunWith(MockitoJUnitRunner.class)
public class VmAffinityWeightPolicyUnitTest extends VmAffinityPolicyUnitTestBase {

    @InjectMocks
    private VmAffinityWeightPolicyUnit policyUnit = new VmAffinityWeightPolicyUnit(null, pendingResourceManager);

    @Test
    public void testNoAffinityGroups() {
        List<VDS> hosts = Arrays.asList(host1, host2);

        Map<Guid, Integer> scores = collectScores(policyUnit.score(cluster, hosts, newVm, null));
        assertEquals(scores.get(host1.getId()), scores.get(host2.getId()));
    }

    @Test
    public void testFirstVm() {
        List<VDS> hosts = Arrays.asList(host1, host2);

        VM vm1 = createVMDown(cluster);
        VM vm2 = createVMDown(cluster);

        affinityGroups.add(createAffinityGroup(cluster, EntityAffinityRule.POSITIVE, true,
                vm1, vm2, newVm));

        Map<Guid, Integer> scores = collectScores(policyUnit.score(cluster, hosts, newVm, null));
        assertEquals(scores.get(host1.getId()), scores.get(host2.getId()));

        affinityGroups.clear();
        affinityGroups.add(createAffinityGroup(cluster, EntityAffinityRule.POSITIVE, false,
                vm1, vm2, newVm));

        scores = collectScores(policyUnit.score(cluster, hosts, newVm, null));
        assertEquals(scores.get(host1.getId()), scores.get(host2.getId()));
    }

    @Test
    public void testPositiveAffinity() {
        List<VDS> hosts = Arrays.asList(host1, host2, host3);

        VM vm1 = createVmRunning(host2);
        VM vm2 = createVmRunning(host2);

        affinityGroups.add(createAffinityGroup(cluster, EntityAffinityRule.POSITIVE, true,
                vm1, vm2, newVm));

        Map<Guid, Integer> scores = collectScores(policyUnit.score(cluster, hosts, newVm, null));
        assertEquals(scores.get(host1.getId()), scores.get(host3.getId()));
        assertThat(scores.get(host1.getId())).isGreaterThan(scores.get(host2.getId()));

        affinityGroups.clear();
        affinityGroups.add(createAffinityGroup(cluster, EntityAffinityRule.POSITIVE, false,
                vm1, vm2, newVm));

        scores = collectScores(policyUnit.score(cluster, hosts, newVm, null));
        assertEquals(scores.get(host1.getId()), scores.get(host3.getId()));
        assertThat(scores.get(host1.getId())).isGreaterThan(scores.get(host2.getId()));
    }

    @Test
    public void testNegativeAffinity() {
        List<VDS> hosts = Arrays.asList(host1, host2, host3);

        VM vm1 = createVmRunning(host1);
        VM vm2 = createVmRunning(host3);


        affinityGroups.add(createAffinityGroup(cluster, EntityAffinityRule.NEGATIVE, true,
                vm1, vm2, newVm));

        Map<Guid, Integer> scores = collectScores(policyUnit.score(cluster, hosts, newVm, null));
        assertEquals(scores.get(host1.getId()), scores.get(host3.getId()));
        assertThat(scores.get(host1.getId())).isGreaterThan(scores.get(host2.getId()));

        affinityGroups.clear();
        affinityGroups.add(createAffinityGroup(cluster, EntityAffinityRule.NEGATIVE, false,
                vm1, vm2, newVm));

        scores = collectScores(policyUnit.score(cluster, hosts, newVm, null));
        assertEquals(scores.get(host1.getId()), scores.get(host3.getId()));
        assertThat(scores.get(host1.getId())).isGreaterThan(scores.get(host2.getId()));
    }

    private Map<Guid, Integer> collectScores(List<Pair<Guid, Integer>> scores) {
        return scores.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }
}