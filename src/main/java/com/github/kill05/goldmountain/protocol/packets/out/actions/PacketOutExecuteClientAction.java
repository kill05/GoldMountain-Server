package com.github.kill05.goldmountain.protocol.packets.out.actions;

import com.github.kill05.goldmountain.protocol.PacketSerializer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import org.joml.Vector2f;

public abstract class PacketOutExecuteClientAction implements Packet {

    /*
    TESTED USING THIS PACKET PAYLOAD:
    - 1764 1100 0000 05 30003000____e1000000

    ===========================================================================

    0x0002 = CRASH (no log)
    0x0003 = CRASH (no log)
    0x0004 = CRASH (no log)
    0x0005 = CRASH (no log)
    0x0010 = CRASH (no log)

    ===========================================================================

    0x0100 = CRASH (with log)
    0x0600 = CRASH (with log)
    0x0a00 = CRASH (with log)
    0x0e00 = CRASH (with log)
    0x2000 = CRASH (with log)
    0x2800 = CRASH (with log)
    0x2900 = CRASH (with log)
    0x3500 = CRASH (with log)
    0x3900 = CRASH (with log)
    0x3a00 = CRASH (with log)
    0x3b00 = CRASH (with log)
    0x3c00 = CRASH (with log)
    0x3d00 = CRASH (with log)
    0x3f00 = CRASH (with log)
    0x5300 = CRASH (with log)
    0x5b00 = CRASH (with log)
    0x6600 = CRASH (with log)

    0xb400 = CRASH (with log)

    ===========================================================================

    0x0900 = NOTHING

    0x1300 = NOTHING
    0x1600 = NOTHING

    0x1e00 = NOTHING
    0x1f00 = NOTHING

    0x2300 = NOTHING
    0x2700 = NOTHING
    0x2a00 = NOTHING
    0x2d00 = NOTHING

    0x3200 = NOTHING
    0x3400 = NOTHING
    0x3700 = NOTHING (sending multiple slowed down the game)
    0x3800 = NOTHING
    0x3e00 = NOTHING

    0x4600 = NOTHING
    0x4700 = NOTHING
    0x4800 = NOTHING
    0x4a00 = NOTHING

    0x5400 = NOTHING
    0x5500 = NOTHING
    0x5600 = NOTHING
    0x5800 = NOTHING
    0x5900 = NOTHING

    0x6f00 = NOTHING

    0x9d00 = NOTHING
    0x9e00 = NOTHING
    0x9f00 = NOTHING

    0xa000 = NOTHING
    0xab00 = NOTHING
    0xac00 = NOTHING
    0xaf00 = NOTHING

    0xb300 = NOTHING

    0xcd00 = NOTHING

    0xd200 = NOTHING
    0xd300 = NOTHING
    0xd500 = NOTHING
    0xd900 = NOTHING

    0xe200 = NOTHING
    0xef00 = NOTHING

    0xf000 = NOTHING

    ===========================================================================

    0x0000 = PLACE ORE
    0x0200 = PLACE ORE
    0x0300 = PLACE ORE
    0x0700 = PLACE ORE
    0x0b00 = PLACE ORE
    0x0c00 = PLACE ORE
    0x0f00 = PLACE ORE

    0x1800 = PLACE ORE
    0x1a00 = PLACE ORE
    0x1d00 = PLACE ORE

    0x2600 = PLACE ORE
    0x2b00 = PLACE ORE
    0x2e00 = PLACE ORE

    0x3300 = PLACE ORE

    0x5700 = PLACE ORE
    0x5c00 = PLACE ORE
    0x5d00 = PLACE ORE
    0x5e00 = PLACE ORE
    0x5f00 = PLACE ORE

    0x6000 = PLACE ORE
    0x6100 = PLACE ORE
    0x6200 = PLACE ORE
    0x6300 = PLACE ORE
    0x6400 = PLACE ORE
    0x6700 = PLACE ORE
    0x6800 = PLACE ORE
    0x6900 = PLACE ORE
    0x6b00 = PLACE ORE
    0x6c00 = PLACE ORE
    0x6d00 = PLACE ORE
    0x6e00 = PLACE ORE

    0x7000 = PLACE ORE
    0x7100 = PLACE ORE
    0x7200 = PLACE ORE
    0x7400 = PLACE ORE
    0x7400 = PLACE ORE
    0x7500 = PLACE ORE
    0x7600 = PLACE ORE
    0x7700 = PLACE ORE
    0x7800 = PLACE ORE
    0x7900 = PLACE ORE
    0x7a00 = PLACE ORE
    0x7b00 = PLACE ORE
    0x7c00 = PLACE ORE
    0x7d00 = PLACE ORE
    0x7e00 = PLACE ORE
    0x7f00 = PLACE ORE

    0x8000 = PLACE ORE
    0x8100 = PLACE ORE
    0x8200 = PLACE ORE
    0x8300 = PLACE ORE
    0x8400 = PLACE ORE
    0x8500 = PLACE ORE
    0x8600 = PLACE ORE
    0x8700 = PLACE ORE
    0x8800 = PLACE ORE
    0x8900 = PLACE ORE
    0x8a00 = PLACE ORE
    0x8b00 = PLACE ORE
    0x8c00 = PLACE ORE
    0x8d00 = PLACE ORE
    0x8e00 = PLACE ORE
    0x8f00 = PLACE ORE

    0x9000 = PLACE ORE
    0x9100 = PLACE ORE
    0x9200 = PLACE ORE
    0x9300 = PLACE ORE
    0x9400 = PLACE ORE
    0x9500 = PLACE ORE
    0x9600 = PLACE ORE
    0x9700 = PLACE ORE

    0x9900 = PLACE ORE
    0x9a00 = PLACE ORE
    0x9b00 = PLACE ORE
    0x9c00 = PLACE ORE

    0xad00 = PLACE ORE

    0xbd00 = PLACE ORE

    0xc700 = PLACE ORE

    ===========================================================================

    0x0400 = OPEN MENU (reincarnation)

    0x1000 = OPEN MENU (sidebar)
    0x1100 = OPEN MENU (inventory)
    0x1200 = OPEN MENU (delete account)
    0x1500 = OPEN MENU (stick options)
    0x1900 = OPEN MENU (closet)

    0x2100 = OPEN MENU (select account)
    0x2500 = OPEN MENU (buy equipment)

    0x4000 = OPEN MENU (settings)
    0x4100 = OPEN MENU (multiplayer guest)
    0x4200 = OPEN MENU (credits)

    0x6a00 = OPEN MENU (ores collection)

    0xbb00 = OPEN MENU (watch ad for catalyst)

    ===========================================================================

    0x0500 = PLAY AD (reward: NOTHING)
    0x1b00 = PLAY AD (reward: REVIVE BOOK)
    0x3100 = PLAY AD (reward: REINCARNATION BOOK)
    0x4400 = PLAY AD (reward: nothing)

    ===========================================================================

    0x1c00 = place infinity key
    0x0d00 = place dimensional pickaxe
    0xce00 = place secret key

    ===========================================================================

    0x3600 = TELEPORT (temple of reincarnation)
    0x7300 = TELEPORT (temple of reincarnation, but slower)

    ===========================================================================


    0x0800 = render pickaxe swing

    0x0001 = SPAWN grim reaper minion

    0x1400 = spawn circle that when you get close becomes temple of engraving circle, spawns a grim reaper minion and sends message "I hear something moving"
    0x1700 = render black square with ? for a tick, then immediately disappears

    0x2200 = place temple of engraving huge stone and circle
    0x2400 = SPAWN BOSS (mine of dimension)
    0x5200 = SPAWN BOSS (gilded goblin)

    0x2c00 = place temple of engraving huge red crystal
    0x2f00 = render summer costume special attack. Also creates an invisible barrier at the location







    0x4300 = render skill menu for a single tick, also makes stick control brighter
    0x4500 = draw semi transparent box that moves with the player (text background maybe?)

    0x4900 = render room fade, then crashes the game because there is no other scene to load
    0x4b00 = render generic player
    0x4c00 = render self player (moves with the main player)
    0x4d00 = render shadow clone for a tick, then disappears
    0x4e00 = render other player ("mate" player) that disappears because there are no location updates
    0x4f00 = render bot player, then crashes the game

    0x5000 = render darkness
    0x5100 = BACKGROUND (free mine?)


    0x5a00 = render "READY?" and then a 3, 2, 1 countdown


    0x6500 = render purple beam thing




    0x9800 = render drill costume special attack



    0xae00 = spawn an egg that explodes, and then it snows

    0xb000 = start cooldown (duration based on dimension)
    0xb100 = stops countdown
    0xb200 = starts spawning snowman waves

    0x3000 = place puzzle wall
    0xb500 = place stairs (brown)
    0xb600 = place stairs (trial)
    0xb700 = place reincarnation circle
    0xb800 = place temple of reincarnation entrance door (bottom)
    0xb900 = place temple of reincarnation entrance door (top)
    0xba00 = place road tile (full)
    0xbc00 = place road tile (half)
    0xbe00 = place locked door
    0xbf00 = place brown wall

    0xc000 = place brown wall with torch
    0xc100 = place red wall
    0xc200 = place red wall with torch
    0xc300 = place white wall
    0xc400 = place white wall with torch
    0xc500 = place sign (crashes game when you click it, there might be a way to put text)
    0xc600 = place store tile

    0xc800 = place closet tile
    0xc900 = place multiplayer tile
    0xca00 = place black barrier that you can't select
    0xcb00 = place mannequin tile
    0xcf00 = place pushable box tile


    0xd000 = render purple beam thing (half)
    0xd100 = place pushable box destination tile

    0xd400 = render money earned from mining icon
    0xd600 = render map spawned an item on floor icon
    0xd700 = render yellow break particle
    0xd800 = render mining slash and crash game

    0xda00 = render floating shield and crash game
    0xdb00 = render bouncing shield
    0xde00 = render yellow explosion (iirc special ability one)
    0xdf00 = render growing snowflake

    0xe000 = spawn single snowman
    0xe100 = render snowball
    0xe300 = render dark matter particle 1
    0xe400 = render dark matter particle 2
    0xe500 = render mini smoke particle
    0xe600 = render mining hit particle
    0xe700 = render reincarnation light beam particle
    0xe800 = render mini player particle (fast)
    0xe900 = render mini player particle (slow)
    0xea00 = render mini blast particle
    0xeb00 = render blue explosion (iirc special ability one)
    0xec00 = spawn energy ball that damages you when you get hit
    0xed00 = spawn damaging zone warning of magma mine bosses
    0xee00 = spawn single damaging zone warning

    0xf100 = spawn meteor
    0xf200 = spawn small MOTD star
    0xf300 = spawn default costume ultimate ability
    0xf400 = spawn drill costume ultimate ability
    0xf500 = activate MOTD
    0xf600 = try to render some ability, then crashes
    0xf700 = render arrow storm
    0xf800 = render random direction flying heart
    0xf900 = render upwards flying arrow
    0xfa00 = spawn archer costume ultimate ability
    0xfb00 = spawn valentine costume ultimate ability
    0xfc00 = render hammer costume hammer for a tick
    0xfd00 = render cross shaped particle for a tick
    0xfe00 = spawn goblin minion
    0xff00 = spawn blue colored miner minion
     */

    private Vector2f location;
    private int tileId;

    @Override
    public void encode(PacketSerializer serializer) {
        //05 x y code id
        serializer.writeLocation(location);
        serializer.writeShortLE(getActionCode());
        serializer.writeIntLE(tileId);
    }

    @Override
    public void decode(PacketSerializer serializer) {

    }

    public abstract short getActionCode();



    public Vector2f getLocation() {
        return location;
    }

    public void setLocation(Vector2f location) {
        this.location = location;
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }
}
