/*
 * MIT License
 *
 * Copyright (c) 2018 José Miguel García Urrutia.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.josemgu91.bakingapp.android.ui.recipe_step_detail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;

/**
 * Created by jose on 2/15/18.
 */

public class RecipeStepDetailFragment extends Fragment implements AudioManager.OnAudioFocusChangeListener, CustomExoPlayerEventListener.CustomExoPlayerEventListenerInterface {

    private SimpleExoPlayerView simpleExoPlayerViewRecipeStepVideo;

    private static final String EXO_PLAYER_USER_AGENT = "BakingApp";

    private static final String PARAM_RECIPE_STEP_DESCRIPTION = "recipe_step_description";
    private static final String PARAM_RECIPE_STEP_SHORT_DESCRIPTION = "recipe_step_short_description";
    private static final String PARAM_RECIPE_STEP_VIDEO_URL = "recipe_step_video_url";
    private static final String PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL = "recipe_step_picture_thumbnail_url";

    private static final String SAVED_INSTANCE_STATE_VIDEO_POSITION = "video_position";
    private static final String SAVED_INSTANCE_STATE_VIDEO_PLAY_VIDEO = "play_video";

    private String recipeStepDescription;
    private String recipeStepShortDescription;
    private String recipeStepVideoUrl;
    private String recipeStepPictureThumbnailUrl;

    private SimpleExoPlayer simpleExoPlayer;
    private MediaFocusManager mediaFocusManager;

    private BroadcastReceiver noisyReceiver;
    private IntentFilter noisyReceiverIntentFilter;

    private boolean hasVideo;

    private long lastVideoPosition;
    private boolean shouldPlayVideo;

    public static RecipeStepDetailFragment newInstance(final GetRecipeStepsViewModel.Step recipeStep) {
        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_RECIPE_STEP_DESCRIPTION, recipeStep.getLongDescription());
        arguments.putString(PARAM_RECIPE_STEP_SHORT_DESCRIPTION, recipeStep.getShortDescription());
        arguments.putString(PARAM_RECIPE_STEP_VIDEO_URL, recipeStep.getVideoUrl());
        arguments.putString(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL, recipeStep.getPictureUrl());
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arguments = getArguments();
        if (!arguments.containsKey(PARAM_RECIPE_STEP_DESCRIPTION)) {
            throw new RuntimeException("Use the newInstance() static method instead the standard constructor!");
        }
        recipeStepDescription = arguments.getString(PARAM_RECIPE_STEP_DESCRIPTION);
        recipeStepShortDescription = arguments.getString(PARAM_RECIPE_STEP_SHORT_DESCRIPTION);
        recipeStepVideoUrl = arguments.getString(PARAM_RECIPE_STEP_VIDEO_URL);
        recipeStepPictureThumbnailUrl = arguments.getString(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL);
        recipeStepDescription = recipeStepDescription != null ? recipeStepDescription : "";
        recipeStepShortDescription = recipeStepShortDescription != null ? recipeStepShortDescription : "";
        recipeStepVideoUrl = recipeStepVideoUrl != null ? recipeStepVideoUrl : "";
        recipeStepPictureThumbnailUrl = recipeStepPictureThumbnailUrl != null ? recipeStepPictureThumbnailUrl : "";

        if (savedInstanceState != null) {
            lastVideoPosition = savedInstanceState.getLong(SAVED_INSTANCE_STATE_VIDEO_POSITION);
            shouldPlayVideo = savedInstanceState.getBoolean(SAVED_INSTANCE_STATE_VIDEO_PLAY_VIDEO);
        } else {
            lastVideoPosition = 0;
            shouldPlayVideo = true;
        }

        mediaFocusManager = new MediaFocusManager(getActivity(), this);
        initializeNoisyReceiver();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        simpleExoPlayerViewRecipeStepVideo = view.findViewById(R.id.simpleexoplayerview_recipe_step_video);
        final TextView textViewRecipeStepShortDescription = view.findViewById(R.id.textview_step_description_title);
        final TextView textViewRecipeStep = view.findViewById(R.id.textview_recipe_step);
        textViewRecipeStep.setText(recipeStepDescription);
        textViewRecipeStepShortDescription.setText(recipeStepShortDescription);
        setVideoLandscapePhoneSize(view);
        return view;
    }

    private void setVideoLandscapePhoneSize(final View view) {
        final Configuration configuration = getActivity().getResources().getConfiguration();
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (configuration.smallestScreenWidthDp < 600 && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    final ViewGroup.LayoutParams layoutParams = simpleExoPlayerViewRecipeStepVideo.getLayoutParams();
                    layoutParams.height = getUsableHeight();
                    simpleExoPlayerViewRecipeStepVideo.setLayoutParams(layoutParams);
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return true;
            }
        });
    }

    private int getUsableHeight() {
        final Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int usableHeight = size.y;
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            usableHeight -= actionBar.getHeight();
        }
        int resource = getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resource > 0) {
            usableHeight -= getActivity().getResources().getDimensionPixelSize(resource);
        }
        return usableHeight;
    }

    @Override
    public void onStart() {
        super.onStart();
        hasVideo = !recipeStepVideoUrl.isEmpty();
        if (!hasVideo) {
            simpleExoPlayerViewRecipeStepVideo.setVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            initMediaResources();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M && hasVideo) {
            initMediaResources();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M && hasVideo) {
            releaseMediaResources();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && hasVideo) {
            releaseMediaResources();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (hasVideo) {
            lastVideoPosition = simpleExoPlayer.getCurrentPosition();
            shouldPlayVideo = simpleExoPlayer.getPlayWhenReady();
            outState.putLong(SAVED_INSTANCE_STATE_VIDEO_POSITION, lastVideoPosition);
            outState.putBoolean(SAVED_INSTANCE_STATE_VIDEO_PLAY_VIDEO, shouldPlayVideo);
        }
    }

    private void initMediaResources() {
        initializeExoPlayer(recipeStepVideoUrl, mediaFocusManager.requestAudioFocus() && shouldPlayVideo);
        getActivity().registerReceiver(noisyReceiver, noisyReceiverIntentFilter);
    }

    private void releaseMediaResources() {
        getActivity().unregisterReceiver(noisyReceiver);
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
        mediaFocusManager.abandonAudioFocus();
    }

    private void initializeNoisyReceiver() {
        noisyReceiverIntentFilter = new IntentFilter();
        noisyReceiverIntentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        noisyReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                    simpleExoPlayer.setPlayWhenReady(false);
                    mediaFocusManager.abandonAudioFocus();
                }
            }
        };
    }

    private void initializeExoPlayer(final String uri, final boolean autoPlay) {
        if (simpleExoPlayer == null) {
            final MediaSource mediaSource = new ExtractorMediaSource.Factory(
                    new DefaultDataSourceFactory(
                            getActivity(),
                            EXO_PLAYER_USER_AGENT
                    )
            ).createMediaSource(Uri.parse(uri));
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.addListener(new CustomExoPlayerEventListener(this));
            simpleExoPlayer.seekTo(lastVideoPosition);
            simpleExoPlayer.setPlayWhenReady(autoPlay && getUserVisibleHint());
            simpleExoPlayerViewRecipeStepVideo.setPlayer(simpleExoPlayer);
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            simpleExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public void onExoPlayerPause() {
        mediaFocusManager.abandonAudioFocus();
    }

    @Override
    public void onExoPlayerPlay() {
        mediaFocusManager.requestAudioFocus();
    }

}
