package com.jgrocho.dicetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SaveDialogFragment extends DialogFragment {

    public static SaveDialogFragment newInstance() {
        SaveDialogFragment frag = new SaveDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.save_dialog_message)
                .setPositiveButton(R.string.save_dialog_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                ((MainActivity) getActivity()).saveRolls();
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
