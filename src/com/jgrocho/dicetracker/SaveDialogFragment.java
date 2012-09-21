package com.jgrocho.dicetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class SaveDialogFragment extends SherlockDialogFragment {

    public static SaveDialogFragment newInstance() {
        SaveDialogFragment frag = new SaveDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getSherlockActivity())
                .setMessage(R.string.save_dialog_message)
                .setPositiveButton(R.string.save_dialog_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                ((MainActivity) getSherlockActivity()).saveRolls();
                            }
                        })
                .setNegativeButton(R.string.save_dialog_no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                // Nothing
                            }
                        })
                .create();
    }

}
