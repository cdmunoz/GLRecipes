package co.cdmunoz.glrecipes.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    @SerializedName("id") val recipeId: Int,
    @SerializedName("title") val recipeTitle: String
) : Parcelable