package com.poupa.vinylmusicplayer.preferences;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.poupa.vinylmusicplayer.R;
import com.poupa.vinylmusicplayer.dialogs.BlacklistFolderChooserDialog;
import com.poupa.vinylmusicplayer.provider.BlacklistStore;
import com.poupa.vinylmusicplayer.util.PreferenceUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class BlacklistPreferenceDialog extends DialogFragment implements BlacklistFolderChooserDialog.FolderCallback {

    private ArrayList<String> paths;

    public static BlacklistPreferenceDialog newInstance() {
        return new BlacklistPreferenceDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BlacklistFolderChooserDialog blacklistFolderChooserDialog = (BlacklistFolderChooserDialog) getChildFragmentManager().findFragmentByTag("FOLDER_CHOOSER");
        if (blacklistFolderChooserDialog != null) {
            blacklistFolderChooserDialog.setCallback(this);
        }

        refreshBlacklistData();

        final Activity activity = requireActivity();
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.library_categories);
        LayoutInflater inflater = this.getLayoutInflater();
        //View parent = inflater.inflate(R.layout.preference_dialog_export_settings, null);
        String[] pathItems = new String[paths.size()];
        pathItems = paths.toArray(pathItems);
        String[] finalPathItems = pathItems;
        alert.setItems(pathItems, (dialogInterface, i) -> new AlertDialog.Builder(alert.getContext())
                .setTitle(R.string.remove_from_blacklist)
                .setMessage(Html.fromHtml(getString(R.string.do_you_want_to_remove_from_the_blacklist, finalPathItems[i].toString())))
                //content.(Html.fromHtml(getString(R.string.do_you_want_to_remove_from_the_blacklist, charSequence)))
                .setPositiveButton(R.string.remove_action, (dialog, id) -> {
                    BlacklistStore.getInstance(getContext()).removePath(new File(finalPathItems[i].toString()));
                    refreshBlacklistData();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {dismiss();})
                .show());
        alert.setNeutralButton(R.string.clear_action, (dialog, id) -> {
            new AlertDialog.Builder(alert.getContext())
                    .setTitle(R.string.clear_blacklist)
                    .setMessage(Html.fromHtml(getString(R.string.do_you_want_to_clear_the_blacklist)))
                    //content.(Html.fromHtml(getString(R.string.do_you_want_to_remove_from_the_blacklist, charSequence)))
                    .setPositiveButton(R.string.remove_action, (dialog2, id2) -> {
                        BlacklistStore.getInstance(getContext()).clear();
                        refreshBlacklistData();
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog2, id2) -> {dismiss();})
                    .show();
        });
        alert.setPositiveButton(android.R.string.ok, (dialog, id) -> {
            dismiss();
        });
        alert.setNegativeButton(R.string.add_action, (dialog, id) -> {
            BlacklistFolderChooserDialog blacklistFolderChooserDialog1 = BlacklistFolderChooserDialog.create();
            blacklistFolderChooserDialog1.setCallback(BlacklistPreferenceDialog.this);
            blacklistFolderChooserDialog1.show(getChildFragmentManager(), "FOLDER_CHOOSER");
        });
        //alert.setCancelable(false);
        AlertDialog dialog = alert.create();

        /*
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, null);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, null);
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
        });*/

        return dialog;

    }

    private void refreshBlacklistData() {
        paths = BlacklistStore.getInstance(getContext()).getPaths();

        AlertDialog dialog = (AlertDialog) getDialog();

        if (dialog != null) {
            String[] pathArray = new String[paths.size()];
            pathArray = paths.toArray(pathArray);
            //dialog.setItems(pathArray);
        }
    }

    @Override
    public void onFolderSelection(@NonNull BlacklistFolderChooserDialog folderChooserDialog, @NonNull File file) {
        BlacklistStore.getInstance(getContext()).addPath(file);
        refreshBlacklistData();
    }
}
