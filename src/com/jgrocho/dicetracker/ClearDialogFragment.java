package com.jgrocho.dicetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ClearDialogFragment extends DialogFragment {

    public static final int CURRENT = 1;
    public static final int HISTORIC = 2;

    public static ClearDialogFragment newInstance(int type) {
        ClearDialogFragment frag = new ClearDialogFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int type = getArguments().getInt("type");
        int message;
        switch (type) {
            case HISTORIC:
                message = R.string.clear_dialog_historic;
                break;
            case CURRENT:
            default:
                message = R.string.clear_dialog_current;
        }

        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(R.string.clear_dialog_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                switch (type) {
                                    case HISTORIC:
                                        ((MainActivity) getActivity()).resetHistoricRolls();
                                        break;
                                    case CURRENT:
                                    default:
                                        ((MainActivity) getActivity()).resetCurrentRolls();
                                }
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
