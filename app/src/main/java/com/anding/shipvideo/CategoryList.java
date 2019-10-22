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


import com.anding.shipvideo.data.Category;

import java.util.ArrayList;
import java.util.List;

public final class CategoryList {
    private static ArrayList<Category> list;
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
        list = new ArrayList<>();
        long id[] = {
                1, 2, 3, 4
        };
        String name[] = {
                "行业类视频",
                "行业类视频",
                "行业类视频",
                "行业类视频",
        };

        String description[] = {
                "按行业分类 ",
                "按时间分类 ",
                "按地区分类 ",
                "按工种分类"};

        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };

        for (int index = 0; index < name.length; ++index) {
            list.add(
                    buildCategoryInfo(id[index],
                            name[index],
                            description[index], cardImageUrl[index]));
        }

        return list;
    }


    /*
     * 安全视频类别
     * */
    public static List<Category> setupSecurityCategorys() {
        list = new ArrayList<>();
        long id[] = {
                5, 6, 7, 8
        };
        String name[] = {
                "安全类视频",
                "安全类视频",
                "安全类视频",
                "安全类视频",
        };

        String description[] = {
                "按行业分类 ",
                "按时间分类 ",
                "按地区分类 ",
                "按工种分类"};

        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };

        for (int index = 0; index < name.length; ++index) {
            list.add(
                    buildCategoryInfo(id[index],
                            name[index],
                            description[index], cardImageUrl[index]));
        }

        return list;
    }

    /*
     * 工种视频类别
     * */
    public static List<Category> setupRegionCategorys() {
        list = new ArrayList<>();
        long id[] = {
                9, 10, 11, 12
        };
        String name[] = {
                "工种类视频",
                "工种类视频",
                "工种类视频",
                "工种类视频",
        };

        String description[] = {
                "按行业分类 ",
                "按时间分类 ",
                "按地区分类 ",
                "按工种分类"};

        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };

        for (int index = 0; index < name.length; ++index) {
            list.add(
                    buildCategoryInfo(id[index],
                            name[index],
                            description[index], cardImageUrl[index]));
        }

        return list;
    }

    /*
     * 工种视频类别
     * */
    public static List<Category> setupColligateCategorys() {
        list = new ArrayList<>();
        long id[] = {
                13, 14, 15, 16
        };
        String name[] = {
                "综合类视频",
                "综合类视频",
                "综合类视频",
                "综合类视频",
        };

        String description[] = {
                "按行业分类 ",
                "按时间分类 ",
                "按地区分类 ",
                "按工种分类"};

        String cardImageUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
        };

        for (int index = 0; index < name.length; ++index) {
            list.add(
                    buildCategoryInfo(id[index],
                            name[index],
                            description[index], cardImageUrl[index]));
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