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

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.josemgu91.bakingapp.R;
import com.josemgu91.bakingapp.adapter.presentation.ui.graphical.GetRecipeStepsViewModel;

/**
 * Created by jose on 2/15/18.
 */

public class RecipeStepDetailFragment extends Fragment implements AudioManager.OnAudioFocusChangeListener, Player.EventListener {

    private SimpleExoPlayerView simpleExoPlayerViewRecipeStepVideo;
    private TextView textViewRecipeStep;

    private static final String EXO_PLAYER_USER_AGENT = "BakingApp";

    private static final String PARAM_RECIPE_STEP_DESCRIPTION = "recipe_step_description";
    private static final String PARAM_RECIPE_STEP_VIDEO_URL = "recipe_step_video_url";
    private static final String PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL = "recipe_step_picture_thumbnail_url";

    private String recipeStepDescription;
    private String recipeStepVideoUrl;
    private String recipeStepPictureThumbnailUrl;

    private SimpleExoPlayer simpleExoPlayer;
    private MediaFocusManager mediaFocusManager;

    /*
     * TODO: Maybe I can make a local Android parcelable view model,
     * but I think that for the moment this is good enough.
     */
    public static RecipeStepDetailFragment newInstance(final GetRecipeStepsViewModel.Step recipeStep) {
        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_RECIPE_STEP_DESCRIPTION, recipeStep.getLongDescription());
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
        recipeStepVideoUrl = arguments.getString(PARAM_RECIPE_STEP_VIDEO_URL);
        recipeStepPictureThumbnailUrl = arguments.getString(PARAM_RECIPE_STEP_PICTURE_THUMBNAIL_URL);
        recipeStepDescription = recipeStepDescription != null ? recipeStepDescription : "";
        recipeStepVideoUrl = recipeStepVideoUrl != null ? recipeStepVideoUrl : "";
        recipeStepPictureThumbnailUrl = recipeStepPictureThumbnailUrl != null ? recipeStepPictureThumbnailUrl : "";

        mediaFocusManager = new MediaFocusManager(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        simpleExoPlayerViewRecipeStepVideo = view.findViewById(R.id.simpleexoplayerview_recipe_step_video);
        textViewRecipeStep = view.findViewById(R.id.textview_recipe_step);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewRecipeStep.setText(recipeStepDescription);
        if (!recipeStepVideoUrl.isEmpty()) {
            initializeExoPlayer(recipeStepVideoUrl, mediaFocusManager.requestAudioFocus());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        simpleExoPlayer.setPlayWhenReady(false);
        mediaFocusManager.abandonAudioFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
        mediaFocusManager.abandonAudioFocus();
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
            simpleExoPlayer.setPlayWhenReady(autoPlay);
            simpleExoPlayer.addListener(this);
            simpleExoPlayerViewRecipeStepVideo.setPlayer(simpleExoPlayer);
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                simpleExoPlayer.setPlayWhenReady(false);
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == Player.STATE_READY) {
            if (playWhenReady) {
                mediaFocusManager.requestAudioFocus();
            } else {
                mediaFocusManager.abandonAudioFocus();
            }
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

}
