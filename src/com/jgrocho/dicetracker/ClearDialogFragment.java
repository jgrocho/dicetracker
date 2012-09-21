package com.jgrocho.dicetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class ClearDialogFragment extends SherlockDialogFragment {

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

        return new AlertDialog.Builder(getSherlockActivity())
                .setMessage(message)
                .setPositiveButton(R.string.clear_dialog_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int whichButton) {
                                switch (type) {
                                    case HISTORIC:
                                        ((MainActivity) getSherlockActivity()).resetHistoricRolls();
                                        break;
                                    case CURRENT:
                                    default:
                                        ((MainActivity) getSherlockActivity()).resetCurrentRolls();
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
