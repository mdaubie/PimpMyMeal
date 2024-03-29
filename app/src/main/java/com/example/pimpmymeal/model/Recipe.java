package com.example.pimpmymeal.model;

import android.graphics.Bitmap;

import com.example.pimpmymeal.services.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

// If a corresponding Ingredient object is implemented, it will need to be serializable as well
public class Recipe implements Serializable {
    public final String name;
    public final String edamamUri;
    public final String imageUri;
    private transient Bitmap image;
    public final String source;
    public final String sourceUri;
    public final String[] ingredients;
    public boolean liked = false;

    public Recipe(JSONObject jsonObject) throws JSONException {
        this(jsonObject, true);
    }

    public Recipe(JSONObject jsonObject, boolean lazyLoading) throws JSONException {
        name = jsonObject.getString("label");
        edamamUri = jsonObject.getString("uri");
        imageUri = jsonObject.getString("image");
        if (!lazyLoading) loadImage(); // Only load if used in UI
        source = jsonObject.getString("source");
        sourceUri = jsonObject.getString("url");
        JSONArray ingredientsJson = jsonObject.getJSONArray("ingredientLines");
        // Later on we might want to use the "ingredients" key instead
        // which gives ingredients as objects (so with more information on each one).
        ingredients = new String[ingredientsJson.length()];
        for (int i = 0; i < ingredientsJson.length(); i++)
            ingredients[i] = ingredientsJson.getString(i);
        // More info can be extracted from the json response.
    }

    public void loadImage() {
        image = Utilities.loadImage(imageUri);
    }

    public Bitmap getImage() {
        if (image == null) loadImage();
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return edamamUri.equals(recipe.edamamUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edamamUri);
    }
}
