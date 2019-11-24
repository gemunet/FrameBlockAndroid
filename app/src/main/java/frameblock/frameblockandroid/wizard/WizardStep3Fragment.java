package frameblock.frameblockandroid.wizard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import frameblock.frameblockandroid.R;
import frameblock.widget.wizard.WizardFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WizardStep3Fragment extends WizardFragment {
    private WizardViewModel wizardViewModel;

    public WizardStep3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wizardViewModel = ViewModelProviders.of(getActivity()).get(WizardViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wizard_step3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnSkip = getView().findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWizardListener().skipWizard();
            }
        });

        Button btnNext = getView().findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wizardViewModel.getExtras().putString("step3", "dato3");
                getWizardListener().finishWizard();
            }
        });

        Button btnBack = getView().findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWizardListener().goBack();
            }
        });

        Button btnCheck = getView().findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWizardListener().setChecked();
            }
        });
    }

}
