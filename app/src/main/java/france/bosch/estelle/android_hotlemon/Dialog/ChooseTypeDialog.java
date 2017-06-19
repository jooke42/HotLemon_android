package france.bosch.estelle.android_hotlemon.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;


import france.bosch.estelle.android_hotlemon.R;

/**
 * Created by ESTEL on 30/05/2017.
 */

public class ChooseTypeDialog extends DialogFragment {
        private ChooseTypeListener listener;
        private TextView titre;
        private TextView prix;
        private String type;
        private Switch isArticle;
        private Switch isEvent;
        public interface ChooseTypeListener {
            void onChooseValidation(String type);
        }

        private DialogInterface.OnDismissListener onDismissListener;

        public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener)
        {
            this.onDismissListener = onDismissListener;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (onDismissListener != null) {
                onDismissListener.onDismiss(dialog);
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View root = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_type, null);

            titre = (TextView) root.findViewById(R.id.titleType);
            isArticle = (Switch) root.findViewById(R.id.newsSwitch);
            isEvent = (Switch) root.findViewById(R.id.eventSwitch);
            isArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isEvent.isChecked()) {
                        isEvent.toggle();
                    }
                }
            });

            isEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isArticle.isChecked()) {
                        isArticle.toggle();
                    }

                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(root);
            builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(isArticle.isChecked()) {
                        type = "article";

                    }
                    else {
                        type = "event";
                    }
                    listener.onChooseValidation(type);
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ChooseTypeDialog.this.getDialog().cancel();
                }
            });


            return builder.create();
        }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            try {

                listener = (ChooseTypeListener) context;
            } catch (ClassCastException e) {

                throw new ClassCastException(context.toString()
                        + " must implement DepenseListener.");
            }
        }
    }

