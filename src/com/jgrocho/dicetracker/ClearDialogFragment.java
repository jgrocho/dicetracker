package com.jgrocho.dicetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ClearDialogFragment extends DialogFragment {

    public static ClearDialogFragment newInstance() {
        ClearDialogFragment frag = new ClearDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.clear_dialog)
                .setPositiveButton(R.string.clear_dialog_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                ((MainActivity) getActivity()).resetRolls();
                            }
                        })
                .setNegativeButton(R.string.clear_dialog_no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                // Nothing
                            }
                        })
                .create();
    }

}
