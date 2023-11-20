package ru.rustore.rustorereviewjavaexample.userflow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import kotlin.Unit;
import ru.rustore.rustorereviewjavaexample.R;
import ru.rustore.sdk.core.tasks.OnCompleteListener;
import ru.rustore.sdk.review.RuStoreReviewManager;
import ru.rustore.sdk.review.RuStoreReviewManagerFactory;
import ru.rustore.sdk.review.model.ReviewInfo;

public class UserFlowFragment extends Fragment {

    public UserFlowFragment() { super(R.layout.fragment_user_flow); }

    TextView counterTitle;

    MaterialButton counterValue;

    private RuStoreReviewManager reviewManager;

    private ReviewInfo reviewInfo = null;

    private int counter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_flow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reviewManager = RuStoreReviewManagerFactory.INSTANCE.create(requireContext());

        counterTitle = view.findViewById(R.id.counterTitle);
        counterValue = view.findViewById(R.id.counterValue);

        counterValue.setOnClickListener(v -> {
            onButtonClicked();
        });

    }

    private void onButtonClicked() {
        counter++;
        requestReviewFlow();
        int counterWinCondition = 5;
        if (counter >= counterWinCondition) {
            launchReviewFlow();
            counter = 0;
        }

    }

    private void requestReviewFlow() {
        if (reviewInfo != null) return;

        reviewManager.requestReviewFlow().addOnSuccessListener(reviewInfo -> {
            this.reviewInfo = reviewInfo;
        });
    }

    private void launchReviewFlow(){
        final ReviewInfo reviewInfo = this.reviewInfo;
        if (reviewInfo != null) {
            reviewManager.launchReviewFlow(reviewInfo).addOnCompleteListener(new OnCompleteListener<Unit>() {
                @Override
                public void onFailure(@NonNull Throwable throwable) {
                    Log.e("ReviewFlow", "Error" + throwable);
                }

                @Override
                public void onSuccess(Unit unit) {
                    Log.w("ReviewFlow", "Review Flow started");
                }
            });
        }
    }
}
