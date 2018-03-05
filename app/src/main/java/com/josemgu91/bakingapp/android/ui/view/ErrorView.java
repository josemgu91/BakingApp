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

package com.josemgu91.bakingapp.android.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.josemgu91.bakingapp.R;

/**
 * Created by jose on 2/26/18.
 */

public class ErrorView extends LinearLayout {

    private String errorText;
    @ColorInt
    private int errorTextColor;
    private Drawable errorDrawable;
    @ColorInt
    private int errorDrawableTint;

    private TextView textViewErrorText;
    private ImageView imageViewErrorPicture;

    public ErrorView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.view_error, this, true);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        textViewErrorText = findViewById(R.id.textview_error_message);
        imageViewErrorPicture = findViewById(R.id.imageview_error_picture);

        if (attrs != null) {
            final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ErrorView,
                    defStyleAttr, defStyleRes);
            try {
                errorText = styledAttributes.getString(R.styleable.ErrorView_errorText);
                errorTextColor = styledAttributes.getColor(R.styleable.ErrorView_errorTextColor, 0xFF000000);
                errorDrawable = styledAttributes.getDrawable(R.styleable.ErrorView_errorDrawable);
                errorDrawableTint = styledAttributes.getColor(R.styleable.ErrorView_errorDrawableTint, -1);
                setErrorText(errorText);
                setErrorTextColor(errorTextColor);
                setErrorDrawable(errorDrawable);
                if (errorDrawableTint != -1) {
                    setErrorDrawableTint(errorDrawableTint);
                }
            } finally {
                styledAttributes.recycle();
            }
        }
    }

    public void setErrorText(final String errorText) {
        textViewErrorText.setText(errorText);
        this.errorText = errorText;
    }

    public void setErrorTextColor(@ColorInt final int errorTextColor) {
        textViewErrorText.setTextColor(errorTextColor);
        this.errorTextColor = errorTextColor;
    }

    public void setErrorDrawable(final Drawable errorDrawable) {
        imageViewErrorPicture.setImageDrawable(errorDrawable);
        this.errorDrawable = errorDrawable;
    }

    public void setErrorDrawableTint(int errorDrawableTint) {
        if (errorDrawable != null) {
            this.errorDrawableTint = errorDrawableTint;
            DrawableCompat.setTint(errorDrawable, ContextCompat.getColor(getContext(), R.color.secondaryColor));
        }
    }
}
