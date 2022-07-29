package com.example;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fragmentswitcher.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThree extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "pathname";
    private static final String ARG_PARAM2 = "layout";
    private static final String ARG_PARAM3 = "enter";
    private static final String ARG_PARAM4 = "exit";
    private static final String ARG_PARAM5 = "newText";


    // TODO: Rename and change types of parameters
    @SerializedName("pathname")
    public String pathName;

    @SerializedName("layout")
    public String layout;

    @SerializedName("enter")
    public String enterAnimation;

    @SerializedName("exit")
    public String exitAnimation;


    public FragmentThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTwo newInstance(String param1, String param2, String param3, String param4) {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pathName = getArguments().getString(ARG_PARAM1);
            layout = getArguments().getString(ARG_PARAM2);
            enterAnimation = getArguments().getString(ARG_PARAM3);
            exitAnimation = getArguments().getString(ARG_PARAM4);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_two, container, false);
        String text = getArguments().getString(ARG_PARAM5);

        TextView tv = (TextView) view.findViewById(R.id.textView_2);
        tv.setText(text);

        Fragment fr = getFragmentFromJSON("com.example.FragmentTwo.json");
        FragmentOne fr1 = (FragmentOne) fr;

        Button btn = (Button) view.findViewById(R.id.button_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(ARG_PARAM5, "FragmentOne\nFragmentTwo\nFragmentThree");
                fr1.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, fr1, null)
                        //.setCustomAnimations(R.anim.enterAnimation, R.anim.exitAnimation, R.anim.fr1.getEnterAnimation(), R.anim.fr1.getExitAnimation());
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });


        return view;
    }

    /*
    This method will return a new fragment using GSON
    however, right now it just returns a blank fragment
     */
    private Fragment getFragmentFromJSON(String pathname) {
        Gson gson = new Gson();
        Reader reader = null;
        //Not sure what the best way to attempt this is. I would ask
        //someone on my team who is better equipped, how to approach this.
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                reader = Files.newBufferedReader(Paths.get(pathname));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //FragmentOne fr1 = gson.fromJson(reader, FragmentOne.class);
        return new FragmentOne();

    }

    public String getEnterAnimation() {
        return enterAnimation;
    }

    public String getExitAnimation() {
        return exitAnimation;
    }
}