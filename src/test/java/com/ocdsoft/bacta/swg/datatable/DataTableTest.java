package com.ocdsoft.bacta.swg.datatable;

import bacta.iff.Iff;
import com.ocdsoft.bacta.tre.TreeFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by crush on 11/21/2015.
 */
public class DataTableTest {
    private static final String resourcesPath = new File(DataTableTest.class.getResource("/").getFile()).getAbsolutePath();

    private DataTable commandTableGround;
    private DataTable badgeMap;

    @Before
    public void shouldLoadDataTables() throws Exception {
        final Iff commandTableGroundIff = new Iff("datatables/command/command_table_ground.iff", Files.readAllBytes(Paths.get(resourcesPath + "\\command_table_ground.iff")));
        final Iff badgeMapIff = new Iff("datatables/badge/badge_map.iff", Files.readAllBytes(Paths.get(resourcesPath + "\\badge_map.iff")));

        final DataTableManager dataTableManager = new DataTableManager(new TreeFile());

        this.commandTableGround = new DataTable();
        this.commandTableGround.load(commandTableGroundIff, dataTableManager);

        this.badgeMap = new DataTable();
        this.badgeMap.load(badgeMapIff, dataTableManager);
    }

    @Test
    public void shouldGetName() {
        Assert.assertEquals("datatables/command/command_table_ground.iff", this.commandTableGround.getName());
        Assert.assertEquals("datatables/badge/badge_map.iff", this.badgeMap.getName());
    }

    @Test
    public void shouldGetNumberOfColumns() {
        Assert.assertEquals(75, this.commandTableGround.getNumColumns());
    }

    @Test
    public void shouldGetNumberOfRows() {
        Assert.assertEquals(5, this.commandTableGround.getNumRows());
    }

    @Test
    public void shouldColumnsExist() {
        Assert.assertTrue(this.commandTableGround.doesColumnExist("commandName"));
        Assert.assertTrue(this.commandTableGround.doesColumnExist("failCppHook"));
        Assert.assertTrue(this.commandTableGround.doesColumnExist("L:walking"));
        Assert.assertFalse(this.commandTableGround.doesColumnExist("TYPE"));
    }

    @Test
    public void shouldGetColumnName() {
        Assert.assertEquals("commandName", this.commandTableGround.getColumnName(0));
        Assert.assertEquals("defaultPriority", this.commandTableGround.getColumnName(1));
        Assert.assertEquals("scriptHook", this.commandTableGround.getColumnName(2));
    }

    @Test
    public void shouldFindColumnNumber() {
        Assert.assertEquals(3, this.commandTableGround.findColumnNumber("failScriptHook"));
        Assert.assertEquals(6, this.commandTableGround.findColumnNumber("defaultTime"));
        Assert.assertEquals(7, this.commandTableGround.findColumnNumber("characterAbility"));
    }

    @Test
    public void shouldGetIntValueByColumnName() {
        Assert.assertEquals(2, this.commandTableGround.getIntValue("visible", 0));
        Assert.assertEquals(0, this.commandTableGround.getIntValue("displayGroup", 0));
        Assert.assertEquals(1, this.commandTableGround.getIntValue("S:shipInterior", 0));
        Assert.assertEquals(0, this.commandTableGround.getIntValue("visible", 2));
    }

    @Test
    public void shouldGetIntValueByColumnIndex() {
        Assert.assertEquals(2, this.commandTableGround.getIntValue(67, 0));
        Assert.assertEquals(0, this.commandTableGround.getIntValue(73, 0));
        Assert.assertEquals(1, this.commandTableGround.getIntValue(63, 0));
        Assert.assertEquals(0, this.commandTableGround.getIntValue(67, 2));
    }

    @Test
    public void shouldGetIntDefaultForColumnByColumnName() {
        Assert.assertEquals(2, this.commandTableGround.getIntDefaultForColumn("defaultPriority"));
        Assert.assertEquals(0, this.commandTableGround.getIntDefaultForColumn("targetType"));
        Assert.assertEquals(0, this.commandTableGround.getIntDefaultForColumn("visible"));
        Assert.assertEquals(0, this.commandTableGround.getIntDefaultForColumn("displayGroup"));
        Assert.assertEquals(1, this.commandTableGround.getIntDefaultForColumn("S:shipInterior"));
    }

    @Test
    public void shouldGetIntDefaultForColumnByColumnIndex() {
        Assert.assertEquals(2, this.commandTableGround.getIntDefaultForColumn(1));
        Assert.assertEquals(0, this.commandTableGround.getIntDefaultForColumn(65));
        Assert.assertEquals(0, this.commandTableGround.getIntDefaultForColumn(67));
        Assert.assertEquals(0, this.commandTableGround.getIntDefaultForColumn(73));
        Assert.assertEquals(1, this.commandTableGround.getIntDefaultForColumn(63));
    }

    @Test
    public void shouldGetFloatValueByColumnName() {
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatValue("defaultTime", 0), 0.0f);
        Assert.assertEquals(16.0f, this.commandTableGround.getFloatValue("maxRangeToTarget", 1), 0.0f);
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatValue("defaultTime", 4), 0.0f);
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatValue("maxRangeToTarget", 3), 0.0f);
    }

    @Test
    public void shouldGetFloatValueByColumnIndex() {
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatValue(6, 0), 0.0f);
        Assert.assertEquals(16.0f, this.commandTableGround.getFloatValue(71, 1), 0.0f);
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatValue(6, 4), 0.0f);
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatValue(71, 2), 0.0f);
    }

    @Test
    public void shouldGetFloatDefaultForColumnByColumnName() {
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatDefaultForColumn("defaultTime"), 0.0f);
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatDefaultForColumn("maxRangeToTarget"), 0.0f);
    }

    @Test
    public void shouldGetFloatDefaultForColumnByColumnIndex() {
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatDefaultForColumn(6), 0.0f);
        Assert.assertEquals(0.0f, this.commandTableGround.getFloatDefaultForColumn(71), 0.0f);
    }

    @Test
    public void shouldGetStringValueByColumnName() {
        Assert.assertEquals("exp_tat_bens_hut", this.badgeMap.getStringValue("KEY", 12));
        Assert.assertEquals("exploration_jedi", this.badgeMap.getStringValue("TYPE", 30));
        Assert.assertEquals("acc_brave_soldier", this.badgeMap.getStringValue("KEY", 35));
        Assert.assertEquals("sound/music_acq_miner.snd", this.badgeMap.getStringValue("MUSIC", 75));
    }

    @Test
    public void shouldGetStringValueByColumnIndex() {
        Assert.assertEquals("exp_tat_bens_hut", this.badgeMap.getStringValue(1, 12));
        Assert.assertEquals("exploration_jedi", this.badgeMap.getStringValue(5, 30));
        Assert.assertEquals("acc_brave_soldier", this.badgeMap.getStringValue(1, 35));
        Assert.assertEquals("sound/music_acq_miner.snd", this.badgeMap.getStringValue(2, 75));
    }

    @Test
    public void shouldGetStringDefaultForColumnByColumnName() {
        Assert.assertEquals("unused", this.badgeMap.getStringDefaultForColumn("KEY"));
        Assert.assertEquals("sound/music_acq_miner.snd", this.badgeMap.getStringDefaultForColumn("MUSIC"));
    }

    @Test
    public void shouldGetStringDefaultForColumnByColumnIndex() {
        Assert.assertEquals("unused", this.badgeMap.getStringDefaultForColumn(1));
        Assert.assertEquals("sound/music_acq_miner.snd", this.badgeMap.getStringDefaultForColumn(2));
    }

    @Test
    public void shouldGetIntColumnByColumnName() {
        Assert.assertArrayEquals(new int[]{2, 1, 0, 2, 0}, this.commandTableGround.getIntColumn("visible"));
        Assert.assertArrayEquals(new int[]{0, 1, 0, 2, 0}, this.commandTableGround.getIntColumn("targetType"));
    }

    @Test
    public void shouldGetIntColumnByColumnIndex() {
        Assert.assertArrayEquals(new int[]{2, 1, 0, 2, 0}, this.commandTableGround.getIntColumn(67));
        Assert.assertArrayEquals(new int[]{0, 1, 0, 2, 0}, this.commandTableGround.getIntColumn(65));
    }

    @Test
    public void shouldGetFloatColumnByColumnName() {
        Assert.assertArrayEquals(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, this.commandTableGround.getFloatColumn("defaultTime"), 0.0f);
        Assert.assertArrayEquals(new float[]{0.0f, 16.0f, 0.0f, 0.0f, 0.0f}, this.commandTableGround.getFloatColumn("maxRangeToTarget"), 0.0f);
    }

    @Test
    public void shouldGetFloatColumnByColumnIndex() {
        Assert.assertArrayEquals(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, this.commandTableGround.getFloatColumn(6), 0.0f);
        Assert.assertArrayEquals(new float[]{0.0f, 16.0f, 0.0f, 0.0f, 0.0f}, this.commandTableGround.getFloatColumn(71), 0.0f);
    }

    @Test
    public void shouldGetStringColumnByColumnName() {
        Assert.assertArrayEquals(new String[]{"eject", "launchIntoSpace", "unstick", "waypoint", "createVendor"}, this.commandTableGround.getStringColumn("commandName"));
        Assert.assertArrayEquals(new String[]{"cmdEject", "", "", "cmdWaypoint", "createVendor"}, this.commandTableGround.getStringColumn("scriptHook"));
    }

    @Test
    public void shouldGetStringColumnByColumnIndex() {
        Assert.assertArrayEquals(new String[]{"eject", "launchIntoSpace", "unstick", "waypoint", "createVendor"}, this.commandTableGround.getStringColumn(0));
        Assert.assertArrayEquals(new String[]{"cmdEject", "", "", "cmdWaypoint", "createVendor"}, this.commandTableGround.getStringColumn(2));
    }
}
