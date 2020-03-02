package com.example.xcardview;

/**
 * @Author : wangjian
 * @Data : 2020-03-01 23:41
 * @Describe :
 */
/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.annotation.Nullable;

/**
 * Interface for platform specific CardView implementations.
 */
interface XCardViewImpl {
    void initialize(XCardViewDelegate cardView, Context context, ColorStateList backgroundColor,
                    float radius, float elevation, float maxElevation);

    void setRadius(XCardViewDelegate cardView, float radius);

    float getRadius(XCardViewDelegate cardView);

    void setElevation(XCardViewDelegate cardView, float elevation);

    float getElevation(XCardViewDelegate cardView);

    void initStatic();

    void setMaxElevation(XCardViewDelegate cardView, float maxElevation);

    float getMaxElevation(XCardViewDelegate cardView);

    float getMinWidth(XCardViewDelegate cardView);

    float getMinHeight(XCardViewDelegate cardView);

    void updatePadding(XCardViewDelegate cardView);

    void onCompatPaddingChanged(XCardViewDelegate cardView);

    void onPreventCornerOverlapChanged(XCardViewDelegate cardView);

    void setBackgroundColor(XCardViewDelegate cardView, @Nullable ColorStateList color);

    ColorStateList getBackgroundColor(XCardViewDelegate cardView);
}

