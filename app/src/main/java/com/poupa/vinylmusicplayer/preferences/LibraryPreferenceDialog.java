package com.poupa.vinylmusicplayer.preferences;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.poupa.vinylmusicplayer.R;
import com.poupa.vinylmusicplayer.adapter.CategoryInfoAdapter;
import com.poupa.vinylmusicplayer.model.CategoryInfo;
import com.poupa.vinylmusicplayer.util.PreferenceUtil;

import java.util.ArrayList;


public class LibraryPreferenceDialog extends DialogFragment {
    public static LibraryPreferenceDialog newInstance() {
        return new LibraryPreferenceDialog();
    }

    private CategoryInfoAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.preference_dialog_library_categories, null);

        ArrayList<CategoryInfo> categoryInfos;
        if (savedInstanceState != null) {
            categoryInfos = savedInstanceState.getParcelableArrayList(PreferenceUtil.LIBRARY_CATEGORIES);
        } else {
            categoryInfos = PreferenceUtil.getInstance().getLibraryCategoryInfos();
        }
        adapter = new CategoryInfoAdapter(categoryInfos);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        final Activity activity = requireActivity();

        adapter.attachToRecyclerView(recyclerView);

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(R.string.library_categories);
        LayoutInflater inflater = this.getLayoutInflater();
        //View parent = inflater.inflate(R.layout.preference_dialog_export_settings, null);
        alert.setView(view);
        alert.setNeutralButton(R.string.reset_action, (dialog, id) -> {
            adapter.setCategoryInfos(PreferenceUtil.getInstance().getDefaultLibraryCategoryInfos());
        });
        alert.setPositiveButton(android.R.string.ok, (dialog, id) -> {
            updateCategories(adapter.getCategoryInfos());
            dismiss();
        });
        alert.setNegativeButton(android.R.string.cancel, (dialog, id) -> {
            dismiss();
        });

        return alert.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PreferenceUtil.LIBRARY_CATEGORIES, adapter.getCategoryInfos());
    }

    private void updateCategories(ArrayList<CategoryInfo> categories) {
        if (getSelected(categories) == 0) return;

        PreferenceUtil.getInstance().setLibraryCategoryInfos(categories);
    }

    private int getSelected(ArrayList<CategoryInfo> categories) {
        int selected = 0;
        for (CategoryInfo categoryInfo : categories) {
            if (categoryInfo.visible)
                selected++;
        }
        return selected;
    }
}
