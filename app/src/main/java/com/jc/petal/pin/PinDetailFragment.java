package com.jc.petal.pin;

import com.jc.petal.R;
import com.jc.petal.data.model.Pin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Use the {@link PinDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PinDetailFragment extends Fragment {

    private static final String ARG_PIN = "pin";

    private Pin mPin;


    public PinDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pin Parameter 1.
     * @return A new instance of fragment PinDetailFragment.
     */
    public static PinDetailFragment newInstance(Pin pin) {
        PinDetailFragment fragment = new PinDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PIN, pin);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPin = getArguments().getParcelable(ARG_PIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin_detail, container, false);
    }

}
