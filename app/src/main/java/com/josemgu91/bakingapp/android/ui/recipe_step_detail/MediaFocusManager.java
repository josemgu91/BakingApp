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

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jose on 2/18/18.
 */

public class MediaFocusManager {

    private final AudioManager audioManager;
    private final AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;

    private AudioFocusRequest audioFocusRequest;

    public MediaFocusManager(@NonNull final Context context, @Nullable final AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.onAudioFocusChangeListener = onAudioFocusChangeListener;
    }

    public boolean requestAudioFocus() {
        final int audioFocusRequest;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .build();
            this.audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                    .build();
            audioFocusRequest = audioManager.requestAudioFocus(
                    this.audioFocusRequest
            );
        } else {
            audioFocusRequest = audioManager.requestAudioFocus(
                    onAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN
            );
        }
        return audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    public boolean abandonAudioFocus() {
        final int audioFocusRequest;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest = audioManager.abandonAudioFocusRequest(
                    this.audioFocusRequest
            );
        } else {
            audioFocusRequest = audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
        return audioFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

}
