package com.example.unsleeper;

import android.graphics.drawable.Icon;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class Unsleeper extends TileService {

    public int save_settings;

    public void keepScreenOn(){
        this.save_settings = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, -1);
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, Integer.MAX_VALUE);
    }

    public void returnToDefaultSettings(){
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, this.save_settings);
    }

    @Override
    public void onClick() {
        super.onClick();
        Tile tile = getQsTile();
        if(tile.getState() == Tile.STATE_INACTIVE) {
            // Turn on

            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_phonelink_lock_24));
            tile.setState(Tile.STATE_ACTIVE);
            keepScreenOn();
        } else {
            // Turn off
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_smartphone_24));
            tile.setState(Tile.STATE_INACTIVE);
            returnToDefaultSettings();
        }

        // Update looks
        getQsTile().updateTile();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();

        // if the screen lock was on, i disable it
        Tile tile = getQsTile();

        if(tile.getState() == Tile.STATE_ACTIVE) {
            tile.setIcon(Icon.createWithResource(this, R.drawable.ic_baseline_smartphone_24));
            tile.setState(Tile.STATE_INACTIVE);
            returnToDefaultSettings();
        }
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();

        // Update state
        getQsTile().setState(Tile.STATE_INACTIVE);

        this.save_settings = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, -1);

        // Update looks
        getQsTile().updateTile();
    }
}