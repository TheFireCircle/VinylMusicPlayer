package com.poupa.vinylmusicplayer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.poupa.vinylmusicplayer.App;
import com.poupa.vinylmusicplayer.R;
import com.poupa.vinylmusicplayer.model.CategoryInfo;
import com.poupa.vinylmusicplayer.preferences.annotation.PrefKey;
import com.poupa.vinylmusicplayer.ui.fragments.mainactivity.folders.FoldersFragment;
import com.poupa.vinylmusicplayer.ui.fragments.player.NowPlayingScreen;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class PreferenceUtil {
    // TODO Use string resources for this, avoid duplicating inside UI code

    @PrefKey
    public static final String GENERAL_THEME = "general_theme";
    private static final String GENERAL_THEME_LIGHT = "light";
    private static final String GENERAL_THEME_DARK = "dark";
    private static final String GENERAL_THEME_BLACK = "black";
    public static final String GENERAL_THEME_FOLLOW_SYSTEM_LIGHT_OR_DARK = "follow_system_light_or_dark";
    public static final String GENERAL_THEME_FOLLOW_SYSTEM_LIGHT_OR_BLACK = "follow_system_light_or_black";

    @PrefKey
    public static final String PRIMARY_COLOR = "primary_color";
    @PrefKey
    public static final String ACCENT_COLOR = "accent_color";

    @PrefKey
    public static final String REMEMBER_LAST_TAB = "remember_last_tab";
    @PrefKey
    private static final String LAST_PAGE = "last_start_page";
    @PrefKey
    private static final String LAST_MUSIC_CHOOSER = "last_music_chooser";
    @PrefKey
    public static final String NOW_PLAYING_SCREEN_ID = "now_playing_screen_id";

    @PrefKey
    private static final String ARTIST_SORT_ORDER = "artist_sort_order";
    @PrefKey
    public static final String ALBUM_SORT_ORDER = "album_sort_order";
    @PrefKey
    public static final String SONG_SORT_ORDER = "song_sort_order";
    @PrefKey
    private static final String FILE_SORT_ORDER = "file_sort_order";
    @PrefKey
    private static final String LAST_ADDED_SORT_ORDER = "last_added_sort_order";
    @PrefKey
    private static final String NOT_RECENTLY_PLAYED_SORT_ORDER = "not_recently_played_sort_order";

    @PrefKey
    private static final String ALBUM_GRID_SIZE = "album_grid_size";
    @PrefKey
    private static final String ALBUM_GRID_SIZE_LAND = "album_grid_size_land";

    @PrefKey
    private static final String SONG_GRID_SIZE = "song_grid_size";
    @PrefKey
    private static final String SONG_GRID_SIZE_LAND = "song_grid_size_land";

    @PrefKey
    private static final String ARTIST_GRID_SIZE = "artist_grid_size";
    @PrefKey
    private static final String ARTIST_GRID_SIZE_LAND = "artist_grid_size_land";

    @PrefKey
    private static final String ALBUM_COLORED_FOOTERS = "album_colored_footers";
    @PrefKey
    private static final String SONG_COLORED_FOOTERS = "song_colored_footers";
    @PrefKey
    private static final String ARTIST_COLORED_FOOTERS = "artist_colored_footers";
    @PrefKey
    private static final String ALBUM_ARTIST_COLORED_FOOTERS = "album_artist_colored_footers";

    @PrefKey
    public static final String COLORED_NOTIFICATION = "colored_notification";
    @PrefKey
    public static final String CLASSIC_NOTIFICATION = "classic_notification";
    @PrefKey
    public static final String COLORED_APP_SHORTCUTS = "colored_app_shortcuts";
    @PrefKey
    public static final String SHOULD_COLOR_APP_SHORTCUTS = "should_color_app_shortcuts";
    @PrefKey
    public static final String TRANSPARENT_BACKGROUND_WIDGET = "make_widget_background_transparent";
    @PrefKey
    public static final String AUDIO_DUCKING = "audio_ducking";
    @PrefKey
    public static final String GAPLESS_PLAYBACK = "gapless_playback";

    @PrefKey
    public static final String LAST_ADDED_CUTOFF_V2 = "last_added_interval_v2";
    @PrefKey
    public static final String RECENTLY_PLAYED_CUTOFF_V2 = "recently_played_interval_v2";
    @PrefKey
    public static final String NOT_RECENTLY_PLAYED_CUTOFF_V2 = "not_recently_played_interval_v2";
    @PrefKey
    public static final String MAINTAIN_TOP_TRACKS_PLAYLIST = "maintain_top_tracks_playlist";

    private static final String EXPORT_SETTINGS = "export_settings";
    public static final String IMPORT_SETTINGS = "import_settings";

    @PrefKey
    public static final String FILE_FORMAT = "file_format";
    @PrefKey
    public static final String VERSION_CODE = "version_code";

    @PrefKey
    public static final String MAINTAIN_SKIPPED_SONGS_PLAYLIST = "maintain_skipped_songs_playlist";

    @PrefKey
    private static final String LAST_SLEEP_TIMER_VALUE = "last_sleep_timer_value";
    @PrefKey(ExportImportable = false)
    private static final String NEXT_SLEEP_TIMER_ELAPSED_REALTIME = "next_sleep_timer_elapsed_real_time";
    @PrefKey
    private static final String SLEEP_TIMER_FINISH_SONG = "sleep_timer_finish_music";

    @PrefKey(ExportImportable = false)
    private static final String LAST_CHANGELOG_VERSION = "last_changelog_version";
    @PrefKey(ExportImportable = false)
    private static final String INTRO_SHOWN = "intro_shown";

    @PrefKey
    public static final String AUTO_DOWNLOAD_IMAGES_POLICY = "auto_download_images_policy";
    private static final String AUTO_DOWNLOAD_ALWAYS = "always";
    private static final String AUTO_DOWNLOAD_WIFI_ONLY = "only_wifi";
    private static final String AUTO_DOWNLOAD_NEVER = "never";

    @PrefKey
    private static final String START_DIRECTORY = "start_directory";

    @PrefKey
    private static final String SYNCHRONIZED_LYRICS_SHOW = "synchronized_lyrics_show";
    @PrefKey
    private static final String ANIMATE_PLAYING_SONG_ICON = "animate_playing_song_icon";
    @PrefKey
    private static final String SHOW_SONG_NUMBER = "show_song_number_on_playing_queue";

    @PrefKey(ExportImportable = false)
    private static final String INITIALIZED_BLACKLIST = "initialized_blacklist";
    @PrefKey
    public static final String WHITELIST_ENABLED = "whitelist_enabled";

    @PrefKey
    public static final String LIBRARY_CATEGORIES = "library_categories";
    @PrefKey
    public static final String REMEMBER_SHUFFLE = "remember_shuffle";

    @PrefKey
    public static final String RG_SOURCE_MODE_V2 = "replaygain_source_mode";
    public static final byte RG_SOURCE_MODE_NONE = 0;
    public static final byte RG_SOURCE_MODE_TRACK = 1;
    public static final byte RG_SOURCE_MODE_ALBUM = 2;

    @PrefKey
    public static final String RG_PREAMP_WITH_TAG = "replaygain_preamp_with_tag";
    @PrefKey
    public static final String RG_PREAMP_WITHOUT_TAG = "replaygain_preamp_without_tag";

    @PrefKey
    public static final String THEME_STYLE = "theme_style";
    private static final String CLASSIC_THEME = "classic";
    public static final String ROUNDED_THEME = "rounded";
    public static final String SHOULD_COLOR_NAVIGATION_BAR = "should_color_navigation_bar";

    @PrefKey(ExportImportable = false)
    private static final String SAF_SDCARD_URI = "saf_sdcard_uri";

    @PrefKey
    public static final String ENQUEUE_SONGS_DEFAULT_CHOICE = "enqueue_songs_default_choice";
    public static final int ENQUEUE_SONGS_CHOICE_ASK = 0;
    public static final int ENQUEUE_SONGS_CHOICE_REPLACE = 1;
    public static final int ENQUEUE_SONGS_CHOICE_NEXT = 2;
    public static final int ENQUEUE_SONGS_CHOICE_ADD = 3;

    @PrefKey
    public static final String OOPS_HANDLER_ENABLED = "oops_handler_enabled";
    @PrefKey(ExportImportable = false)
    public static final String OOPS_HANDLER_EXCEPTIONS = "oops_handler_exceptions";

    private Set<Field> exportableFields = null;
    public static final String QUEUE_SYNC_MEDIA_STORE_ENABLED = "queue_sync_with_media_store";

    private static PreferenceUtil sInstance;

    private final SharedPreferences mPreferences;

    private PreferenceUtil() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(App.getStaticContext());
        migratePreferencesIfNeeded();
        checkAnnotations();
    }

    public static PreferenceUtil getInstance() {
        if (sInstance == null) {
            sInstance = new PreferenceUtil();
        }
        return sInstance;
    }

    private void migratePreferencesIfNeeded() {
        // Nothing to do for now
    }

    private void checkAnnotations() {
        final Set<Field> annotatedFields = Arrays.stream(PreferenceUtil.class.getDeclaredFields())
                        .filter(field -> {
                            final PrefKey annotation = field.getAnnotation(PrefKey.class);
                            return (annotation != null);
                        })
                .collect(Collectors.toSet());
        final Set<String> annotatedPrefs = annotatedFields.stream()
                .map(field -> {
                    // get the value of a 'static final' field
                    try {return (String) field.get(null);}
                    catch (IllegalAccessException ignored) {return null;}
                })
                .collect(Collectors.toSet());
        exportableFields = annotatedFields.stream()
                .filter(field -> {
                    final PrefKey annotation = field.getAnnotation(PrefKey.class);
                    return annotation.ExportImportable();
                })
                .collect(Collectors.toSet());

        final Set<String> allPrefs = mPreferences.getAll().keySet();
        final Set<String> notAnnotated = allPrefs.stream().filter(name -> !annotatedPrefs.contains(name)).collect(Collectors.toSet());
        final Set<String> notUsed = annotatedPrefs.stream().filter(name -> !allPrefs.contains(name)).collect(Collectors.toSet());

        // TODO Following tests are examples on how annotation can be used
        if (!notAnnotated.isEmpty()) {
            throw new RuntimeException("Pref used but not annotated: " + notAnnotated);
        }
        if (!notUsed.isEmpty()) {
            SafeToast.show(App.getStaticContext(), "Pref annotated but not used: " + notAnnotated);
        }
    }

    public Set<Field> getExportableFields() {
        if(exportableFields == null) {
            throw new RuntimeException("exportableFields not initialized");
        }
        return exportableFields;
    }

    public static boolean isAllowedToDownloadMetadata(final Context context) {
        switch (getInstance().autoDownloadImagesPolicy()) {
            case AUTO_DOWNLOAD_ALWAYS:
                return true;
            case AUTO_DOWNLOAD_WIFI_ONLY:
                final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                return netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI && netInfo.isConnectedOrConnecting();
            case AUTO_DOWNLOAD_NEVER:
            default:
                return false;
        }
    }

    public void registerOnSharedPreferenceChangedListener(final SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener) {
        mPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    public void unregisterOnSharedPreferenceChangedListener(final SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener) {
        mPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @StyleRes
    public int getGeneralTheme() {
        return getThemeResFromPrefValue(mPreferences.getString(GENERAL_THEME, GENERAL_THEME_LIGHT));
    }

    @StyleRes
    public static int getThemeResFromPrefValue(@NonNull final String themePrefValue) {
        final boolean isNightMode = (VinylMusicPlayerColorUtil.getSystemNightMode(App.getStaticContext()) == Configuration.UI_MODE_NIGHT_YES);

        switch (themePrefValue) {
            case GENERAL_THEME_DARK:
                return R.style.Theme_VinylMusicPlayer;
            case GENERAL_THEME_BLACK:
                return R.style.Theme_VinylMusicPlayer_Black;
            case GENERAL_THEME_FOLLOW_SYSTEM_LIGHT_OR_DARK:
                return isNightMode ? R.style.Theme_VinylMusicPlayer : R.style.Theme_VinylMusicPlayer_Light;
            case GENERAL_THEME_FOLLOW_SYSTEM_LIGHT_OR_BLACK:
                return isNightMode ? R.style.Theme_VinylMusicPlayer_Black : R.style.Theme_VinylMusicPlayer_Light;
            case GENERAL_THEME_LIGHT:
            default:
                return R.style.Theme_VinylMusicPlayer_Light;
        }
    }

    public void setRememberLastTab(final boolean rememberLastTab) {
        mPreferences.edit()
                .putBoolean(REMEMBER_LAST_TAB, rememberLastTab)
                .apply();
    }

    public boolean rememberLastTab() {
        return mPreferences.getBoolean(REMEMBER_LAST_TAB, true);
    }

    public void setLastPage(final int value) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(LAST_PAGE, value);
        editor.apply();
    }

    public int getLastPage() {
        return mPreferences.getInt(LAST_PAGE, 0);
    }

    public void setLastMusicChooser(final int value) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(LAST_MUSIC_CHOOSER, value);
        editor.apply();
    }

    public int getLastMusicChooser() {
        return mPreferences.getInt(LAST_MUSIC_CHOOSER, 0);
    }

    public NowPlayingScreen getNowPlayingScreen() {
        final int id = mPreferences.getInt(NOW_PLAYING_SCREEN_ID, 0);
        for (final NowPlayingScreen nowPlayingScreen : NowPlayingScreen.values()) {
            if (nowPlayingScreen.id == id) {return nowPlayingScreen;}
        }
        return NowPlayingScreen.CARD;
    }

    public void setNowPlayingScreen(NowPlayingScreen nowPlayingScreen) {
        mPreferences.edit()
                .putInt(NOW_PLAYING_SCREEN_ID, nowPlayingScreen.id)
                .apply();
    }

    public boolean coloredNotification() {
        return mPreferences.getBoolean(COLORED_NOTIFICATION, true);
    }

    public boolean classicNotification() {
        return mPreferences.getBoolean(CLASSIC_NOTIFICATION, false);
    }

    public void setColoredNotification(final boolean value) {
        mPreferences.edit()
                .putBoolean(COLORED_NOTIFICATION, value)
                .apply();
    }

    public void setClassicNotification(final boolean value) {
        mPreferences.edit()
                .putBoolean(CLASSIC_NOTIFICATION, value)
                .apply();
    }

    public void setColoredAppShortcuts(final boolean value) {
        mPreferences.edit()
                .putBoolean(COLORED_APP_SHORTCUTS, value)
                .apply();
    }

    public boolean coloredAppShortcuts() {
        return mPreferences.getBoolean(COLORED_APP_SHORTCUTS, true);
    }

    public void setColoredNavigationBar(final boolean value) {
        mPreferences.edit()
                .putBoolean(SHOULD_COLOR_NAVIGATION_BAR, value)
                .apply();
    }

    public boolean coloredNavigationBar() {
        return mPreferences.getBoolean(SHOULD_COLOR_NAVIGATION_BAR, true);
    }

    public void setTransparentBackgroundWidget(final boolean value) {
        mPreferences.edit()
                .putBoolean(TRANSPARENT_BACKGROUND_WIDGET, value)
                .apply();
    }

    public boolean transparentBackgroundWidget() {
        return mPreferences.getBoolean(TRANSPARENT_BACKGROUND_WIDGET, false);
    }

    public void setGaplessPlayback(final boolean value) {
        mPreferences.edit()
                .putBoolean(GAPLESS_PLAYBACK, value)
                .apply();
    }

    public boolean gaplessPlayback() {
        return mPreferences.getBoolean(GAPLESS_PLAYBACK, false);
    }

    public void setAudioDucking(final boolean value) {
        mPreferences.edit()
                .putBoolean(AUDIO_DUCKING, value)
                .apply();
    }

    public boolean audioDucking() {
        return mPreferences.getBoolean(AUDIO_DUCKING, true);
    }

    public String getArtistSortOrder() {
        return mPreferences.getString(ARTIST_SORT_ORDER, "");
    }

    public void setArtistSortOrder(final String sortOrder) {
        mPreferences.edit()
                .putString(ARTIST_SORT_ORDER, sortOrder)
                .apply();
    }

    public String getAlbumSortOrder() {
        return mPreferences.getString(ALBUM_SORT_ORDER, "");
    }

    public void setAlbumSortOrder(final String sortOrder) {
        mPreferences.edit()
                .putString(ALBUM_SORT_ORDER, sortOrder)
                .apply();
    }

    public String getSongSortOrder() {
        return mPreferences.getString(SONG_SORT_ORDER, "");
    }

    public void setSongSortOrder(final String sortOrder) {
        mPreferences.edit()
                .putString(SONG_SORT_ORDER, sortOrder)
                .apply();
    }

    public String getFileSortOrder() {
        return mPreferences.getString(FILE_SORT_ORDER, "");
    }

    public void setFileSortOrder(final String sortOrder) {
        mPreferences.edit()
                .putString(FILE_SORT_ORDER, sortOrder)
                .apply();
    }

    // The last added cutoff time is compared against the Android media store timestamps, which is seconds based.
    public long getLastAddedCutoffTimeSecs() {
        return getCutoffTimeMillisV2(LAST_ADDED_CUTOFF_V2) / 1000;
    }

    // The not recently played cutoff time is compared against the internal (private) database timestamps, which is milliseconds based.
    public long getNotRecentlyPlayedCutoffTimeMillis() {
        return getCutoffTimeMillisV2(NOT_RECENTLY_PLAYED_CUTOFF_V2);
    }

    // The recently played cutoff time is compared against the internal (private) database timestamps, which is milliseconds based.
    public long getRecentlyPlayedCutoffTimeMillis() {
        return getCutoffTimeMillisV2(RECENTLY_PLAYED_CUTOFF_V2);
    }

    @NonNull
    public Pair<Integer, ChronoUnit> getCutoffTimeV2(@NonNull final String cutoff) {
        final Pair<Integer, ChronoUnit> disabledValue = new Pair<>(0, ChronoUnit.DAYS);
        final Pair<Integer, ChronoUnit> defaultValue = new Pair<>(1, ChronoUnit.MONTHS);

        final String value = mPreferences.getString(cutoff, "");
        final Pattern pattern = Pattern.compile("^([0-9]*?)([dwmy])$");
        final Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            final int count = Integer.parseInt(Objects.requireNonNull(matcher.group(1)));
            final String unit = matcher.group(2);

            if (count == 0) {return disabledValue;}
            else if (count < 0 || unit == null) {return defaultValue;}
            else {
                switch (unit) {
                    case "d": return new Pair<>(count, ChronoUnit.DAYS);
                    case "w": return new Pair<>(count, ChronoUnit.WEEKS);
                    case "m": return new Pair<>(count, ChronoUnit.MONTHS);
                    case "y": return new Pair<>(count, ChronoUnit.YEARS);
                    default: return defaultValue;
                }
            }
        } else {
            return defaultValue;
        }
    }

    private long getCutoffTimeMillisV2(@NonNull final String cutoff) {
        final Pair<Integer, ChronoUnit> value = getCutoffTimeV2(cutoff);
        if (value.first <= 0) {return 0;} // Disabled

        final CalendarUtil calendarUtil = new CalendarUtil();
        long interval = System.currentTimeMillis();

        if (value.second == ChronoUnit.DAYS) {
            return interval - calendarUtil.getElapsedDays(value.first);
        } else if (value.second == ChronoUnit.WEEKS) {
            return interval - calendarUtil.getElapsedWeeks(value.first);
        } else if (value.second == ChronoUnit.MONTHS) {
            return interval - calendarUtil.getElapsedMonths(value.first);
        } else if (value.second == ChronoUnit.YEARS) {
            return interval - calendarUtil.getElapsedYears(value.first);
        }

        return 0; // Disabled
    }

    @NonNull
    private String getCutoffTextV2(@NonNull final String cutoff, final Context context) {
        final Pair<Integer, ChronoUnit> value = getCutoffTimeV2(cutoff);
        if (value.first <= 0) {return context.getString(R.string.pref_playlist_disabled);}

        if (value.second == ChronoUnit.DAYS) {
            return value.first == 1
                    ? context.getString(R.string.today)
                    : context.getString(R.string.past_X_days, value.first);
        } else if (value.second == ChronoUnit.WEEKS) {
            return value.first == 1
                    ? context.getString(R.string.this_week)
                    : context.getString(R.string.past_X_weeks, value.first);
        } else if (value.second == ChronoUnit.MONTHS) {
            return value.first == 1
                    ? context.getString(R.string.this_month)
                    : context.getString(R.string.past_X_months, value.first);
        } else if (value.second == ChronoUnit.YEARS) {
            return value.first == 1
                    ? context.getString(R.string.this_year)
                    : context.getString(R.string.past_X_years, value.first);
        }

        return context.getString(R.string.pref_playlist_disabled);
    }

    @NonNull
    public String getLastAddedCutoffText(@NonNull final Context context) {
        return getCutoffTextV2(LAST_ADDED_CUTOFF_V2, context);
    }

    @NonNull
    public String getRecentlyPlayedCutoffText(final Context context) {
        return getCutoffTextV2(RECENTLY_PLAYED_CUTOFF_V2, context);
    }

    @NonNull
    public String getNotRecentlyPlayedCutoffText(final Context context) {
        return getCutoffTextV2(NOT_RECENTLY_PLAYED_CUTOFF_V2, context);
    }

    public @NonNull String getLastAddedSortOrder() {
        return mPreferences.getString(LAST_ADDED_SORT_ORDER, SONG_SORT_ORDER);
    }

    public void setLastAddedSortOrder(@NonNull final String value) {
        mPreferences.edit().putString(LAST_ADDED_SORT_ORDER, value).apply();
    }

    public @NonNull String getNotRecentlyPlayedSortOrder() {
        return mPreferences.getString(NOT_RECENTLY_PLAYED_SORT_ORDER, SONG_SORT_ORDER);
    }

    public void setNotRecentlyPlayedSortOrder(@NonNull final String value) {
        mPreferences.edit().putString(NOT_RECENTLY_PLAYED_SORT_ORDER, value).apply();
    }

    public int getLastSleepTimerValue() {
        return mPreferences.getInt(LAST_SLEEP_TIMER_VALUE, 30);
    }

    public void setLastSleepTimerValue(final int value) {
        mPreferences.edit()
                .putInt(LAST_SLEEP_TIMER_VALUE, value)
                .apply();
    }

    public long getNextSleepTimerElapsedRealTime() {
        return mPreferences.getLong(NEXT_SLEEP_TIMER_ELAPSED_REALTIME, -1);
    }

    public void setNextSleepTimerElapsedRealtime(final long value) {
        mPreferences.edit()
                .putLong(NEXT_SLEEP_TIMER_ELAPSED_REALTIME, value)
                .apply();
    }

    public boolean getSleepTimerFinishMusic() {
        return mPreferences.getBoolean(SLEEP_TIMER_FINISH_SONG, false);
    }

    public void setSleepTimerFinishMusic(final boolean value) {
        mPreferences.edit()
                .putBoolean(SLEEP_TIMER_FINISH_SONG, value)
                .apply();
    }

    public void setAlbumGridSize(final int gridSize) {
        mPreferences.edit()
                .putInt(ALBUM_GRID_SIZE, gridSize)
                .apply();
    }

    public int getAlbumGridSize(@NonNull final Context context) {
        return mPreferences.getInt(ALBUM_GRID_SIZE, context.getResources().getInteger(R.integer.default_grid_columns));
    }

    public void setSongGridSize(final int gridSize) {
        mPreferences.edit()
                .putInt(SONG_GRID_SIZE, gridSize)
                .apply();
    }

    public int getSongGridSize(@NonNull final Context context) {
        return mPreferences.getInt(SONG_GRID_SIZE, context.getResources().getInteger(R.integer.default_list_columns));
    }

    public void setArtistGridSize(final int gridSize) {
        mPreferences.edit()
                .putInt(ARTIST_GRID_SIZE, gridSize)
                .apply();
    }

    public int getArtistGridSize(@NonNull final Context context) {
        return mPreferences.getInt(ARTIST_GRID_SIZE, context.getResources().getInteger(R.integer.default_list_columns));
    }

    public void setAlbumGridSizeLand(final int gridSize) {
        mPreferences.edit()
                .putInt(ALBUM_GRID_SIZE_LAND, gridSize)
                .apply();
    }

    public int getAlbumGridSizeLand(@NonNull final Context context) {
        return mPreferences.getInt(ALBUM_GRID_SIZE_LAND, context.getResources().getInteger(R.integer.default_grid_columns_land));
    }

    public void setSongGridSizeLand(final int gridSize) {
        mPreferences.edit()
                .putInt(SONG_GRID_SIZE_LAND, gridSize)
                .apply();
    }

    public int getSongGridSizeLand(@NonNull final Context context) {
        return mPreferences.getInt(SONG_GRID_SIZE_LAND, context.getResources().getInteger(R.integer.default_list_columns_land));
    }

    public void setArtistGridSizeLand(final int gridSize) {
        mPreferences.edit()
                .putInt(ARTIST_GRID_SIZE_LAND, gridSize)
                .apply();
    }

    public int getArtistGridSizeLand(@NonNull final Context context) {
        return mPreferences.getInt(ARTIST_GRID_SIZE_LAND, context.getResources().getInteger(R.integer.default_list_columns_land));
    }

    public void setAlbumColoredFooters(final boolean value) {
        mPreferences.edit()
                .putBoolean(ALBUM_COLORED_FOOTERS, value)
                .apply();
    }

    public boolean albumColoredFooters() {
        return mPreferences.getBoolean(ALBUM_COLORED_FOOTERS, true);
    }

    public void setAlbumArtistColoredFooters(final boolean value) {
        mPreferences.edit()
                .putBoolean(ALBUM_ARTIST_COLORED_FOOTERS, value)
                .apply();
    }

    public boolean albumArtistColoredFooters() {
        return mPreferences.getBoolean(ALBUM_ARTIST_COLORED_FOOTERS, true);
    }

    public void setSongColoredFooters(final boolean value) {
        mPreferences.edit()
                .putBoolean(SONG_COLORED_FOOTERS, value)
                .apply();
    }

    public boolean songColoredFooters() {
        return mPreferences.getBoolean(SONG_COLORED_FOOTERS, true);
    }

    public void setArtistColoredFooters(final boolean value) {
        mPreferences.edit()
                .putBoolean(ARTIST_COLORED_FOOTERS, value)
                .apply();
    }

    public boolean artistColoredFooters() {
        return mPreferences.getBoolean(ARTIST_COLORED_FOOTERS, true);
    }

    public void setLastChangeLogVersion(int version) {
        mPreferences.edit().putInt(LAST_CHANGELOG_VERSION, version).apply();
    }

    public int getLastChangelogVersion() {
        return mPreferences.getInt(LAST_CHANGELOG_VERSION, -1);
    }

    public void setIntroShown() {
        mPreferences.edit().putBoolean(INTRO_SHOWN, true).apply();
    }

    public boolean introShown() {
        return mPreferences.getBoolean(INTRO_SHOWN, false);
    }

    public void setRememberShuffle(final boolean rememberShuffle) {
        mPreferences.edit()
                .putBoolean(REMEMBER_SHUFFLE, rememberShuffle)
                .apply();
    }

    public boolean rememberShuffle() {
        return mPreferences.getBoolean(REMEMBER_SHUFFLE, true);
    }

    private String autoDownloadImagesPolicy() {
        return mPreferences.getString(AUTO_DOWNLOAD_IMAGES_POLICY, AUTO_DOWNLOAD_WIFI_ONLY);
    }

    public File getStartDirectory() {
        return new File(mPreferences.getString(START_DIRECTORY, FoldersFragment.getDefaultStartDirectory().getPath()));
    }

    public void setStartDirectory(final File file) {
        mPreferences.edit()
                .putString(START_DIRECTORY, FileUtil.safeGetCanonicalPath(file))
                .apply();
    }

    public boolean synchronizedLyricsShow() {
        return mPreferences.getBoolean(SYNCHRONIZED_LYRICS_SHOW, true);
    }

    public boolean animatePlayingSongIcon() {
        return mPreferences.getBoolean(ANIMATE_PLAYING_SONG_ICON, false);
    }

    boolean showSongNumber() {
        return mPreferences.getBoolean(SHOW_SONG_NUMBER, false);
    }

    public void setMaintainTopTrackPlaylist(final boolean value) {
        mPreferences.edit()
                .putBoolean(MAINTAIN_TOP_TRACKS_PLAYLIST, value)
                .apply();
    }

    public boolean maintainTopTrackPlaylist() {
        return mPreferences.getBoolean(MAINTAIN_TOP_TRACKS_PLAYLIST, true);
    }

    public void setMaintainSkippedSongsPlaylist(final boolean value) {
        mPreferences.edit()
                .putBoolean(MAINTAIN_SKIPPED_SONGS_PLAYLIST, value)
                .apply();
    }

    public boolean maintainSkippedSongsPlaylist() {
        return mPreferences.getBoolean(MAINTAIN_SKIPPED_SONGS_PLAYLIST, false);
    }

    public void setInitializedBlacklist() {
        mPreferences.edit()
                .putBoolean(INITIALIZED_BLACKLIST, true)
                .apply();
    }

    public boolean initializedBlacklist() {
        return mPreferences.getBoolean(INITIALIZED_BLACKLIST, false);
    }

    public boolean getWhitelistEnabled() {
        return mPreferences.getBoolean(WHITELIST_ENABLED, false);
    }

    public void setLibraryCategoryInfos(final ArrayList<CategoryInfo> categories) {
        final Gson gson = new Gson();
        final Type collectionType = new TypeToken<ArrayList<CategoryInfo>>() {
        }.getType();

        mPreferences.edit()
                .putString(LIBRARY_CATEGORIES, gson.toJson(categories, collectionType))
                .apply();
    }

    public void setPrimaryColor(final int selectedColor) {
        mPreferences.edit()
                .putInt(PRIMARY_COLOR, selectedColor)
                .apply();
    }

    public int getPrimaryColor() {
        //return mPreferences.getInt(PRIMARY_COLOR, 4149685);
        return mPreferences.getInt(PRIMARY_COLOR, -12627531);
    }

    public void setAccentColor(final int selectedColor) {
        mPreferences.edit()
                .putInt(ACCENT_COLOR, selectedColor)
                .apply();
    }

    public int getAccentColor() {
        //return mPreferences.getInt(ACCENT_COLOR, 16056407);
        return mPreferences.getInt(ACCENT_COLOR, -720809);
    }

    public ArrayList<CategoryInfo> getLibraryCategoryInfos() {
        String data = mPreferences.getString(LIBRARY_CATEGORIES, null);
        if (data != null) {
            final Gson gson = new Gson();
            final Type collectionType = new TypeToken<ArrayList<CategoryInfo>>() {
            }.getType();

            try {
                return gson.fromJson(data, collectionType);
            } catch (final JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        return getDefaultLibraryCategoryInfos();
    }

    public ArrayList<CategoryInfo> getDefaultLibraryCategoryInfos() {
        final ArrayList<CategoryInfo> defaultCategoryInfos = new ArrayList<>(5);
        defaultCategoryInfos.add(new CategoryInfo(CategoryInfo.Category.SONGS, true));
        defaultCategoryInfos.add(new CategoryInfo(CategoryInfo.Category.ALBUMS, true));
        defaultCategoryInfos.add(new CategoryInfo(CategoryInfo.Category.ARTISTS, true));
        defaultCategoryInfos.add(new CategoryInfo(CategoryInfo.Category.GENRES, true));
        defaultCategoryInfos.add(new CategoryInfo(CategoryInfo.Category.PLAYLISTS, true));
        return defaultCategoryInfos;
    }

    @NonNull
    public String getThemeStyle() {
        return mPreferences.getString(THEME_STYLE, CLASSIC_THEME);
    }

    public byte getReplayGainSourceMode() {
        byte sourceMode = RG_SOURCE_MODE_NONE;

        switch (mPreferences.getString(RG_SOURCE_MODE_V2, "none")) {
            case "track":
                sourceMode = RG_SOURCE_MODE_TRACK;
                break;
            case "album":
                sourceMode = RG_SOURCE_MODE_ALBUM;
                break;
        }

        return sourceMode;
    }

    private float getDefaultPreamp() {
        if (!App.DYNAMICS_PROCESSING_AVAILABLE) {
            // Older android versions cannot use DynamicsProcessing, so MultiPlayer uses the volume instead.
            // Use a default preamp that allows increasing the sound of the most quiet song in the DB.
            // Kept in the range -6dB..0dB to ensure we don't make everything too quiet only because of 1 outlier song.
            return Math.max(-6.0f, Math.min(-App.getDiscography().getMaxReplayGain(), 0f));
        } else {
            return 0.0f;
        }
    }

    public float getRgPreampWithTag() {
        return mPreferences.getFloat(RG_PREAMP_WITH_TAG, getDefaultPreamp());
    }

    public float getRgPreampWithoutTag() {
        return mPreferences.getFloat(RG_PREAMP_WITHOUT_TAG, getDefaultPreamp());
    }

    public void setReplayGainPreamp(float with, float without) {
        mPreferences.edit()
                .putFloat(RG_PREAMP_WITH_TAG, with)
                .putFloat(RG_PREAMP_WITHOUT_TAG, without)
                .apply();
    }

    public String getSAFSDCardUri() {
        return mPreferences.getString(SAF_SDCARD_URI, "");
    }

    public void setSAFSDCardUri(@NonNull final Uri uri) {
        mPreferences.edit()
                .putString(SAF_SDCARD_URI, uri.toString())
                .apply();
    }

    public void setOopsHandlerEnabled(final boolean value) {
        mPreferences.edit()
                .putBoolean(OOPS_HANDLER_ENABLED, value)
                .apply();
    }

    public boolean isOopsHandlerEnabled() {
        return mPreferences.getBoolean(OOPS_HANDLER_ENABLED, false);
    }


    public @Nullable List<String> getOopsHandlerReports() {
        if (!isOopsHandlerEnabled()) {return null;}

        final String json = mPreferences.getString(OOPS_HANDLER_EXCEPTIONS, "");
        if (json.isEmpty()) {return null;}

        return new ArrayList<>(Arrays.asList(new Gson().fromJson(json, String[].class)));
    }

    void pushOopsHandlerReport(@NonNull String report) {
        if (!isOopsHandlerEnabled()) {return;}

        List<String> reports = getOopsHandlerReports();
        if (reports == null) {reports = new ArrayList<>();}

        // The last report sits in the first position (LIFO)
        reports.add(0, report);

        // Prune too old entries
        final int limit = 10;
        while (reports.size() > limit) {
            reports.remove(reports.size() - 1);
        }

        final String json = new Gson().toJson(reports);
        mPreferences.edit()
                .putString(OOPS_HANDLER_EXCEPTIONS, json)
                .apply();
    }

    public @Nullable String popOopsHandlerReport() {
        if (!isOopsHandlerEnabled()) {return null;}

        List<String> reports = getOopsHandlerReports();
        if (reports == null || reports.isEmpty()) {return null;}

        final String result = reports.remove(0);

        final String json = new Gson().toJson(reports);
        mPreferences.edit()
                .putString(OOPS_HANDLER_EXCEPTIONS, json)
                .apply();

        return result;
    }

    public int getEnqueueSongsDefaultChoice() {
        return mPreferences.getInt(ENQUEUE_SONGS_DEFAULT_CHOICE, ENQUEUE_SONGS_CHOICE_REPLACE);
    }

    public void setEnqueueSongsDefaultChoice(int choice) {
        mPreferences.edit()
                .putInt(ENQUEUE_SONGS_DEFAULT_CHOICE, choice)
                .apply();
    }

    public boolean isQueueSyncWithMediaStoreEnabled() {
        return mPreferences.getBoolean(QUEUE_SYNC_MEDIA_STORE_ENABLED, false);
    }
}
