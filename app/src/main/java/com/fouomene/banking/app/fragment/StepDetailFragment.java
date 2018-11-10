package com.fouomene.banking.app.fragment;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.fouomene.banking.app.R;
import com.fouomene.banking.app.databinding.FragmentStepDetailBinding;
import com.fouomene.banking.app.model.Step;
import com.fouomene.banking.app.utils.Utility;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class StepDetailFragment extends Fragment {
    private static final String LOG_TAG = StepDetailFragment.class.getSimpleName();

    private FragmentStepDetailBinding binding;

    private Step mStep;

    private SimpleExoPlayer mExoPlayer;

    private final String SELECTED_POSITION = "selected_position";
    private final String PLAY_WHEN_READY = "play_when_ready";

    private boolean playWhenReady = true;
    private long position = -1;


    public StepDetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mStep = arguments.getParcelable(Utility.EXTRA_STEP);
           // ((TextView) getActivity().findViewById(R.id.action_bar_step_description)).setText(mStep.getShortDescription());
        }


        if (savedInstanceState != null && savedInstanceState.containsKey(Utility.EXTRA_STEP)) {
            mStep = savedInstanceState.getParcelable(Utility.EXTRA_STEP);
            position = savedInstanceState.getLong(SELECTED_POSITION, C.TIME_UNSET);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);

        }


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        final View rootView = binding.getRoot();

        return rootView;

    }

    private void initializerPlayer(Uri mediaUri){
        if (mExoPlayer==null){
            binding.playerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            binding.playerView.setPlayer(mExoPlayer);

            binding.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position>0)
                mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

            binding.stepShortDescription.setText(mStep.getShortDescription());
            binding.stepDescription.setText(mStep.getDescription());

            if (!TextUtils.isEmpty(mStep.getVideoURL()))
                initializerPlayer(Uri.parse(mStep.getVideoURL()));
            else if (!mStep.getThumbnailURL().isEmpty()) {
                binding.thumbnailView.setVisibility(View.VISIBLE);
                Picasso.with(getContext())
                        .load(mStep.getThumbnailURL())
                        .placeholder(R.drawable.noposterdetail780)
                        .error(R.drawable.noposterdetail780)
                        .into(binding.thumbnailView);
            }


    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null){
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        outState.putParcelable(Utility.EXTRA_STEP, mStep);
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_POSITION, position);
        outState.putBoolean(PLAY_WHEN_READY , playWhenReady);
    }


}
