package ru.rustore.rustorereviewjavaexample.userflow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import ru.rustore.rustorereviewjavaexample.R;
import ru.rustore.sdk.review.RuStoreReviewManager;
import ru.rustore.sdk.review.RuStoreReviewManagerFactory;
import ru.rustore.sdk.review.model.ReviewInfo;

public class UserFlowFragment extends Fragment {

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

        requestReviewFlow();

        counterValue = view.findViewById(R.id.counterValue);

        counterValue.setOnClickListener(v -> onButtonClicked());

    }

    private void onButtonClicked() {
        counter++;
        int counterWinCondition = 5;
        if (counter >= counterWinCondition) {
            launchReviewFlow();
            counter = 0;
        }

    }

    private void requestReviewFlow() {
        if (reviewInfo != null) return;

        reviewManager.requestReviewFlow()
                .addOnSuccessListener(reviewInfo -> {
                    this.reviewInfo = reviewInfo;
                })
                .addOnFailureListener(throwable -> {
                    Log.w("ReviewFlow", throwable.toString());
                });
    }

    private void launchReviewFlow(){
        if (reviewInfo != null) {
            reviewManager.launchReviewFlow(reviewInfo)
                    .addOnSuccessListener(reviewInfo -> Log.w("ReviewFlow", "Review Flow started"))
                    .addOnFailureListener(throwable -> Log.e("ReviewFlow", "Review Flow error" + throwable));
        }
    }
}
