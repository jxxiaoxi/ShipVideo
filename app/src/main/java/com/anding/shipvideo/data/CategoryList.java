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

package com.anding.shipvideo.data;


import com.anding.shipvideo.utils.Constants;
import com.anding.shipvideo.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

public final class CategoryList {
    private static long count = 0;    //电影种类
    public static final String HEADER_CATEGORYS[] = {
            "行业",
            "安全",
            "工种",
            "综合",
    };

    /*
     * 行业视频类别
     * */
    public static List<Category> setupTradeCategorys() {
        ArrayList<Category> list = new ArrayList<>();
        List<CategorySub> categorySubs = DatabaseUtils.getInstance().queryCategorySubs(Constants.CATEGORY_TRADE + "");

        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };

        if (categorySubs == null) {
            return null;
        }

        for (int index = 0; index < categorySubs.size(); ++index) {
            list.add(
                    buildCategoryInfo(Constants.CATEGORY_TRADE,
                            "行业类视频",
                            categorySubs.get(index).getLabel(), cardImageUrl[Constants.CATEGORY_TRADE]));
        }
        return list;
    }


    /*
     * 安全视频类别
     * */
    public static List<Category> setupSecurityCategorys() {
        ArrayList<Category> list = new ArrayList<>();
        List<CategorySub> categorySubs = DatabaseUtils.getInstance().queryCategorySubs(Constants.CATEGORY_TIME + "");
        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };
        if (categorySubs == null) {
            return null;
        }
        for (int index = 0; index < categorySubs.size(); ++index) {
            list.add(
                    buildCategoryInfo(Constants.CATEGORY_TIME,
                            "安全视频类",
                            categorySubs.get(index).getLabel(), cardImageUrl[Constants.CATEGORY_TIME]));
        }
        return list;
    }

    /*
     * 工种视频类别
     * */
    public static List<Category> setupRegionCategorys() {
        ArrayList<Category> list = new ArrayList<>();
        List<CategorySub> categorySubs = DatabaseUtils.getInstance().queryCategorySubs(Constants.CATEGORY_REGION + "");
        if (categorySubs == null) {
            return null;
        }

        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };
        for (int index = 0; index < categorySubs.size(); ++index) {
            list.add(
                    buildCategoryInfo(Constants.CATEGORY_REGION,
                            "工种类视频",
                            categorySubs.get(index).getLabel(), cardImageUrl[Constants.CATEGORY_REGION]));
        }
        return list;
    }

    /*
     * 综合视频类别
     * */
    public static List<Category> setupColligateCategorys() {
        ArrayList<Category> list = new ArrayList<>();
        List<CategorySub> categorySubs = DatabaseUtils.getInstance().queryCategorySubs(Constants.CATEGORY_COLLIGATE + "");
        if (categorySubs == null) {
            return null;
        }

        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };

        for (int index = 0; index < categorySubs.size(); ++index) {
            list.add(
                    buildCategoryInfo(Constants.CATEGORY_COLLIGATE,
                            "综合类视频",
                            categorySubs.get(index).getLabel(), cardImageUrl[Constants.CATEGORY_COLLIGATE-1]));
        }

        return list;
    }

    private static Category buildCategoryInfo(
            long id,
            String title,
            String description,
            String cardImageUrl) {
        Category category = new Category();
        category.setId(id);
        category.setName(title);
        category.setDescription(description);
        category.setCardImageUrl(cardImageUrl);
        return category;
    }
}