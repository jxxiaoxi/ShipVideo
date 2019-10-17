/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.anding.shipvideo;

import com.anding.shipvideo.been.Category;

import java.util.ArrayList;
import java.util.List;

public final class CategoryList {
    private static ArrayList<Category> list;
    private static long count = 0;    //电影种类
    public static final String HEADER_CATEGORYS[] = {
            "行业",
            "安全类别",
            "工种",
            "综合",
    };

/*    public static List<Category> getList() {
        if (list == null) {
            list = setupCategorys();
        }
        return list;
    }*/

    /*
    *
    * */
    public static List<Category> setupTradeCategorys() {
        list = new ArrayList<>();
        String title[] = {
                "按安全类别分类",
                "按时间分类",
                "按地区分类",
                "按工种分类",
        };

        String description = "此类别按行业分类"
                + "此类别按行业分类 "
                + "此类别按时间分类 "
                + "此类别按地区分类 "
                + "此类别按工种分类";

        String bgImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/bg.jpg",
        };
        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };

        for (int index = 0; index < title.length; ++index) {
            list.add(
                    buildCategoryInfo(
                            title[index],
                            description, cardImageUrl[index],
                            bgImageUrl[index]));
        }

        return list;
    }

    private static Category buildCategoryInfo(
            String title,
            String description,
            String cardImageUrl,
            String backgroundImageUrl) {
        Category category = new Category();
        category.setId(count++);
        category.setTitle(title);
        category.setDescription(description);
        category.setCardImageUrl(cardImageUrl);
        category.setBackgroundImageUrl(backgroundImageUrl);
        return category;
    }
}