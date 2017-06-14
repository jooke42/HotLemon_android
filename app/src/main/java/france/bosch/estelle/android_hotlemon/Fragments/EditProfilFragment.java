package france.bosch.estelle.android_hotlemon.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import france.bosch.estelle.android_hotlemon.R;


public class EditProfilFragment extends Fragment {


    public EditProfilFragment() {
        // Required empty public constructor
    }

    public static EditProfilFragment newInstance() {
        EditProfilFragment fragment = new EditProfilFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.edit_profil_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
