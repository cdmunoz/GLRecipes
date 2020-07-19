package co.cdmunoz.glrecipes.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeDetails(
    @SerializedName("id") val recipeId: Int = -1,
    @SerializedName("title") val recipeTitle: String = "",
    @SerializedName("rating") val rating: Int = -1,
    @SerializedName("image") val imageUrl: String = "",
    @SerializedName("instructions") val instructions: String = ""
) : Parcelable