package tw.skyarrow.ehreader.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.util.Date;

import tw.skyarrow.ehreader.Constant;
import tw.skyarrow.ehreader.R;
import tw.skyarrow.ehreader.api.API;

/**
 * Created by SkyArrow on 2015/9/24.
 */
public abstract class GalleryBase {
    public static final int CATEGORY_DOUJINSHI = 1;
    public static final int CATEGORY_MANGA = 2;
    public static final int CATEGORY_ARTISTCG = 3;
    public static final int CATEGORY_GAMECG = 4;
    public static final int CATEGORY_WESTERN = 5;
    public static final int CATEGORY_NON_H = 6;
    public static final int CATEGORY_IMAGESET = 7;
    public static final int CATEGORY_COSPLAY = 8;
    public static final int CATEGORY_ASIANPORN = 9;
    public static final int CATEGORY_MISC = 10;

    public abstract Long getId();
    public abstract void setId(Long id);

    public abstract String getToken();
    public abstract void setToken(String token);

    public abstract String getTitle();
    public abstract void setTitle(String title);

    public abstract String getSubtitle();
    public abstract void setSubtitle(String subtitle);

    public abstract String getThumbnail();
    public abstract void setThumbnail(String thumbnail);

    public abstract Integer getCount();
    public abstract void setCount(Integer count);

    public abstract Float getRating();
    public abstract void setRating(Float rating);

    public abstract String getUploader();
    public abstract void setUploader(String uploader);

    public abstract Date getCreated();
    public abstract void setCreated(Date date);

    public abstract Integer getCategory();
    public abstract void setCategory(Integer category);

    public abstract Long getSize();
    public abstract void setSize(Long size);

    public abstract String getTags();
    public abstract void setTags(String tags);

    public abstract Boolean getStarred();
    public abstract void setStarred(Boolean starred);

    public abstract Date getLastread();
    public abstract void setLastread(Date lastread);

    public abstract Integer getProgress();
    public abstract void setProgress(Integer progress);

    public abstract String getShowkey();
    public abstract void setShowkey(String showkey);

    public abstract Integer getPhotoPerPage();
    public abstract void setPhotoPerPage(Integer photoPerPage);

    public void setCategory(String category) {
        if (category.equals("Doujinshi")) {
            setCategory(CATEGORY_DOUJINSHI);
        } else if (category.equals("Manga")) {
            setCategory(CATEGORY_MANGA);
        } else if (category.equals("Artist CG Sets")) {
            setCategory(CATEGORY_ARTISTCG);
        } else if (category.equals("Game CG Sets")) {
            setCategory(CATEGORY_GAMECG);
        } else if (category.equals("Western")) {
            setCategory(CATEGORY_WESTERN);
        } else if (category.equals("Non-H")) {
            setCategory(CATEGORY_NON_H);
        } else if (category.equals("Image Sets")) {
            setCategory(CATEGORY_IMAGESET);
        } else if (category.equals("Cosplay")) {
            setCategory(CATEGORY_COSPLAY);
        } else if (category.equals("Asian Porn")) {
            setCategory(CATEGORY_ASIANPORN);
        } else {
            setCategory(CATEGORY_MISC);
        }
    }

    public int getCategoryString() {
        switch (getCategory()) {
            case CATEGORY_DOUJINSHI:
                return R.string.category_doujinshi;
            case CATEGORY_MANGA:
                return R.string.category_manga;
            case CATEGORY_ARTISTCG:
                return R.string.category_artistcg;
            case CATEGORY_GAMECG:
                return R.string.category_gamecg;
            case CATEGORY_WESTERN:
                return R.string.category_western;
            case CATEGORY_NON_H:
                return R.string.category_non_h;
            case CATEGORY_IMAGESET:
                return R.string.category_imageset;
            case CATEGORY_COSPLAY:
                return R.string.category_cosplay;
            case CATEGORY_ASIANPORN:
                return R.string.category_asianporn;
            default:
                return R.string.category_misc;
        }
    }

    public int getCategoryColor() {
        switch (getCategory()) {
            case CATEGORY_DOUJINSHI:
                return R.color.category_doujinshi;
            case CATEGORY_MANGA:
                return R.color.category_manga;
            case CATEGORY_ARTISTCG:
                return R.color.category_artistcg;
            case CATEGORY_GAMECG:
                return R.color.category_gamecg;
            case CATEGORY_WESTERN:
                return R.color.category_western;
            case CATEGORY_NON_H:
                return R.color.category_non_h;
            case CATEGORY_IMAGESET:
                return R.color.category_imageset;
            case CATEGORY_COSPLAY:
                return R.color.category_cosplay;
            case CATEGORY_ASIANPORN:
                return R.color.category_asianporn;
            default:
                return R.color.category_misc;
        }
    }

    public void setTags(String[] tags) {
        JsonArray arr = new JsonArray();

        for (String tag : tags) {
            arr.add(new JsonPrimitive(tag));
        }

        setTags(arr.toString());
    }

    public String[] getTagArray(){
        return API.getGson().fromJson(getTags(), String[].class);
    }

    public String getUrl(){
        return Constant.BASE_URL + String.format(Constant.GALLERY_URL, getId(), getToken());
    }

    public String[] getPreferredTitles(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean displayJapaneseTitle = preferences.getBoolean(context.getString(R.string.pref_japanese_title),
                context.getResources().getBoolean(R.bool.pref_japanese_title_default));
        String title = getTitle();
        String subtitle = getSubtitle();

        if (displayJapaneseTitle && !TextUtils.isEmpty(subtitle)) {
            title = getSubtitle();
            subtitle = getTitle();
        }

        String[] arr = {title, subtitle};

        return arr;
    }

    public void setDefaultFields(){
        setStarred(false);
        setProgress(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof GalleryBase){
            GalleryBase gallery = (GalleryBase) o;
            return getId().equals(gallery.getId());
        }

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return getId().intValue();
    }
}